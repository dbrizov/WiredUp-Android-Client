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

	public void register(UserRegisterModel model, IOnSuccess onSuccess,
			IOnError onError) {
		String url = this.rootUrl + "register";
		String jsonData = this.gson.toJson(model);

		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}

	public void login(UserLoginModel model, IOnSuccess onSuccess,
			IOnError onError) {
		String url = this.rootUrl + "login";
		String jsonData = this.gson.toJson(model);

		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}

	public void logout(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%slogout?sessionKey=%s", this.rootUrl,
				sessionKey);

		HttpPutJsonTask put = new HttpPutJsonTask(url, "{}");
		put.setOnSuccess(onSuccess);
		put.setOnError(onError);

		put.execute();
	}

	public void getDetails(int userId, String sessionKey, IOnSuccess onSuccess,
			IOnError onError) {
		String url = String.format("%sdetails?userId=%d&sessionKey=%s",
				this.rootUrl, userId, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}

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
