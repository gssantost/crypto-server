package com.gssantost.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.gssantost.utils.AES;
import com.gssantost.utils.RSA;
import com.gssantost.utils.JMulter;

@MultipartConfig
public class DecryptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("resource")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		JSONObject obj = new JSONObject();
		
		Part part = req.getPart("file");;
		InputStream filecontent = part.getInputStream();
		OutputStream os = null;

		JMulter jmulter = new JMulter();
		jmulter.setPart(part);
		jmulter.setPath(System.getProperty("user.home") + "\\decryptedFiles");
		
		try {
			jmulter.upload(filecontent, os);
			obj.put("status", 200).put("response", "OK").put("msg", "Done. Your file has been uploaded!");
			System.out.println("Done. File Received.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String filePath = jmulter.getPath();
		
		AES aes = new AES("keys/aes/key.bin", "keys/aes/iv.bin");
		boolean uncrypted = false;
		
		try {
			aes.decrypt(filePath, "decrypted/decPublica.txt");
			System.out.println("Done!");
			obj.put("pd", "File decrypted with default public key");
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Imposible desencriptar con llave predeterminada. (NOT_SYMMETRIC).");
			uncrypted = true;
		}
		
		if (uncrypted) {
			RSA rsa = new RSA("keys/rsa/public.pub");
			Scanner sc = new Scanner(System.in);
		    System.out.println("Ingrese ruta de llave privada:  ");
		    String providedKey = sc.nextLine();
		    System.out.println("...Procesando");
		    
		    try {
				rsa.decrypt(filePath, "decrypted/decPrivada.txt", providedKey);
				System.out.println("Done!");
				obj.put("pd", "File decrypted with provided private key");
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println("Error");
				obj.put("pd", "File uploaded but not decrypted :(");
			}
		}
		
		jmulter.clear();
		out.print(obj);
	}
}
