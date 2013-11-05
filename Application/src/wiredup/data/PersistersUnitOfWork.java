package wiredup.data;

public class PersistersUnitOfWork {
	private String rootUrl;
	private UsersPersister users;
	
	public PersistersUnitOfWork(String rootUrl) {
		this.rootUrl = rootUrl;
		this.users = new UsersPersister(this.rootUrl);
	}
	
	public UsersPersister getUsers() {
		return this.users;
	}
}
