package com.pokerace.ejb.model;

import java.io.Serializable;
import java.util.Date;

import org.json.simple.JSONObject;

import com.google.gson.JsonObject;
import com.pokerace.ejb.model.base.BasicEntity;

public class Tournament extends BasicEntity implements Serializable {
	/**
	 * Serialiation ID
	 */
	private static final long serialVersionUID = 1L;

	//fields
	private long id;
	
	private String name;
	private String type;
	private String status;
	
	private Date createdAt;
	
	private long startingCredits;
	private long numGames;
	private long minPlayers;
	private long maxPlayers;
	private long bitletsRequired;
	
	@SuppressWarnings("unused")
	private Tournament() {
		//default constructor not allowed
	}
	
	public Tournament(JSONObject json) {
		super(json);
	}
	
	public Tournament(com.google.gson.JsonObject json) {
		super(json);
	}
	
	public Tournament(String name, String type, int credits, int games, int minPlayers, int maxPlayers) {
		this.id = 0;
		this.name = name;
		this.type = type;
		this.status = "active";
		
		this.createdAt = new Date();
		
		this.bitletsRequired = 0;
		this.startingCredits = credits;
		this.numGames = games;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public long getStartingCredits() {
		return startingCredits;
	}
	public void setStartingCredits(long startingCredits) {
		this.startingCredits = startingCredits;
	}
	
	public String getStartingCreditsFormatted() {
		return String.format("%,d", this.getStartingCredits());
	}

	public long getNumGames() {
		return numGames;
	}
	public void setNumGames(long numGames) {
		this.numGames = numGames;
	}

	public long getMinPlayers() {
		return minPlayers;
	}
	public void setMinPlayers(long minPlayers) {
		this.minPlayers = minPlayers;
	}

	public long getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(long maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public long getBitletsRequired() {
		return bitletsRequired;
	}
	public void setBitletsRequired(long bitletsRequired) {
		this.bitletsRequired = bitletsRequired;
	}
	public String getBitletsRequiredFormatted() {
		return String.format("%,d", this.getBitletsRequired());
	}

	@Override
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	public long getFirstPlaceCredits() {
		return this.getStartingCredits() * 5;
	}
	public String getFirstPlaceCreditsFormatted() {
		return String.format("%,d", this.getFirstPlaceCredits());
	}
	
	public long getSecondPlaceCredits() {
		return (long)(this.getStartingCredits() * 3);
	}
	public String getSecondPlaceCreditsFormatted() {
		return String.format("%,d", this.getSecondPlaceCredits());
	}

	public long getThirdPlaceCredits() {
		return (long)(this.getStartingCredits() * 1.5);
	}
	public String getThirdPlaceCreditsFormatted() {
		return String.format("%,d", this.getThirdPlaceCredits());
	}
	
	public long getFirstPlaceBitlets() {
		return this.getBitletsRequired() * 5;
	}
	public String getFirstPlaceBitletsFormatted() {
		return String.format("%,d", this.getFirstPlaceBitlets());
	}
	
	public long getSecondPlaceBitlets() {
		return (long)(Math.round(this.getBitletsRequired() * 3));
	}
	public String getSecondPlaceBitletsFormatted() {
		return String.format("%,d", this.getSecondPlaceBitlets());
	}

	public long getThirdPlaceBitlets() {
		return (long)(Math.round(this.getBitletsRequired() * 1.5));
	}
	public String getThirdPlaceBitletsFormatted() {
		return String.format("%,d", this.getThirdPlaceBitlets());
	}

	@Override
	protected void loadFromGson(JsonObject json) {
		name = jsonString(json, "Tournament_name");
		status = jsonString(json, "Status");
		type = jsonString(json, "Type");
		
		createdAt = parseDate(json, "CreatedAt");
		
		id = parseLong(json, "Tournament_id");
		startingCredits = parseLong(json, "starting_credit_points");
		numGames = parseLong(json, "Number_of_games");
		minPlayers = parseLong(json, "Number_of_MinPlayers");
		maxPlayers = parseLong(json, "Number_of_MaxPlayers");
		bitletsRequired = parseLong(json, "Required_bitlets");
	}

	@Override
	protected void loadFromJson(JSONObject json) {
		name = jsonString(json, "Tournament_name");
		status = jsonString(json, "Status");
		type = jsonString(json, "Type");
		
		createdAt = parseDate(json, "CreatedAt");
		
		id = parseLong(json, "Tournament_id");
		startingCredits = parseLong(json, "starting_credit_points");
		numGames = parseLong(json, "Number_of_games");
		minPlayers = parseLong(json, "Number_of_MinPlayers");
		maxPlayers = parseLong(json, "Number_of_MaxPlayers");
		bitletsRequired = parseLong(json, "Required_bitlets");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JSONObject jsonRepresentation() {
		JSONObject obj = new JSONObject();
				
		obj.put("_id", name);
		obj.put("Tournament_id", id);
		
	    obj.put("Tournament_name", name);
	    obj.put("starting_credit_points", startingCredits + "");
	    obj.put("Number_of_games", numGames + "");
	    obj.put("Number_of_MinPlayers", minPlayers + "");
	    obj.put("Number_of_MaxPlayers", maxPlayers + "");
	    obj.put("Required_bitlets", bitletsRequired + "");
	    obj.put("Type", type);
	    obj.put("Status", status);      
	    obj.put("CreatedAt", this.createdAt == null ? new Date().toString() : this.createdAt.toString());
	    obj.put("CreatedAt_timestamp", this.createdAt == null ? new Date().getTime() : this.createdAt.getTime());
	    
	    return obj;
	}

	@Override
	protected JsonObject gsonRepresentation() {
		com.google.gson.JsonObject obj = new com.google.gson.JsonObject();
		
		obj.addProperty("_id", name);
		obj.addProperty("Tournament_id", id);
		
	    obj.addProperty("Tournament_name", name);
	    obj.addProperty("starting_credit_points", startingCredits + "");
	    obj.addProperty("Number_of_games", numGames + "");
	    obj.addProperty("Number_of_MinPlayers", minPlayers + "");
	    obj.addProperty("Number_of_MaxPlayers", maxPlayers + "");
	    obj.addProperty("Required_bitlets", bitletsRequired + "");
	    obj.addProperty("Type", type);
	    obj.addProperty("Status", status);      
	    obj.addProperty("CreatedAt", this.createdAt == null ? new Date().toString() : this.createdAt.toString());
	    obj.addProperty("CreatedAt_timestamp", this.createdAt == null ? new Date().getTime() : this.createdAt.getTime());
	    
	    return obj;
	}

	@Override
	protected String csvRepresentation() {
		return name + "," + startingCredits + "," + numGames + "," + minPlayers + "," + maxPlayers + "," + type;
	}

}
