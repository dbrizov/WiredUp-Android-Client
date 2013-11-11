package wiredup.models;

public class UserEditModel {
	private String languages;
	private int countryId;
	private byte[] photo;
	private String aboutMe;
	
	public UserEditModel() {
		
	}
	
	public UserEditModel(String languages, int countryId, byte[] photo, String aboutMe) {
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

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getAboutMe() {
		return this.aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
}
