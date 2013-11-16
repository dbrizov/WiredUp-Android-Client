package wiredup.activities;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.pager.adapters.ProfileActivityPagerAdapter;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ProfileActivity extends OptionsMenuActivity {
	private String connectionRequestsAsJsonString;
	private ProfileActivityPagerAdapter pagerAdapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		FragmentManager fragmentManager = this.getSupportFragmentManager();
		this.pagerAdapter = new ProfileActivityPagerAdapter(fragmentManager, WiredUpApp.getUserId());

		this.viewPager = (ViewPager) this.findViewById(R.id.pager_profile_activity);
		this.viewPager.setAdapter(this.pagerAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.inflateConnectionRequestsMenuItem(menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_item_connection_requests:
			Log.d("debug", this.connectionRequestsAsJsonString);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void inflateConnectionRequestsMenuItem(final Menu menu) {
		final MenuInflater inflater = this.getMenuInflater();
		
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				if (data.length() > 2) {
					// The user has connection requests
					// The server returns a non-empty json array (The string "[]" is an empty json array)
					// That is why I check for data.length() > 2
					ProfileActivity.this.connectionRequestsAsJsonString = data;
					inflater.inflate(R.menu.menu_item_connection_requests, menu);
				}
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(ProfileActivity.this, data);
			}
		};
		
		WiredUpApp.getData().getConnectionRequests()
				.getAll(WiredUpApp.getSessionKey(), onSuccess, onError);
	}
}
