package wiredup.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.adapters.UserPostsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserPostModel;
import wiredup.utils.ErrorNotifier;
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
import com.google.gson.reflect.TypeToken;

public class UserPostsFragment extends Fragment {
	private int userId;
	private boolean isPostsDataLoaded;

	private List<UserPostModel> posts;
	private UserPostsAdapter postsAdapter;

	private ListView listViewPosts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.posts = new ArrayList<UserPostModel>();

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(WiredUpApp.USER_ID_BUNDLE_KEY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootLayoutView = inflater.inflate(R.layout.fragment_user_posts,
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
		this.postsAdapter = new UserPostsAdapter(context,
				R.layout.list_row_user_post, posts);

		this.listViewPosts.setAdapter(this.postsAdapter);
	}

	private void loadPostsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<UserPostModel>>() {
		}.getType();

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
