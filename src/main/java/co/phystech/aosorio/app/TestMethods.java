package co.phystech.aosorio.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
		InputStream input = null;
				
		try {
			
			System.out.println(
					"class loader for this class: " + TestMethods.class.getClassLoader().getResource("MyLabels_en_US.properties"));
			
			input = TestMethods.class.getClassLoader().getResource("MyLabels_en_US.properties").openStream();
			
			//input = new FileInputStream("MyLabels_en_US.properties");
			prop.load(input);
						
			slf4jLogger.info( prop.getProperty("how_are_you") );
			
			pResponse.status(200);
			return "OK";
			
		} catch(IOException io) {
			io.printStackTrace();
			pResponse.status(404);
			return "NOTOK";
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					pResponse.status(404);
					return "NOTOK";
				}
			}
		}		
	}
	
}
