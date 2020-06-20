package au.com.suncoastpc.conf;


/**
 * Configuration for CouchDB connection details (and other things, as needed).
 * 
 * Configured values can be initialized by setting system properties, and may be 
 * dyanmically updated at runtime if/when required.
 * 
 * @author aroth
 */
public class Configuration {
	//server setup
	private static final int SERVER_PORT;
	private static final String SERVER_HOST_NAME;
	private static final boolean REQUIRES_SECURE_CONNECTION;
	
	//HTTP sessions
	private static final int HTTP_TIMEOUT_MINUTES;
	private static Integer httpTimeoutMinutes = null;
	
	//logging
	private static final boolean DEBUG_ENABLED;
	private static Boolean debugEnabled;
	
	private static String serverHostName = null;
	private static Integer serverPort = null;
	private static String serverProtocol = null;
	private static Boolean serverRequiresSecureConnection = null;
	
	//NoSQL database
	private static final String COUCH_HOST;
	private static final int COUCH_PORT;
	private static final String COUCH_USER;
	private static final String COUCH_PASS;
	private static final String COUCH_PROTOCOL;
	
	private static String couchHost;
	private static Integer couchPort;
	private static String couchUser;
	private static String couchPass;
	private static String couchProtocol;
	
	//Websockets (provide connection into the 'StreamlineSockets' project)
	private static final String WEBSOCKET_TEST_HOST;
	private static final int WEBSOCKET_TEST_PORT;
	private static final String WEBSOCKET_GAMEPLAY_HOST;
	private static final int WEBSOCKET_GAMEPLAY_PORT;
	private static final int WEBSOCKET_THREADPOOL_SIZE;
	
	private static String websocketTestHost;
	private static Integer websocketTestPort;
	private static String websocketGameplayHost;
	private static Integer websocketGameplayPort;
	private static Integer websocketThreadpoolSize;
	
	//email
	private static final String ADMIN_EMAIL_ADDRESS;
	private static final String ADMIN_EMAIL_NAME;
	private static final String EMAIL_HOST;
	private static final String EMAIL_PASS;
	private static final boolean EMAIL_SECURE;
	
	private static String adminEmailAddress;
	private static String adminEmailName;
	private static String emailHost;
	private static String emailPass;
	private static Boolean emailSecure;
	
	//social
	private static final int FACEBOOK_SHARE_CREDITS;
	private static final int FACEBOOK_LIKE_CREDITS;
	
	private static Integer facebookShareCredits;
	private static Integer facebookLikeCredits;
	
	//purchases
	private static final String CREDITS_PRODUCT_GROUP;
	private static final String GOLD_PRODUCT_GROUP;
	private static final String COMBO_PRODUCT_GROUP;
	
	private static String creditsProductGroup;
	private static String goldProductGroup;
	private static String comboProductGroup;

	static {
		String host = System.getProperty("com.pokerace.server.hostname", "localhost").toLowerCase();
		String port = System.getProperty("com.pokerace.server.port", "8080");
		String protocol = System.getProperty("com.pokerace.server.protocol", "http").toLowerCase();
		
		SERVER_HOST_NAME = host;
		SERVER_PORT = Integer.parseInt(port);
		REQUIRES_SECURE_CONNECTION = "https".equals(protocol);
		
		String timeout = System.getProperty("com.pokerace.server.http.timeout", "120");
		HTTP_TIMEOUT_MINUTES = Integer.parseInt(timeout);
		
		String debug = System.getProperty("com.pokerace.logging.debug.enabled", "true").toLowerCase();
		DEBUG_ENABLED = "true".equals(debug);
		
		COUCH_HOST = System.getProperty("com.pokerace.couch.db.host", "103.18.108.72");
		COUCH_USER = System.getProperty("com.pokerace.couch.db.user", null);
		COUCH_PASS = System.getProperty("com.pokerace.couch.db.pass", null);
		String casPort = System.getProperty("com.pokerace.couch.db.port", "5984");
		COUCH_PORT = Integer.parseInt(casPort);		
		COUCH_PROTOCOL = System.getProperty("com.pokerace.couch.db.protocol", "http");
		
		WEBSOCKET_TEST_HOST = System.getProperty("com.pokerace.websocket.test.host", "103.18.108.72");		//XXX:  not expected to actually work, ever
		String socketTestPort = System.getProperty("com.pokerace.websocket.test.port", "1066");
		WEBSOCKET_TEST_PORT = Integer.parseInt(socketTestPort);	
		
		WEBSOCKET_GAMEPLAY_HOST = System.getProperty("com.pokerace.websocket.gameplay.host", "103.18.108.105");		//XXX:  not expected to actually work, ever
		String socketGamePort = System.getProperty("com.pokerace.websocket.gameplay.port", "1071");
		WEBSOCKET_GAMEPLAY_PORT = Integer.parseInt(socketGamePort);	
		
		String socketPool = System.getProperty("com.pokerace.websocket.threadpool.size", "128");
		WEBSOCKET_THREADPOOL_SIZE = Integer.parseInt(socketPool);	
		
		ADMIN_EMAIL_ADDRESS = System.getProperty("com.pokerace.mail.admin.address", "biz@racingpoker.com");
		ADMIN_EMAIL_NAME = System.getProperty("com.pokerace.mail.admin.name", "Racing Poker");
		EMAIL_HOST = System.getProperty("com.pokerace.mail.server", "mail.racingpoker.com");
		EMAIL_PASS = System.getProperty("com.pokerace.mail.password", "PokerAce001");
		
		String secureEmail = System.getProperty("com.pokerace.mail.server.isSecure", "true");
		EMAIL_SECURE = "true".equals(secureEmail);
		
		String shareCredits = System.getProperty("com.pokerace.social.share.credits", "5000");
		FACEBOOK_SHARE_CREDITS = Integer.parseInt(shareCredits);
		
		String likeCredits = System.getProperty("com.pokerace.social.like.credits", "1000");
		FACEBOOK_LIKE_CREDITS = Integer.parseInt(likeCredits);
		
		CREDITS_PRODUCT_GROUP = System.getProperty("com.pokerace.purchases.credits.group", "LegacyProductionCredits");
		GOLD_PRODUCT_GROUP = System.getProperty("com.pokerace.purchases.gold.group", "LegacyProductionGold");
		COMBO_PRODUCT_GROUP = System.getProperty("com.pokerace.purchases.combos.group", "StageComboPacks");
	}
	
