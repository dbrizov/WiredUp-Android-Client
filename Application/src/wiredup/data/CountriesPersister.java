package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;

public class CountriesPersister extends MainPersister {
	public CountriesPersister(String rootUrl) {
		super(rootUrl + "countries/");
	}
	
	public void getAll(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?sessionKey=%s", this.rootUrl, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}
}
