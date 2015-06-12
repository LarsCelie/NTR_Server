package main.java.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import main.java.controller.AnswerController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("result")
public class ResultService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postResults(String json) {
		new AnswerController().postAnswers((JsonObject)new JsonParser().parse(json));
	}
}
