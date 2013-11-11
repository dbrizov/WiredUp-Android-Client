package wiredup.activities;

import wiredup.client.R;
import wiredup.models.UserDetailsModel;
import wiredup.utils.Keys;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class EditProfileActivity extends FragmentActivity {
	private UserDetailsModel userDetailsModel;
	
	private EditText editTextAboutMe;
	private EditText editTextLanguages;
	private AutoCompleteTextView autoCompleteCountry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_edit_profile);
		this.setupActionBar();
		
		Intent intent = this.getIntent();
		this.userDetailsModel = (UserDetailsModel) intent
				.getSerializableExtra(Keys.INTENT_KEY_USER_DETAILS_MODEL);
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
}
