package wiredup.fragments.user.activity;

import com.google.gson.Gson;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserDetailsModel;
import wiredup.utils.BundleKeys;
import wiredup.utils.Encryptor;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutFragment extends Fragment {
	private int userId;
	private UserDetailsModel userDetailsModel;
	private Bitmap userPhotoBitmap;
	private boolean isDataLoaded;

	private ImageView imageViewPhoto;
	private TextView textViewDisplayName;
	private TextView textViewEmail;
	private TextView textViewCountry;
	private TextView textViewAbout;
	private TextView textViewLanguages;
	private Button btnSendMessage;
	private Button btnSendConnectionRequest;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(BundleKeys.USER_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(
				R.layout.fragment_about_user_activity, container, false);

		// Find the sub-views
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

		this.btnSendMessage = (Button) rootLayoutView
				.findViewById(R.id.btn_sendMessage);

		this.btnSendConnectionRequest = (Button) rootLayoutView
				.findViewById(R.id.btn_sendConnectionRequest);

		if (!this.isDataLoaded) {
			this.getDataFromServerAndSetUpView();
		} else {
			this.setUpView();
		}

		return rootLayoutView;
	}

	private void getDataFromServerAndSetUpView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				AboutFragment.this.loadUserDetailsData(data);
				AboutFragment.this.setUpView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						AboutFragment.this.getActivity(), data);
			}
		};

		WiredUpApp
				.getData()
				.getUsers()
				.getDetails(this.userId, WiredUpApp.getSessionKey(), onSuccess,
						onError);
	}
	
	private void loadUserDetailsData(String data) {
		Gson gson = new Gson();
		this.userDetailsModel = gson.fromJson(data, UserDetailsModel.class);
		this.isDataLoaded = true;

		Log.d("debug", "User About Loaded");
	}
	
	private void setUpView() {
		// Set up the user photo
		String userPhotoBase64String = this.userDetailsModel.getPhoto();
		SetUpUserPhotoTask setUserPhoto = new SetUpUserPhotoTask();
		setUserPhoto.execute(userPhotoBase64String);

		// Set up the text-views
		this.textViewDisplayName
				.setText(this.userDetailsModel.getDisplayName());

		this.textViewEmail.setText(this.userDetailsModel.getEmail());

		this.textViewCountry.setText(this.userDetailsModel.getCountry());

		if (this.userDetailsModel.getAboutMe() != null
				&& this.userDetailsModel.getAboutMe().length() != 0) {
			this.textViewAbout.setText("About me: "
					+ this.userDetailsModel.getAboutMe());
		}

		if (this.userDetailsModel.getLanguages() != null
				&& this.userDetailsModel.getLanguages().length() != 0) {
			this.textViewLanguages.setText("Languages: "
					+ this.userDetailsModel.getLanguages());
		}
		
		// TODO Set up the buttons
	}
	
	private class SetUpUserPhotoTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... params) {
			String userPhotoBase64String = params[0];
			if (userPhotoBase64String != null) {
				if (AboutFragment.this.userPhotoBitmap != null) {
					return AboutFragment.this.userPhotoBitmap;
				}

				byte[] userPhotoByteArray = Encryptor
						.Base64StringToByteArray(userPhotoBase64String);

				AboutFragment.this.userPhotoBitmap = BitmapFactory
						.decodeByteArray(userPhotoByteArray, 0,
								userPhotoByteArray.length);

				return AboutFragment.this.userPhotoBitmap;
			}

			return AboutFragment.this.userPhotoBitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

			if (result != null) {
				AboutFragment.this.imageViewPhoto.setImageBitmap(result);
			} else {
				AboutFragment.this.imageViewPhoto
						.setImageResource(R.drawable.default_user_image);
			}
		}
	}
}
