package com.gssantost.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSA {
	
	private Cipher cipher;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public RSA(String publicKeyFile) {
		try {
			this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			this.publicKey = Asymmetric.loadPublicKey(publicKeyFile);
			this.privateKey = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void encrypt(String inFile, String outFile) throws Exception {
		this.cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		this.processFile(this.cipher, inFile, outFile);
	}
	
	public void decrypt(String inFile, String outFile, String pkFile) throws Exception {
		this.privateKey = Asymmetric.loadPrivateKey(pkFile);
		this.cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		this.processFile(this.cipher, inFile, outFile);
	}
	
	private void processFile(Cipher cipher, String inFile, String outFile) throws Exception {
		try (FileInputStream in = new FileInputStream(inFile);
			 FileOutputStream out = new FileOutputStream(outFile)) {
			byte[] inputBuf = new byte[1024];
			int len;
			while ((len = in.read(inputBuf)) != -1) {
				byte[] outputBuf = cipher.update(inputBuf, 0, len);
				if ( outputBuf != null ) {
					out.write(outputBuf);
				}
			}
			byte[] outputBuf = cipher.doFinal();
            if ( outputBuf != null ) {
            	out.write(outputBuf);
            }
		}
	}

}
