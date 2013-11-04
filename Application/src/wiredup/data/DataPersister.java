package wiredup.data;

public class DataPersister {
	private String rootUrl;
	private UsersPersister users;
	
	public DataPersister(String rootUrl) {
		this.rootUrl = rootUrl;
		this.users = new UsersPersister(this.rootUrl);
	}
	
	public String getRootUrl() {
		return this.rootUrl;
	}
	
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	public UsersPersister getUsers() {
		return this.users;
	}
}
