package wiredup.adapters;

import java.util.List;

import wiredup.models.CertificateModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserActivityCertificatesAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<CertificateModel> certificates;
	
	public UserActivityCertificatesAdapter(Context context, int rowLayoutId, List<CertificateModel> certificates) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView listRow = (TextView) convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = (TextView) inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		listRow.setText(this.certificates.get(position).getName());
		
		return listRow;
	}
}
