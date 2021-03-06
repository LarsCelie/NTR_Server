package main.java.controller;

import java.util.List;

import org.hibernate.HibernateException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import main.java.dao.ResearchDao;
import main.java.domain.Research;
/**
 * Converts the raw data receiver from the corresponding DAO class to the format that is required by the rest service
 * 
 * @author Milamber
 *
 */
public class ResearchController {
	private ResearchDao dao = new ResearchDao();
	
	public List<Research> getAllAvailableResearches() {
		try {
			return dao.getAllAvailableResearches();
		} catch(HibernateException e) {
			return null;
		}
	}
	
	public List<Research> getAllResearches() {
		try {
			return dao.readAll();
		} catch(HibernateException e) {
			return null;
		}
	}

	public Research getResearch(int id) {
		try {
			return dao.read(id);
		} catch(HibernateException e) {
			return null;
		}
	}

	public boolean putResearch(JsonObject json) {
		Research research = new Gson().fromJson(json, Research.class);
		try {
			dao.create(research);
			return true;
		} catch(HibernateException e) {
			return false;
		}	
	}
}
