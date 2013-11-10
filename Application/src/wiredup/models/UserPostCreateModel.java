package wiredup.models;

public class UserPostCreateModel {
	private String content;
	
	public UserPostCreateModel(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
