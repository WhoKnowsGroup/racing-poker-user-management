package au.com.suncoastpc.util;

import java.util.EnumSet;
import java.util.List;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

import com.google.gson.JsonObject;

import au.com.suncoastpc.conf.Configuration;
import au.com.suncoastpc.util.types.CouchDatabase;

/**
 * Clones the entire contents of one couchDB instance into another, non-destructively
 * 
 * @author aroth
 */
public class DatabaseClone {
	private static final String LOCAL_DB_HOST = "127.0.0.1";
	private static final String LOCAL_DB_USER = "racingpoker";
	private static final String LOCAL_DB_PASS = "R@c1NgpoK3r__Prod$!6*";
	
	static {
		Configuration.setCouchHost(LOCAL_DB_HOST);
		Configuration.setCouchUser(LOCAL_DB_USER);
		Configuration.setCouchPass(LOCAL_DB_PASS);
	}
	
	public static void main(String[] args) {
		//for (CouchDatabase database : CouchDatabase.values()) {
		//	copyData(database);
		//}
		//pokerace_tournament_winners
		//pokerace_users
		for (CouchDatabase database : EnumSet.of(CouchDatabase.TOURNAMENT_WINNERS, CouchDatabase.USERS)) {
			copyData(database);
		}
	}
	
	private static void copyData(CouchDatabase dbName) {
		System.out.println("Processing database:  " + dbName.getDatabaseName());
		
		
		CouchDbClient src = getLegacyClient(dbName); 
		CouchDbClient dest = CouchDBUtil.getClient(dbName);
		if (src != null && dest != null) {
			int numItems = 0;
			int numAdded = 0;
			List<JsonObject> list = src.view("_all_docs").query(JsonObject.class);
			for (JsonObject obj : list) {
				if (! obj.has("id")) {
					System.out.println("WARN:  No 'id' property found for entity; dbName=" + dbName.getDatabaseName() + ", entity=" + obj.toString());
					continue;
				}
				
				obj = find(obj.get("id").getAsString(), src);
				if (! obj.has("_id")) {
					System.out.println("WARN:  No '_id' property found for entity; dbName=" + dbName.getDatabaseName() + ", entity=" + obj.toString());
					continue;
				}
				
				JsonObject existing = find(obj.get("_id").getAsString(), dest);
				if (existing == null) {
					System.out.println("Saving new entity; dbName=" + dbName.getDatabaseName() + ", entityId=" + obj.get("_id").getAsString());
					//if (! obj.has("_id")) {
					//	obj.add("_id", obj.get("id"));
					//}
					if (obj.has("rev")) {
						obj.remove("rev");
					}
					if (obj.has("_rev")) {
						obj.remove("_rev");
					}
					dest.save(obj);
					
					numAdded++;
				}
				numItems++;
			}
			
			System.out.println("Processed database:  dbName=" + dbName.getDatabaseName() + ", numItems=" + numItems + ", numAdded=" + numAdded);
		}
		else {
			System.out.println("ERROR:  Failed to connect to database; src=" + src + ", dest=" + dest + ", db=" + dbName.getDatabaseName());
		}
	}
	
	private static JsonObject find(String id, CouchDbClient database) {
		JsonObject result = null;
		try {
			result = database.find(JsonObject.class, id);
		}
		catch (Throwable expected) {
			//not found
		}
		
		return result;
	}
	
	private static CouchDbClient getLegacyClient(CouchDatabase database) {
		CouchDbClient result = null;
		try {
			CouchDbProperties dbProperties = new CouchDbProperties()
	        	.setDbName(database.getDatabaseName())
	        	.setCreateDbIfNotExist(true)
	        	.setProtocol(Configuration.getCouchProtocol())
	        	.setHost("racingpoker.com")
	        	.setPort(5984)
	        	.setUsername("pokerace")
	        	.setPassword("pokerace");
			
			result = new CouchDbClient(dbProperties);
		}
		catch (Throwable ignored) {
			ignored.printStackTrace();
		}
		
		return result;
	}
}
