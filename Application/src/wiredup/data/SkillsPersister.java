package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.SkillModel;

public class SkillsPersister extends MainPersister {
	public SkillsPersister(String rootUrl) {
		super(rootUrl + "skills/");
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

	public void remove(SkillModel model, String sessionKey,
			IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sremove?sessionKey=%s", this.rootUrl, sessionKey);
		String jsonData = this.gson.toJson(model);
		
		HttpPutJsonTask put = new HttpPutJsonTask(url, jsonData);
		put.setOnSuccess(onSuccess);
		put.setOnError(onError);
		
		put.execute();
	}
}
