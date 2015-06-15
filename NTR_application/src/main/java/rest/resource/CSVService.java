package main.java.rest.resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import main.java.controller.Controller;
import main.java.domain.Answer;

@Path("/CSVResults")
public class CSVService {
	
	private Controller controller = new Controller();
	
	private static final String FILE_PATH = "c:\\NTR\\result.csv";
	
	@GET
	@Path("/{surveyId}")
	//@Produces(MediaType.TEXT_PLAIN)
	@Produces("application/vnd.ms-excel")
	public Response getResults(@PathParam("surveyId") String id) throws SQLException {
		
		ArrayList<Answer> answers = controller.getAnswers(id);
		writeCSV(answers);
		
		File file = new File(FILE_PATH);
		 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=new-excel-file.csv");
		return response.build();
		
	}
	
	
	
	public void writeCSV(ArrayList<Answer> answers) {
		// Delimiter used in CSV file
		final String COMMA_DELIMITER = ";";
		final String NEW_LINE_SEPARATOR = "\n";
			
		// CSV file header
		final String FILE_HEADER = "id,answer";
		
		// Filewriter
		FileWriter fileWriter = null;
		try{
			
			fileWriter = new FileWriter("c:\\NTR\\result.csv");
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			for(Answer a : answers) {
				fileWriter.append(String.valueOf(a.getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(a.getAnswer()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				fileWriter.flush();
				fileWriter.close();
	
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
