package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ConnectionModel;
import wiredup.utils.Encryptor;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConnetionsAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<ConnectionModel> connections;
	
	public ConnetionsAdapter(Context context, int rowLayoutId, List<ConnectionModel> connections) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.connections = connections;
	}

	@Override
	public int getCount() {
		return this.connections.size();
	}

	@Override
	public Object getItem(int position) {
		return this.connections.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.connections.get(position).getId();
	}
	
	public int getUserId(int position) {
		return this.connections.get(position).getOtherUserId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		ConnectionModel connectionModel = this.connections.get(position);
		
		// Set up the user display name
		TextView textViewUserDisplayName = (TextView) listRow.findViewById(R.id.textView_userDisplayName);
		textViewUserDisplayName.setText(connectionModel.getOtherUserDisplayName());
		
		// Set up the user photo
		ImageView imageViewUserPhoto = (ImageView) listRow.findViewById(R.id.imageView_userPhoto);
		SetUpUserPhotoTask setUpUserPhotoTask = new SetUpUserPhotoTask(imageViewUserPhoto);
		setUpUserPhotoTask.execute(connectionModel.getOtherUserPhoto());
		
		// Set up the delete connection button
		ImageView imageViewDeleteConnectionButton = (ImageView) listRow.findViewById(R.id.imageView_deleteButton);
		imageViewDeleteConnectionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnetionsAdapter.this.showDeleteConnectionDialog(position);
			}
		});
		
		return listRow;
	}
	
	private void deleteConnection(final int rowIndex) {
		int connectionId = (int) ConnetionsAdapter.this.getItemId(rowIndex);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ConnetionsAdapter.this.connections.remove(rowIndex);
				ConnetionsAdapter.this.notifyDataSetChanged();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ConnetionsAdapter.this.context, data);
			}
		};

		WiredUpApp
				.getData()
				.getConnections()
				.delete(connectionId, WiredUpApp.getSessionKey(), onSuccess,
						onError);
	}
	
	private void showDeleteConnectionDialog(final int rowIndex) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);
		
		dialogBuilder.setTitle("Delete this connection?");
		dialogBuilder.setNegativeButton(R.string.btn_no, null);
		dialogBuilder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ConnetionsAdapter.this.deleteConnection(rowIndex);
			}
		});
		
		Dialog dialog = dialogBuilder.create();
		dialog.show();
	}
	
	private class SetUpUserPhotoTask extends AsyncTask<String, Void, Bitmap> {
		private ImageView userPhoto;
		
		public SetUpUserPhotoTask(ImageView userPhoto) {
			this.userPhoto = userPhoto;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String userPhotoBase64String = params[0];
			Bitmap userPhotoBitmap = null;
			
			if (userPhotoBase64String != null) {
				byte[] userPhotoByteArray = Encryptor.Base64StringToByteArray(userPhotoBase64String);

				userPhotoBitmap = BitmapFactory.decodeByteArray(
						userPhotoByteArray, 0, userPhotoByteArray.length);
				
				return userPhotoBitmap;
			}
			
			return userPhotoBitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			
			if (result != null) {
				this.userPhoto.setImageBitmap(result);
			}
		}
	}
}
