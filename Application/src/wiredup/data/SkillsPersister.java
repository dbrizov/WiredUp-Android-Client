package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.SkillModel;

public class SkillsPersister extends MainPersister {
	public SkillsPersister(String rootUrl) {
		super(rootUrl + "skills/");
	}

	/**
	 * Gets all skills for a specific user
	 * @param userId - The id of the user whom skills will be read from the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getAllForUser(int userId, String sessionKey, IOnSuccess onSuccess,
			IOnError onError) {
		String url = String.format("%sall?userId=%d&sessionKey=%s",
				this.rootUrl, userId, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
	
	/**
	 * Gets all skills from the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getAll(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?sessionKey=%s", this.rootUrl, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}

	/**
	 * Removes a skill from a user. The skill is only removed from the user
	 * profile, not deleted from the entire database
	 * @param model - The model which contains the data that needs to be send to the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void remove(SkillModel model, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sremove?sessionKey=%s", this.rootUrl, sessionKey);
		String jsonData = this.gson.toJson(model);
		
		HttpPutJsonTask put = new HttpPutJsonTask(url, jsonData);
		put.setOnSuccess(onSuccess);
		put.setOnError(onError);
		
		put.execute();
	}
	
	/**
	 * Adds a new skill for a user
	 * @param model - The model that contains the data that needs to be send to the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void add(SkillModel model, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sadd?sessionKey=%s", this.rootUrl, sessionKey);
		String jsonData = this.gson.toJson(model);
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);
		
		post.execute();
	}
}
