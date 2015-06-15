package main.java.controller;

import java.sql.*;
import java.util.ArrayList;

import main.java.domain.Answer;
import main.java.domain.Question;
import main.java.domain.Research;
import main.java.domain.Survey;

public class Controller {


	public void close(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// do nothing
			}
		}
	}
	
	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// do nothing
			}
		}
	}

	public ArrayList<Research> getAllResearches(boolean available) {
		// TODO ask the research object to ask the database object the data.
		//filter them with researches you can currently participate
		
		return null;
	}

	public Research getResearch(String id) {
		// TODO ask research object the specified research with this id
		return null;
	}
	
	
	public ArrayList<Answer> getAnswers(String Surveyid) throws SQLException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet resultQ = null;
		
		ArrayList<ResultSet> resultSetQuestions = new ArrayList<ResultSet>();
		ArrayList<Question> questions = new ArrayList<Question>();
		ArrayList<Answer> answers = new ArrayList<Answer>();
		
		try {
			conn = connect();
			
			// First get all Questions
			ps = conn.prepareStatement("SELECT * FROM QUESTION WHERE SURVEYID = ?");
			ps.setString(1, Surveyid);
			resultQ = ps.executeQuery();
			
			while (resultQ.next()) {
				Question question = new Question();
				question.setId(resultQ.getInt("ID"));
				questions.add(question);
			}
			
			ResultSet resultA = null;
			// Get all answers
			for(Question q : questions) {
				ps = conn.prepareStatement("SELECT * FROM ANSWER WHERE QUESTIONID = ?");
				ps.setLong(1, q.getId());
				resultA = ps.executeQuery();
				resultSetQuestions.add(resultA);
			}
			
			for(ResultSet r : resultSetQuestions) {
				while (r.next()) {
					Answer answer = new Answer();
					answer.setId(r.getInt("ID"));
					answer.setAnswer(r.getString("ANSWER"));
					answers.add(answer);
				}
				r.close();
			}
			
		} finally {
			close(ps);
			closeConnection(conn);
		}
		System.out.println(answers);
		return answers;
	}
	
	
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String serverName = "92.109.48.222";
			String portNumber = "1521";
			String sid = "xe";
			String url = "jdbc:oracle:thin:@"+serverName+":"+portNumber+":"+sid;
			String username = "NTR_database";
			String password = "NTR";
			con = DriverManager.getConnection(url,username,password);
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	public void closeConnection(Connection con) {
		try {
			if(con != null) con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public Survey getSurvey(String id) {
		// TODO get the Survey with specific id
		return null;
	}
}
