package main.java.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.java.controller.UserController;
import main.java.domain.User;

import com.google.gson.Gson;


@Path("/session")
public class UserService {
	
	private UserController controller = new UserController();
	
	@POST //Post so you can't see the information in the browser history easily
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(@FormParam("username") String username, @FormParam("password") String password){
		User user = controller.authenticate(username, password);
		if (user != null){
			String json = new Gson().toJson(user);
			return Response.status(200).entity(json).build();
		} else {
			return Response.status(401).entity("Username and/or password is incorrect").build();
		}
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(String json){
		boolean created = false;

			User user = new Gson().fromJson(json, User.class);
			created = controller.createUser(user);
	
		if (created){
			return Response.status(200).entity("Success!").build();
		} else {
			return Response.status(500).entity("Something went wrong").build();
		}
	}
}
