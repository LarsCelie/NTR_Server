package main.java.rest.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/image")
public class ImageRestService {
	
	@GET
	@Path("/{id}")
	public Response getImage(@PathParam("id") String id) {
		return encodeToBase64(id);
	}
	
	public Response encodeToBase64(String id) {
		String encoded = null;
		String location = "C:/"+id+".png";
		try {
			BufferedImage img = ImageIO.read(new File(location));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			baos.flush();
			encoded = Base64.getEncoder().encodeToString(baos.toByteArray());
			baos.close();
			return Response.status(200).entity(encoded).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Something went wrong fetching the image, the file might not exist").build();
		}

	}
}
