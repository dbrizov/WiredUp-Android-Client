package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ConnectionRequestSendModel;

public class ConnectionRequestsPersister extends MainPersister {
	public ConnectionRequestsPersister(String rootUrl) {
		super(rootUrl + "connectionRequests/");
	}

	/**
	 * Sends a connection request to a specific user
	 * @param model - The model that contains the id of the user to who the request will be send
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void send(ConnectionRequestSendModel model, String sessionKey,
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
	 * The current user accepts a connection request with a specific id
	 * @param connectionRequestId - The id of the connection request
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void accept(int connectionRequestId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%saccept/%d?sessionKey=%s", this.rootUrl,
				connectionRequestId, sessionKey);
		String jsonData = "{}";
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}
	
	/**
	 * The current user declines a connection request with a specific id
	 * @param connectionRequestId - The id of the connection request
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void decline(int connectionRequestId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdecline/%d?sessionKey=%s", this.rootUrl,
				connectionRequestId, sessionKey);
		String jsonData = "{}";
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}
	
	/**
	 * Gets all connection requests that need to be accepted or declined from the current user/**
	 * The current user accepts a connection request with a specific id
	 * @param connectionRequestId - The id of the connection request
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
}
