package wiredup.adapters.profile;

import java.util.List;

import wiredup.client.R;
import wiredup.models.UserPostModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserPostsAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<UserPostModel> posts;

	public UserPostsAdapter(Context context, int rowLayoutId,
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}

		TextView textViewPostContent = (TextView) listRow
				.findViewById(R.id.textView_postContent);
		textViewPostContent.setText(this.posts.get(position).getContent());

		return listRow;
	}

}
