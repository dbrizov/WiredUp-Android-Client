package wiredup.fragments.profile.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.adapters.ProfileActivityUserPostsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserPostModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.BundleKeys;
import wiredup.utils.WiredUpApp;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UserPostsFragment extends Fragment {
	private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	private int userId;
	private boolean isPostsDataLoaded;

	private List<UserPostModel> posts;
	private ProfileActivityUserPostsAdapter postsAdapter;

	private ListView listViewPosts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.posts = new ArrayList<UserPostModel>();

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(BundleKeys.USER_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootLayoutView = inflater.inflate(R.layout.fragment_user_posts_profile_activity,
				container, false);

		this.listViewPosts = (ListView) rootLayoutView
				.findViewById(R.id.listView_userPosts);

		if (!this.isPostsDataLoaded) {
			this.getDataFromServerAndSetUpListView();
		} else {
			this.setUpListView(this.getActivity(), this.posts);
		}

		return rootLayoutView;
	}

	private void setUpListView(Context context, List<UserPostModel> posts) {
		this.postsAdapter = new ProfileActivityUserPostsAdapter(context,
				R.layout.list_row_user_post_profile_activity, posts);

		this.listViewPosts.setAdapter(this.postsAdapter);
	}

	private void loadPostsData(String data) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		Type listType = new TypeToken<List<UserPostModel>>() {}.getType();

		this.posts = gson.fromJson(data, listType);
		this.isPostsDataLoaded = true;

		Log.d("debug", "Posts Loaded");
	}

	private void getDataFromServerAndSetUpListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				UserPostsFragment.this.loadPostsData(data);
				UserPostsFragment.this.setUpListView(
						UserPostsFragment.this.getActivity(),
						UserPostsFragment.this.posts);
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						UserPostsFragment.this.getActivity(), data);
			}
		};

		WiredUpApp
				.getData()
				.getUserPosts()
				.getAllForUser(this.userId, WiredUpApp.getSessionKey(),
						onSuccess, onError);
	}
}
