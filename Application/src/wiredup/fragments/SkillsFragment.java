package wiredup.fragments;

import java.lang.reflect.Type;
import java.util.List;

import wiredup.adapters.SkillsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ServerResponseModel;
import wiredup.models.SkillModel;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SkillsFragment extends Fragment {
	private boolean isDataLoaded;
	private List<SkillModel> skills;

	private ListView listViewSkills;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.isDataLoaded = false;
		this.skills = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(R.layout.fragment_skills,
				container, false);

		this.listViewSkills = (ListView) rootLayoutView
				.findViewById(R.id.listView_skills);

		int userId = WiredUpApp.getUserId();
		String sessionKey = WiredUpApp.getSessionKey();

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				SkillsFragment.this.loadSkillsData(data);
				SkillsFragment.this.initializeListView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				SkillsFragment.this.displayErrorMessage(data);
			}
		};

		if (!this.isDataLoaded) {
			WiredUpApp.getData().getSkills()
					.getAll(userId, sessionKey, onSuccess, onError);
		} else {
			this.initializeListView();
		}

		return rootLayoutView;
	}

	private void loadSkillsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<SkillModel>>() {}.getType();

		this.skills = gson.fromJson(data, listType);
		this.isDataLoaded = true;
		
		Log.d("debug", "Skills Loaded");
	}

	private void initializeListView() {
		ListAdapter skillsAdapter = new SkillsAdapter(this.getActivity(),
				R.layout.list_row_skill, this.skills);

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
