package com.appshala.userService.ServcieImpl;

import com.appshala.userService.Client.GroupServiceClient;
import com.appshala.userService.Enum.Role;
import com.appshala.userService.Enum.SortDirection;
import com.appshala.userService.Enum.Status;
import com.appshala.userService.Enum.UserSortBy;
import com.appshala.userService.Model.User;
import com.appshala.userService.Payloads.UserCreationRequest;
import com.appshala.userService.Payloads.UserRequest;
import com.appshala.userService.Payloads.UserResponse;
import com.appshala.userService.Payloads.*;
import com.appshala.userService.Repository.UserRepository;
import com.appshala.userService.Service.UserService;
import com.appshala.userService.event.UserDeletedEvent;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final GroupServiceClient groupServiceClient;

    private final KafkaTemplate<String , UserDeletedEvent> kafkaTemplate;

    private final String userTopic;

    public UserServiceImpl(UserRepository userRepository , GroupServiceClient groupServiceClient , KafkaTemplate<String , UserDeletedEvent> kafkaTemplate,
                           @Value("${kafka-topic.user-events}") String userTopic)
    {
        this.userRepository = userRepository;
        this.groupServiceClient = groupServiceClient;
        this.kafkaTemplate = kafkaTemplate; // INJECTED
        this.userTopic = userTopic;
    }


    @Override
    public UserResponse createUser(UserCreationRequest userCreationRequest, UUID adminId) {
        User user = User.builder()
                .name(userCreationRequest.getName())
                .email(userCreationRequest.getEmail())
                .role(userCreationRequest.getRole())
                .status(userCreationRequest.getStatus())
                .createdBy(adminId)
                .updatedBy(adminId)
                .build();
        User savedUser = userRepository.save(user);

        return convertToUserResponse(savedUser);
    }

    private UserResponse convertToUserResponse(User savedUser) {
        return UserResponse.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .status(savedUser.getStatus())
                .build();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());

    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }


    public Page<UserResponse> findUsers(
            Role role,
            Status status,
            String userGroupName,
            UserSortBy sortBy,
            SortDirection sortDirection,
            int page,
            int size,
            UUID adminId
    ) {

        Sort.Direction direction = sortDirection.getDirection();

        Sort sort = Sort.by(direction, sortBy.getDbField());
        Pageable pageable = PageRequest.of(page, size, sort);

        List<UUID> memberUserIds = getMemberIdsByGroupName(userGroupName , adminId);
        if(memberUserIds == null || memberUserIds.isEmpty())
            return Page.empty(pageable);

        //  Execute Query with only Role and Status filters
        return userRepository.findAll(
                buildSpecification(role, status, memberUserIds ),
                pageable
        ).map(this::convertToUserResponse);
    }


    private Specification<User> buildSpecification(
            Role role,
            Status status,
            List<UUID> userIds) {
        return (root, query, criteriaBuilder) -> {
            // List to hold active filter conditions
            List<Predicate> predicates = new ArrayList<>();

            if (role != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (userIds != null && !userIds.isEmpty()) {
                predicates.add(root.get("id").in(userIds));
            }
            // Combine all active predicates with an AND logical operator
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getMemberIdsByGroupName(String groupName, UUID adminId) {

        if (groupName == null || groupName.isEmpty() || adminId == null) {
            return Collections.emptyList();
        }

        UUID groupId;
        List<UUID> memberUserIds = Collections.emptyList();

        try {
            groupId = groupServiceClient.getGroupIdByName(groupName, adminId);

            memberUserIds = groupServiceClient.getMemberUserIdsByGroupId(groupId);

        } catch (RuntimeException e) {

            throw new RuntimeException("Could not retrieve members for group '" + groupName + "'. Reason: " + e.getMessage(), e);
        }

        if (memberUserIds.isEmpty()) {
            return Collections.emptyList();
        }
        return memberUserIds;
    }

    @Override
    public UUID getCurrentAdminId(UUID adminID) {
        return null;
    }



    @Override
    public List<UserResponse> createUsers(List<UserCreationRequest> userCreationRequests, UUID adminId) {
        List<UserResponse> userResponses = new ArrayList<>();
        for(UserCreationRequest userCreationRequest : userCreationRequests)
        {
            User user =
            User.builder()
                    .name(userCreationRequest.getName())
                    .email(userCreationRequest.getEmail())
                    .role(userCreationRequest.getRole())
                    .createdBy(adminId)
                    .updatedBy(adminId)
                   .build();
            User savedUser = userRepository.save(user);
            userResponses.add(convertToUserResponse(savedUser));
        }
        return userResponses;
    }

    @Override
    public void deleteUserById(UUID id) {
        User user  = userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with ID :"+id));
        userRepository.delete(user);
        log.info("user deleted successfully from the database : ID {}",id);
        UserDeletedEvent event = new UserDeletedEvent(id, Instant.now().toString());
        kafkaTemplate.send(userTopic , id.toString() , event)
                .whenComplete((result , ex )->{
                    if(ex==null)
                    {
                        log.info("KAFKA : successfully published UserDeletedEvent for user ID {} to topic {}" , id , userTopic);
                    }else{
                        log.error("KAFKA ERROR : Failed to publish UserDeletedEvent for user ID {}: {}", id, ex.getMessage());
                    }
                });

    }

    @Override
    public UserResponse updateUserById(UUID id , UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("user not found with ID: "+id));
        if(userRequest.getName() != null)
            user.setName(userRequest.getName());

        if(userRequest.getStatus() != null)
            user.setStatus(userRequest.getStatus());
        User savedUser = userRepository.save(user);
        return convertToUserResponse(savedUser);
    }
    @Override
    public boolean checkUserExistsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public ImportResult processBulkImport(MultipartFile file, UUID adminId) throws Exception {
        List<UserCsvRecord> allRecords;

        try(Reader reader = new InputStreamReader(file.getInputStream()))
        {
            allRecords = new CsvToBeanBuilder<UserCsvRecord>(reader)
                    .withType(UserCsvRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        }
        catch(Exception e)
        {
            throw new Exception("Error parsing CSV file : " +e.getMessage());
        }

        List<UserCsvRecord> usersToProcess = new ArrayList<>();
        List<Map<String,String>> invalidEntries = new ArrayList<>();
        Set<String> emailsInCsv = new HashSet<>();

        AtomicInteger rowIndex = new AtomicInteger(1);
        for(UserCsvRecord record : allRecords)
        {
            Map<String,String> error = validateRecord(record, emailsInCsv , rowIndex.get());
            if(!error.isEmpty())
                invalidEntries.add(error);
            else {
                usersToProcess.add(record);
                emailsInCsv.add(record.getEmail().toLowerCase());
            }
            rowIndex.getAndIncrement();
        }
        if(!usersToProcess.isEmpty())
        {
            Set<String> uniqueEmails = usersToProcess.stream()
                    .map(r -> r.getEmail().toLowerCase())
                    .collect(Collectors.toSet());
            Set<String> existingEmails = userRepository.findExistingEmails(new ArrayList<>(uniqueEmails));
            usersToProcess.removeIf(record -> {
                if(existingEmails.contains(record.getEmail().toLowerCase()))
                {
                    invalidEntries.add(Map.of(
                            "Line",String.valueOf(rowIndex.getAndIncrement()),
                            "Email", record.getEmail(),
                            "Error" , "Email already exists in the system."
                    ));
                    return true;
                }
                return  false;
            });
        }
        if (!invalidEntries.isEmpty()) {
            return ImportResult.builder()
                    .status("partial success")
                    .message(invalidEntries.size()+"errors. Some essential fields are missing or duplicated. No users were created.")
                    .errorCount(invalidEntries.size())
                    .errorDetails(invalidEntries)
                    .build();
        }
        if(usersToProcess.isEmpty())
        {
            return ImportResult.builder()
                    .status("Failure")
                    .message("No valid users found to import.")
                    .errorCount(0)
                    .build();
        }
         return createUsersAndSendInvites(usersToProcess);
    }


    private Map<String, String> validateRecord(UserCsvRecord record, Set<String> emailsInCsv, int rowIndex) {
        List<String> errors = new ArrayList<>();
        String emailNormalized = record.getEmail() != null ? record.getEmail().trim().toLowerCase() : null;
        if (record.getName() == null || record.getName().isBlank() ||
                record.getRole() == null || record.getRole().isBlank() ||
                record.getEmail() == null || record.getEmail().isBlank()) {
            errors.add("One or more essential fields (Name, Role, Email) are missing.");
        }
        if (emailNormalized != null && emailsInCsv.contains(emailNormalized)) {
            errors.add("Email is duplicated within the uploaded file.");
        }
        if(errors.isEmpty())
            return Collections.emptyMap();
        else{
            return Map.of(
                    "Line",String.valueOf(rowIndex),
                    "Email" , record.getEmail() != null ? record.getEmail() : "MISSING",
                    "Error" , String.join("|" , errors)
            );
        }
    }


    @Transactional
    protected ImportResult createUsersAndSendInvites(List<UserCsvRecord> records) {
        List<User> newUsers = records.stream()
                .map(record -> {
                    String token = UUID.randomUUID().toString();
//                    sendInvitationEmail(record.getEmail(),record.getName(),token);
                    return User.builder()
                            .name(record.getName())
                            .role(Role.valueOf(record.getRole().toString()))
                            .email(record.getEmail().toLowerCase())
                            .status(Status.INVITED)
                            .build();

                }).collect(Collectors.toList());
        return ImportResult.builder()
                .status("Success")
                .message("Bulk Import completed. Invitation emails sent to "+ newUsers.size() + " users.")
                .build();

    }

//    private void sendInvitationEmail(String toEmail, String name, String token) 
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("no-reply@your-app.com");
//        message.setTo(toEmail);
//        message.setSubject("You've been invited to join!");
//
//        String inviteLink = "https://your-frontend.com/verify-invite?token=" + token;
//        message.setText(String.format("Hello %s,\n\nPlease click the link to set up your account: %s", name, inviteLink));
//
//        // This is where you would call emailSender.send(message) in a real app.
//        System.out.println("--- MOCK EMAIL SENT to " + toEmail + " with token: " + token);
//    }



}
