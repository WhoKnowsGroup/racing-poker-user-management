package com.pokerace.ejb.model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import au.com.suncoastpc.auth.util.StringUtilities;
import au.com.suncoastpc.util.CryptoUtil;

import com.google.gson.JsonObject;
import com.pokerace.ejb.model.base.BasicEntity;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;

public class User extends BasicEntity implements Serializable {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final long ONE_DAY = 1000L * 60 * 60 * 24;
	
	private static final Map<Integer, String> LEVELS = new LinkedHashMap<>();
	
	static {
		LEVELS.put(0, 		"Plankton");
		LEVELS.put(2, 		"Sardine");
		LEVELS.put(5, 		"Small Fry");
		LEVELS.put(10,		"Mullet");
		LEVELS.put(20, 		"Snapper");
		LEVELS.put(35, 		"Tuna");
		LEVELS.put(60, 		"Marlin");
		LEVELS.put(100, 	"Reef Shark");
		LEVELS.put(150, 	"Bull Shark");
		LEVELS.put(225, 	"Tiger Shark");
		LEVELS.put(325, 	"Hammerhead Shark");
		LEVELS.put(450, 	"Grey Nurse Shark");
		LEVELS.put(575, 	"Great White Shark");
		LEVELS.put(725, 	"Whale");
		LEVELS.put(900, 	"Whale Shark");
		LEVELS.put(1100, 	"Killer Whale");
		LEVELS.put(1400, 	"Sea Monster");
		LEVELS.put(1800, 	"The Kraken");
	}
	
	//fields
	private long id;
	private String revision;
	
	private long credits;
	private double bitlets;
	
	private double playerLevel;
	private long numTournaments;
	private long numBonuses;
	private long num2Shots;
	private long num3Shots;
	private long num4Shots;
	private long num5Shots;
	private long num6Shots;
	private long num7Shots;
	private long num8Shots;
	private long num9Shots;
	private long num10Shots;
	
    private String email;
    private String firstName;
    private String lastName;
    private String nickname;
    private String gender;
    
    private String salt;
    private String hashedPassword;
    private String userType;
    private String status;
    private String selectedAgeGroup;
    private int yearOfBirth; 
    
    private boolean verifyEmail;
    private boolean musicEnabled;
    private boolean speechEnabled;
    
    private Date createdAt;
    private Date lastLogin;
    private Date lastShare;
    private Date lastLike;
    
    private String authToken;
    private String verifyToken;
    
    private String payments;
    
    //achievements
    private double highestOdds;
    private int highestReturn;
    private int royalFlushCount;
    private int straightFlushCount;
    private int doubleCreditsCount;
    private int tripleCreditsCount;
    private int reachLevel;
    
    
    @SuppressWarnings("unused")
	private User() {
    	//default constructor not allowed
    }
    
    public User(String email, String password, String firstName, String lastName, String nickname, String gender) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	setEmail(email);
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.nickname = nickname;
    	this.gender = gender;
    	
    	this.bitlets = 10.0;				//FIXME:  const/configurable param
    	this.userType = "normaluser";		//FIXME:  enum
        this.status = "inactive";			//FIXME:  enum
        
        this.createdAt = new Date();
        this.lastShare = new Date(0);
        this.lastLike = new Date(0);
    	
