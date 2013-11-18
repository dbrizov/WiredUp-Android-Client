package wiredup.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import wiredup.client.R;
import wiredup.models.UserPostModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserActivityUserPostsAdapter extends BaseAdapter {
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final int POST_CONTENT_LENGTH_LIMIT = 35;
	
	private Context context;
	private int rowLayoutId;
	private List<UserPostModel> posts;

	public UserActivityUserPostsAdapter(Context context, int rowLayoutId, List<UserPostModel> posts) {
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

		return listRow;
	}
}
