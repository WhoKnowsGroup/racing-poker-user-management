package au.com.suncoastpc.util;

import java.util.List;

import org.lightcouch.CouchDbClient;

import au.com.suncoastpc.util.types.CouchDatabase;

import com.google.gson.JsonObject;

public class DatabaseMigrationTest {
	public static void main(String[] args) {
		//copy the pokerace users over
		CouchDbClient pokerDb = CouchDBUtil.getClient(CouchDatabase.USERS);
		CouchDbClient suncoastDb = CouchDBUtil.getClient(CouchDatabase.SUNCOASTPC_USERS);	
		List<JsonObject> users = pokerDb.view("_all_docs").query(JsonObject.class);
		for(JsonObject meta : users) {
        	if (meta.has("id")) {
	        	JsonObject user = pokerDb.find(JsonObject.class, meta.get("id").getAsString());
	        	if (user.has("Password")) {
	        		JsonObject suncoastUser = suncoastDb.find(JsonObject.class, meta.get("id").getAsString());
	        		if (suncoastUser == null) {
	        			suncoastDb.save(user);
	        		}
	        		else {
	        			suncoastDb.update(user);
	        		}
	        	}
        	}
		}
		
		
		//migrate the user accounts inside of the test schema
        List<JsonObject> list = suncoastDb.view("_all_docs").query(JsonObject.class);
        
        /*for(JsonObject meta : list) {
        	if (meta.has("id")) {
	        	JsonObject user = suncoastDb.find(JsonObject.class, meta.get("id").getAsString());
	        	if (user.has("Password")) {
	        		try {
	        			String salt = StringUtilities.randomStringWithLengthBetween(12, 20);
		        		String password = user.get("Password").getAsString();		//FIXME:  should probably take the substring instead of relying upon replace(); otherwise will break for string with literal quotes in them
		        		String hashedPassword = CryptoUtil.computeHash(password, salt);
		        		
		        		user.addProperty("salt", salt);
		        		user.addProperty("hashedPassword", hashedPassword);
		        		user.remove("Password");
		        		
		        		suncoastDb.update(user);
	        		}
	        		catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        	}
        	}
        }*/
		
	}
}
