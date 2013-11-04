package wiredup.models;

public class UserChangePasswordModel {
	private String oldAuthCode;
	private String newAuthCode;
	private String confirmNewAuthCode;

	public UserChangePasswordModel() {

	}

	public UserChangePasswordModel(String oldAuthCode, String newAuthCode,
			String confirmAuthCode) {
		this.oldAuthCode = oldAuthCode;
		this.newAuthCode = newAuthCode;
		this.confirmNewAuthCode = confirmAuthCode;
	}

	public String getOldAuthCode() {
		return this.oldAuthCode;
	}

	public void setOldAuthCode(String oldAuthCode) {
		this.oldAuthCode = oldAuthCode;
	}

	public String getNewAuthCode() {
		return this.newAuthCode;
	}

	public void setNewAuthCode(String newAuthCode) {
		this.newAuthCode = newAuthCode;
	}

	public String getConfirmNewAuthCode() {
		return this.confirmNewAuthCode;
	}
	
	public void setConfirmNewAuthCode(String confirmNewAuthCode) {
		this.confirmNewAuthCode = confirmNewAuthCode;
	}
}
