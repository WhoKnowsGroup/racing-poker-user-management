package au.com.suncoastpc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

import au.com.suncoastpc.conf.Configuration;
import au.com.suncoastpc.util.types.CouchDatabase;

/**
 * Simple utility for acquiring (and releasing) CouchDB connections.  Using configured 
 * details when establishing connections.  Caches clients for reuse.
 * 
 * @author aroth
 */
public class CouchDBUtil {
	private static final Logger LOG = Logger.getLogger(CouchDBUtil.class.getName());
	
	private static final Map<String, CouchDbClient> CLIENTS = new HashMap<>();
	
	private static CouchDbClient getClient(String databaseName, boolean autoCreate) {
		synchronized(CLIENTS) {
			CouchDbClient db = CLIENTS.get(databaseName);
			if (db == null) {
				try {
					CouchDbProperties dbProperties = new CouchDbProperties()
		                .setDbName(databaseName)
		                .setCreateDbIfNotExist(autoCreate)
		                .setProtocol(Configuration.getCouchProtocol())
		                .setHost(Configuration.getCouchHost())
		                .setPort(Configuration.getCouchPort())
		                .setUsername(Configuration.getCouchUser())
		                .setPassword(Configuration.getCouchPass());
		        
					db = new CouchDbClient(dbProperties);
					CLIENTS.put(databaseName, db);
				}
				catch (Throwable e) {
					LOG.log(Level.WARNING, "Unexpected exception when attempting to access CouchDB; name=" + databaseName, e); 
					e.printStackTrace();
				}
			}
			
			return db;
		}
	}
	
	
	public static CouchDbClient getClient(CouchDatabase database, boolean autoCreate) {
		return getClient(database.getDatabaseName(), autoCreate);
	}
	
	public static CouchDbClient getClient(CouchDatabase database) {
		return getClient(database, true);
	}
	
	public static synchronized void shutdownClient(CouchDatabase database) {
		synchronized(CLIENTS) {
			CouchDbClient db = CLIENTS.get(database.getDatabaseName());
			if (db != null) {
				db.shutdown();
				CLIENTS.remove(database.getDatabaseName());
			}
		}
	}
}
