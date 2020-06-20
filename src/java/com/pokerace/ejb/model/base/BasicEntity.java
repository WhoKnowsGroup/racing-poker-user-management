package com.pokerace.ejb.model.base;

import java.io.Serializable;
import java.util.Date;

import org.json.simple.JSONObject;

public abstract class BasicEntity implements Serializable {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected BasicEntity() {
		
	}
	
	public BasicEntity(JSONObject source) {
		this.loadFromJson(source);
	}
	
	public BasicEntity(com.google.gson.JsonObject source) {
		this.loadFromGson(source);
	}
	
	public JSONObject toJson() {
		return this.jsonRepresentation();
	}
	
	public com.google.gson.JsonObject toGson() {
		return this.gsonRepresentation();
	}
	
	public String toCsv() {
		return this.csvRepresentation();
	}
	
	public String toJsonString() {
		return this.toJson().toJSONString();
	}
	
	public long getId() {
		return this.hashCode();
	}
	
	protected Date parseDate(JSONObject json, String key) {
    	return parseDate(json, key, new Date());
    }
	
	protected Date parseDate(JSONObject json, String key, Date defaultResult) {
    	if (json.containsKey(key + "_timestamp")) {
    		return new Date(parseLong(json, key + "_timestamp"));
    	}
    	try {
    		return new Date(jsonString(json, key));
    	}
    	catch (Throwable e) {
    		return defaultResult;
    	}
    }
    
	protected String jsonString(JSONObject json, String key) {
    	if (! json.containsKey(key)) {
    		return null;
    	}
    	return json.get(key).toString().replace("\"", "");
    }
    
    protected long parseLong(JSONObject json, String key) {
    	return (long)parseDouble(json, key);
    }
    
    protected double parseDouble(JSONObject json, String key) {
    	try {
    		String text = json.get(key).toString().replace("\"", "");
    		return Double.parseDouble(text);
    	}
    	catch (Throwable e) {
    		return 0;
    	}
    }
    
    protected Date parseDate(com.google.gson.JsonObject json, String key) {
    	return parseDate(json, key, new Date());
    }
    
    protected Date parseDate(com.google.gson.JsonObject json, String key, Date defaultResult) {
    	if (json.has(key + "_timestamp")) {
    		return new Date(parseLong(json, key + "_timestamp"));
    	}
    	try {
    		return new Date(jsonString(json, key));
    	}
    	catch (Throwable e) {
    		return defaultResult;
    	}
    }
    
    protected String jsonString(com.google.gson.JsonObject json, String key) {
    	if (! json.has(key)) {
    		return null;
    	}
    	return json.get(key).toString().replace("\"", "");
    }
    
    protected long parseLong(com.google.gson.JsonObject json, String key) {
    	return (long)parseDouble(json, key);
    }
    
    protected double parseDouble(com.google.gson.JsonObject json, String key) {
    	try {
    		String text = json.get(key).toString().replace("\"", "");
    		return Double.parseDouble(text);
    	}
    	catch (Throwable e) {
    		//e.printStackTrace();
    		return 0;
    	}
    }
	
	protected abstract void loadFromGson(com.google.gson.JsonObject json);
	protected abstract void loadFromJson(JSONObject json);
	protected abstract JSONObject jsonRepresentation();
	protected abstract com.google.gson.JsonObject gsonRepresentation();
	protected abstract String csvRepresentation();
}
