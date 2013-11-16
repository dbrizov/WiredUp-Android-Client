package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.models.ConnectionRequestModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConnectionRequestsAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<ConnectionRequestModel> connectionRequests;

	public ConnectionRequestsAdapter(Context context, int rowLayoutId,
			List<ConnectionRequestModel> connectionRequests) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.connectionRequests = connectionRequests;
	}

	@Override
	public int getCount() {
		return this.connectionRequests.size();
	}

	@Override
	public Object getItem(int position) {
		return this.connectionRequests.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.connectionRequests.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		TextView textViewUserDisplayName = (TextView) listRow.findViewById(R.id.textView_senderName);
		textViewUserDisplayName.setText(this.connectionRequests.get(position).getSenderDisplayName());
		
		ImageView imageViewBtnAccept = (ImageView) listRow.findViewById(R.id.imageView_acceptButton);
		// TODO add click listener to accept button
		
		ImageView imageViewBtnCancel = (ImageView) listRow.findViewById(R.id.imageView_cancelButton);
		// TODO add click listener to cancel button
		
		return listRow;
	}

}
