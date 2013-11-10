package wiredup.models;

import org.joda.time.DateTime;

public class UserPostModel {
	private int id;
	private String content;
	private String postedBy;
	private DateTime postDate;
	
	public UserPostModel() {
		
	}
	
	public UserPostModel(int id, String content, String postedBy, DateTime postDate) {
		this.id = id;
		this.content = content;
		this.postedBy = postedBy;
		this.postDate = postDate;
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

	public String getPostedBy() {
		return this.postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public DateTime getPostDate() {
		return this.postDate;
	}

	public void setPostDate(DateTime postDate) {
		this.postDate = postDate;
	}
}
