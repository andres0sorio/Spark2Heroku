package co.phystech.aosorio.app;

import static spark.Spark.*;

import java.io.IOException;

import co.phystech.aosorio.config.CorsFilter;
import co.phystech.aosorio.config.Routes;
import co.phystech.aosorio.controllers.DocGenerator;
import co.phystech.aosorio.controllers.FicheController;
import co.phystech.aosorio.services.GeneralSvc;

public class Main {
	
    public static void main(String[] args) {
    	
    	port(getHerokuAssignedPort());
    	
    	CorsFilter.apply();
    	
        get("/hello", (req, res) -> "Hello World");
        
        post(Routes.FICHES, FicheController::createFicheDocx, GeneralSvc.json());
        
        DocGenerator docgen = new DocGenerator();
        try {
			docgen.generate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
