package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;

public class CertificatesPersister extends MainPersister {
	public CertificatesPersister(String rootUrl) {
		super(rootUrl + "certificates/");
	}

	public void getAll(int userId, String sessionKey, IOnSuccess onSuccess,
			IOnError onError) {
		String url = String.format("%sall?userId=%d&sessionKey=%s",
				this.rootUrl, userId, sessionKey);
		
		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);
		
		get.execute();
	}
}
