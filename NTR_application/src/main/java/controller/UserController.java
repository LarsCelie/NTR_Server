package main.java.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import main.java.dao.UserDao;
import main.java.domain.User;

public class UserController {
	private final static int ITERATION_NUMBER = 1000;

	public User authenticate(String username, String password) throws NoSuchAlgorithmException {
		boolean userExist = true;
		if (username == null || password == null) {
			userExist = false;
			username = "";
			password = "";
		}
		User user = new User();
		UserDao dao = new UserDao();
		user = dao.getUserByUsername(username);
		String digest,salt;
		if(user != null) {
			digest = user.getPassword();
			salt = user.getSalt();
			
		} else {
			digest = "000000000000000000000000000=";
			salt = "00000000000=";
			userExist = false;
		}

		byte[] baseDigest = Base64.getDecoder().decode(digest.getBytes(StandardCharsets.UTF_8));
		byte[] baseSalt = Base64.getDecoder().decode(salt.getBytes(StandardCharsets.UTF_8));
		byte[] proposedDigest = getHash(ITERATION_NUMBER, password, baseSalt);

		if (Arrays.equals(proposedDigest, baseDigest) && userExist){
			return user; 
		} else {
			return null;
		} 
	}

	public boolean createUser(User user) throws NoSuchAlgorithmException {
		if (user.getUsername() != null && user.getPassword() != null && user.getUsername().length() <= 100) {
			
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			// Salt generation 64 bits long
			byte[] bSalt = new byte[8];
			random.nextBytes(bSalt);
			// Digest computation
			byte[] bDigest = getHash(ITERATION_NUMBER, user.getPassword(), bSalt);
		
			String sDigest = Base64.getEncoder().encodeToString(bDigest);
			String sSalt = Base64.getEncoder().encodeToString(bSalt);

			user.setPassword(sDigest);
			user.setSalt(sSalt);
			UserDao dao = new UserDao();
			dao.create(user);	
			return true;
		} else {
			return false;
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
}
