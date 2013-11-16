package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ConnectionRequestModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context)
					.getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}

		TextView textViewUserDisplayName = (TextView) listRow
				.findViewById(R.id.textView_senderName);
		textViewUserDisplayName.setText(this.connectionRequests.get(position)
				.getSenderDisplayName());

		ImageView imageViewAcceptButton = (ImageView) listRow
				.findViewById(R.id.imageView_acceptButton);
		imageViewAcceptButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnectionRequestsAdapter.this.acceptConnectionRequest(position);
			}
		});

		ImageView imageViewCancelButton = (ImageView) listRow
				.findViewById(R.id.imageView_cancelButton);
		imageViewCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnectionRequestsAdapter.this.declineConnectionRequest(position);
			}
		});

		return listRow;
	}

	private void acceptConnectionRequest(final int rowIndex) {
		int connectionRequestId = (int) this.getItemId(rowIndex);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ConnectionRequestsAdapter.this.connectionRequests.remove(rowIndex);
				ConnectionRequestsAdapter.this.notifyDataSetChanged();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ConnectionRequestsAdapter.this.context, data);
			}
		};

		WiredUpApp
				.getData()
				.getConnectionRequests()
				.accept(connectionRequestId, WiredUpApp.getSessionKey(),
						onSuccess, onError);
	}
	
	private void declineConnectionRequest(final int rowIndex) {
		int connectionRequestId = (int) this.getItemId(rowIndex);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ConnectionRequestsAdapter.this.connectionRequests.remove(rowIndex);
				ConnectionRequestsAdapter.this.notifyDataSetChanged();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ConnectionRequestsAdapter.this.context, data);
			}
		};

		WiredUpApp
				.getData()
				.getConnectionRequests()
				.decline(connectionRequestId, WiredUpApp.getSessionKey(),
						onSuccess, onError);
	}
}
