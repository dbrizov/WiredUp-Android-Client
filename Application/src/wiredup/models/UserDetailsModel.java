package wiredup.models;

import java.util.List;

public class UserDetailsModel {
	private String displayName;
	private String email;
	private byte[] photo;
	private String country;
	private List<String> skills;
	private String languages;
	private List<CertificateModel> certificates;
	private List<ProjectModel> projects;
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public byte[] getPhoto() {
		return this.photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getLanguages() {
		return this.languages;
	}
	
	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public List<String> getSkills() {
		return this.skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<CertificateModel> getCertificates() {
		return this.certificates;
	}

	public void setCertificates(List<CertificateModel> certificates) {
		this.certificates = certificates;
	}

	public List<ProjectModel> getProjects() {
		return this.projects;
	}

	public void setProjects(List<ProjectModel> projects) {
		this.projects = projects;
	}
}
