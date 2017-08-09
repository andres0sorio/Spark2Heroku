package co.phystech.aosorio.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;

public class TestMethods {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(TestMethods.class);
	
	public static Object getProperties(Request pRequest, Response pResponse) { 
		
		pResponse.type("application/json");
		
		Properties prop = new Properties();
		OutputStream output = null;
		
		try {
			
			System.out.println(
					"class loader for this class: " + TestMethods.class.getClassLoader().getResource("MyLabels_en_US.properties"));
			
			output = new FileOutputStream("MyLabels_en_US.properties");
			slf4jLogger.info( prop.getProperty("how_are_you") );
			
			pResponse.status(200);
			return "OK";
			
		} catch(IOException io) {
			io.printStackTrace();
			pResponse.status(404);
			return "NOTOK";
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
					pResponse.status(404);
					return "NOTOK";
				}
			}
		}		
	}
	
}
