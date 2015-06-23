package main.java.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.java.controller.ResearchController;
import main.java.domain.Research;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Class bundling all the rest services which primarily use the Research object.
 * 
 * @author Milamber
 *
 */
@Path("/research")
public class ResearchService {
	private ResearchController controller = new ResearchController();
	
	/**
	 * rest service that returns a Response object with all researches whom's start date is before and end date is after the current date.
	 * 
	 * @return a Response object with a Json string of all the available researches
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/available")
	public Response allAvailableResearches(){
		List<Research> researches = controller.getAllAvailableResearches();
		
		if (researches != null){
			if(!researches.isEmpty()) {
				String json = new Gson().toJson(researches);
				return Response.status(200).entity(json).build();
			} else {
				return Response.status(500).entity("No researches available").build();
			}			
		} else {
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	/**
	 * rest service that returns a Response object with all the researches
	 * 
	 * @return a Response object with a json string of all the researches
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allResearches() {
		List<Research> researches = controller.getAllResearches();
		
		if (researches != null){
			if(!researches.isEmpty()) {
				String json = new Gson().toJson(researches);
				return Response.status(200).entity(json).build();
			} else {
				return Response.status(500).entity("No researches available").build();
			}			
		} else {
			return Response.status(500).entity("Something went wrong").build();
		}
	}
	
	/**
	 * rest service that returns a Response object with a research that matches the id parameter.
	 * 
	 * @param id the id of the research object the user consumer wishes to see
	 * @return a Response object with a json string of the research with given id
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{researchId}")
	public Response research(@PathParam("researchId") int id){
		Research research = controller.getResearch(id);
		if (research != null){
			String json = new Gson().toJson(research);
			return Response.status(200).entity(json).build();
		}  else {
			return Response.status(500).entity("Requested research resource is not available").build();
		}
	}
	
	/**
	 * rest service that posts one Research object to the database
	 * 
	 * @param json contains the Research object to be posted to the database
	 * @return a Response object to let the caller know if the Research was successfully posted.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putResearch(JsonObject json) {
		boolean succes = controller.putResearch(json);
		if(succes) {
			return Response.status(200).entity("succes").build();
		} else {
			return Response.status(500).entity("Something went wrong").build();
		}
	}
}
