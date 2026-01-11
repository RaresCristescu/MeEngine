package com.app.security.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {

	public static KeyPair generateEs512KeyPair() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp521r1"); // REQUIRED for ES512
		kpg.initialize(ecSpec, new SecureRandom());
		return kpg.generateKeyPair();
	}
	
	public static PrivateKey loadEs512PrivateKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(spec);
    }
}
