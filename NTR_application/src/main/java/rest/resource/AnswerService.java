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

@Path("/answer")
public class AnswerService {
	
	private AnswerController controller = new AnswerController();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postAnswers(String json) {
		new AnswerController().postAnswers((JsonObject)new JsonParser().parse(json));
	}
	
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
