package wiredup.utils;

import wiredup.models.ServerResponseModel;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

public class ErrorNotifier {
	public static void displayErrorMessage(Context context, String jsonData) {
		Gson gson = new Gson();
		ServerResponseModel response = gson.fromJson(jsonData,
				ServerResponseModel.class);

		Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG)
				.show();
	}
}
