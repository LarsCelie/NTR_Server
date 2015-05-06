package main.java.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import main.java.domain.Research;

public class Controller {

	private final static int ITERATION_NUMBER = 1000;

	/*
	 * This way of authenticating was inspired by owasp;
	 * Hashing in Java
	 * https://www.owasp.org/index.php/Hashing_Java 
	 */
	public boolean authenticate(String username, String password) throws NoSuchAlgorithmException, SQLException {
		Connection con = null; //TODO: Reference a database connection
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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

			ps = con.prepareStatement("SELECT PASSWORD, SALT FROM CREDENTIAL WHERE LOGIN = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			String digest, salt;
			if (rs.next()) {
				digest = rs.getString("PASSWORD");
				salt = rs.getString("SALT");
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

			return Arrays.equals(proposedDigest, baseDigest) && userExist;
		} catch (SQLException ex) {
			throw new SQLException("Database inconsistant Salt or Digested Password altered");
		} finally {
			close(rs);
			close(ps);
		}
	}

	public boolean createUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
		PreparedStatement ps = null;
		try {
			if (username != null && password != null && username.length() <= 100) {
				Connection con = null; //TODO: Reference a database connection
				// Uses a secure Random not a simple Random
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				// Salt generation 64 bits long
				byte[] bSalt = new byte[8];
				random.nextBytes(bSalt);
				// Digest computation
				byte[] bDigest = getHash(ITERATION_NUMBER, password, bSalt);
			
				String sDigest = Base64.getEncoder().encodeToString(bDigest);
				String sSalt = Base64.getEncoder().encodeToString(bSalt);

				ps = con.prepareStatement("INSERT INTO CREDENTIAL (LOGIN, PASSWORD, SALT) VALUES (?,?,?)");
				ps.setString(1, username);
				ps.setString(2, sDigest);
				ps.setString(3, sSalt);
				ps.executeUpdate();
				return true;
			} else {
				return false;
			}
		} finally {
			close(ps);
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
		return null;
	}

	public Research getResearch(String id) {
		// TODO ask research object the specified research with this id
		return null;
	}
}
