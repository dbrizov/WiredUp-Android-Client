package wiredup.pager.adapters;

import wiredup.fragments.CertificatesFragment;
import wiredup.fragments.ConnectionsFragment;
import wiredup.fragments.MessagesFragment;
import wiredup.fragments.PostsFragment;
import wiredup.fragments.ProfileFragment;
import wiredup.fragments.SkillsFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class UserActivityPagerAdapter extends FragmentPagerAdapter {
	private static final int PAGES_COUNT = 6;
	private static final String MESSAGES_TITLE = "Messages";
	private static final String PROFILE_TITLE = "Profile";
	private static final String POSTS_TITLE = "Posts";
	private static final String CONNECTIONS_TITLE = "Connections";
	private static final String CERTIFICATES_TITLE = "Certificates";
	private static final String SKILLS_TITLE = "Skills";

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
			fragment = new ConnectionsFragment();
			break;
		case 3:
			fragment = new SkillsFragment();
			break;
		case 4:
			fragment = new CertificatesFragment();
			break;
		case 5:
			fragment = new PostsFragment();
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
			pageTitle = CONNECTIONS_TITLE;
			break;
		case 3:
			pageTitle = SKILLS_TITLE;
			break;
		case 4:
			pageTitle = CERTIFICATES_TITLE;
			break;
		case 5:
			pageTitle = POSTS_TITLE;
			break;
		}

		return pageTitle;
	}
}
