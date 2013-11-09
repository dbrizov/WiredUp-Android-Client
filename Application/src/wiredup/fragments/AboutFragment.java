package wiredup.fragments;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserDetailsModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class AboutFragment extends Fragment {
	private int userId;
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

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(WiredUpApp.USER_ID_BUNDLE_KEY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(R.layout.fragment_about,
				container, false);

		this.imageViewPhoto = (ImageView) rootLayoutView
				.findViewById(R.id.imageView_userPhoto);

		this.textViewDisplayName = (TextView) rootLayoutView
				.findViewById(R.id.textView_userDisplayName);

		this.textViewEmail = (TextView) rootLayoutView
				.findViewById(R.id.textView_email);

		this.textViewCountry = (TextView) rootLayoutView
				.findViewById(R.id.textView_country);

		this.textViewAbout = (TextView) rootLayoutView
				.findViewById(R.id.textView_about);

		this.textViewLanguages = (TextView) rootLayoutView
				.findViewById(R.id.textView_languages);

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
				ErrorNotifier.displayErrorMessage(
						AboutFragment.this.getActivity(), data);
			}
		};

		if (!this.isDataLoaded) {
			WiredUpApp
					.getData()
					.getUsers()
					.getDetails(this.userId, WiredUpApp.getSessionKey(),
							onSuccess, onError);
		} else {
			this.initializeViews();
		}

		return rootLayoutView;
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

		this.textViewDisplayName
				.setText(this.userDetailsModel.getDisplayName());

		this.textViewEmail.setText(this.userDetailsModel.getEmail());

		this.textViewCountry.setText(this.userDetailsModel.getCountry());

		this.textViewAbout.setText(this.userDetailsModel.getAboutMe());

		this.textViewLanguages.setText(this.userDetailsModel.getLanguages());
	}
}
