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
import android.widget.TextView;

public class ProfileActivitySkillsAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<SkillModel> skills;

	public ProfileActivitySkillsAdapter(Context context, int rowLayoutId, List<SkillModel> skills) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}

		TextView textViewSkill = (TextView) listRow.findViewById(R.id.textView_skill);
		textViewSkill.setText(this.skills.get(position).getName());

		ImageView deleteButton = (ImageView) listRow.findViewById(R.id.imageView_deleteButton);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View imageViewDeleteButton) {
				Dialog dialog = ProfileActivitySkillsAdapter.this
						.createDeleteSkillAlertDialog(position);

				dialog.show();
			}
		});

		return listRow;
	}

	private Dialog createDeleteSkillAlertDialog(final int rowIndex) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

		builder.setTitle("Delete this skill?");

		builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ProfileActivitySkillsAdapter.this.deleteSkill(rowIndex);
			}
		});

		builder.setNegativeButton(R.string.btn_no, null);

		Dialog dialog = builder.create();
		return dialog;
	}
	
	private void deleteSkill(final int rowIndex) {
		int skillId = this.skills.get(rowIndex).getId();
		SkillModel model = new SkillModel();
		model.setId(skillId);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProfileActivitySkillsAdapter.this.skills.remove(rowIndex);
				ProfileActivitySkillsAdapter.this.notifyDataSetChanged();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ProfileActivitySkillsAdapter.this.context, data);
			}
		};

		WiredUpApp
				.getData()
				.getSkills()
				.remove(model, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
}
