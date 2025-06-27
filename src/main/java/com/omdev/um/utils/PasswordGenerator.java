package com.omdev.um.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;

    private static Random random = new Random();
    
    private PasswordGenerator() { }
    
    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

}
