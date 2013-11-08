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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutFragment extends Fragment {
	private UserDetailsModel userDetailsModel;
	private boolean isDataLoaded;

	private ImageView imageViewPhoto;
	private TextView textViewDisplayName;
	private TextView textViewEmail;
	private TextView textViewCountry;
	private TextView textViewAbout;
	private TextView textViewLanguages;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.userDetailsModel = null;
		this.isDataLoaded = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);

		this.imageViewPhoto = (ImageView) rootView
				.findViewById(R.id.imageView_userPhoto);

		this.textViewDisplayName = (TextView) rootView
				.findViewById(R.id.textView_userDisplayName);

		this.textViewEmail = (TextView) rootView
				.findViewById(R.id.textView_email);

		this.textViewCountry = (TextView) rootView
				.findViewById(R.id.textView_country);

		this.textViewAbout = (TextView) rootView
				.findViewById(R.id.textView_about);

		this.textViewLanguages = (TextView) rootView
				.findViewById(R.id.textView_languages);

		int userId = WiredUpApp.getUserId();
		String sessionKey = WiredUpApp.getSessionKey();

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				AboutFragment.this.loadUserDetailsData(data);
				AboutFragment.this.initializeViews();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				AboutFragment.this.displayErrorMessage(data);
			}
		};

		if (!this.isDataLoaded) {
			WiredUpApp.getData().getUsers()
					.getDetails(userId, sessionKey, onSuccess, onError);
		} else {
			this.initializeViews();
		}

		return rootView;
	}

	private void loadUserDetailsData(String data) {
		Gson gson = new Gson();
		this.userDetailsModel = gson.fromJson(data, UserDetailsModel.class);
		this.isDataLoaded = true;
		
		Log.d("debug", "About Loaded");
	}

	private void initializeViews() {
		if (this.userDetailsModel.getPhoto() != null) {
			// TODO Load photo from database
		} else {
			this.imageViewPhoto.setImageResource(R.drawable.default_user_image);
		}

		this.textViewDisplayName.setText(this.userDetailsModel.getDisplayName());
		
		this.textViewEmail.setText(this.userDetailsModel.getEmail());
		
		this.textViewCountry.setText(this.userDetailsModel.getCountry());
		
		this.textViewAbout.setText(this.userDetailsModel.getAboutMe());
		
		this.textViewLanguages.setText(this.userDetailsModel.getLanguages());
	}

	private void displayErrorMessage(String data) {
		Gson gson = new Gson();
		ServerResponseModel response = gson.fromJson(data,
				ServerResponseModel.class);

		Toast.makeText(this.getActivity(), response.getMessage(),
				Toast.LENGTH_LONG).show();
	}
}
