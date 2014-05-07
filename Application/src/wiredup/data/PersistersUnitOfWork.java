package wiredup.data;

public class PersistersUnitOfWork {
	private String rootUrl;
	private UsersPersister users;
	private SkillsPersister skills;
	private CertificatesPersister certificates;
	private UserPostsPersister userPosts;
	private CountriesPersister countries;
	private MessagesPersister messages;
	private ConnectionRequestsPersister connectionRequests;
	private ConnectionsPersister connections;
	private ProjectsPersister projects;
	
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
	
	public MessagesPersister getMessages() {
		if (this.messages == null) {
			this.messages = new MessagesPersister(this.rootUrl);
		}
		
		return this.messages;
	}
	
	public ConnectionRequestsPersister getConnectionRequests() {
		if (this.connectionRequests == null) {
			this.connectionRequests = new ConnectionRequestsPersister(this.rootUrl);
		}
		
		return this.connectionRequests;
	}
	
	public ConnectionsPersister getConnections() {
		if (this.connections == null) {
			this.connections = new ConnectionsPersister(this.rootUrl);
		}
		
		return this.connections;
	}
	
	public ProjectsPersister getProjects() {
		if (this.projects == null) {
			this.projects = new ProjectsPersister(this.rootUrl);
		}
		
		return this.projects;
	}
}
