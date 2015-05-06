package main.java.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import main.java.domain.Answer;

@Path("result")
public class ResultService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postResults(Answer[] answers) {
		
	}
}
