/**
 * 
 */
package co.phystech.aosorio.app;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.ResponseTransformer;

/**
 * @author AOSORIO
 *
 */
public class GeneralSvc {
	
	private final static Logger slf4jLogger = LoggerFactory.getLogger(GeneralSvc.class);
	
	public static String LOCAL_TMP_PATH;
	private static String LOCAL_TMP_DEFAULT = "tmp/";
	public static final String LOCAL_TMP_PATH_ENV = "LOCAL_TMP_PATH_ENV";
	
	public static String dataToJson(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			//mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, data);
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException("IOException from a StringWriter?");
		}
	}
	
	public static ResponseTransformer json() {

		return GeneralSvc::dataToJson;
	}

	public static String convertFileToString(File file) throws IOException{
        byte[] bytes = Files.readAllBytes(file.toPath());   
        return new String(Base64.getEncoder().encode(bytes));
    }
	
	public static void configTmpDir() throws Exception {

		ArrayList<String> localStorage = new ArrayList<String>();

		localStorage.add(LOCAL_TMP_PATH_ENV); // This is the preferred
		localStorage.add("TMP"); // second best - linux, windows
		localStorage.add("HOME"); // if previous fail, last chance

		Iterator<String> itrPath = localStorage.iterator();

		boolean found = false;

		while (itrPath.hasNext()) {
			String testPath = itrPath.next();
			String value = System.getenv(testPath);
			if (value != null) {
				LOCAL_TMP_PATH = value;
				slf4jLogger.info(LOCAL_TMP_PATH);
				found = true;
				break;
			}
		}

		if (!found) {
			LOCAL_TMP_PATH = LOCAL_TMP_DEFAULT;
			throw new Exception("LOCAL_TMP_PATH_ENV not defined!");
		}

	}
	
}
