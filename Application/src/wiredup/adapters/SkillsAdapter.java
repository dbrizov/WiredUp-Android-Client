package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.SkillModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context)
					.getLayoutInflater();
			listRow = inflater.inflate(this.layoutId, parent, false);
		}

		TextView textViewSkill = (TextView) listRow
				.findViewById(R.id.textView_skill);
		textViewSkill.setText(this.skills.get(position).getName());

		ImageView deleteButton = (ImageView) listRow
				.findViewById(R.id.imageView_deleteButton);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				int skillId = SkillsAdapter.this.skills.get(position).getId();
				SkillModel model = new SkillModel();
				model.setId(skillId);

				IOnSuccess onSuccess = new IOnSuccess() {
					@Override
					public void performAction(String data) {
						View row = (View) v.getParent();
						SkillsAdapter.this.removeRowFromListView(row, position);
					}
				};

				IOnError onError = new IOnError() {
					@Override
					public void performAction(String data) {
						ErrorNotifier.displayErrorMessage(
								SkillsAdapter.this.context, data);
					}
				};

				WiredUpApp
						.getData()
						.getSkills()
						.remove(model, WiredUpApp.getSessionKey(), onSuccess, onError);

			}
		});

		return listRow;
	}

	private void removeRowFromListView(View listRow, int position) {
		this.skills.remove(position);

		ListAdapter skillsAdapter = new SkillsAdapter((Activity) this.context,
				R.layout.list_row_skill, this.skills);

		((ListView) listRow.getParent()).setAdapter(skillsAdapter);
	}
}
