package main.java.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import main.java.controller.QuestionController;
import main.java.domain.Question;

/**
 * Class bundling all the rest services which primarily use the Question object.
 * 
 * @author Milamber
 *
 */
@Path("/Question")
public class QuestionService {
	private QuestionController controller = new QuestionController();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{surveyId}")
	public Response getQuestionsBySurvey(@PathParam("surveyId")int id) {
		List<Question> questions = controller.getQuestionsBySurvey(id);
		if(questions != null) {
			String json = new Gson().toJson(questions);
			return Response.status(200).entity(json).build();
		} else {
			return Response.status(500).entity("something went wrong").build();
		}
	}
}
