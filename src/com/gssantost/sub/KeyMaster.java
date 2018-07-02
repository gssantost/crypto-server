package com.gssantost.sub;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

import com.gssantost.utils.Asymmetric;
import com.gssantost.utils.Symmetric;

public class KeyMaster {
	
	public static final String ivFile = "keys/aes/iv.bin";
	public static final String keyFile = "keys/aes/key.bin";
	public static final String keyPairFile = "keys/rsa/";
	
	// Generate both AES Symmetric Key and RSA Asymmetric Key Pair
	
	public static void main(String[] args) {
		
		//Generate AES SecretKey and Initialization Vector
		
		try (FileOutputStream out = new FileOutputStream(ivFile)) {
		    out.write(Symmetric.getIv());
		    System.out.println("Initialization Vector saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try (FileOutputStream out = new FileOutputStream(keyFile)) {
			SecretKey skey = Symmetric.getSecretKey();
			byte[] keyb = skey.getEncoded();
		    out.write(keyb);
		    System.out.println("Secret Key saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Generate RSA key pair
		
		KeyPair kp = null;
		try {
			kp = Asymmetric.getKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		try (FileOutputStream out = new FileOutputStream(keyPairFile + "private" + ".key")) {
		    out.write(kp.getPrivate().getEncoded());
		    System.out.println("Private Key saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileOutputStream out = new FileOutputStream(keyPairFile + "public" + ".pub")) {
		    out.write(kp.getPublic().getEncoded());
		    System.out.println("Public Key saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
