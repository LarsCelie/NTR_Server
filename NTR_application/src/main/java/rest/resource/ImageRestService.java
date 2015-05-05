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

@Path("/image")
public class ImageRestService {
	
	@GET
	@Path("/{id}")
	public String getImage(@PathParam("id") String id) {
		String imageEncoded = null;
		imageEncoded = encodeToBase64(id);
		
		return imageEncoded;
	}
	
	public String encodeToBase64(String id) {
		String response = null;
		String location = "C:/"+id+".png";
		try {
			BufferedImage img = ImageIO.read(new File(location));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			baos.flush();
			response = Base64.getEncoder().encodeToString(baos.toByteArray());
			baos.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return response;
	}
}
