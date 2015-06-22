package main.java.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.dao.QuestionDao;
import main.java.dao.ResearchDao;
import main.java.dao.SurveyDao;
import main.java.domain.Attachment;
import main.java.domain.Option;
import main.java.domain.Question;
import main.java.domain.Survey;
import main.java.util.Utility;

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
		/*
		 * 0: researchid 1: name 2: begin date 3: end date
		 * 
		 * 4+ = questions
		 */
		int researchid = multiPart.getBodyParts().get(0).getEntityAs(Integer.class);
		String name = multiPart.getBodyParts().get(1).getEntityAs(String.class);
		String begindate = multiPart.getBodyParts().get(2).getEntityAs(String.class);
		String enddate = multiPart.getBodyParts().get(3).getEntityAs(String.class);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setLenient(true);
		Date begin = null;
		Date end = null;
		try {
			begin = formatter.parse(begindate);
			end = formatter.parse(enddate);
		} catch (ParseException e) {
			// invalid date
			return false;
		}

		Survey survey = new Survey();
		survey.setBeginDate(begin);
		survey.setEndDate(end);
		survey.setName(name);
		survey.setResearch(new ResearchDao().load(researchid));
		new SurveyDao().create(survey);
		//create Survey so the ID can be accessed

		List<BodyPart> parts = multiPart.getBodyParts();
		Question q = null;
		int sequence = 0;
		List<Option> options = new ArrayList<Option>();
		List<Question> questions = new ArrayList<Question>();
		for (int i = 4; i < parts.size(); i++) {
			BodyPart part = parts.get(i);
			String contentName = part.getContentDisposition().getParameters().get("name");
			if (contentName.equals("questionIllustrationFile")) {

				sequence++;
				q = new Question();
				questions.add(q);
				q.setSequence(sequence);
				q.setSurvey(survey);

				String fileName = part.getContentDisposition().getFileName();
				System.out.println(fileName);
				if (!fileName.isEmpty()) {
					String type = null;
					String[] split = fileName.split("\\.");
					String extension = split[split.length-1];
					Utility util = Utility.getUtility();
					if (util.isAudio(extension)){
						type = "audio";
					} else if (util.isVideo(extension)){
						type = "video";
					} else if (util.isImage(extension)){
						type = "image";
					}
					if (type != null){
						InputStream fileStream = part.getEntityAs(FileInputStream.class);
						String newFileName = researchid + "_" + survey.getId() + "_" + sequence;
						boolean save = saveAsFile(fileStream, type, newFileName);
						if (save){
							Attachment a = new Attachment();
							a.setLocation(newFileName);
							a.setQuestion(q);
							a.setType(type);
						}
					} else {
						System.out.println("Type unsupported");
					}
				} else {
					System.out.println("No file attached, so not adding attachment to question");
				}
			} else if (contentName.equals("questionType")) {
				String type = part.getEntityAs(String.class);
				q.setType(type);
			} else if (contentName.equals("questionValue")) {
				String value = part.getEntityAs(String.class);
				q.setDescription(value);
			} else if (contentName.equals("questionOption")) {
				String option = part.getEntityAs(String.class);
				Option o = new Option();
				o.setQuestion(q);
				o.setValue(option);
				options.add(o);
			}
		}
		//save the questions
		QuestionDao qd = new QuestionDao();
		for (Question quest : questions) {
			qd.create(quest);
		}
		return true;
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
