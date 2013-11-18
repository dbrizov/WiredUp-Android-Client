package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ProjectModel;
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

public class ProfileActivityProjectsAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<ProjectModel> projects;
	
	public ProfileActivityProjectsAdapter(Context context, int rowLayoutId, List<ProjectModel> projects) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		TextView textViewProjectName = (TextView) listRow.findViewById(R.id.textView_projectName);
		textViewProjectName.setText(this.projects.get(position).getName());
		
		ImageView imageViewDeleteButton = (ImageView) listRow.findViewById(R.id.imageView_deleteButton);
		imageViewDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProfileActivityProjectsAdapter.this.showDeleteProjectDialog(position);
			}
		});
		
		return listRow;
	}
	
	private void deleteProject(final int rowIndex) {
		int projectId = (int) this.getItemId(rowIndex);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProfileActivityProjectsAdapter.this.projects.remove(rowIndex);
				ProfileActivityProjectsAdapter.this.notifyDataSetChanged();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ProfileActivityProjectsAdapter.this.context, data);
			}
		};

		WiredUpApp
				.getData()
				.getProjects()
				.delete(projectId, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void showDeleteProjectDialog(final int rowIndex) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);
		
		dialogBuilder.setTitle("Delete this project?");
		dialogBuilder.setNegativeButton(R.string.btn_no, null);
		dialogBuilder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ProfileActivityProjectsAdapter.this.deleteProject(rowIndex);
			}
		});
		
		Dialog dialog = dialogBuilder.create();
		dialog.show();
	}
}
