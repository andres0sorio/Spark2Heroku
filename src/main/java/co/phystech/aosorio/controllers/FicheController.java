/**
 * 
 */
package co.phystech.aosorio.controllers;

//import java.io.IOException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import com.fasterxml.jackson.databind.ObjectMapper;

//import co.phystech.aosorio.config.Constants;
import co.phystech.aosorio.models.BackendMessage;
import spark.Request;
import spark.Response;

/**
 * @author AOSORIO
 *
 */
public class FicheController {

	//private final static Logger slf4jLogger = LoggerFactory.getLogger(FicheController.class);

	public static Object createFicheDocx(Request pRequest, Response pResponse) {

		BackendMessage returnMessage = new BackendMessage();

		pResponse.type("application/json");

	//	try {


			pResponse.status(200);

			return returnMessage.getOkMessage(String.valueOf(0));
/*
		} catch (IOException jpe) {
			slf4jLogger.debug("Problem adding fiche");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Problem adding fiche");
		}*/

	}

}
