package wiredup.data;

public class PersistersUnitOfWork {
	private String rootUrl;
	private UsersPersister users;
	
	public PersistersUnitOfWork(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	public UsersPersister getUsers() {
		if (this.users == null) {
			this.users = new UsersPersister(this.rootUrl);
		}
		
		return this.users;
	}
}
