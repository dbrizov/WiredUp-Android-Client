package wiredup.adapters;

import java.util.List;

import wiredup.models.ProjectModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserActivityProjectsAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<ProjectModel> projects;
	
	public UserActivityProjectsAdapter(Context context, int rowLayoutId, List<ProjectModel> projects) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.projects = projects;
	}
	
	@Override
	public int getCount() {
		return this.projects.size();
	}

	@Override
	public Object getItem(int position) {
		return this.projects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.projects.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textViewProjectName = (TextView) convertView;
		if (textViewProjectName == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			textViewProjectName = (TextView) inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		textViewProjectName.setText(this.projects.get(position).getName());
		
		return textViewProjectName;
	}

}