    	setPassword(password);
    }
    
    public User(JSONObject json) {
    	super(json);
    }
    
    public User(com.google.gson.JsonObject json) {
    	super(json);
    }
    
    public long getCredits() {
		return credits;
	}
	public void setCredits(long credits) {
		this.credits = credits;
	}
	
	public String getCreditsFormatted() {
		return String.format("%,d", this.getCredits());
	}

	public double getBitlets() {
		return bitlets;
	}
	public void setBitlets(double bitlets) {
		this.bitlets = bitlets;
	}
	
	public int getBitletsInt() {
		return (int)bitlets;
	}
	
	public String getBitletsIntFormatted() {
		return String.format("%,d", this.getBitletsInt());
	}

	public double getPlayerLevel() {
		return playerLevel;
	}
	public void setPlayerLevel(double playerLevel) {
		this.playerLevel = playerLevel;
	}
	
	public int getPlayerLevelInt() {
		return (int)playerLevel;
	}
	
	public String getLevelName() {
		String level = LEVELS.get(0);
		for (Map.Entry<Integer, String> entry : LEVELS.entrySet()) {
			if (getPlayerLevel() >= entry.getKey()) {
				level = entry.getValue();
			}
			else {
				break;
			}
		}
		
		return level;
	}
	
	public String getNextLevelName() {
		String level = "(none)";
		for (Map.Entry<Integer, String> entry : LEVELS.entrySet()) {
			if (entry.getKey() > getPlayerLevel()) {
				level = entry.getValue();
				break;
			}
		}
		
		return level;
	}
	
	public double getLevelProgress() {
		return this.getPlayerLevel() - (int)this.getPlayerLevel();
	}
	
	public String getRevision() {
		return revision;
	}

	public long getNumTournaments() {
		return numTournaments;
	}
	public void setNumTournaments(long numTournaments) {
		this.numTournaments = numTournaments;
	}
	
	public String getSelectedAgeGroup() {
		return selectedAgeGroup;
	}
	public void setSelectedAgeGroup(String selectedAgeGroup) {
		this.selectedAgeGroup = selectedAgeGroup;
	}
	
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(int year) {
		this.yearOfBirth = year;
	}
	
	public boolean isVerifyEmail() {
		return verifyEmail;
	}
	public void setVerifyEmail(boolean verifyEmail) {
		this.verifyEmail = verifyEmail;
	}

	public boolean isMusicEnabled() {
		return musicEnabled;
	}
	public void setMusicEnabled(boolean musicEnabled) {
		this.musicEnabled = musicEnabled;
	}

	public boolean isSpeechEnabled() {
		return speechEnabled;
	}
	public void setSpeechEnabled(boolean speechEnabled) {
		this.speechEnabled = speechEnabled;
	}

	public long getNumBonuses() {
		return numBonuses;
	}
	public void setNumBonuses(long numBonuses) {
		this.numBonuses = numBonuses;
	}

	public long getNum2Shots() {
		return num2Shots;
	}
	public void setNum2Shots(long num2Shots) {
		this.num2Shots = num2Shots;
	}

	public long getNum3Shots() {
		return num3Shots;
	}
	public void setNum3Shots(long num3Shots) {
		this.num3Shots = num3Shots;
	}

	public long getNum4Shots() {
		return num4Shots;
	}
	public void setNum4Shots(long num4Shots) {
		this.num4Shots = num4Shots;
	}

	public long getNum5Shots() {
		return num5Shots;
	}
	public void setNum5Shots(long num5Shots) {
		this.num5Shots = num5Shots;
	}

	public long getNum6Shots() {
		return num6Shots;
	}
	public void setNum6Shots(long num6Shots) {
		this.num6Shots = num6Shots;
	}

	public long getNum7Shots() {
		return num7Shots;
	}
	public void setNum7Shots(long num7Shots) {
		this.num7Shots = num7Shots;
	}

	public long getNum8Shots() {
		return num8Shots;
	}
	public void setNum8Shots(long num8Shots) {
		this.num8Shots = num8Shots;
	}

	public long getNum9Shots() {
		return num9Shots;
	}
	public void setNum9Shots(long num9Shots) {
		this.num9Shots = num9Shots;
	}

	public long getNum10Shots() {
		return num10Shots;
	}
	public void setNum10Shots(long num10Shots) {
		this.num10Shots = num10Shots;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if (email != null) {
			email = email.toLowerCase();
		}
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public Date getLastShare() {
		return lastShare;
	}
	public void setLastShare(Date lastShare) {
		this.lastShare = lastShare;
	}
	public long getSecondsUntilNextShare() {
		return (getLastShare().getTime() + ONE_DAY - System.currentTimeMillis()) / 1000;
	}

	public Date getLastLike() {
		return lastLike;
	}
	public void setLastLike(Date lastLike) {
		this.lastLike = lastLike;
	}
	public long getSecondsUntilNextLike() {
		return (getLastLike().getTime() + ONE_DAY - System.currentTimeMillis()) / 1000;
	}

	@Override
	public long getId() {
		return id;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getSalt() {
    	if (StringUtilities.isEmpty(this.salt)) {
    		this.salt = StringUtilities.randomStringWithLengthBetween(12, 24);
    	}
    	return this.salt;
    }
    
    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	this.hashedPassword = CryptoUtil.computeHash(password, this.getSalt());
    }
    
    public boolean authenticate(String password) {
    	return CryptoUtil.authenticateUser(this, password);
    }
    
    public String getAvatarUrl() {
    	Gravatar gravatar = new Gravatar();
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        return gravatar.getUrl(this.getEmail()).replace("d=404", "d=identicon");
    }
    
	public double getHighestOdds() {
		return highestOdds;
	}
	public void setHighestOdds(double highestOdds) {
		this.highestOdds = highestOdds;
	}

	public int getHighestReturn() {
		return highestReturn;
	}
	public void setHighestReturn(int highestReturn) {
		this.highestReturn = highestReturn;
	}

	public int getRoyalFlushCount() {
		return royalFlushCount;
	}
	public void setRoyalFlushCount(int royalFlushCount) {
		this.royalFlushCount = royalFlushCount;
	}

	public int getStraightFlushCount() {
		return straightFlushCount;
	}
	public void setStraightFlushCount(int straightFlushCount) {
		this.straightFlushCount = straightFlushCount;
	}

	public int getDoubleCreditsCount() {
		return doubleCreditsCount;
	}
	public void setDoubleCreditsCount(int doubleCreditsCount) {
		this.doubleCreditsCount = doubleCreditsCount;
	}

	public int getTripleCreditsCount() {
		return tripleCreditsCount;
	}
	public void setTripleCreditsCount(int tripleCreditsCount) {
		this.tripleCreditsCount = tripleCreditsCount;
	}

	public int getReachLevel() {
		return reachLevel;
	}
	public void setReachLevel(int reachLevel) {
		this.reachLevel = reachLevel;
	}
	
	public String getPayments() {
		return payments;
	}
	public void setPayments(String payments) {
		this.payments = payments;
	}
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getVerifyToken() {
		return verifyToken;
	}
	public void setVerifyToken(String verifyToken) {
		this.verifyToken = verifyToken;
	}

	@Override
	protected void loadFromGson(JsonObject json) {
		nickname = jsonString(json, "Nickname");
    	firstName = jsonString(json, "Firstname");
        lastName = jsonString(json, "Lastname");
        setEmail(jsonString(json, "Email"));
        salt = jsonString(json, "salt");
        hashedPassword = jsonString(json, "hashedPassword");
        gender = jsonString(json, "Gender");
        userType = jsonString(json, "User_type");
        status = jsonString(json, "Status");
        selectedAgeGroup = jsonString(json, "SelectedAgeGroup");
        payments = jsonString(json, "Payments");
        authToken = jsonString(json, "auth_token");
        verifyToken = jsonString(json, "verify_token");
        revision = jsonString(json, "_rev");
        
        verifyEmail = "true".equalsIgnoreCase(jsonString(json, "verify_email"));
        musicEnabled = "true".equalsIgnoreCase(jsonString(json, "MusicEnabled"));
        speechEnabled = "true".equalsIgnoreCase(jsonString(json, "SpeechEnabled"));
        
        createdAt = parseDate(json, "CreatedAt");
        lastLogin = parseDate(json, "Last_login", new Date(0));
        lastLike = parseDate(json, "Last_like", new Date(0));
        lastShare = parseDate(json, "Last_share", new Date(0));
        				
        
        credits = parseLong(json, "Credit");
        bitlets = parseLong(json, "no_of_bitlets");
        playerLevel = parseDouble(json, "playerlevel");
        id = parseLong(json, "User_id");
        
        numTournaments = parseLong(json, "no_of_tournaments");
        numBonuses = parseLong(json, "no_of_bonuses");
        num2Shots = parseLong(json, "no_of_2shots");
        num3Shots = parseLong(json, "no_of_3shots");
        num4Shots = parseLong(json, "no_of_4shots");
        num5Shots = parseLong(json, "no_of_5shots");
        num6Shots = parseLong(json, "no_of_6shots");
        num7Shots = parseLong(json, "no_of_7shots");
        num8Shots = parseLong(json, "no_of_8shots");
        num9Shots = parseLong(json, "no_of_9shots");
        num10Shots = parseLong(json, "no_of_10shots");
        
        highestOdds = parseDouble(json, "Highest_odds");
        highestReturn = (int)parseLong(json, "Highest_return");
        royalFlushCount = (int)parseLong(json, "Royal_flush");
        straightFlushCount = (int)parseLong(json, "Straight_flush");
        doubleCreditsCount = (int)parseLong(json, "Double_credits");
        tripleCreditsCount = (int)parseLong(json, "Triple_credits");
        reachLevel = (int)parseLong(json, "Reach_level");
        
        yearOfBirth = (int)parseLong(json, "yearOfBirth");
	}

	@Override
	protected void loadFromJson(JSONObject json) {
		nickname = jsonString(json, "Nickname");
    	firstName = jsonString(json, "Firstname");
        lastName = jsonString(json, "Lastname");
        setEmail(jsonString(json, "Email"));
        salt = jsonString(json, "salt");
        hashedPassword = jsonString(json, "hashedPassword");
        gender = jsonString(json, "Gender");
        userType = jsonString(json, "User_type");
        status = jsonString(json, "Status");
        selectedAgeGroup = jsonString(json, "SelectedAgeGroup");
        payments = jsonString(json, "Payments");
        authToken = jsonString(json, "auth_token");
        verifyToken = jsonString(json, "verify_token");
        revision = jsonString(json, "_rev");
        
        verifyEmail = "true".equalsIgnoreCase(jsonString(json, "verify_email"));
        musicEnabled = "true".equalsIgnoreCase(jsonString(json, "MusicEnabled"));
        speechEnabled = "true".equalsIgnoreCase(jsonString(json, "SpeechEnabled"));
        
        createdAt = parseDate(json, "CreatedAt");
        lastLogin = parseDate(json, "Last_login", new Date(0));
        lastLike = parseDate(json, "Last_like", new Date(0));
        lastShare = parseDate(json, "Last_share", new Date(0));
        
        id = parseLong(json, "User_id");
        credits = parseLong(json, "Credit");
        bitlets = parseLong(json, "no_of_bitlets");
        playerLevel = parseDouble(json, "playerlevel");
        
        numTournaments = parseLong(json, "no_of_tournaments");
        numBonuses = parseLong(json, "no_of_bonuses");
        num2Shots = parseLong(json, "no_of_2shots");
        num3Shots = parseLong(json, "no_of_3shots");
        num4Shots = parseLong(json, "no_of_4shots");
        num5Shots = parseLong(json, "no_of_5shots");
        num6Shots = parseLong(json, "no_of_6shots");
        num7Shots = parseLong(json, "no_of_7shots");
        num8Shots = parseLong(json, "no_of_8shots");
        num9Shots = parseLong(json, "no_of_9shots");
        num10Shots = parseLong(json, "no_of_10shots");
        
        highestOdds = parseDouble(json, "Highest_odds");
        highestReturn = (int)parseLong(json, "Highest_return");
        royalFlushCount = (int)parseLong(json, "Royal_flush");
        straightFlushCount = (int)parseLong(json, "Straight_flush");
        doubleCreditsCount = (int)parseLong(json, "Double_credits");
        tripleCreditsCount = (int)parseLong(json, "Triple_credits");
        reachLevel = (int)parseLong(json, "Reach_level");
        
        yearOfBirth = (int)parseLong(json, "yearOfBirth");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JSONObject jsonRepresentation() {
		JSONObject result = new JSONObject();
    	
        result.put("Email", getEmail());
        result.put("salt", getSalt());
        result.put("hashedPassword", getHashedPassword());
        result.put("Firstname", getFirstName());
        result.put("Lastname", getLastName());
        result.put("Gender", getGender());
        result.put("Nickname", getNickname());
        result.put("Credit", getCredits());   
        result.put("CreatedAt", getCreatedAt().toString());
        result.put("CreatedAt_timestamp", getCreatedAt().getTime());
        result.put("Last_login", getLastLogin() == null ? new Date(0).toString() : getLastLogin().toString());
        result.put("Last_login_timestamp", getLastLogin() == null ? new Date(0).getTime() : getLastLogin().getTime());
        result.put("User_type", getUserType());
        result.put("Status", getStatus());
        result.put("_id", getEmail());
        result.put("User_id", this.getId());
        result.put("SelectedAgeGroup", this.getSelectedAgeGroup());
        result.put("auth_token", this.getAuthToken());
        result.put("verify_token", this.getVerifyToken());
        result.put("Payments", this.getPayments() /*this.getPayments() == null ? "" : this.getPayments()*/);
        
        result.put("Last_like", getLastLike() == null ? new Date(0).toString() : getLastLike().toString());
        result.put("Last_like_timestamp", getLastLike() == null ? new Date(0).getTime() : getLastLike().getTime());
        result.put("Last_share", getLastShare() == null ? new Date(0).toString() : getLastShare().toString());
        result.put("Last_share_timestamp", getLastShare() == null ? new Date(0).getTime() : getLastShare().getTime());
        
        result.put("verify_email", this.verifyEmail ? "true" : "false");
        result.put("MusicEnabled", this.musicEnabled ? "true" : "false");
        result.put("SpeechEnabled", this.speechEnabled ? "true" : "false");
        
        //for legacy compatibility, we send these as strings
        result.put("no_of_tournaments", this.getNumTournaments() + "");
        result.put("no_of_bitlets", this.getBitlets() + "");
        result.put("no_of_bonuses", this.getNumBonuses() + "");
        result.put("playerlevel", this.getPlayerLevel() + "");
        result.put("no_of_2shots", this.getNum2Shots() + "");
        result.put("no_of_3shots", this.getNum3Shots() + "");
        result.put("no_of_4shots", this.getNum4Shots() + "");
        result.put("no_of_5shots", this.getNum5Shots() + "");
        result.put("no_of_6shots", this.getNum6Shots() + "");
        result.put("no_of_7shots", this.getNum7Shots() + "");
        result.put("no_of_8shots", this.getNum8Shots() + "");
        result.put("no_of_9shots", this.getNum9Shots() + "");
        result.put("no_of_10shots", this.getNum10Shots() + "");
        
        result.put("Highest_odds", this.getHighestOdds() + "");
        result.put("Highest_return", this.getHighestReturn() + "");
        result.put("Royal_flush", this.getRoyalFlushCount() + "");
        result.put("Straight_flush", this.getStraightFlushCount() + "");
        result.put("Double_credits", this.getDoubleCreditsCount() + "");
        result.put("Triple_credits", this.getTripleCreditsCount() + "");
        result.put("Reach_level", this.getReachLevel() + "");
        
        result.put("yearOfBirth", this.getYearOfBirth() + "");
        
        if (revision != null) {
        	result.put("_rev", revision);
        }
        
        return result;
	}

	@Override
	protected JsonObject gsonRepresentation() {
		com.google.gson.JsonObject result = new com.google.gson.JsonObject();
    	
        result.addProperty("Email", getEmail());
        result.addProperty("salt", getSalt());
        result.addProperty("hashedPassword", getHashedPassword());
        result.addProperty("Firstname", getFirstName());
        result.addProperty("Lastname", getLastName());
        result.addProperty("Gender", getGender());
        result.addProperty("Nickname", getNickname());
        result.addProperty("Credit", getCredits());   
        result.addProperty("CreatedAt", getCreatedAt().toString());
        result.addProperty("CreatedAt_timestamp", getCreatedAt().getTime());
        result.addProperty("Last_login", getLastLogin() == null ? new Date(0).toString() : getLastLogin().toString());
        result.addProperty("Last_login_timestamp", getLastLogin() == null ? new Date(0).getTime() : getLastLogin().getTime());
        result.addProperty("User_type", getUserType());
        result.addProperty("Status", getStatus());
        result.addProperty("_id", getEmail());
        result.addProperty("User_id", this.getId());
        result.addProperty("SelectedAgeGroup", getSelectedAgeGroup());
        result.addProperty("auth_token", this.getAuthToken());
        result.addProperty("verify_token", this.getVerifyToken());
        result.addProperty("Payments", this.getPayments()/*this.getPayments() == null ? "" : this.getPayments()*/);
        
        result.addProperty("Last_like", getLastLike() == null ? new Date(0).toString() : getLastLike().toString());
        result.addProperty("Last_like_timestamp", getLastLike() == null ? new Date(0).getTime() : getLastLike().getTime());
        result.addProperty("Last_share", getLastShare() == null ? new Date(0).toString() : getLastShare().toString());
        result.addProperty("Last_share_timestamp", getLastShare() == null ? new Date(0).getTime() : getLastShare().getTime());
        
        result.addProperty("verify_email", this.verifyEmail ? "true" : "false");
        result.addProperty("MusicEnabled", this.musicEnabled ? "true" : "false");
        result.addProperty("SpeechEnabled", this.speechEnabled ? "true" : "false");
        
        //for legacy compatibility, we send these as strings
        result.addProperty("no_of_tournaments", this.getNumTournaments() + "");
        result.addProperty("no_of_bitlets", this.getBitlets() + "");
        result.addProperty("no_of_bonuses", this.getNumBonuses() + "");
        result.addProperty("playerlevel", this.getPlayerLevel() + "");
        result.addProperty("no_of_2shots", this.getNum2Shots() + "");
        result.addProperty("no_of_3shots", this.getNum3Shots() + "");
        result.addProperty("no_of_4shots", this.getNum4Shots() + "");
        result.addProperty("no_of_5shots", this.getNum5Shots() + "");
        result.addProperty("no_of_6shots", this.getNum6Shots() + "");
        result.addProperty("no_of_7shots", this.getNum7Shots() + "");
        result.addProperty("no_of_8shots", this.getNum8Shots() + "");
        result.addProperty("no_of_9shots", this.getNum9Shots() + "");
        result.addProperty("no_of_10shots", this.getNum10Shots() + "");
        
        result.addProperty("Highest_odds", this.getHighestOdds() + "");
        result.addProperty("Highest_return", this.getHighestReturn() + "");
        result.addProperty("Royal_flush", this.getRoyalFlushCount() + "");
        result.addProperty("Straight_flush", this.getStraightFlushCount() + "");
        result.addProperty("Double_credits", this.getDoubleCreditsCount() + "");
        result.addProperty("Triple_credits", this.getTripleCreditsCount() + "");
        result.addProperty("Reach_level", this.getReachLevel() + "");
        
        result.addProperty("yearOfBirth", this.getYearOfBirth() + "");
        
        if (revision != null) {
        	result.addProperty("_rev", revision);
        }
        
        return result;
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (! (otherObj instanceof User)) {
			return false;
		}
		
		User otherUser = (User)otherObj;
		if (this.getEmail() == otherUser.getEmail()) {
			return true;
		}
		if (this.getEmail() == null || otherUser.getEmail() == null) {
			return false;
		}
		return this.getEmail().equalsIgnoreCase(otherUser.getEmail());
	}
	
	@Override
	public int hashCode() {
		return this.getEmail() == null ? 0 : this.getEmail().toLowerCase().hashCode();
	}

	@Override
	protected String csvRepresentation() {
		return nickname + "," + credits + "," + firstName + "," + lastName + "," + userType + "," + numTournaments + "," + bitlets + "," + playerLevel + ","
                + numBonuses + "," + num2Shots + "," + num3Shots + "," + num4Shots + "," + num5Shots + "," + num6Shots + "," + num7Shots + "," 
    			+ num8Shots + "," + num9Shots + "," + num10Shots; 
	}
}
