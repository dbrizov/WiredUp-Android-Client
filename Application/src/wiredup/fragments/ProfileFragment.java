package wiredup.fragments;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ServerResponseModel;
import wiredup.models.UserDetailsModel;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ProfileFragment extends Fragment {
	private UserDetailsModel userDetailsModel;
	private boolean isDataLoaded;

	private TextView textViewDisplayName;
	private TextView textViewEmail;
	private TextView textViewCountry;
	private LinearLayout linearLayoutSkills;
	private ListView listViewSkills;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.isDataLoaded = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);

		this.textViewDisplayName = (TextView) rootView
				.findViewById(R.id.textView_userDisplayName);
		this.textViewEmail = (TextView) rootView
				.findViewById(R.id.textView_email);
		this.textViewCountry = (TextView) rootView
				.findViewById(R.id.textView_country);

		this.linearLayoutSkills = (LinearLayout) rootView
				.findViewById(R.id.linearLayout_skills);
		this.listViewSkills = (ListView) this.linearLayoutSkills
				.findViewById(R.id.listView_skills);

		int userId = WiredUpApp.getUserId();
		String sessionKey = WiredUpApp.getSessionKey();

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProfileFragment.this.loadUserDetails(data);
				ProfileFragment.this.displayUserDetails();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ProfileFragment.this.displayErrorMessage(data);
			}
		};

		if (!this.isDataLoaded) {
			WiredUpApp.getData().getUsers()
					.getDetails(userId, sessionKey, onSuccess, onError);
		} else {
			this.displayUserDetails();
		}

		return rootView;
	}

	private void loadUserDetails(String data) {
		Gson gson = new Gson();
		this.userDetailsModel = gson.fromJson(data, UserDetailsModel.class);
		this.isDataLoaded = true;

		Log.d("debug", "data loaded");
	}

	private void displayUserDetails() {
		this.textViewDisplayName
				.setText(this.userDetailsModel.getDisplayName());
		this.textViewEmail.setText(this.userDetailsModel.getEmail());
		this.textViewCountry.setText(this.userDetailsModel.getCountry());

		ListAdapter skillsAdapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				this.userDetailsModel.getSkills());
		this.listViewSkills.setAdapter(skillsAdapter);
	}

	private void displayErrorMessage(String data) {
		Gson gson = new Gson();
		ServerResponseModel response = gson.fromJson(data,
				ServerResponseModel.class);

		Toast.makeText(this.getActivity(), response.getMessage(),
				Toast.LENGTH_LONG).show();
	}
}
