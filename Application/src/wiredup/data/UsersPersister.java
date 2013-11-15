package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserEditModel;
import wiredup.models.UserLoginModel;
import wiredup.models.UserRegisterModel;
import wiredup.models.UserSearchModel;

public class UsersPersister extends MainPersister {
	public UsersPersister(String rootUrl) {
		super(rootUrl + "users/");
	}

	/**
	 * Registers a new user
	 * @param model - The models which holds the data that needs to be send to the database:
	 * First Name, Last Name...
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void register(UserRegisterModel model, IOnSuccess onSuccess,
			IOnError onError) {
		String url = this.rootUrl + "register";
		String jsonData = this.gson.toJson(model);

		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}

	/**
	 * Logins a user
	 * @param model - The model which holds the data that needs to be send to the database
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void login(UserLoginModel model, IOnSuccess onSuccess,
			IOnError onError) {
		String url = this.rootUrl + "login";
		String jsonData = this.gson.toJson(model);

		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}

	/**
	 * Log outs the current logged user
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void logout(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%slogout?sessionKey=%s", this.rootUrl,
				sessionKey);

		HttpPutJsonTask put = new HttpPutJsonTask(url, "{}");
		put.setOnSuccess(onSuccess);
		put.setOnError(onError);

		put.execute();
	}

	/**
	 * Gets the details of a specific user
	 * @param userId - The id of the user whom details will be read from the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getDetails(int userId, String sessionKey, IOnSuccess onSuccess,
			IOnError onError) {
		String url = String.format("%sdetails?userId=%d&sessionKey=%s",
				this.rootUrl, userId, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}

	/**
	 * Edits a user
	 * @param model - The model which holds that data that needs to be send to the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void edit(UserEditModel model, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sedit?sessionKey=%s", this.rootUrl,
				sessionKey);
		String jsonData = this.gson.toJson(model);

		HttpPutJsonTask put = new HttpPutJsonTask(url, jsonData);
		put.setOnSuccess(onSuccess);
		put.setOnError(onError);

		put.execute();
	}

	/**
	 * Searches for a user. The search is done by name and/or email
	 * @param model - The model which holds the query data that needs to be send to the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void search(UserSearchModel model, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%ssearch?sessionKey=%s", this.rootUrl, sessionKey);
		String jsonData = this.gson.toJson(model);

		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}
}
