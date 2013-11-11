package wiredup.activities;

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
import wiredup.utils.ErrorNotifier;
import wiredup.utils.Keys;
import wiredup.utils.WiredUpApp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EditProfileActivity extends FragmentActivity {
	private UserDetailsModel userDetailsModel;
	private List<CountryModel> countries; // Needed for the auto-complete
	private CountriesAdapter countriesAdapter;
	private boolean areCountriesLoaded;

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
		this.editTextAboutMe = (EditText) this.findViewById(R.id.editText_aboutMe);
		this.editTextLanguages = (EditText) this.findViewById(R.id.editText_languages);
		this.spinnerCountry = (Spinner) this.findViewById(R.id.spinner_country);
		this.btnEditProfile = (Button) this.findViewById(R.id.btn_editProfile);

		// Get the userDetailsModel extra from the Intent
		Intent intent = this.getIntent();
		this.userDetailsModel = (UserDetailsModel) intent
				.getSerializableExtra(Keys.INTENT_KEY_USER_DETAILS_MODEL);

		// Set-Up the views
		this.editTextAboutMe.setText(this.userDetailsModel.getAboutMe());
		this.editTextLanguages.setText(this.userDetailsModel.getLanguages());

		this.btnEditProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditProfileActivity.this.editProfile();
			}
		});

		if (!this.areCountriesLoaded) {
			this.getCountriesFromDatabaseAndSetUpSpinner();
		} else {
			this.setUpSpinner();
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
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
		Type listType = new TypeToken<List<CountryModel>>() {}.getType();

		this.countries = gson.fromJson(data, listType);
		this.areCountriesLoaded = true;

		Log.d("debug", "Countries Loaded");
	}

	private void setUpSpinner() {
		this.countriesAdapter = new CountriesAdapter(this,
				R.layout.list_row_country, this.countries);

		this.spinnerCountry.setAdapter(countriesAdapter);

		int selectedItemPosition = this
				.findSelectedItemPosition(this.userDetailsModel.getCountry());

		if (selectedItemPosition >= 0) {
			this.spinnerCountry.setSelection(selectedItemPosition);
		}
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
		editModel.setPhoto(null); // TODO make a byte[] from a picture from the file system
		
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				EditProfileActivity.this.startProfileActivity();
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(EditProfileActivity.this, data);
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
	
	private int findSelectedItemPosition(String countryName) {
		if (countryName == null) {
			return -1;
		}
		
		int middleIndex;
		int leftIndex = 0;
		int rightIndex = this.countries.size() - 1;

		while (leftIndex <= rightIndex) {
			middleIndex = (rightIndex + leftIndex) / 2;

			if (this.countries.get(middleIndex).getName().compareTo(countryName) < 0) {
				leftIndex = middleIndex + 1;
			} else if (this.countries.get(middleIndex).getName().compareTo(countryName) > 0) {
				rightIndex = middleIndex - 1;
			} else {
				return middleIndex;
			}
		}
		
		return -1;
	}
}
