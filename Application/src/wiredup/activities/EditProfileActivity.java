package wiredup.activities;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.adapters.CountriesAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CountryModel;
import wiredup.models.UserDetailsModel;
import wiredup.models.UserEditModel;
import wiredup.utils.BundleKey;
import wiredup.utils.Encryptor;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EditProfileActivity extends OptionsMenuActivity {
	private static final int TAKE_PICTURE_FROM_CAMERA = 1;
	private static final int PICK_PICTURE_FROM_GALLERY = 2;

	private UserDetailsModel userDetailsModel;
	private List<CountryModel> countries; // Needed for the auto-complete
	private CountriesAdapter countriesAdapter;
	private boolean areCountriesLoaded;
	private short[] userPhotoUnsignedByteArray;

	private ImageView imageViewUserPhoto;
	private Button btnTakePictureFromCamera;
	private Button btnPickPictureFromGalery;
	private EditText editTextAboutMe;
	private EditText editTextLanguages;
	private Spinner spinnerCountry;
	private Button btnEditProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_edit_profile);
		this.setupActionBar();

		this.countries = new ArrayList<CountryModel>();

		// Initialize the views
		this.imageViewUserPhoto = (ImageView) this.findViewById(R.id.imageView_userPhoto);
		this.btnTakePictureFromCamera = (Button) this.findViewById(R.id.btn_takePictureFromCamera);
		this.btnPickPictureFromGalery = (Button) this.findViewById(R.id.btn_pickPictureFromGalery);
		this.editTextAboutMe = (EditText) this.findViewById(R.id.editText_aboutMe);
		this.editTextLanguages = (EditText) this.findViewById(R.id.editText_languages);
		this.spinnerCountry = (Spinner) this.findViewById(R.id.spinner_country);
		this.btnEditProfile = (Button) this.findViewById(R.id.btn_editProfile);

		// Get the userDetailsModel extra from the Intent
		Intent intent = this.getIntent();
		this.userDetailsModel = (UserDetailsModel) intent
				.getSerializableExtra(BundleKey.USER_DETAILS_MODEL);
		
		// Set-Up the views
		this.setUpImageView();
		this.setUpEditTextViews();
		this.setUpButtons();
		
		if (!this.areCountriesLoaded) {
			this.getCountriesFromDatabaseAndSetUpSpinner();
		} else {
			this.setUpSpinner();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == TAKE_PICTURE_FROM_CAMERA) {
			if (resultCode == RESULT_OK) {
				this.setUpImageViewFromCameraIntentResult(intent);
			}
		}
		else if (requestCode == PICK_PICTURE_FROM_GALLERY) {
			if (resultCode == RESULT_OK) {
				this.setUpImageViewFromGalleyIntentResult(intent);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void setupActionBar() {
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void setUpSpinner() {
		this.countriesAdapter = new CountriesAdapter(this,
				R.layout.list_row_country, this.countries);

		this.spinnerCountry.setAdapter(countriesAdapter);

		int selectedItemPosition = this
				.findCountryPositionInTheSpinner(this.userDetailsModel.getCountry());

		if (selectedItemPosition >= 0) {
			this.spinnerCountry.setSelection(selectedItemPosition);
		}
	}

	private void setUpImageViewFromCameraIntentResult(Intent intent) {
		SetUpImageViewFromCameraIntentResultTask task = new SetUpImageViewFromCameraIntentResultTask();
		task.execute(intent);
	}
	
	private void setUpImageViewFromGalleyIntentResult(Intent intent) {
		SetUpImageViewFromGalleyIntentResultTask task = new SetUpImageViewFromGalleyIntentResultTask();
		task.execute(intent);
	}
	
	private void setUpImageView() {
		String userPhotoBase64String = this.userDetailsModel.getPhoto();
		SetUpUserPhotoTask setUserPhotoTask = new SetUpUserPhotoTask();
		setUserPhotoTask.execute(userPhotoBase64String);
	}
	
	private void setUpEditTextViews() {
		this.editTextAboutMe.setText(this.userDetailsModel.getAboutMe());
		this.editTextLanguages.setText(this.userDetailsModel.getLanguages());
	}
	
	private void setUpButtons() {
		this.btnEditProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditProfileActivity.this.editProfile();
			}
		});
		
		this.btnTakePictureFromCamera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditProfileActivity.this
						.dispatchTakePictureFromCameraIntent(TAKE_PICTURE_FROM_CAMERA);
			}
		});
		
		this.btnPickPictureFromGalery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditProfileActivity.this
						.dispatchPickPictureFromGalleryIntent(PICK_PICTURE_FROM_GALLERY);
			}
		});
	}

	private void getCountriesFromDatabaseAndSetUpSpinner() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				EditProfileActivity.this.loadCountries(data);
				EditProfileActivity.this.setUpSpinner();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(EditProfileActivity.this,
						data);
			}
		};

		WiredUpApp.getData().getCountries()
				.getAll(WiredUpApp.getSessionKey(), onSuccess, onError);
	}

	private void loadCountries(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<CountryModel>>() {
		}.getType();

		this.countries = gson.fromJson(data, listType);
		this.areCountriesLoaded = true;

		Log.d("debug", "Countries Loaded");
	}

	private void editProfile() {
		UserEditModel editModel = new UserEditModel();
		editModel.setAboutMe(this.editTextAboutMe.getText().toString());
		editModel.setLanguages(this.editTextLanguages.getText().toString());

		int selectedItemPosition = this.spinnerCountry
				.getSelectedItemPosition();
		int countryId = (int) this.countriesAdapter
				.getItemId(selectedItemPosition);

		editModel.setCountryId(countryId);
		
		if (this.userPhotoUnsignedByteArray != null) {
			editModel.setPhoto(this.userPhotoUnsignedByteArray);
		}

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				EditProfileActivity.this.startProfileActivity();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(EditProfileActivity.this,
						data);
			}
		};

		WiredUpApp
				.getData()
				.getUsers()
				.edit(editModel, WiredUpApp.getSessionKey(), onSuccess, onError);
	}

	private void startProfileActivity() {
		Intent intent = new Intent(this, ProfileActivity.class);
		this.startActivity(intent);
	}

	private int findCountryPositionInTheSpinner(String countryName) {
		if (countryName == null) {
			return -1;
		}

		int middleIndex;
		int leftIndex = 0;
		int rightIndex = this.countries.size() - 1;

		while (leftIndex <= rightIndex) {
			middleIndex = (rightIndex + leftIndex) / 2;

			if (this.countries.get(middleIndex).getName()
					.compareTo(countryName) < 0) {
				leftIndex = middleIndex + 1;
			} else if (this.countries.get(middleIndex).getName()
					.compareTo(countryName) > 0) {
				rightIndex = middleIndex - 1;
			} else {
				return middleIndex;
			}
		}

		return -1;
	}

	private void dispatchTakePictureFromCameraIntent(int requestCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		this.startActivityForResult(takePictureIntent, requestCode);
	}
	
	private void dispatchPickPictureFromGalleryIntent(int requestCode) {
		Intent pickPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
		pickPictureIntent.setType("image/*");
		
		this.startActivityForResult(pickPictureIntent, requestCode);
	}
	
	private class SetUpUserPhotoTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... params) {
			String userPhotoBase64String = params[0];
			Bitmap userPhotoBitmap = null;
			
			if (userPhotoBase64String != null) {
				byte[] userPhotoByteArray = Encryptor.Base64StringToByteArray(userPhotoBase64String);

				userPhotoBitmap = BitmapFactory.decodeByteArray(
						userPhotoByteArray, 0, userPhotoByteArray.length);
				
				return userPhotoBitmap;
			}
			
			return userPhotoBitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			
			if (result != null) {
				EditProfileActivity.this.imageViewUserPhoto.setImageBitmap(result);
			} else {
				EditProfileActivity.this.imageViewUserPhoto.setImageResource(R.drawable.default_user_image);
			}
		}
	}
	
	private class SetUpImageViewFromGalleyIntentResultTask extends AsyncTask<Intent, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(Intent... params) {
			Intent intent = params[0];
			Bitmap userPhotoBitmap = null;
			
			try {
				// Convert the photo to bitmap
				InputStream inputStream = getContentResolver().openInputStream(intent.getData());
				userPhotoBitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
				
				// Compress the user photo and initialize the userPhotoUnsignedByteArray
				EditProfileActivity.this.compressTheUserPhotoToUnsignedByteArray(userPhotoBitmap);
				
				return userPhotoBitmap;
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			return userPhotoBitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			
			EditProfileActivity.this.imageViewUserPhoto.setImageBitmap(result);
		}
	}
	
	private class SetUpImageViewFromCameraIntentResultTask extends AsyncTask<Intent, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(Intent... params) {
			Intent intent = params[0];
			
			// Convert the photo to bitmap
			Bundle extras = intent.getExtras();
			Bitmap userPhotoBitmap = (Bitmap) extras.get("data");
			
			// Compress the user photo and initialize the userPhotoUnsignedByteArray
			EditProfileActivity.this.compressTheUserPhotoToUnsignedByteArray(userPhotoBitmap);
			
			return userPhotoBitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			
			EditProfileActivity.this.imageViewUserPhoto.setImageBitmap(result);
		}
	}
	
	private void compressTheUserPhotoToUnsignedByteArray(Bitmap userPhotoBitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		userPhotoBitmap.compress(CompressFormat.JPEG, 10, outputStream);
		
		this.userPhotoUnsignedByteArray =
				Encryptor.byteArrayToUnsignedByteArray(outputStream.toByteArray());
	}
}
