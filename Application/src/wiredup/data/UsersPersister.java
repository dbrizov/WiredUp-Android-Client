package wiredup.data;

import com.google.gson.Gson;

import wiredup.http.HttpRequester;

public class UsersPersister {
	private HttpRequester httpRequester;
	private String rootUrl;
	private Gson gson;
	
	public UsersPersister(String rootUrl, HttpRequester httpRequester, Gson gson) {
		this.httpRequester = httpRequester;
		this.rootUrl = rootUrl;
		this.gson = gson;
	}
	
	public String getRootUrl() {
		return this.rootUrl;
	}
	
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
}
