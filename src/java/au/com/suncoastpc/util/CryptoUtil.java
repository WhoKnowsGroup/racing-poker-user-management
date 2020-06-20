package au.com.suncoastpc.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.google.gson.JsonObject;
import com.pokerace.ejb.model.User;

import au.com.suncoastpc.auth.util.Base64;
import au.com.suncoastpc.auth.util.StringUtilities;

public class CryptoUtil {
	private static final Logger LOG = Logger.getLogger(CryptoUtil.class.getName());
	
	public static String computeHash(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 2048, 160);
		SecretKeyFactory fact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		return Base64.encodeBytes(fact.generateSecret(spec).getEncoded());
	}
	
	public static boolean authenticateUser(User user, String password) {
		if (! StringUtilities.isEmpty(user.getSalt()) && ! StringUtilities.isEmpty(user.getHashedPassword())) {
			try {
	        	String salt = user.getSalt();
	        	String hashedPassword = computeHash(password, salt);
	        	
	        	return user.getHashedPassword().equals(hashedPassword);
			}
			catch (Exception e) {
				LOG.log(Level.WARNING, "Unexpected exception when trying to authenticate; user=" + user.getEmail() + ", ex=" + e.getMessage(), e);
				return false;
			}
        }
        
		LOG.log(Level.WARNING, "Performing an insecure/legacy authentication attempt for user with email=" + user.getEmail());
        	
		JsonObject jsonUser = user.toGson();
        String user_password = jsonUser.has("Password") ? jsonUser.get("Password").getAsString() : null;
        return user_password != null && user_password.equals(password);
	}
	
	@Deprecated
	//prefer using the 'User' object over raw json/gson objects
	public static boolean authenticateUser(JsonObject user, String password) {
		if (user == null || password == null) {
			return false;
		}
		
		return authenticateUser(new User(user), password);
	}
}
