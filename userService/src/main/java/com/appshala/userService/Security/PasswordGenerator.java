package com.appshala.userService.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*()";
    private static final String ALL = LOWERCASE + UPPERCASE + DIGITS + SPECIALS;
    private static final int PASSWORD_LENGTH = 12;
    private static final SecureRandom random = new SecureRandom();

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String generateRandomEncodedPassword() {
        // Generate password with all required character types
        List<Character> passwordChars = new ArrayList<>();
        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIALS.charAt(random.nextInt(SPECIALS.length())));
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            passwordChars.add(ALL.charAt(random.nextInt(ALL.length())));
        }
        Collections.shuffle(passwordChars, random);
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        // Encode the password with BCrypt
        return encoder.encode(password.toString());
    }
}
