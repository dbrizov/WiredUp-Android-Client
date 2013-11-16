package wiredup.models;

public class ConnectionRequestModel {
	private int receiverId;
	
	public ConnectionRequestModel(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(int receivedId) {
		this.receiverId = receivedId;
	}
}
