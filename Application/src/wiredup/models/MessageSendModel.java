package wiredup.models;

public class MessageSendModel {
	private int receiverId;
	private String content;
	
	public MessageSendModel(int receiverId, String content) {
		this.receiverId = receiverId;
		this.content = content;
	}

	public int getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
