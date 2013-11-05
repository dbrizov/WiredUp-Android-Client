package wiredup.models;

public class UserLoginModel {
	private String email;
	private String authCode;
	
	public UserLoginModel(String email, String authCode) {
		this.email = email;
		this.authCode = authCode;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAuthCode() {
		return this.authCode;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
}
