package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;

public class ConnectionsPersister extends MainPersister {
	public ConnectionsPersister(String rootUrl) {
		super(rootUrl + "connections/");
	}
	
	/**
	 * Gets all connections for the current user
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
	 * Deletes a connection from the current user's connections
	 * @param connectionId - The id of the connection in the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void delete(int connectionId, String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdelete/%d?sessionKey=%s", this.rootUrl,
				connectionId, sessionKey);
		
		HttpDeleteTask delete = new HttpDeleteTask(url);
		delete.setOnSuccess(onSuccess);
		delete.setOnError(onError);
		
		delete.execute();
	}
}
