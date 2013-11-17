package wiredup.models;

public class ConnectionModel {
	private int id;
	private int otherUserId;
	private String otherUserDisplayName;
	private String otherUserPhoto; // Base64 string
	
	public ConnectionModel() {
		
	}
	
	public ConnectionModel(int id, int otherUserId, String otherUserDisplayName, String otherUserPhoto) {
		this.id = id;
		this.otherUserId = otherUserId;
		this.otherUserDisplayName = otherUserDisplayName;
		this.otherUserPhoto = otherUserPhoto;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOtherUserId() {
		return this.otherUserId;
	}

	public void setOtherUserId(int otherUserId) {
		this.otherUserId = otherUserId;
	}

	public String getOtherUserDisplayName() {
		return this.otherUserDisplayName;
	}

	public void setOtherUserDisplayName(String otherUserDisplayName) {
		this.otherUserDisplayName = otherUserDisplayName;
	}

	public String getOtherUserPhoto() {
		return this.otherUserPhoto;
	}

	public void setOtherUserPhoto(String otherUserPhoto) {
		this.otherUserPhoto = otherUserPhoto;
	}
}
