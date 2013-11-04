package wiredup.models;

public class UserRegisterModel {
	private String firstName;
	private String lastName;
	private String email;
	private String authCode;
	private String confirmAuthCode;
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	public String getConfirmAuthCode() {
		return this.confirmAuthCode;
	}
	
	public void setConfirmAuthCode(String confirmAuthCode) {
		this.confirmAuthCode = confirmAuthCode;
	}
}
