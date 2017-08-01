/**
 * 
 */
package co.phystech.aosorio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.phystech.aosorio.config.Constants;
import co.phystech.aosorio.models.BackendMessage;
import co.phystech.aosorio.models.NewFichePayload;
import spark.Request;
import spark.Response;

/**
 * @author AOSORIO
 *
 */
public class FicheController {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(FicheController.class);

	public static Object createFicheDocx(Request pRequest, Response pResponse) {

		BackendMessage returnMessage = new BackendMessage();

		pResponse.type("application/json");

		try {

			ObjectMapper mapper = new ObjectMapper();

			NewFichePayload inputFiche = mapper.readValue(pRequest.body(), NewFichePayload.class);

			DocGenerator docxGen = new DocGenerator(inputFiche);
			docxGen.generate();
		
			pResponse.status(200);

			return returnMessage.getOkMessage(String.valueOf(0));

		} catch (IOException jpe) {
			slf4jLogger.debug("Problem adding fiche");
			pResponse.status(Constants.HTTP_BAD_REQUEST);
			return returnMessage.getNotOkMessage("Problem adding fiche");
		}

	}

}
