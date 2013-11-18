package wiredup.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserPostModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivityUserPostsAdapter extends BaseAdapter {
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final int POST_CONTENT_LENGTH_LIMIT = 35;
	
	private Context context;
	private int rowLayoutId;
	private List<UserPostModel> posts;

	public ProfileActivityUserPostsAdapter(Context context, int rowLayoutId,
			List<UserPostModel> posts) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.posts = posts;
	}

	@Override
	public int getCount() {
		return this.posts.size();
	}

	@Override
	public Object getItem(int position) {
		return this.posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.posts.get(position).getId();
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		// Set up the listRow view
		String postedBy = "Posted by: " + this.posts.get(position).getPostedBy();
		TextView textViewPostedBy = (TextView) listRow.findViewById(R.id.textView_postedBy);
		textViewPostedBy.setText(postedBy);
		
		String postContent = this.posts.get(position).getContent();
		if (postContent.length() > POST_CONTENT_LENGTH_LIMIT) {
			postContent = postContent.substring(0, POST_CONTENT_LENGTH_LIMIT - 1) + "...";
		}
		
		TextView textViewPostContent = (TextView) listRow.findViewById(R.id.textView_postContent);
		textViewPostContent.setText(postContent);
		
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String postDateAsString = dateFormat.format(this.posts.get(position).getPostDate());
		TextView textViewPostDate = (TextView) listRow.findViewById(R.id.textView_postDate);
		textViewPostDate.setText(postDateAsString);
		
		ImageView deleteButton = (ImageView) listRow.findViewById(R.id.imageView_deleteButton);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProfileActivityUserPostsAdapter.this
					.showDeletePostDialog(position);
			}
		});

		return listRow;
	}
	
	private void deletePost(final int rowIndex) {
		int postId = (int) this.getItemId(rowIndex);
		
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProfileActivityUserPostsAdapter.this.posts.remove(rowIndex);
				ProfileActivityUserPostsAdapter.this.notifyDataSetChanged();
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ProfileActivityUserPostsAdapter.this.context, data);
			}
		};

		WiredUpApp
			.getData()
			.getUserPosts()
			.delete(postId, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void showDeletePostDialog(final int rowIndex) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);
		
		dialogBuilder.setTitle("Delete this post?");
		dialogBuilder.setNegativeButton(R.string.btn_no, null);
		dialogBuilder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ProfileActivityUserPostsAdapter.this
					.deletePost(rowIndex);
			}
		});
		
		Dialog dialog = dialogBuilder.create();
		dialog.show();
	}
}
