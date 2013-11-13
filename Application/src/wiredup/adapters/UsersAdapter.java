package wiredup.adapters;

import java.util.List;

import wiredup.client.R;
import wiredup.models.UserModel;
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

public class UsersAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<UserModel> users;
	
	private ImageView imageViewUserPhoto;
	private TextView textViewUserDisplayName;
	
	public UsersAdapter(Context context, int rowLayoutId, List<UserModel> users) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.users = users;
	}

	@Override
	public int getCount() {
		return this.users.size();
	}

	@Override
	public Object getItem(int position) {
		return this.users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.users.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		UserModel userModel = this.users.get(position);
		
		// Set up the user display name
		this.textViewUserDisplayName = (TextView) listRow.findViewById(R.id.textView_userDisplayName);
		this.textViewUserDisplayName.setText(userModel.getDisplayName());
		
		// Set up the user photo
		this.imageViewUserPhoto = (ImageView) listRow.findViewById(R.id.imageView_userPhoto);
		SetUpUserPhotoTask setUpUserPhotoTask = new SetUpUserPhotoTask(this.imageViewUserPhoto);
		setUpUserPhotoTask.execute(userModel.getPhoto());
		
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
