package wiredup.pager.adapters;

import wiredup.fragments.ConnectionsFragment;
import wiredup.fragments.MessagesFragment;
import wiredup.fragments.PostsFragment;
import wiredup.fragments.ProfileFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class UserActivityPagerAdapter extends FragmentPagerAdapter {
	private static final int PAGES_COUNT = 4;
	private static final String MESSAGES_TITLE = "Messages";
	private static final String PROFILE_TITLE = "Profile";
	private static final String POSTS_TITLE = "Posts";
	private static final String CONNECTIONS_TITLE = "Connections";

	public UserActivityPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pageIndex) {
		Fragment fragment = null;
		
		switch (pageIndex) {
		case 0:
			fragment = new ProfileFragment();
			break;
		case 1:
			fragment = new MessagesFragment();
			break;
		case 2:
			fragment = new PostsFragment();
			break;
		case 3:
			fragment = new ConnectionsFragment();
			break;
		}

		return fragment;
	}

	@Override
	public int getCount() {
		return PAGES_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int pageIndex) {
		String pageTitle = null;

		switch (pageIndex) {
		case 0:
			pageTitle = PROFILE_TITLE;
			break;
		case 1:
			pageTitle = MESSAGES_TITLE;
			break;
		case 2:
			pageTitle = POSTS_TITLE;
			break;
		case 3:
			pageTitle = CONNECTIONS_TITLE;
			break;
		}

		return pageTitle;
	}
}
