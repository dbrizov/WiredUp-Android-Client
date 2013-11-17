package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.models.ConnectionModel;
import wiredup.utils.Encryptor;
import android.app.Activity;
import android.content.Context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
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
				// TODO Delete the connection
			}
		});
		
		return listRow;
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
