package wiredup.models;

import java.util.Date;

public class UserPostModel {
	private int id;
	private String content;
	private String postedBy;
	private Date postDate;
	
	public UserPostModel() {
		
	}
	
	public UserPostModel(int id, String content, String postedBy, Date postDate) {
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

	public Date getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
}
