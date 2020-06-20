package com.pokerace.ejb;

import java.util.List;

import javax.ejb.Stateless;

import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;

import au.com.suncoastpc.auth.util.StringUtilities;
import au.com.suncoastpc.util.CouchDBUtil;
import au.com.suncoastpc.util.types.CouchDatabase;

import com.google.gson.JsonObject;
import com.pokerace.ejb.iface.TournamentManager;
import com.pokerace.ejb.model.Tournament;

@Stateless
public class TournamentEJB implements TournamentManager {

	@Override
	public Tournament findTournament(String tournamentName) {			//was tournament_find()
		try {
			CouchDbClient db = CouchDBUtil.getClient(CouchDatabase.ACTIVE_TOURNAMENTS);			
			JsonObject json = db.find(JsonObject.class, tournamentName);
			if (json != null) {
				return new Tournament(json);
			}
        
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean createTournament(Tournament tournament) {		//was Store_tournament()
		if (tournament == null || StringUtilities.isEmpty(tournament.getName())) {
			return false;
		}
		
		return insert(tournament);
	}
	
    @SuppressWarnings("unchecked")
	protected synchronized boolean insert(Tournament tournament) {
        try {
        	Tournament existing = findTournament(tournament.getName());
    		if (existing != null) {
    			return false;
    		}
        	
        	CouchDbClient db = CouchDBUtil.getClient(CouchDatabase.TOURNAMENTS);		
        	CouchDbClient db1 = CouchDBUtil.getClient(CouchDatabase.ACTIVE_TOURNAMENTS);
        
        	//FIXME:  this is probably a very poor/inefficient way of getting a new tournament id
        	List<JsonObject> list = db.view("_all_docs").query(JsonObject.class);
        	int count = list.size();
        	long tournament_id = count+1;
        
        	JSONObject obj = tournament.toJson();
        	obj.put("Tournament_id", tournament_id);
        	db1.save(obj);
        	db.save(obj);
        	
        	return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

}
