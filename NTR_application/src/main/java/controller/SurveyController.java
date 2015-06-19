package main.java.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.dao.QuestionDao;
import main.java.dao.ResearchDao;
import main.java.dao.SurveyDao;
import main.java.domain.Option;
import main.java.domain.Question;
import main.java.domain.Survey;
import oracle.net.aso.q;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;

public class SurveyController {
	public List<Survey> getAvailableSurveysByResearch(int id) {
		return new SurveyDao().getAvailableSurveysByResearch(id);
	}

	public Survey getSurveyById(int id) {
		return new SurveyDao().read(id);
	}

	public List<Survey> getSurveys() {
		return new SurveyDao().readAll();
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
		survey.setResearch(new ResearchDao().load(researchid));
		
		
		List<BodyPart> parts = multiPart.getBodyParts();
		Question q = null;
		int sequence = 0;
		List<Option> options = new ArrayList<Option>();
		List<Question> questions = new ArrayList<Question>();
		for (int i = 4; i < parts.size(); i++){
			BodyPart part = parts.get(i);
			String contentName = part.getContentDisposition().getParameters().get("name");
			if (contentName.equals("questionIllustrationFile")){
				
				sequence++;
				q = new Question();
				questions.add(q);
				q.setSequence(sequence);
				q.setSurvey(survey);
				
				String fileName = part.getContentDisposition().getFileName();	
				if (!fileName.isEmpty()){
					System.out.println(fileName);
				} else {
					System.out.println("No file!");
				}
			} else if (contentName.equals("questionType")) {
				String type = part.getEntityAs(String.class);
				q.setType(type);
			} else if (contentName.equals("questionValue")){
				String value = part.getEntityAs(String.class);
				q.setDescription(value);
			} else if (contentName.equals("questionOption")){
				String option = part.getEntityAs(String.class);
				Option o = new Option();
				o.setQuestion(q);
				o.setValue(option);
				options.add(o);
			}
			//questionIllustrationFile
			//questionType
			//questionValue
			//questionOption
//			String x = part.getContentDisposition().toString();
//			System.out.println(x);
		}
		
		new SurveyDao().create(survey);
		QuestionDao qd = new QuestionDao();
		for (Question quest : questions){
			qd.create(quest);
		}
		return true;
	}
	
	private boolean fileHandler(File file){
		
		return false;
	}

}
