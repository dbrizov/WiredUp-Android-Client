package wiredup.models;

import java.io.Serializable;
import java.util.Date;

public class MessageModel implements Serializable {
	/**
	 * The message model is Serializable, because that way I can pass it
	 * to and intent as a Serializable extra
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String content;
	private int senderId;
	private String senderName;
	private int receiverId;
	private String receiverName;
	private Date postDate;
	
	public MessageModel() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getSenderId() {
		return this.senderId;
	}
	
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public int getReceiverId() {
		return this.receiverId;
	}
	
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Date getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
}
