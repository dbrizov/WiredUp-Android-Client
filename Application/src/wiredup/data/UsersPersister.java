package wiredup.data;

public class UsersPersister {
	private String rootUrl;
	
	public UsersPersister(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	public String getRootUrl() {
		return this.rootUrl;
	}
	
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
}
