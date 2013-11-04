package wiredup.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

			String jsonData = responsePair.getJsonData();
			int statusCode = responsePair.getStatusCode();

			if (statusCode / 100 == 4 || statusCode / 100 == 5
					|| statusCode == 0) {
				this.onError.performAction(jsonData);
			} else {
				this.onSuccess.performAction(jsonData);
			}
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
