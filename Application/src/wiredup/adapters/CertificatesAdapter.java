package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CertificateModel;
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

public class CertificatesAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<CertificateModel> certificates;

	public CertificatesAdapter(Context context, int rowLayoutId,
			List<CertificateModel> certificates) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.certificates = certificates;
	}

	@Override
	public int getCount() {
		return this.certificates.size();
	}

	@Override
	public Object getItem(int position) {
		return this.certificates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.certificates.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}

		TextView textViewCertificate = (TextView) listRow.findViewById(R.id.textView_certificate);
		textViewCertificate.setText(this.certificates.get(position).getName());

		ImageView deleteButton = (ImageView) listRow.findViewById(R.id.imageView_deleteButton);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View imageViewDeleteButton) {
				Dialog dialog = CertificatesAdapter.this
						.createDeleteSkillAlertDialog(imageViewDeleteButton, position);

				dialog.show();
			}
		});

		return listRow;
	}
	
	private void removeRowFromListView(View listRow, int position) {
		this.certificates.remove(position);
		this.notifyDataSetChanged();
	}

	private Dialog createDeleteSkillAlertDialog(final View deleteButton,
			final int rowIndex) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CertificatesAdapter.this.context);

		builder.setTitle(R.string.are_you_sure);

		builder.setPositiveButton(R.string.btn_yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int certificateId = CertificatesAdapter.this.certificates
								.get(rowIndex).getId();

						IOnSuccess onSuccess = new IOnSuccess() {
							@Override
							public void performAction(String data) {
								View row = (View) deleteButton.getParent();
								CertificatesAdapter.this.removeRowFromListView(
										row, rowIndex);
							}
						};

						IOnError onError = new IOnError() {
							@Override
							public void performAction(String data) {
								ErrorNotifier.displayErrorMessage(
										CertificatesAdapter.this.context, data);
							}
						};

						WiredUpApp
								.getData()
								.getCertificates()
								.delete(certificateId,
										WiredUpApp.getSessionKey(), onSuccess,
										onError);
					}
				});

		builder.setNegativeButton(R.string.btn_no, null);

		Dialog dialog = builder.create();
		return dialog;
	}
}
