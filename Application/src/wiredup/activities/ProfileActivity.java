package wiredup.activities;

import wiredup.client.R;
import wiredup.pager.adapters.UserActivityPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class ProfileActivity extends FragmentActivity {
	private UserActivityPagerAdapter pagerAdapter;
	private ViewPager pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		FragmentManager fragmentManaget = this.getSupportFragmentManager();
		this.pagerAdapter = new UserActivityPagerAdapter(fragmentManaget);
		
		this.pager = (ViewPager) this.findViewById(R.id.pager_profile_activity);
		this.pager.setAdapter(this.pagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
