package wiredup.models;

public class UserModel {
	private int id;
	private String displayName;
	private String photo; // Base64 String
	
	public UserModel() {
		
	}
	
	public UserModel(int id, String displayName, String photo) {
		this.id = id;
		this.displayName = displayName;
		this.photo = photo;
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

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
