package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;

public class UserPostsPersister extends MainPersister {
	public UserPostsPersister(String rootUrl) {
		super(rootUrl + "userposts/");
	}

	/**
	 * Gets all posts for a specific user
	 * @param userId - The id of the user whom posts will be read from the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getAllForUser(int userId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?userId=%d&sessionKey=%s", this.rootUrl, userId, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}
}
