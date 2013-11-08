package wiredup.fragments;

import wiredup.client.R;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
	private ImageView imageViewPhoto;
	private TextView textViewDisplayName;
	private TextView textViewEmail;
	private TextView textViewCountry;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);

		this.imageViewPhoto = (ImageView) rootView
				.findViewById(R.id.imageView_userPhoto);
		
		this.textViewDisplayName = (TextView) rootView
				.findViewById(R.id.textView_userDisplayName);
		this.textViewDisplayName.setText(WiredUpApp.getUserDisplayName());
		
		this.textViewEmail = (TextView) rootView
				.findViewById(R.id.textView_email);
		
		this.textViewCountry = (TextView) rootView
				.findViewById(R.id.textView_country);

		return rootView;
	}

	private void displayUserDetails() {
	}
}
