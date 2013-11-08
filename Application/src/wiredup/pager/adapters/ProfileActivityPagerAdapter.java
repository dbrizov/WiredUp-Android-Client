package wiredup.pager.adapters;

import wiredup.fragments.CertificatesFragment;
import wiredup.fragments.ConnectionsFragment;
import wiredup.fragments.MessagesFragment;
import wiredup.fragments.PostsFragment;
import wiredup.fragments.AboutFragment;
import wiredup.fragments.ProjectsFragment;
import wiredup.fragments.SkillsFragment;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProfileActivityPagerAdapter extends FragmentPagerAdapter {
	private static final int PAGES_COUNT = 7;
	private static final String MESSAGES_TITLE = "Messages";
	private static final String ABOUT_TITLE = "About";
	private static final String POSTS_TITLE = "Posts";
	private static final String CONNECTIONS_TITLE = "Connections";
	private static final String CERTIFICATES_TITLE = "Certificates";
	private static final String SKILLS_TITLE = "Skills";
	private static final String PROJECTS_TITLE = "Projects";

	public ProfileActivityPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pageIndex) {
		Fragment fragment = null;
		
		switch (pageIndex) {
		case 0:
			fragment = new AboutFragment();
			break;
		case 1:
			fragment = new MessagesFragment();
			break;
		case 2:
			fragment = new ConnectionsFragment();
			break;
		case 3:
			fragment = new ProjectsFragment();
			break;
		case 4:
			fragment = new SkillsFragment();
			break;
		case 5:
			fragment = new CertificatesFragment();
			break;
		case 6:
			fragment = new PostsFragment();
			break;
		}
		
		Bundle bundle = new Bundle();
		bundle.putInt(WiredUpApp.USER_ID_BUNDLE_KEY, WiredUpApp.getUserId());
		
		fragment.setArguments(bundle);

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
			pageTitle = ABOUT_TITLE;
			break;
		case 1:
			pageTitle = MESSAGES_TITLE;
			break;
		case 2:
			pageTitle = CONNECTIONS_TITLE;
			break;
		case 3:
			pageTitle = PROJECTS_TITLE;
			break;
		case 4:
			pageTitle = SKILLS_TITLE;
			break;
		case 5:
			pageTitle = CERTIFICATES_TITLE;
			break;
		case 6:
			pageTitle = POSTS_TITLE;
			break;
		}

		return pageTitle;
	}
}
