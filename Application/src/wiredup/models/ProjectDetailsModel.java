package wiredup.models;

import java.util.List;

public class ProjectDetailsModel {
	private String name;
	private String description;
	private String url;
	private List<String> members;
	
	public ProjectDetailsModel() {
		
	}
	
	public ProjectDetailsModel(String name, String description, String url, List<String> members) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.members = members;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getMembers() {
		return this.members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}
}
