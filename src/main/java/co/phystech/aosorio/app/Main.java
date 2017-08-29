package co.phystech.aosorio.app;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		port(getHerokuAssignedPort());

		get("/hello", (req, res) -> "Hello World");

		slf4jLogger.info("Program is working fine");
		
		post("/fileupload/", DocumentSvc::uploadFile, GeneralSvc.json());

	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567;
	}

}
