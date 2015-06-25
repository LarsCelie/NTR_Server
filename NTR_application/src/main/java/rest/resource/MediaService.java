package main.java.rest.resource;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import main.java.controller.MediaController;

@Path("/media")
public class MediaService {
	private MediaController controller = new MediaController();
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postMedia(@FormDataParam("file") InputStream file,
		@FormDataParam("file") FormDataContentDisposition fileDisposition) {
		boolean succes = controller.postMedia(file,fileDisposition);
		if(succes) {
			return Response.status(500).entity("succes").build();
		} else {
			return Response.status(500).entity("something went wrong").build();
		}		
	}	
}
