package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.utils.WiredUpApp;

public class CertificatesPersister extends MainPersister {
	public CertificatesPersister(String rootUrl) {
		super(rootUrl + "certificates/");
	}

	public void getAllForUser(int userId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?userId=%d&sessionKey=%s",
				this.rootUrl, userId, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}

	public void delete(int certificateId, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sdelete/%d?sessionKey=%s", this.rootUrl,
				certificateId, WiredUpApp.getSessionKey());
		
		HttpDeleteTask delete = new HttpDeleteTask(url);
		delete.setOnSuccess(onSuccess);
		delete.setOnError(onError);
		
		delete.execute();
	}
}
