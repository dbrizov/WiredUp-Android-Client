package wiredup.client;

import wiredup.data.PersistersUnitOfWork;
import android.app.Application;

public class WiredUpApp extends Application {
	private static final String ROOT_URL = "http://wiredup.apphb.com/api/";
	
	private PersistersUnitOfWork data;
	
	public PersistersUnitOfWork getData() {
		if (this.data == null) {
			this.data = new PersistersUnitOfWork(ROOT_URL);
		}
		
		return this.data;
	}
}
