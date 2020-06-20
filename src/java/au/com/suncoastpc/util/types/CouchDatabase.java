package au.com.suncoastpc.util.types;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Enumeration of the different CouchDB tables/database used by the app.  Can be used 
 * when fetching a connection to a particular table/database.
 * 
 * @author aroth
 */
public enum CouchDatabase {
	ACTIVE_TOURNAMENTS("pokerace_tournaments_active"),
	BOTS("kentuckypoker_bots"),
	CHAT("pokerace_chat_tournaments"),
	COUNTRIES("pokerace_countries"),
	CREDIT_HISTORY("pokerace_users_credit_history"),
	GUEST_USERS("pokerace_guest_users"),
	KENTUCKY_USERS("kentuckypoker_users"),
	LOADING_TOURNAMENTS("pokerace_loading_tournaments"),					//XXX:  appears unused?
	PAYMENT_HISTORY("pokerace_payment_history"),
	RUNNING_BETS("pokerace_running_tournaments_bets"),
	RUNNING_TOURNAMENTS("pokerace_running_tournaments"),
	SHOTS("pokerace_shots"),
	SINGLE_PLAYER_TOURNAMENTS("pokerace_single_player_tournaments"),		//XXX:  appears unused?
	SUNCOASTPC_USERS("suncoast_users"),										//XXX:  internal/test only
	TOP_50_PLAYERS("pokerace_top_50_players"),
	TOP_PLAYERS("pokerace_top_players"),
	TOP_SHOTS("pokerace_top_shots"),										//XXX:  appears unused?
	TOURNAMENTS("pokerace_tournaments"),
	TOURNAMENT_BOTS("pokerace_tournament_bots"),
	TOURNAMENT_RANKING("pokerace_tournaments_ranking"),
	TOURNAMENT_RESULTS("pokerace_tournaments_results"),
	TOURNAMENT_WINNERS("pokerace_tournament_winners"),
	USERS("pokerace_users");
	
	private static final Logger LOG = Logger.getLogger(CouchDatabase.class.getName());
	private static final Map<String, CouchDatabase> VALUES = new HashMap<>();
	
	static {
		for (CouchDatabase db : CouchDatabase.values()) {
			VALUES.put(db.getDatabaseName().toLowerCase(), db);
		}
	}
	
	private String databaseName;
	
	private CouchDatabase(String dbName) {
		this.databaseName = dbName;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}

	public String toString() {
		return this.databaseName;
	}
	
	public static CouchDatabase forName(String name) {
		if (name == null) {
			return null;
		}
		
		if (! VALUES.containsKey(name.toLowerCase())) {
			LOG.log(Level.WARNING, "Unknown CouchDB database:  " + name);
		}
		
		return VALUES.get(name.toLowerCase());
	}
}
