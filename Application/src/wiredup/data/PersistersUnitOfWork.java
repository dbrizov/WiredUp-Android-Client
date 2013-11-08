package wiredup.data;

public class PersistersUnitOfWork {
	private String rootUrl;
	private UsersPersister users;
	private SkillsPersister skills;
	
	public PersistersUnitOfWork(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	public UsersPersister getUsers() {
		if (this.users == null) {
			this.users = new UsersPersister(this.rootUrl);
		}
		
		return this.users;
	}
	
	public SkillsPersister getSkills() {
		if (this.skills == null) {
			this.skills = new SkillsPersister(this.rootUrl);
		}
		
		return this.skills;
	}
}