	//composite configuration property, not settable directly
	public static String getServerAddress() {
		return getServerProtocol() + "://" + getServerHostName() + ((getServerPort() == 80 || getServerPort() == 443) ? "" : (":" + getServerPort()));
	}

	//server host, configurable
	public static String getServerHostName() {
		return serverHostName == null ? SERVER_HOST_NAME : serverHostName;
	}
	public static void setServerHostName(String serverHostName) {
		Configuration.serverHostName = serverHostName;
	}

	//server port, configurable
	public static int getServerPort() {
		return serverPort == null ? SERVER_PORT : serverPort;
	}
	public static void setServerPort(String serverPort) {
		if (serverPort == null) {
			Configuration.serverPort = null;
			return;
		}
		Configuration.serverPort = Integer.parseInt(serverPort);
	}
	
	//server protocol (http or https)
	public static String getServerProtocol() {
		return serverProtocol  == null ? (getServerRequiresSecureConnection() ? "https" : "http") : serverProtocol;
	}
	public static void setServerProtocol(String serverProtocol) {
		Configuration.serverProtocol = serverProtocol;
	}

	//whether or not to require a secure connection
	public static boolean getServerRequiresSecureConnection() {
		return serverRequiresSecureConnection == null ? REQUIRES_SECURE_CONNECTION : serverRequiresSecureConnection;
	}
	public static void setServerRequiresSecureConnection(String serverRequiresSecureConnection) {
		if (serverRequiresSecureConnection == null) {
			Configuration.serverRequiresSecureConnection = null;
			return;
		}
		Configuration.serverRequiresSecureConnection = Boolean.valueOf(serverRequiresSecureConnection);
	}
	
	//HTTP session timeout, configurable
	public static int getHttpTimeoutMinutes() {
		return httpTimeoutMinutes == null ? HTTP_TIMEOUT_MINUTES : httpTimeoutMinutes;
	}
	public static void setHttpTimeoutMinutes(String minutes) {
		if (minutes == null) {
			Configuration.httpTimeoutMinutes = null;
			return;
		}
		Configuration.httpTimeoutMinutes = Integer.parseInt(minutes);
	}
	
	//logging/debug
	public static boolean getDebugEnabled() {
		return debugEnabled == null ? DEBUG_ENABLED : debugEnabled;
	}
	public static void setDebugEnabled(String debug) {
		if (debug == null) {
			Configuration.debugEnabled = null;
			return;
		}
		Configuration.debugEnabled = Boolean.valueOf(debug);
	}
	
	//Couch host, configurable
	public static String getCouchHost() {
		return couchHost == null ? COUCH_HOST : couchHost;
	}
	public static void setCouchHost(String couchHost) {
		Configuration.couchHost = couchHost;
	}
	
	//Couch protocol, configurable
	public static String getCouchProtocol() {
		return couchProtocol == null ? COUCH_PROTOCOL : couchProtocol;
	}
	public static void setCouchProtocol(String couchProtocol) {
		Configuration.couchProtocol = couchProtocol;
	}
	
	//Couch port, configurable
	public static int getCouchPort() {
		return couchPort == null ? COUCH_PORT : couchPort;
	}
	public static void setCouchPort(String serverPort) {
		if (serverPort == null) {
			Configuration.couchPort = null;
			return;
		}
		Configuration.couchPort = Integer.parseInt(serverPort);
	}
	
	//Couch username, configurable
	public static String getCouchUser() {
		return couchUser == null ? COUCH_USER : couchUser;
	}
	public static void setCouchUser(String couchUser) {
		Configuration.couchUser = couchUser;
	}
	
	//Couch password, configurable
	public static String getCouchPass() {
		return couchPass == null ? COUCH_PASS : couchPass;
	}
	public static void setCouchPass(String couchPass) {
		Configuration.couchPass = couchPass;
	}
	
