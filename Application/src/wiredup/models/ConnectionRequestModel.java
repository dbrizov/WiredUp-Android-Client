package wiredup.models;

public class ConnectionRequestModel {
	private int receivedId;
	
	public ConnectionRequestModel(int receiverId) {
		this.receivedId = receiverId;
	}

	public int getReceivedId() {
		return this.receivedId;
	}

	public void setReceivedId(int receivedId) {
		this.receivedId = receivedId;
	}
}
