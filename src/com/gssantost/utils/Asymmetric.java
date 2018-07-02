package com.gssantost.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Asymmetric {
	
	public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		return kpg.generateKeyPair();
	}
	
	public static PublicKey loadPublicKey(String path) throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));   
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    return kf.generatePublic(new X509EncodedKeySpec(keyBytes));
	}
	
	public static PrivateKey loadPrivateKey(String path) throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));   
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    return kf.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
	}

}
