package wiredup.data;

public class PersistersUnitOfWork {
	private String rootUrl;
	private UsersPersister users;
	private SkillsPersister skills;
	private CertificatesPersister certificates;
	private UserPostsPersister userPosts;
	private CountriesPersister countries;
	
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
	
	public CertificatesPersister getCertificates() {
		if (this.certificates == null) {
			this.certificates = new CertificatesPersister(this.rootUrl);
		}
		
		return this.certificates;
	}
	
	public UserPostsPersister getUserPosts() {
		if (this.userPosts == null) {
			this.userPosts = new UserPostsPersister(this.rootUrl);
		}
		
		return this.userPosts;
	}
	
	public CountriesPersister getCountries() {
		if (this.countries == null) {
			this.countries = new CountriesPersister(this.rootUrl);
		}
		
		return this.countries;
	}
}
