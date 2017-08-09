package co.phystech.aosorio.app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		port(getHerokuAssignedPort());

		get("/hello", (req, res) -> "Hello World");

		try {

			File dir = new File(".");
			File[] filesList = dir.listFiles();
			for (File file : filesList) {
				if (file.isFile()) {
					slf4jLogger.info(file.getName());
				}
			}
			slf4jLogger.info("Gresat work!");

		} catch (Exception e) {
			e.printStackTrace();
			slf4jLogger.info("Problem extracting Properties file");
		}

		System.out.println("class loader for HashMap: " + java.util.HashMap.class.getClassLoader());

		System.out.println("class loader for this class: " + Main.class.getClassLoader());

		System.out.println(
				"class loader for this class: " + Main.class.getClassLoader().getResource("MyLabels_en_US.properties"));

		get("/properties", TestMethods::getProperties);

		slf4jLogger.info("Program is working fine");

	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567;
	}

}
