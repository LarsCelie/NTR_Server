package main.java.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import main.java.domain.Answer;
import main.java.domain.Question;
import main.java.domain.Research;
import main.java.domain.Survey;
import main.java.domain.User;

public class Controller {

	private final static int ITERATION_NUMBER = 1000;

	/*
	 * This way of authenticating was inspired by owasp;
	 * Hashing in Java
	 * https://www.owasp.org/index.php/Hashing_Java 
	 */
	public User authenticate(String username, String password) throws NoSuchAlgorithmException, SQLException {
		Connection con = null; //TODO: Reference a database connection
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = connect();
			boolean userExist = true;
			// INPUT VALIDATION
			if (username == null || password == null) {
				// TIME RESISTANT ATTACK
				// Computation time is equal to the time needed by a legitimate
				// user
				userExist = false;
				username = "";
				password = "";
			}
			User user = new User();
			ps = con.prepareStatement("SELECT * FROM NTR_USER WHERE USERNAME = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			String digest, salt;
			if (rs.next()) {
				digest = rs.getString("PASSWORD");
				salt = rs.getString("SALT");
				user.setEmail(rs.getString("EMAIL"));
				user.setFirstname(rs.getString("FIRST_NAME"));
				user.setLastname(rs.getString("LAST_NAME"));
				user.setUsername(rs.getString("USERNAME"));
				// DATABASE VALIDATION
				if (digest == null || salt == null) {
					throw new SQLException("Database inconsistant Salt or Digested Password altered");
				}
				if (rs.next()) { // Should not append, because login is the
									// primary key
					throw new SQLException("Database inconsistent two CREDENTIALS with the same LOGIN");
				}
			} else { // TIME RESISTANT ATTACK (Even if the user does not exist
						// the
				// Computation time is equal to the time needed for a legitimate
				// user
				digest = "000000000000000000000000000=";
				salt = "00000000000=";
				userExist = false;
			}
			

			byte[] baseDigest = Base64.getDecoder().decode(digest.getBytes(StandardCharsets.UTF_8));
			byte[] baseSalt = Base64.getDecoder().decode(salt.getBytes(StandardCharsets.UTF_8));

			// Compute the new DIGEST
			byte[] proposedDigest = getHash(ITERATION_NUMBER, password, baseSalt);

			if (Arrays.equals(proposedDigest, baseDigest) && userExist){
				return user; //TODO: Map database to object
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new SQLException("Database inconsistant Salt or Digested Password altered");
		} finally {
			close(rs);
			close(ps);
			closeConnection(con);
		}
	}

	public boolean createUser(User user) throws SQLException, NoSuchAlgorithmException {
		PreparedStatement ps = null;
		Connection con = null;
		try {
			if (user.getUsername() != null && user.getPassword() != null && user.getUsername().length() <= 100) {
				con = connect();
				// Uses a secure Random not a simple Random
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				// Salt generation 64 bits long
				byte[] bSalt = new byte[8];
				random.nextBytes(bSalt);
				// Digest computation
				byte[] bDigest = getHash(ITERATION_NUMBER, user.getPassword(), bSalt);
			
				String sDigest = Base64.getEncoder().encodeToString(bDigest);
				String sSalt = Base64.getEncoder().encodeToString(bSalt);
				
				
				ps = con.prepareStatement("INSERT INTO NTR_USER (USERNAME, PASSWORD, SALT, ID,FIRST_NAME,LAST_NAME,EMAIL) VALUES (?,?,?,NTR_USER_ID.nextval,?,?,?)");
				ps.setString(1, user.getUsername());
				ps.setString(2, sDigest);
				ps.setString(3, sSalt);
				ps.setString(4, user.getFirstname());
				ps.setString(5, user.getLastname());
				ps.setString(6, user.getEmail());
				ps.executeUpdate();
				
				return true;
			} else {
				return false;
			}
		} finally {
			close(ps);
			closeConnection(con);
		}
	}

	public byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		for (int i = 0; i < iterationNb; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}

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
		ResultSet resultA = null;
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
			
			// Get all answers
			for(Question q : questions) {
				ps = conn.prepareStatement("SELECT * FROM ANSWER WHERE QUESTIONID = ?");
				ps.setLong(1, q.getId());
			}
			resultA = ps.executeQuery();
			
			while (resultA.next()) {
				Answer answer = new Answer();
				answer.setId(resultA.getInt("ID"));
				answer.setAnswer(resultA.getString("ANSWER"));
				answers.add(answer);
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
			String serverName = "192.168.1.100";
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
}
