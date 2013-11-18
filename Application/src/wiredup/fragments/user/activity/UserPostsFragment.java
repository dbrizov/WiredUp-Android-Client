package wiredup.fragments.user.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.activities.UserPostDetailsActivity;
import wiredup.adapters.UserActivityUserPostsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserPostModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UserPostsFragment extends Fragment {
private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	private int userId;
	private boolean isPostsDataLoaded;

	private List<UserPostModel> posts;
	private UserActivityUserPostsAdapter postsAdapter;

	private ListView listViewPosts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.posts = new ArrayList<UserPostModel>();
		this.isPostsDataLoaded = false;

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(BundleKey.USER_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_user_posts_user_activity,
				container, false);

		this.listViewPosts = (ListView) rootView.findViewById(R.id.listView_userPosts);

		if (!this.isPostsDataLoaded) {
			this.getDataFromServerAndSetUpListView();
		} else {
			this.setUpListView(this.getActivity(), this.posts);
		}

		return rootView;
	}

	private void setUpListView(Context context, List<UserPostModel> posts) {
		this.postsAdapter = new UserActivityUserPostsAdapter(context,
				R.layout.list_row_user_post_user_activity, posts);

		this.listViewPosts.setAdapter(this.postsAdapter);
		
		this.listViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View row, int rowIndex, long postId) {
				UserPostsFragment.this.startUserPostDetailsActivity((int) postId);
			}
		});
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
				.getAllForUser(this.userId, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void startUserPostDetailsActivity(int postId) {
		Intent intent = new Intent(this.getActivity(), UserPostDetailsActivity.class);
		intent.putExtra(BundleKey.POST_ID, postId);
		
		this.startActivity(intent);
	}
}
