package wiredup.adapters;

import java.util.List;

import wiredup.models.SkillModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserActivitySkillsAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<SkillModel> skills;

	public UserActivitySkillsAdapter(Context context, int rowLayoutId, List<SkillModel> skills) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.skills = skills;
	}
	
	@Override
	public int getCount() {
		return this.skills.size();
	}

	@Override
	public Object getItem(int position) {
		return this.skills.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.skills.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView listRow = (TextView) convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = (TextView) inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		listRow.setText(this.skills.get(position).getName());
		
		return listRow;
	}

}
