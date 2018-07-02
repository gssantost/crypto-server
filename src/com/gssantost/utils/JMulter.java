package com.gssantost.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

public class JMulter {
	
	private Part part;
	private String path;
	
	public JMulter() {}
	
	public void upload(InputStream in, OutputStream out) throws IOException {
		String fileName = this.getFileName(this.part);
		try { 
			this.path = this.path + "\\" + fileName;
			out = new FileOutputStream(this.path);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	
	public String getFileName(Part file) {
		for (String content :  file.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void clear() {
		this.part = null;
		this.path = "";
	}

}
