package wiredup.client;

import wiredup.data.DataPersister;
import wiredup.http.HttpRequester;
import android.app.Application;

public class WiredUpApp extends Application {
	private static final String ROOT_URL = "http://wiredup.apphb.com/api/";
	
	private DataPersister dataPersister;
	private HttpRequester httpRequester;
	
	public DataPersister getData() {
		if (this.dataPersister == null) {
			this.httpRequester = new HttpRequester();
			this.dataPersister = new DataPersister(ROOT_URL, this.httpRequester);
		}
		
		return this.dataPersister;
	}
}
