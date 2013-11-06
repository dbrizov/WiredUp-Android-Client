package wiredup.utils;

import wiredup.data.PersistersUnitOfWork;

public class WiredUpApp {
	private static final String ROOT_URL = "http://wiredup.apphb.com/api/";
	
	private static PersistersUnitOfWork data;
	private static String sessionKey;
	
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
}
