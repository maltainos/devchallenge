package com.ibi.challenge.ws.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class ChallengeUtils {
	
	private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	private final Random RANDOM = new SecureRandom();

	public String generateResourceId(int length) {
		return randomGeneratedID(length);
	}

	private String randomGeneratedID(int length) {
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < length; i++) 
			builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));

		return new String(builder);
	}
}
