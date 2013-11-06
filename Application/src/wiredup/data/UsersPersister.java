package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserLoginModel;
import wiredup.models.UserRegisterModel;
import wiredup.utils.WiredUpApp;

public class UsersPersister extends MainPersister {
	public UsersPersister(String rootUrl) {
		super(rootUrl + "users/");
	}
	
	public void register(UserRegisterModel model, IOnSuccess onSuccess, IOnError onError) {
		String url = this.rootUrl + "register";
		String jsonData = this.gson.toJson(model);
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);
		
		post.execute();
	}
	
	public void login(UserLoginModel model, IOnSuccess onSuccess, IOnError onError) {
		String url = this.rootUrl + "login";
		String jsonData = this.gson.toJson(model);
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);
		
		post.execute();
	}
	
	public void logout(IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%slogout?sessionKey=%s", this.rootUrl, WiredUpApp.getSessionKey());
		
		HttpPutJsonTask put = new HttpPutJsonTask(url, "{}");
		put.setOnSuccess(onSuccess);
		put.setOnError(onError);
		
		put.execute();
	}
}
