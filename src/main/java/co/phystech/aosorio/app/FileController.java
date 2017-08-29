/** Copyright or License
 *
 */

package co.phystech.aosorio.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import spark.Request;
import spark.Response;

/**
 * Package: co.phystech.aosorio.app
 * Class: FileController FileController.java
 * Original Author: @author AOSORIO
 * Description: Class implementing the document/file service
 * Implementation:
 * Created: 28/08/2017
 */

public class FileController {

	static Logger logger = LogManager.getRootLogger();
		
	public static Object uploadFile(Request pRequest, Response pResponse) {

		File uploadDir = null;
		long timestamp = System.currentTimeMillis();
		
		pResponse.header("Access-Control-Allow-Origin", "*");
		
		try {
	
			GeneralSvc.configTmpDir(); //capture LOCAL_TMP_PATH from environment
			uploadDir = new File(GeneralSvc.LOCAL_TMP_PATH);
			logger.info("LOCAL_TMP_PATH=" + GeneralSvc.LOCAL_TMP_PATH);

		} catch (Exception e) {
	
			uploadDir = new File(GeneralSvc.LOCAL_TMP_PATH);
			uploadDir.mkdir();
			logger.info("LOCAL_TMP_PATH set to default value");
			logger.error(e.getMessage());
		}
		
		pRequest.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(GeneralSvc.LOCAL_TMP_PATH));

		try {

			Collection<Part> parts;
			parts = pRequest.raw().getParts();
			Part fPart = parts.iterator().next();

			InputStream input = fPart.getInputStream();

			String fileExt = null;

			try {
				logger.info(fPart.getSubmittedFileName());
				fileExt = fPart.getSubmittedFileName().split(".")[1];
			} catch (Exception ioex) {
				fileExt = ".tmp";
			}

			Path tempFile = Files.createTempFile(uploadDir.toPath(), String.valueOf(timestamp), fileExt);

			Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			
			logger.info(tempFile.toString());
			
		} catch (IOException | ServletException e) {
			e.printStackTrace();
			return "NOTOK";
		}
		
		return "OK";

	}

	
	public static Object listFiles(Request pRequest, Response pResponse) {

		ArrayList<String> files;
		
		pResponse.header("Access-Control-Allow-Origin", "*");
		
		try {

			GeneralSvc.configTmpDir();
			files = GeneralSvc.listDownloadedFiles(GeneralSvc.LOCAL_TMP_PATH);
			Iterator<String> itrFiles = files.iterator();
			
			while (itrFiles.hasNext()) {
				
				String fileName = itrFiles.next();
				logger.info(fileName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "NOTOK";		
		}

		return files;

	} 
	

}
