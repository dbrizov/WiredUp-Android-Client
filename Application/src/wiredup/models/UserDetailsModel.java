package wiredup.models;

public class UserDetailsModel {
	private String displayName;
	private String email;
	private byte[] photo;
	private String country;
	private String languages;
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public byte[] getPhoto() {
		return this.photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getLanguages() {
		return this.languages;
	}
	
	public void setLanguages(String languages) {
		this.languages = languages;
	}
}
