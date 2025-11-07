package com.appshala.userService.ServcieImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.frontend.invite-url}")
    private String frontendInviteUrl;

    public MailService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public void sendInvitationEmail(String toEmail , String userName , String token)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        final String inviteLink = frontendInviteUrl + token;

        message.setFrom(senderEmail);
        message.setTo(toEmail);
        message.setSubject("You've Been Invited to Join the App Shala Platform!");

        String emailBody = String.format(
                "Hello %s,\n\n" +
                        "You have been invited to join our platform. Please use the link below to set up your account and password:\n\n" +
                        "%s\n\n" +
                        "If you have any questions, please contact support.",
                userName,
                inviteLink
        );
        message.setText(emailBody);

        try{
            mailSender.send(message);
            log.info("Successfully sent invitation email to : {}",toEmail);
        }
        catch (Exception e)
        {
            log.error("Failed to send invitation email to : {}",toEmail);
        }
    }
}
