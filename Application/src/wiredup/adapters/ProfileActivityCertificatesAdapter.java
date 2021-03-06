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

public class ProfileActivityCertificatesAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<CertificateModel> certificates;

	public ProfileActivityCertificatesAdapter(Context context, int rowLayoutId,
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
				Dialog dialog = ProfileActivityCertificatesAdapter.this
						.createDeleteSkillAlertDialog(position);

				dialog.show();
			}
		});

		return listRow;
	}

	private Dialog createDeleteSkillAlertDialog(final int rowIndex) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);

		builder.setTitle("Delete this certificate?");

		builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ProfileActivityCertificatesAdapter.this.deleteCertificate(rowIndex);
			}
		});

		builder.setNegativeButton(R.string.btn_no, null);

		Dialog dialog = builder.create();
		return dialog;
	}
	
	private void deleteCertificate(final int rowIndex) {
		int certificateId = this.certificates.get(rowIndex).getId();

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProfileActivityCertificatesAdapter.this.certificates.remove(rowIndex);
				ProfileActivityCertificatesAdapter.this.notifyDataSetChanged();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ProfileActivityCertificatesAdapter.this.context, data);
			}
		};

		WiredUpApp
				.getData()
				.getCertificates()
				.delete(certificateId,
						WiredUpApp.getSessionKey(), onSuccess,
						onError);
	}
}
