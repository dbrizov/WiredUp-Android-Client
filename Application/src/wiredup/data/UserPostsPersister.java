package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserPostCreateModel;

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
	
	/**
	 * Deletes a post with specified id
	 * @param postId - The id of the post that will be deleted
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void delete(int postId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdelete/%d?sessionKey=%s", this.rootUrl, postId, sessionKey);
		
		HttpDeleteTask delete = new HttpDeleteTask(url);
		delete.setOnSuccess(onSuccess);
		delete.setOnError(onError);
		
		delete.execute();
	}
	
	/**
	 * Gets the details of a post with specified id
	 * @param postId - The id of the post in the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getDetails(int postId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdetails/%d?sessionKey=%s", this.rootUrl, postId, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}
	
	/**
	 * Creates a new post for the current logged user
	 * @param model - The post model that contains the content of the post
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void create(UserPostCreateModel model, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%screate?sessionKey=%s", this.rootUrl, sessionKey);
		String jsonData = this.gson.toJson(model);
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);
		
		post.execute();
	}
}
