package wiredup.data;

import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.MessageSendModel;

public class MessagesPersister extends MainPersister {
	public MessagesPersister(String rootUrl) {
		super(rootUrl + "messages/");
	}

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

	public void getAll(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sall?sessionKey=%s", this.rootUrl, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}

	public void getSent(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%ssent?sessionKey=%s", this.rootUrl, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
	
	public void getReceived(String sessionKey, IOnSuccess onSuccess, IOnError onError) {
		String url = String.format("%sreceived?sessionKey=%s", this.rootUrl, sessionKey);

		HttpGetJsonTask get = new HttpGetJsonTask(url);
		get.setOnSuccess(onSuccess);
		get.setOnError(onError);

		get.execute();
	}
}
