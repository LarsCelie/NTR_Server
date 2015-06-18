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

@Path("/research")
public class ResearchService {
	private ResearchController controller = new ResearchController();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allAvailableResearches(){
		List<Research> researches = controller.getAllAvailableResearches();
		
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
	public Response research(@PathParam("researchId") int id){
		Research research = controller.getResearch(id);
		if (research != null){
			String json = new Gson().toJson(research);
			return Response.status(200).entity(json).build();  //Return the research obj with surveys (not the questions yet)
		}  else {
			return Response.status(500).entity("Requested research resource is not available").build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("")
	public Response putResearch(JsonObject json) {
		boolean succes = controller.putResearch(json);
		if(succes) {
			return Response.status(200).entity("succes").build();
		} else {
			return Response.status(500).entity("Something went wrong").build();
		}
	}
}
