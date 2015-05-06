package main.java.rest.resource;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import main.java.controller.Controller;

@Path("/Research")
public class ResearchService {
	
	private Controller controller = new Controller();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response allResearches(@QueryParam("onlyAvailable") boolean available){
		ArrayList<Object> researches = controller.getAllResearches(available);
		
		if (researches != null && !researches.isEmpty()){
			JSONObject json = new JSONObject();
//			for (Research r : researches){
//				//TODO: map everything to the JSON object
//			}
			return Response.status(200).entity(json).build();
		} else {
			return Response.status(500).entity("No researches available").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response research(@PathParam("id") String id){
		Object research = controller.getResearch(id);
		if (research != null){
			JSONObject json = new JSONObject();
			//TODO: map research to json
			return Response.status(200).entity(json).build();
		}  else {
			return Response.status(500).entity("Requested resource is not available").build();
		}
	}
}
