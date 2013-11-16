package wiredup.activities;

import wiredup.client.R;
import wiredup.pager.adapters.ProfileActivityPagerAdapter;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

public class ProfileActivity extends OptionsMenuActivity {
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
