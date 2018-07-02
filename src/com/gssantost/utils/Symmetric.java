package com.gssantost.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@SuppressWarnings("unused")
public class Symmetric {
	
	public static byte[] getIv() {
		SecureRandom srandom = new SecureRandom();
		byte[] iv = new byte[128/8];
		srandom.nextBytes(iv);
		return iv;
	}
	
	public static SecretKey getSecretKey() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		return kgen.generateKey();
	}
	
	public static IvParameterSpec loadIv(String ivFile) {
		byte[] iv = null;
		
		try {
			iv = Files.readAllBytes(Paths.get(ivFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new IvParameterSpec(iv);
	}
	
	public static SecretKeySpec loadSecretKey(String keyFile) {
		byte[] keyb = null;
		
		try {
			keyb = Files.readAllBytes(Paths.get(keyFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new SecretKeySpec(keyb, "AES");
	}

}
