package main.java.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import main.java.controller.SurveyController;
import main.java.domain.Survey;

@Path("/survey")
public class SurveyService {
	private SurveyController controller = new SurveyController();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/research/{researchId}")
	public Response getAvailableSurveysByResearch(@PathParam("researchId") int id) {
		List<Survey> surveys = controller.getAvailableSurveysByResearch(id);
		if(surveys != null && !surveys.isEmpty()) {
			String json = new Gson().toJson(surveys);
			return Response.status(200).entity(json).build();			
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
			return Response.status(200).entity(json).build();
		} else {
			return Response.status(500).entity("something went wrong").build();
		}
	}
}
