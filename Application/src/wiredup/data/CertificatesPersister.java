package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CertificateAddModel;
import wiredup.utils.WiredUpApp;

public class CertificatesPersister extends MainPersister {
	public CertificatesPersister(String rootUrl) {
		super(rootUrl + "certificates/");
	}

	/**
	 * Gets all certificate for a specific user
	 * @param userId - The id of the user whom certificates will be read from the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getAllForUser(int userId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?userId=%d&sessionKey=%s",
				this.rootUrl, userId, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
	
	/**
	 * Deletes a certificate - The current user can delete only certificates
	 * that are his and only his
	 * @param certificateId - The id of the certificate which will be deleted
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void delete(int certificateId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdelete/%d?sessionKey=%s", this.rootUrl,
				certificateId, WiredUpApp.getSessionKey());

		HttpDeleteTask delete = new HttpDeleteTask(url);
		delete.setOnSuccess(onSuccess);
		delete.setOnError(onError);

		delete.execute();
	}

	/**
	 * Adds a new certificate for the current user
	 * @param model - The certificate model that contains the data which will be send to the database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void add(CertificateAddModel model, String sessioKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sadd?sessionKey=%s", this.rootUrl,
				sessioKey);
		String jsonData = this.gson.toJson(model);

		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);

		post.execute();
	}

	/**
	 * Gets the details for a specific certificate
	 * @param certificateId - The id of the certificate whom details will be read from read database
	 * @param sessionKey - The sessionKey of the current user
	 * @param onSuccess - onSuccess event handler
	 * @param onError - onError event handler
	 */
	public void getDetails(int certificateId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdetails/%d?sessionKey=%s", this.rootUrl,
				certificateId, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
}
