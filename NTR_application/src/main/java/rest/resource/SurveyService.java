package main.java.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;

import com.google.gson.Gson;

import main.java.controller.SurveyController;
import main.java.domain.Survey;

@Path("/survey")
public class SurveyService {
	private SurveyController controller = new SurveyController();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSurveys() {
		List<Survey> surveys = controller.getSurveys();
		if(surveys != null && !surveys.isEmpty()) {
			String json = new Gson().toJson(surveys);
			return Response.status(200).entity(json).build();
		} else {
			return Response.status(500).entity("soemthing went wrong").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/research/{researchId}")
	public Response getAvailableSurveysByResearch(@PathParam("researchId") int id) {
		List<Survey> surveys = controller.getAvailableSurveysByResearch(id);
		if(surveys != null && !surveys.isEmpty()) {
			String json = new Gson().toJson(surveys);
			return Response.ok(json).build();			
		} else {
			return Response.status(500).entity("soemthing went wrong").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{surveyId}")
	public Response getSurveyById(@PathParam("surveyId") int id) {
		Survey survey = controller.getSurveyById(id);
		if(survey != null) {
			String json = new Gson().toJson(survey);
			return Response.ok(json).build();
		} else {
			return Response.status(500).entity("something went wrong").build();
		}
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postSurvey(final MultiPart multiPart){
		boolean success = controller.postSurvey(multiPart);
		if (success) {
			return Response.ok("Survey is gemaakt").build();
		} else {
			return Response.serverError().build();
		}
	}
}
