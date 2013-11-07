package wiredup.fragments;

import com.google.gson.Gson;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ServerResponseModel;
import wiredup.models.UserDetailsModel;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
	private UserDetailsModel userDetailsModel;

	private TextView textViewDisplayName;
	private TextView textViewEmail;
	private TextView textViewCountry;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);

		this.textViewDisplayName = (TextView) rootView
				.findViewById(R.id.textView_userDisplayName);
		this.textViewEmail = (TextView) rootView
				.findViewById(R.id.textView_email);
		this.textViewCountry = (TextView) rootView
				.findViewById(R.id.textView_country);

		int userId = WiredUpApp.getUserId();
		String sessionKey = WiredUpApp.getSessionKey();

		Log.d("debug", Integer.toString(userId));
		Log.d("debug", sessionKey);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProfileFragment.this.displayUserDetails(data);
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ProfileFragment.this.displayErrorMessage(data);
			}
		};

		WiredUpApp.getData().getUsers()
				.getDetails(userId, sessionKey, onSuccess, onError);

		return rootView;
	}

	private void displayUserDetails(String data) {
		Gson gson = new Gson();
		this.userDetailsModel = gson.fromJson(data, UserDetailsModel.class);

		this.textViewDisplayName
				.setText(this.userDetailsModel.getDisplayName());
		this.textViewEmail.setText(this.userDetailsModel.getEmail());
		this.textViewCountry.setText(this.userDetailsModel.getCountry());
	}

	private void displayErrorMessage(String data) {
		Gson gson = new Gson();
		ServerResponseModel response = gson.fromJson(data,
				ServerResponseModel.class);

		Toast.makeText(this.getActivity(), response.getMessage(),
				Toast.LENGTH_LONG).show();
	}
}
