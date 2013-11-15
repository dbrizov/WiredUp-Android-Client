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
		String url = String.format("%ssend?sessionKey=%s", this.rootUrl, sessionKey);
		String jsonData = this.gson.toJson(model);
		
		HttpPostJsonTask post = new HttpPostJsonTask(url, jsonData);
		post.setOnSuccess(onSuccess);
		post.setOnError(onError);
		
		post.execute();
	}
}
