package main.java.controller;

import java.util.List;

import main.java.dao.SurveyDao;
import main.java.domain.Survey;

public class SurveyController {
	public List<Survey> getAvailableSurveysByResearch(int id) {
		return new SurveyDao().getAvailableSurveysByResearch(id);
	}

	public Survey getSurveyById(int id) {
		return new SurveyDao().read(id);
	}

}
