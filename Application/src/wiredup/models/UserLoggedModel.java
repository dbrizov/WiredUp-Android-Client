package wiredup.models;

public class UserLoggedModel {
	private int id;
	private String displayName;
	private String sessionKey;
	
	public UserLoggedModel() {
		
	}
	
	public UserLoggedModel(int id, String displayName, String sessionKey) {
		this.id = id;
		this.displayName = displayName;
		this.sessionKey = sessionKey;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getSessionKey() {
		return this.sessionKey;
	}
	
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}
