package wiredup.models;

public class ConnectionRequestModel {
	private int id;
	private int senderId;
	private String senderDisplayName;
	
	public ConnectionRequestModel(int id, int senderId, String senderDisplayName) {
		this.id = id;
		this.senderId = senderId;
		this.senderDisplayName = senderDisplayName;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getSenderDisplayName() {
		return this.senderDisplayName;
	}

	public void setSenderDisplayName(String senderDisplayName) {
		this.senderDisplayName = senderDisplayName;
	}
}
