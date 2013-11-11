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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_edit_profile);
		this.setupActionBar();

		this.countries = new ArrayList<CountryModel>();

		this.editTextAboutMe = (EditText) this
				.findViewById(R.id.editText_aboutMe);
		this.editTextLanguages = (EditText) this
				.findViewById(R.id.editText_languages);
		this.spinnerCountry = (Spinner) this
				.findViewById(R.id.spinner_country);

		Intent intent = this.getIntent();
		this.userDetailsModel = (UserDetailsModel) intent
				.getSerializableExtra(Keys.INTENT_KEY_USER_DETAILS_MODEL);

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
	}
}
