package main.java.rest.resource;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import main.java.controller.Controller;
import main.java.domain.Research;
import main.java.domain.Survey;

@Path("/Research")
public class ResearchService {
	
	private Controller controller = new Controller();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allResearches(@QueryParam("onlyAvailable") boolean available){
		ArrayList<Research> researches = controller.getAllResearches(available);
		
		if (researches != null && !researches.isEmpty()){
			String json = new Gson().toJson(researches);
			return Response.status(200).entity(json).build(); //get all researches, but not linked to surveys
		} else {
			return Response.status(500).entity("No researches available").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{researchId}")
	public Response research(@PathParam("researchId") String id){
		Research research = controller.getResearch(id);
		if (research != null){
			String json = new Gson().toJson(research);
			return Response.status(200).entity(json).build();  //Return the research obj with surveys (not the questions yet)
		}  else {
			return Response.status(500).entity("Requested research resource is not available").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/survey/{surveyId}")
	public Response getSurvey(@PathParam("surveyId") String id){
		Survey survey = controller.getSurvey(id);
		if (survey != null){
			String json = new Gson().toJson(survey);
			return Response.status(200).entity(json).build();
		} else {
			return Response.status(500).encoding("Requested survey resource is not available").build();
		}
	}
}
