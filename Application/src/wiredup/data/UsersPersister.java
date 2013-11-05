package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserRegisterModel;

public class UsersPersister extends MainPersister {
	public UsersPersister(String rootUrl) {
		super(rootUrl + "users/");
	}
	
	public void register(UserRegisterModel model, IOnSuccess onSuccess, IOnError onError) {
		String jsonData = this.gson.toJson(model);
		String url = this.rootUrl + "register";
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnEror(onError);
		
		post.execute();
	}
}
