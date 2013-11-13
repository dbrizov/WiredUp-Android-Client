package wiredup.activities;

import wiredup.client.R;
import wiredup.pager.adapters.ProfileActivityPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class ProfileActivity extends OptionsMenuActivity {
	private ProfileActivityPagerAdapter pagerAdapter;
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		FragmentManager fragmentManaget = this.getSupportFragmentManager();
		this.pagerAdapter = new ProfileActivityPagerAdapter(fragmentManaget);

		this.pager = (ViewPager) this.findViewById(R.id.pager_profile_activity);
		this.pager.setAdapter(this.pagerAdapter);
	}
}
