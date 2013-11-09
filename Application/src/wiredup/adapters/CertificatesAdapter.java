package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.models.CertificateModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CertificatesAdapter extends BaseAdapter {
	private Context context;
	private int layoutId;
	private List<CertificateModel> certificates;

	public CertificatesAdapter(Context context, int layoutId,
			List<CertificateModel> certificates) {
		this.context = context;
		this.layoutId = layoutId;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.layoutId, parent, false);
		}

		TextView textViewCertificate = (TextView) listRow.findViewById(R.id.textView_certificate);
		textViewCertificate.setText(this.certificates.get(position).getName());

		return listRow;
	}

}
