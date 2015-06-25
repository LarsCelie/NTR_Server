package main.java.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public class MediaController {

	public void postMedia(InputStream file,
			FormDataContentDisposition fileDisposition) {
		saveAsFile(file,fileDisposition.getType(),fileDisposition.getFileName());
		
	}
	private boolean saveAsFile(InputStream inputStream, String type, String fileName) {
		try {
			OutputStream outStream = new FileOutputStream("C:/NTR/UPLOAD/"+type.toUpperCase() + "/" + fileName); //Save the file
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			System.out.println("File save success: "+fileName);
			outStream.flush();
			outStream.close();
			
			return true;
		} catch (IOException ioex) {
			System.out.println("Something went wrong when saving the file: "+fileName);
			ioex.printStackTrace();
			return false;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.out.println("Inputstream not closed");
				e.printStackTrace();
			}
		}
	}

}
