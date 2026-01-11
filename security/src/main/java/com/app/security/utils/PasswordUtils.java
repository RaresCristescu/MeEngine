package com.app.security.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordUtils {
	private static  PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder() ;
	

	public static boolean matches(String loginPassword, String dbPassword) {
		return passwordEncoder.matches(loginPassword, dbPassword);
	}
	
	public static String encode(String password) {
		return passwordEncoder.encode(password);
	}

}
