package main.java.rest.resource;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.java.controller.AnswerController;

/**
 * Class bundling all the rest services which primarily use the Answer object.
 * 
 * @author Milamber
 *
 */
@Path("/answer")
public class AnswerService {
	
	private AnswerController controller = new AnswerController();
	
	/**
	 * rest service that posts a set of Answers to the database
	 * 
	 * @param json contains a set of Answer objects to be posted to the database.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postAnswers(String json) {
		new AnswerController().postAnswers((JsonObject)new JsonParser().parse(json));
	}
	
	/**
	 * rest service which gives a CSV with results from a survey
	 * 
	 * @param id the id of the Survey for which the user wants the results
	 * @return a CSV file containing the results of a survey
	 */
	@GET
	@Path("/{surveyId}")
	@Produces("application/vnd.ms-excel")
	public Response getResults(@PathParam("surveyId") int id) {
		File file = controller.getCSV(id);
		if(file != null) {
			ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=new-excel-file.csv");
		return response.build();	
		} else {
			return Response.status(500).entity("something went wrong").build();
		}		
	}
}
