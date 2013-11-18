package wiredup.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserPostModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;

public class UserPostDetailsActivity extends OptionsMenuActivity {
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	
	private UserPostModel userPostModel;
	
	private TextView textViewPostedBy;
	private TextView textViewPostDate;
	private TextView textViewPostContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_post_details);
		
		this.textViewPostedBy = (TextView) this.findViewById(R.id.textView_postedBy);
		this.textViewPostDate = (TextView) this.findViewById(R.id.textView_postDate);
		this.textViewPostContent = (TextView) this.findViewById(R.id.textView_postContent);
		
		int postId = this.getIntent().getExtras().getInt(BundleKey.POST_ID);
		this.getUserPostDetailsFromServerAndSetUpView(postId);
	}
	
	private void getUserPostDetailsFromServerAndSetUpView(int postId) {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				UserPostDetailsActivity.this.loadPostDetailsData(data);
				UserPostDetailsActivity.this.setUpView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(UserPostDetailsActivity.this, data);
			}
		};

		WiredUpApp
				.getData()
				.getUserPosts()
				.getDetails(postId, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void loadPostDetailsData(String data) {
		Gson gson = new Gson();
		this.userPostModel = gson.fromJson(data, UserPostModel.class);
	}
	
	@SuppressLint("SimpleDateFormat")
	private void setUpView() {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String postDateAsString = dateFormat.format(this.userPostModel.getPostDate());
		this.textViewPostDate.setText(postDateAsString);
		
		String postedBy = "Posted by: " + this.userPostModel.getPostedBy();
		this.textViewPostedBy.setText(postedBy);
		
		String postContent = this.userPostModel.getContent();
		this.textViewPostContent.setText(postContent);
	}
}
