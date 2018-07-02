package com.gssantost.sub;

import com.gssantost.utils.AES;
import com.gssantost.utils.RSA;

public class GenerateEncryptedTxt {
	
	// Generate encrypted files with AES and RSA keys given by the KeyMaster subprogram
	
	public static void main(String[] args) {
		AES aes = new AES("keys/aes/key.bin", "keys/aes/iv.bin");
		RSA rsa = new RSA("keys/rsa/public.pub");
		
		try {
			aes.encrypt("tests/hola.txt", "tests/hola.enc");
			rsa.encrypt("tests/secretmessage.txt", "tests/secretmessage.enc");
			System.out.println("Done!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fail!");
		}
		
	}

}
