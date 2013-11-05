package wiredup.data;

import com.google.gson.Gson;

import wiredup.http.HttpRequester;

public class MainPersister extends HttpRequester {
	protected String rootUrl;
	protected Gson gson;
	
	public MainPersister(String rootUrl) {
		this.rootUrl = rootUrl;
		this.gson = new Gson();
	}
}
