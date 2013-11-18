package wiredup.models;

import java.util.List;

public class ProjectAddModel {
	private String name;
	private String description;
	private String url;
	private List<Integer> membersIds;
	
	public ProjectAddModel() {
		
	}
	
	public ProjectAddModel(String name, String description, String url, List<Integer> membersIds) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.membersIds = membersIds;
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

	public List<Integer> getMembersIds() {
		return this.membersIds;
	}

	public void setMembersIds(List<Integer> membersIds) {
		this.membersIds = membersIds;
	}
}
