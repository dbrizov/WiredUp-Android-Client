package wiredup.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;

public class HttpActivity extends Activity {
	protected class GetJsonTask extends AsyncTask<Void, Void, ResponsePair> {
		private String requestUrl;
		private IOnSuccess onSuccess;
		private IOnError onError;

		public GetJsonTask(String requestUrl) {
			this.requestUrl = requestUrl;
		}

		public void setOnSuccess(IOnSuccess onSuccess) {
			this.onSuccess = onSuccess;
		}

		public void setOnEror(IOnError onError) {
			this.onError = onError;
		}

		@Override
		protected ResponsePair doInBackground(Void... params) {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(this.requestUrl);
			get.addHeader("content-type", "application/json");

			ResponsePair responsePair = new ResponsePair();

			try {
				HttpResponse response = client.execute(get);
				String jsonData = HttpActivity.this
						.getResponseContentAsString(response);
				int statusCode = response.getStatusLine().getStatusCode();

				responsePair.setJsonData(jsonData);
				responsePair.setStatusCode(statusCode);

				return responsePair;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return responsePair;
		}

		@Override
		protected void onPostExecute(ResponsePair responsePair) {
			super.onPostExecute(responsePair);

			HttpActivity.this.executeEvents(this.onSuccess, this.onError,
					responsePair);
		}
	}

	protected class PostJsonTask extends AsyncTask<Void, Void, ResponsePair> {
		private String requestUrl;
		private String jsonString;
		private IOnSuccess onSuccess;
		private IOnError onError;

		public PostJsonTask(String requestUrl, String jsonString) {
			this.requestUrl = requestUrl;
			this.jsonString = jsonString;
		}

		public void setOnSuccess(IOnSuccess onSuccess) {
			this.onSuccess = onSuccess;
		}

		public void setOnEror(IOnError onError) {
			this.onError = onError;
		}

		@Override
		protected ResponsePair doInBackground(Void... params) {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(this.requestUrl);
			post.addHeader("content-type", "application/json");

			try {
				post.setEntity(new StringEntity(this.jsonString));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			ResponsePair responsePair = new ResponsePair();

			try {
				HttpResponse response = client.execute(post);
				String jsonData = HttpActivity.this
						.getResponseContentAsString(response);
				int statusCode = response.getStatusLine().getStatusCode();

				responsePair.setJsonData(jsonData);
				responsePair.setStatusCode(statusCode);

				return responsePair;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return responsePair;
		}

		@Override
		protected void onPostExecute(ResponsePair responsePair) {
			super.onPostExecute(responsePair);

			HttpActivity.this.executeEvents(this.onSuccess, this.onError,
					responsePair);
		}
	}

	protected class PutJsonTask extends AsyncTask<Void, Void, ResponsePair> {
		private String requestUrl;
		private String jsonString;
		private IOnSuccess onSuccess;
		private IOnError onError;

		public PutJsonTask(String requestUrl, String jsonString) {
			this.requestUrl = requestUrl;
			this.jsonString = jsonString;
		}

		public void setOnSuccess(IOnSuccess onSuccess) {
			this.onSuccess = onSuccess;
		}

		public void setOnEror(IOnError onError) {
			this.onError = onError;
		}

		@Override
		protected ResponsePair doInBackground(Void... params) {
			HttpClient client = new DefaultHttpClient();
			HttpPut put = new HttpPut(this.requestUrl);
			put.addHeader("content-type", "application/json");

			try {
				put.setEntity(new StringEntity(this.jsonString));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			ResponsePair responsePair = new ResponsePair();

			try {
				HttpResponse response = client.execute(put);
				String jsonData = HttpActivity.this
						.getResponseContentAsString(response);
				int statusCode = response.getStatusLine().getStatusCode();

				responsePair.setJsonData(jsonData);
				responsePair.setStatusCode(statusCode);

				return responsePair;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return responsePair;
		}

		@Override
		protected void onPostExecute(ResponsePair responsePair) {
			super.onPostExecute(responsePair);

			HttpActivity.this.executeEvents(this.onSuccess, this.onError,
					responsePair);
		}
	}

	private void executeEvents(IOnSuccess onSuccess, IOnError onError,
			ResponsePair responsePair) {
		String jsonData = responsePair.getJsonData();
		int statusCode = responsePair.getStatusCode();

		if (statusCode / 100 == 4 || statusCode / 100 == 5 || statusCode == 0) {
			onError.performAction(jsonData);
		} else {
			onSuccess.performAction(jsonData);
		}
	}

	private String getResponseContentAsString(HttpResponse response) {
		String stringContent = null;

		try {
			InputStream inputStream = response.getEntity().getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			StringBuilder stringContentBuilder = new StringBuilder();
			String line = bufferedReader.readLine();

			while (line != null) {
				stringContentBuilder.append(line);
				line = bufferedReader.readLine();
			}

			stringContent = stringContentBuilder.toString();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return stringContent;
	}
}
