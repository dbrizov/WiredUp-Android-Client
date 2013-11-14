package wiredup.fragments.user.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import wiredup.adapters.UserActivitySkillsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.SkillModel;
import wiredup.utils.BundleKeys;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SkillsFragment extends Fragment {
	private int userId;
	private List<SkillModel> skills;
	private UserActivitySkillsAdapter skillsAdapter;
	private boolean isDataLoaded;

	private ListView listViewSkills;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(BundleKeys.USER_ID);

		this.skills = new ArrayList<SkillModel>();
		this.isDataLoaded = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(
				R.layout.fragment_skills_user_activity, container, false);

		// Find the listView
		this.listViewSkills = (ListView) rootLayoutView.findViewById(R.id.listView_skills);
		
		// Set up the listView
		if (!this.isDataLoaded) {
			this.getDataFromServerAndSetUpSkillsListView();
		} else {
			this.setUpSkillsListView();
		}

		return rootLayoutView;
	}

	private void getDataFromServerAndSetUpSkillsListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				SkillsFragment.this.loadSkillsData(data);
				SkillsFragment.this.setUpSkillsListView();
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						SkillsFragment.this.getActivity(), data);
			}
		};
		
		WiredUpApp
				.getData()
				.getSkills()
				.getAllForUser(this.userId, WiredUpApp.getSessionKey(),
						onSuccess, onError);
	}
	
	private void loadSkillsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<SkillModel>>() {}.getType();

		this.skills = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "Skills Loaded");
	}
	
	private void setUpSkillsListView() {
		this.skillsAdapter = new UserActivitySkillsAdapter(this.getActivity(),
				R.layout.list_row_skill_user_activity, this.skills);
		
		this.listViewSkills.setAdapter(this.skillsAdapter);
	}
}
