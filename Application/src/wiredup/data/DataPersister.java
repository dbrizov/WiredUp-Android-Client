package wiredup.data;

import com.google.gson.Gson;

import wiredup.http.HttpRequester;

public class DataPersister {
	private HttpRequester httpRequester;
	private String rootUrl;
	private UsersPersister users;
	private Gson gson;
	
	public DataPersister(String rootUrl, HttpRequester httpRequester) {
		this.httpRequester = httpRequester;
		this.rootUrl = rootUrl;
		this.gson = new Gson();
		this.users = new UsersPersister(this.rootUrl, this.httpRequester, this.gson);
	}
	
	public String getRootUrl() {
		return this.rootUrl;
	}
	
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	public HttpRequester getHttpRequester() {
		return this.httpRequester;
	}
	
	public void setHttpRequester(HttpRequester httpRequester) {
		this.httpRequester = httpRequester;
	}
	
	public UsersPersister users() {
		return this.users;
	}
}
