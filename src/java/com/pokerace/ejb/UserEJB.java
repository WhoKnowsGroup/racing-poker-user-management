package com.pokerace.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;

import au.com.suncoastpc.auth.util.StringUtilities;
import au.com.suncoastpc.util.CouchDBUtil;
import au.com.suncoastpc.util.CryptoUtil;
import au.com.suncoastpc.util.types.CouchDatabase;

import com.google.gson.JsonObject;
import com.pokerace.ejb.iface.UserManager;
import com.pokerace.ejb.model.User;

@Stateless
@LocalBean
public class UserEJB implements UserManager {
	@Override
	public User getUserDetails(String email) {  //was user_detail()
        CouchDbClient db = CouchDBUtil.getClient(CouchDatabase.USERS);
        
        try {
        	JsonObject json = db.find(JsonObject.class,email);
        	if (json != null) {
        		return new User(json);		//this used to return the user's CSV representation
        	}
        }
        catch(Exception e) {
            //expected; Couch will throw if user doesn't exist
        	//e.printStackTrace();
        }
        return null;
	}

	@Override
	public User authenticate(String email, String password, String userAgent) {		//was user_find()
		CouchDbClient db = CouchDBUtil.getClient(CouchDatabase.USERS);			
        try {
	        JsonObject json = db.find(JsonObject.class, email);
	        boolean successfulLogin = CryptoUtil.authenticateUser(new User(json), password);
	        if (successfulLogin && userAgent != null) {
		        String revision = json.get("_rev").toString().replace("\"","");
	        	
		        //FIXME:  should probably set/update these against the user object
	        	json.addProperty("Status", "active");
		        json.addProperty("_rev",revision);
		        json.addProperty("Last_login", new Date().toString());
		        json.addProperty("Last_login_timestamp", new Date().getTime());
		        json.addProperty("userAgent", userAgent);
	        	
		        db.update(json);
	        }
	        if (successfulLogin) {
	        	return new User(json);		//used to return the user type
	        }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
	}

	@Override
	public boolean register(User user) {	//was user_registeration()
		if (user == null || StringUtilities.isEmpty(user.getEmail()) || StringUtilities.isEmpty(user.getHashedPassword())) {
			return false;
		}
		
		try {
			return this.insertUser(user); 
		}		
        catch(Exception e){
        	e.printStackTrace();
        }
		
		return false;
	}
	
	@Override
	public boolean update(User user) {
		if (user == null || StringUtilities.isEmpty(user.getEmail()) || StringUtilities.isEmpty(user.getHashedPassword())) {
			return false;
		}
		
		try {
			CouchDbClient db = CouchDBUtil.getClient(CouchDatabase.USERS);
	        JsonObject existingUser = db.find(JsonObject.class, user.getEmail());
	        if (existingUser != null) {
	        	return StringUtilities.isEmpty(db.update(user.toGson()).getError());
	        }
		}		
        catch(Exception e){
        	e.printStackTrace();
        }
		
		return false;
	}

	@SuppressWarnings("unchecked")
	protected synchronized boolean insertUser(User user) {
        try {
        	User existingUser = getUserDetails(user.getEmail());
    		if (existingUser != null) {
    			return false;
    		}
        	
        	JSONObject obj = user.toJson();
        	CouchDbClient db = CouchDBUtil.getClient(CouchDatabase.USERS);			
        
        	//FIXME:  this is probably a very poor/inefficient way of assigning user-ids; also may collide if the EJB is running on multiple hosts concurrently
	        List<JsonObject > list = db.view("_all_docs").query(JsonObject.class);
	        int count = list.size();
	        long user_id = count+1;
        
	        obj.put("User_id", user_id);
	        db.save(obj);
	        
	        return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
