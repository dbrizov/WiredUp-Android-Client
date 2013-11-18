package wiredup.fragments.profile.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.activities.UserPostDetailsActivity;
import wiredup.adapters.ProfileActivityUserPostsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserPostCreateModel;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
	private EditText editTextPostContent;
	private Button btnCreatePost;

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
		View rootView = inflater.inflate(R.layout.fragment_user_posts_profile_activity,
				container, false);

		this.listViewPosts = (ListView) rootView.findViewById(R.id.listView_userPosts);
		this.editTextPostContent = (EditText) rootView.findViewById(R.id.editText_postContent);
		
		this.btnCreatePost = (Button) rootView.findViewById(R.id.btn_createPost);
		this.btnCreatePost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UserPostsFragment.this.createPost();
			}
		});

		if (!this.isPostsDataLoaded) {
			this.getDataFromServerAndSetUpListView();
		} else {
			this.setUpListView(this.getActivity(), this.posts);
		}

		return rootView;
	}

	private void setUpListView(Context context, List<UserPostModel> posts) {
		this.postsAdapter = new ProfileActivityUserPostsAdapter(context,
				R.layout.list_row_user_post_profile_activity, posts);

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
				.getAllForUser(this.userId, WiredUpApp.getSessionKey(),
						onSuccess, onError);
	}
	
	private void createPost() {
		String content = this.editTextPostContent.getText().toString().trim();
		
		// Validate content
		if (content.length() == 0) {
			Toast.makeText(
					this.getActivity(), "The post content is required", Toast.LENGTH_LONG).show();
			
			return;
		}
		
		UserPostCreateModel newPost = new UserPostCreateModel(content);
		
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
				UserPostModel postModel = gson.fromJson(data, UserPostModel.class);
				
				UserPostsFragment.this.posts.add(0, postModel);
				UserPostsFragment.this.postsAdapter.notifyDataSetChanged();
				
				UserPostsFragment.this.editTextPostContent.setText("");
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(UserPostsFragment.this.getActivity(), data);
			}
		};
		
		WiredUpApp.getData().getUserPosts().create(newPost, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void startUserPostDetailsActivity(int postId) {
		Intent intent = new Intent(this.getActivity(), UserPostDetailsActivity.class);
		intent.putExtra(BundleKey.POST_ID, postId);
		
		this.startActivity(intent);
	}
}
