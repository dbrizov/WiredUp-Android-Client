package wiredup.utils;

import wiredup.data.PersistersUnitOfWork;

public class WiredUpApp {
	public static final String USER_ID_BUNDLE_KEY = "userId";
	
	private static final String ROOT_URL = "http://wiredup.apphb.com/api/";
	
	private static PersistersUnitOfWork data;
	private static String sessionKey;
	private static String userDisplayName;
	private static int userId;
	
	public static PersistersUnitOfWork getData() {
		if (WiredUpApp.data == null) {
			WiredUpApp.data = new PersistersUnitOfWork(ROOT_URL);
		}
		
		return WiredUpApp.data;
	}
	
	public static String getSessionKey() {
		return WiredUpApp.sessionKey;
	}
	
	public static void setSessionKey(String sessionKey) {
		WiredUpApp.sessionKey = sessionKey;
	}
	
	public static String getUserDisplayName() {
		return WiredUpApp.userDisplayName;
	}
	
	public static void setUserDisplayName(String displayName) {
		WiredUpApp.userDisplayName = displayName;
	}
	
	public static int getUserId() {
		return WiredUpApp.userId;
	}
	
	public static void setUserId(int id) {
		WiredUpApp.userId = id;
	}
}
