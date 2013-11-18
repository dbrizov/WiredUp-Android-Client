package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ProjectAddModel;

public class ProjectsPersister extends MainPersister {
	public ProjectsPersister(String rootUrl) {
		super(rootUrl + "projects/");
	}
	
	/**
	 * Adds a new post to a user with a specified session key
	 * @param model - The model that will be send to the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void add(ProjectAddModel model, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sadd?sessionKey=%s", this.rootUrl, sessionKey);
		String jsonData = this.gson.toJson(model);
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);
		
		post.execute();
	}
	
	/**
	 * Gets all projects of a specified user
	 * @param userId - The id of the user whom projects will be read from the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getAll(int userId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?userId=%d&sessionKey=%s", this.rootUrl, userId, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}
	
	/**
	 * Gets the details of a specified project
	 * @param projectId - The id of the project in the databaseprojectId
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getDetails(int projectId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdetails/%d?sessionKey=%s", this.rootUrl, projectId, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}
	
	/**
	 * Deletes a specified project
	 * @param projectId - The id of the project which will be deleted from the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void delete(int projectId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdelete/%d?sessionKey=%s", this.rootUrl, projectId, sessionKey);
		
		HttpDeleteTask delete = new HttpDeleteTask(url);
		delete.setOnSuccess(onSuccess);
		delete.setOnError(onError);
		
		delete.execute();
	}
}
