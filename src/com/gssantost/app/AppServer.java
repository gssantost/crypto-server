package com.gssantost.app;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class AppServer {
	
	public static void main(String[] args) throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		Service service = tomcat.getService();
		service.addConnector(getSslConnector());
		Context ctx = null;
		
		try {
			ctx = tomcat.addWebapp("/app", System.getProperty("user.dir") + "\\" + "www");
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		//Mappings
		Tomcat.addServlet(ctx, "DecryptServlet", new DecryptServlet());
		ctx.addServletMappingDecoded("/decrypt", "DecryptServlet");
		
		ctx.setAllowCasualMultipartParsing(true);
		
		tomcat.start();
		tomcat.getServer().await();
	}
	
	private static Connector getSslConnector() {
		Connector con = new Connector();
		con.setPort(9010);
		con.setSecure(true);
		con.setScheme("https");
		con.setMaxPostSize(600000);
		return con;
	}
	
}
