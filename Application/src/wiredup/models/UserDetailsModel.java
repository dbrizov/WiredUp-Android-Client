package wiredup.models;

import java.io.Serializable;

public class UserDetailsModel implements Serializable {
	/**
	 * Made it serializable in order to pass it as Intent Extra
	 */
	private static final long serialVersionUID = 1L;
	private String displayName;
	private String email;
	private String photo; // Base64 String
	private String country;
	private String aboutMe;
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
	
	public String getPhoto() {
		return this.photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getAboutMe() {
		return this.aboutMe;
	}
	
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public String getLanguages() {
		return this.languages;
	}
	
	public void setLanguages(String languages) {
		this.languages = languages;
	}
}
