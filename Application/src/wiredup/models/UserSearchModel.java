package wiredup.models;

public class UserSearchModel {
	private String query;
	
	public UserSearchModel(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
}
