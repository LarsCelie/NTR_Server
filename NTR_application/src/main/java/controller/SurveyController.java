package main.java.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;

import main.java.dao.SurveyDao;
import main.java.domain.Survey;

public class SurveyController {
	public List<Survey> getAvailableSurveysByResearch(int id) {
		return new SurveyDao().getAvailableSurveysByResearch(id);
	}

	public Survey getSurveyById(int id) {
		return new SurveyDao().read(id);
	}

	public boolean postSurvey(final MultiPart multiPart) {
		/*	0: researchid
		 *  1: name
		 *  2: begin date
		 *  3: end date
		 *  
		 *  4+ = questions
		 */
		int researchid = multiPart.getBodyParts().get(0).getEntityAs(Integer.class);
		String name = multiPart.getBodyParts().get(1).getEntityAs(String.class);
		String begindate = multiPart.getBodyParts().get(2).getEntityAs(String.class);
		String enddate = multiPart.getBodyParts().get(3).getEntityAs(String.class);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setLenient(true);
		Date begin = null;
		Date end = null;
		try{
			begin = formatter.parse(begindate);
			end = formatter.parse(enddate);
		} catch(ParseException e){
			//invalid date
			return false;
		}
		
		Survey survey = new Survey();
		survey.setBeginDate(begin);
		survey.setEndDate(end);
		survey.setName(name);
		
		
		
		for (BodyPart part : multiPart.getBodyParts()){
			System.out.println(part.getEntity().toString());
			System.out.println(part.getHeaders().toString());
			System.out.println(part.getContentDisposition().toString());
		}
		
		return true;
	}
	
	private boolean fileHandler(File file){
		
		return false;
	}

}
