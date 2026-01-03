package com.gnas.starter.accountservice.application.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public String generate() {
        return String.format("%03d-%s-%03d", randomDigits(3), randomLetters(3), randomDigits(3));
    }

    private int randomDigits(int count) {
        int bound = (int) Math.pow(10, count);
        return RANDOM.nextInt(bound);
    }

    private String randomLetters(int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(LETTERS[RANDOM.nextInt(LETTERS.length)]);
        }
        return sb.toString();
    }
}
