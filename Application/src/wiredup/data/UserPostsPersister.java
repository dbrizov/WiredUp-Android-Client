package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;

public class UserPostsPersister extends MainPersister {
	public UserPostsPersister(String rootUrl) {
		super(rootUrl + "userposts/");
	}

	public void getAllForUser(int userId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?userId=%d&sessionKey=%s", this.rootUrl, userId, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}
}
