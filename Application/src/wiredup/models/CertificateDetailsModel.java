package wiredup.models;

public class CertificateDetailsModel {
	private String name;
	private String url;
	private String owner;
	
	public CertificateDetailsModel() {
		
	}
	
	public CertificateDetailsModel(String name, String url, String owner) {
		this.name = name;
		this.url = url;
		this.owner = owner;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