	//Websocket host, configurable
	public static String getWebsocketTestHost() {
		return websocketTestHost == null ? WEBSOCKET_TEST_HOST : websocketTestHost;
	}
	public static void setWebsocketTestHost(String websocketHost) {
		Configuration.websocketTestHost = websocketHost;
	}
	
	//Websocket port, configurable
	public static int getWebsocketTestPort() {
		return websocketTestPort == null ? WEBSOCKET_TEST_PORT : websocketTestPort;
	}
	public static void setWebsocketTestPort(String serverPort) {
		if (serverPort == null) {
			Configuration.websocketTestPort = null;
			return;
		}
		Configuration.websocketTestPort = Integer.parseInt(serverPort);
	}
	
	//Websocket host, configurable
	public static String getWebsocketGameplayHost() {
		return websocketGameplayHost == null ? WEBSOCKET_GAMEPLAY_HOST : websocketGameplayHost;
	}
	public static void setWebsocketGameplayHost(String websocketHost) {
		Configuration.websocketGameplayHost = websocketHost;
	}
	
	//Websocket port, configurable
	public static int getWebsocketGameplayPort() {
		return websocketGameplayPort == null ? WEBSOCKET_GAMEPLAY_PORT : websocketGameplayPort;
	}
	public static void setWebsocketGameplayPort(String serverPort) {
		if (serverPort == null) {
			Configuration.websocketGameplayPort = null;
			return;
		}
		Configuration.websocketGameplayPort = Integer.parseInt(serverPort);
	}
	
	//Websocket threadpool size
	public static int getWebsocketThreadpoolSize() {
		return websocketThreadpoolSize== null ? WEBSOCKET_THREADPOOL_SIZE : websocketThreadpoolSize;
	}
	public static void setWebsocketThreadpoolSize(String poolSize) {
		if (poolSize == null) {
			Configuration.websocketThreadpoolSize = null;
			return;
		}
		Configuration.websocketThreadpoolSize = Integer.parseInt(poolSize);
	}
	
	//Share reward credits
	public static int getFacebookShareCredits() {
		return facebookShareCredits == null ? FACEBOOK_SHARE_CREDITS : facebookShareCredits;
	}
	public static void setFacebookShareCredits(String poolSize) {
		if (poolSize == null) {
			Configuration.facebookShareCredits = null;
			return;
		}
		Configuration.facebookShareCredits = Integer.parseInt(poolSize);
	}
	
	//Like reward credits
	public static int getFacebookLikeCredits() {
		return facebookLikeCredits == null ? FACEBOOK_LIKE_CREDITS : facebookLikeCredits;
	}
	public static void setFacebookLikeCredits(String poolSize) {
		if (poolSize == null) {
			Configuration.facebookLikeCredits = null;
			return;
		}
		Configuration.facebookLikeCredits = Integer.parseInt(poolSize);
	}
	
	//credits purchase
	public static String getCreditsProductGroup() {
		return creditsProductGroup == null ? CREDITS_PRODUCT_GROUP : creditsProductGroup;
	}
	public static void setCreditsProductGroup(String creditsProductGroup) {
		Configuration.creditsProductGroup = creditsProductGroup;
	}
	
	//gold purchase
	public static String getGoldProductGroup() {
		return goldProductGroup == null ? GOLD_PRODUCT_GROUP : goldProductGroup;
	}
	public static void setGoldProductGroup(String goldProductGroup) {
		Configuration.goldProductGroup = goldProductGroup;
	}
	
	//combo purchase
	public static String getComboProductGroup() {
		return comboProductGroup == null ? COMBO_PRODUCT_GROUP : comboProductGroup;
	}
	public static void setComboProductGroup(String productGroup) {
		Configuration.comboProductGroup = productGroup;
	}
	
	//email
	public static String getAdminEmailAddress() {
		return adminEmailAddress == null ? ADMIN_EMAIL_ADDRESS : adminEmailAddress;
	}
	public static void setAdminEmailAddress(String adminEmailAddress) {
		Configuration.adminEmailAddress = adminEmailAddress;
	}
	public static String getAdminEmailName() {
		return adminEmailName == null ? ADMIN_EMAIL_NAME : adminEmailName;
	}
	public static void setAdminEmailName(String adminEmailName) {
		Configuration.adminEmailName = adminEmailName;
	}
	public static String getEmailHost() {
		return emailHost == null ? EMAIL_HOST : emailHost;
	}
	public static void setEmailHost(String emailHost) {
		Configuration.emailHost = emailHost;
	}
	public static String getEmailPass() {
		return emailPass == null ? EMAIL_PASS : emailPass;
	}
	public static void setEmailPass(String emailPass) {
		Configuration.emailPass = emailPass;
	}
	public static boolean getEmailSecure() {
		return emailSecure == null ? EMAIL_SECURE : emailSecure;
	}
	public static void setEmailSecure(String secure) {
		if (secure == null) {
			Configuration.emailSecure = null;
			return;
		}
		Configuration.emailSecure = Boolean.valueOf(secure);
	}
}
