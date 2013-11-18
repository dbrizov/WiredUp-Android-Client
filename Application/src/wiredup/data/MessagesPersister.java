package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.MessageSendModel;

public class MessagesPersister extends MainPersister {
	public MessagesPersister(String rootUrl) {
		super(rootUrl + "messages/");
	}

	/**
	 * Sends a new message
	 * @param model - The model that contains the message content and receiver id
	 * @param sessionKey - The sessionKey of the user sending the message
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void send(MessageSendModel model, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%ssend?sessionKey=%s", this.rootUrl,
				sessionKey);
		String jsonData = this.gson.toJson(model);

		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}

	/**
	 * Gets all of the messages for the current user
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
	 * Gets the sent messages for the current user
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getSent(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%ssent?sessionKey=%s", this.rootUrl, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
	
	/**
	 * Gets the received messages for the current user
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getReceived(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sreceived?sessionKey=%s", this.rootUrl, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
	
	/**
	 * Gets the details of a specified message
	 * @param messageId - The id of the message in the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getDetails(int messageId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdetails/%d?sessionKey=%s", this.rootUrl, messageId, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
	
	/**
	 * Deletes a specified message. The user can delete only his messages
	 * @param messageId - The id of the message in the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void delete(int messageId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format(String.format("%sdelete/%d?sessionKey=%s", this.rootUrl, messageId, sessionKey));
		
		HttpDeleteTask delete = new HttpDeleteTask(url);
		delete.setOnSuccess(onSuccess);
		delete.setOnError(onError);
		
		delete.execute();
	}
}
