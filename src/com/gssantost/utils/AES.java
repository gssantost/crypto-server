package com.gssantost.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	private Cipher cipher;
	private SecretKeySpec key;
	private IvParameterSpec ivspec;
	
	public AES(String keyFile, String ivFile)  {
		try {
			this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			this.key = Symmetric.loadSecretKey(keyFile);
			this.ivspec = Symmetric.loadIv(ivFile);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	public void encrypt(String inFile, String outFile) throws Exception {
		this.cipher.init(Cipher.ENCRYPT_MODE, this.key, this.ivspec);
		this.processFile(this.cipher, inFile, outFile);
	}
	
	public void decrypt(String inFile, String outFile) throws Exception {
		this.cipher.init(Cipher.DECRYPT_MODE, this.key, this.ivspec);
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
