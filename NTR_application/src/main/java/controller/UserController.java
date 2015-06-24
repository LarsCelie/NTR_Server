package main.java.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import main.java.dao.UserDao;
import main.java.domain.User;
/**
 * Converts the raw data receiver from the corresponding DAO class to the format that is required by the rest service
 * 
 * @author Milamber
 *
 */
public class UserController {
	private final static int ITERATION_NUMBER = 1000;
	
	/**
	 * Checks if the password and user name match
	 * 
	 * @param username the user that is attempting to log in
	 * @param password the password of the user that is attempting to log in
	 * @return
	 */
	public User authenticate(String username, String password) {
		boolean userExist = true;
		if (username == null || password == null) {
			userExist = false;
			username = "";
			password = "";
		}
		User user = new UserDao().getUserByUsername(username);
		//The encrypted password
		String digest;
		//The encryption key
		String salt;
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
		byte[] proposedDigest = null;
		try {
			proposedDigest = getHash(ITERATION_NUMBER, password, baseSalt);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		if (Arrays.equals(proposedDigest, baseDigest) && userExist){
			return user; 
		} else {
			return null;
		} 
	}
	/**
	    * Creates a salt and encodes the password of the user with this salt. 
	    * Then asks the DAO to add the user to the database.
	    * 
	    * @param login String The login of the user
	    * @param password String The password of the user
	    * @return boolean Returns true if the user was succesfully created
	    */
	public boolean createUser(User user) {
		if (user.getUsername() != null && user.getPassword() != null && user.getUsername().length() <= 100) {
			
			SecureRandom random;
			try {
				random = SecureRandom.getInstance("SHA1PRNG");
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
			} catch (NoSuchAlgorithmException e) {
				return false;
			}	
		} else {
			return false;
		}

	}

	/**
    * From a password, a number of iterations and a salt,
    * returns the corresponding digest
    * 
    * @param iterationNb int The number of iterations of the algorithm
    * @param password String The password to encrypt
    * @param salt byte[] The salt
    * @return byte[] The digested password
    * @throws NoSuchAlgorithmException If the algorithm doesn't exist
    */
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
