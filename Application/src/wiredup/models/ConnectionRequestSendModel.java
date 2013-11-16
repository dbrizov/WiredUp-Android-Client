package wiredup.models;

public class ConnectionRequestSendModel {
	private int receiverId;
	
	public ConnectionRequestSendModel(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(int receivedId) {
		this.receiverId = receivedId;
	}
}
