package com.app.security.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;

public class JwtUtils {

	public static String generateToken(final String sessionId, final String apiSessionKey,
			final String privateKeyBase64) throws Exception {
		final Map<String, Object> calims = new HashMap<>();
		calims.put("session", sessionId);
		PrivateKey privateKey;

		try {
			privateKey = KeyUtils.loadEs512PrivateKey(privateKeyBase64);
			return Jwts.builder().issuer("MeEngine").subject(apiSessionKey).claims(calims).signWith(privateKey).compact();
		} catch (Exception e) {
			throw e;
		}
	}

	public static String extractSessionIdUnverified(String token) {
		try {
			String[] parts = token.split("\\.");
			if (parts.length != 3) {
				throw new IllegalArgumentException("Invalid JWT format");
			}

			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

			String key = "\"session\":\"";
			int start = payloadJson.indexOf(key);
			if (start == -1) {
				return null;
			}
			start += key.length();
			int end = payloadJson.indexOf("\"", start);
			if (end == -1) {
				return null;
			}

			return payloadJson.substring(start, end);

		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to extract sessionId", e);
		}
	}

	public static void validateToken(String token, String publicKeyBase64) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64);

		PublicKey publicKey = KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(keyBytes));

		Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token);
	}

}
