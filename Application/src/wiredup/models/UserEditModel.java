package wiredup.models;

public class UserEditModel {
	private String languages;
	private int countryId;
	private short[] photo;
	private String aboutMe;
	
	public UserEditModel() {
		
	}
	
	public UserEditModel(String languages, int countryId, short[] photo, String aboutMe) {
		this.languages = languages;
		this.countryId = countryId;
		this.photo = photo;
		this.aboutMe = aboutMe;
	}

	public String getLanguages() {
		return this.languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public short[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(short[] photo) {
		this.photo = photo;
	}

	public String getAboutMe() {
		return this.aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
}
