package wiredup.pager.adapters;

import wiredup.fragments.user.activity.AboutFragment;
import wiredup.fragments.user.activity.CertificatesFragment;
import wiredup.fragments.user.activity.ProjectsFragment;
import wiredup.fragments.user.activity.SkillsFragment;
import wiredup.fragments.user.activity.UserPostsFragment;
import wiredup.utils.BundleKeys;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class UserActivityPagerAdapter extends FragmentPagerAdapter {
	private static final int PAGES_COUNT = 5;
	private static final String ABOUT_TITLE = "About";
	private static final String POSTS_TITLE = "Posts";
	private static final String CERTIFICATES_TITLE = "Certificates";
	private static final String SKILLS_TITLE = "Skills";
	private static final String PROJECTS_TITLE = "Projects";

	private int userId;

	public UserActivityPagerAdapter(FragmentManager fm, int userId) {
		super(fm);
		this.userId = userId;
	}

	@Override
	public Fragment getItem(int pageIndex) {
		Fragment fragment = null;

		switch (pageIndex) {
		case 0:
			fragment = new AboutFragment();
			break;
		case 1:
			fragment = new ProjectsFragment();
			break;
		case 2:
			fragment = new SkillsFragment();
			break;
		case 3:
			fragment = new CertificatesFragment();
			break;
		case 4:
			fragment = new UserPostsFragment();
			break;
		}

		Bundle bundle = new Bundle();
		bundle.putInt(BundleKeys.USER_ID, this.userId);

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
			pageTitle = PROJECTS_TITLE;
			break;
		case 2:
			pageTitle = SKILLS_TITLE;
			break;
		case 3:
			pageTitle = CERTIFICATES_TITLE;
			break;
		case 4:
			pageTitle = POSTS_TITLE;
			break;
		}

		return pageTitle;
	}
}
