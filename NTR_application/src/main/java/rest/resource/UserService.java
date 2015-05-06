package main.java.rest.resource;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import main.java.controller.Controller;

@Path("/session")
public class UserService {
	
	private Controller controller = new Controller();
	
	@POST //Post so you can't see the information in the browser easily
	public Response authenticate(@QueryParam("username") String username, @QueryParam("password") String password){
		boolean authenticated = false;
		try {
			 authenticated = controller.authenticate(username, password);
		} catch (NoSuchAlgorithmException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (authenticated){
			return Response.status(200).entity("Success!").build();
		} else {
			return Response.status(401).entity("Username and/or password is incorrect").build();
		}
	}
	
	
	@POST
	@Path("/create")
	public Response createUser(@QueryParam("username") String username, @QueryParam("password") String password){
		boolean created = false;
		try {
			created = controller.createUser(username, password);
		} catch (NoSuchAlgorithmException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (created){
			return Response.status(200).entity("Success!").build();
		} else {
			return Response.status(500).entity("Something went wrong").build();
		}
	}
}
