package wiredup.fragments.profile.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.activities.ProjectDetailsActivity;
import wiredup.adapters.ProfileActivityProjectsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ProjectModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProjectsFragment extends Fragment {
	private int userId;
	private List<ProjectModel> projects;
	private boolean isDataLoaded;
	
	private ListView listViewProjects;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(BundleKey.USER_ID);
		
		this.projects = new ArrayList<ProjectModel>();
		this.isDataLoaded = false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_projects_profile_activity, container, false);
		
		this.listViewProjects = (ListView) rootView.findViewById(R.id.listView_projects);
		
		if (!this.isDataLoaded) {
			this.getProjectsDataFromServerAndSetUpListView();
		} else {
			this.setUpListView();
		}
		
		return rootView;
	}
	
	private void getProjectsDataFromServerAndSetUpListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProjectsFragment.this.loadProjectsData(data);
				ProjectsFragment.this.setUpListView();
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ProjectsFragment.this.getActivity(), data);
			}
		};
		
		WiredUpApp.getData().getProjects().getAll(this.userId, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void loadProjectsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<ProjectModel>>() {}.getType();

		this.projects = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "Projects Loaded");
	}
	
	private void setUpListView() {
		ProfileActivityProjectsAdapter adapter =
				new ProfileActivityProjectsAdapter(
						this.getActivity(),
						R.layout.list_row_project_profile_activity,
						this.projects);
		
		this.listViewProjects.setAdapter(adapter);
		this.listViewProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View row, int rowIndex, long projectId) {
				ProjectsFragment.this.startProjectDetailsActivity((int) projectId);
			}
		});
	}
	
	private void startProjectDetailsActivity(int projectId) {
		Intent intent = new Intent(this.getActivity(), ProjectDetailsActivity.class);
		intent.putExtra(BundleKey.PROJECT_ID, projectId);
		
		this.startActivity(intent);
	}
}
