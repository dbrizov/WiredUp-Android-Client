package wiredup.activities;

import wiredup.client.R;
import wiredup.pager.adapters.UserActivityPagerAdapter;
import wiredup.utils.BundleKeys;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class UserActivity extends OptionsMenuActivity {
	private UserActivityPagerAdapter pagerAdapter;
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		int userId = this.getIntent().getExtras().getInt(BundleKeys.USER_ID);
		
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		this.pagerAdapter = new UserActivityPagerAdapter(fragmentManager, userId);
		
		this.viewPager = (ViewPager) this.findViewById(R.id.pager_user_activity);
		this.viewPager.setAdapter(this.pagerAdapter);
	}
}
