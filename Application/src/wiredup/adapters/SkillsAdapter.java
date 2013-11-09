package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.models.SkillModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SkillsAdapter extends BaseAdapter {
	private Context context;
	private int layoutId;
	private List<SkillModel> skills;

	public SkillsAdapter(Context context, int layoutId, List<SkillModel> skills) {
		this.context = context;
		this.layoutId = layoutId;
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
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.layoutId, parent, false);
		}

		TextView textViewSkill = (TextView) listRow.findViewById(R.id.textView_skill);
		textViewSkill.setText(this.skills.get(position).getName());

		return listRow;
	}
}
