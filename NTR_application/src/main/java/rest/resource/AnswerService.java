package main.java.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.java.controller.AnswerController;

@Path("/answer")
public class AnswerService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postAnswers(String json) {
		new AnswerController().postAnswers((JsonObject)new JsonParser().parse(json));
	}
}
