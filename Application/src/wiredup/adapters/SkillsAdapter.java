package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.SkillModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
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
			public void onClick(final View imageViewDeleteButton) {
				Dialog dialog = SkillsAdapter.this.createAlertDialog(
						imageViewDeleteButton, position);
				dialog.show();
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

	private Dialog createAlertDialog(final View deleteButton, final int rowIndex) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SkillsAdapter.this.context);

		builder.setTitle(R.string.are_you_sure);
		
		builder.setPositiveButton(R.string.btn_yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int skillId = SkillsAdapter.this.skills.get(rowIndex)
								.getId();
						SkillModel model = new SkillModel();
						model.setId(skillId);

						IOnSuccess onSuccess = new IOnSuccess() {
							@Override
							public void performAction(String data) {
								View row = (View) deleteButton.getParent();
								SkillsAdapter.this.removeRowFromListView(row,
										rowIndex);
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
								.remove(model, WiredUpApp.getSessionKey(),
										onSuccess, onError);
					}
				});

		builder.setNegativeButton(R.string.btn_no, null);

		Dialog dialog = builder.create();
		return dialog;
	}
}
