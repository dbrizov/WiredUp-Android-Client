package wiredup.activities;

import com.google.gson.Gson;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ProjectDetailsModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProjectDetailsActivity extends OptionsMenuActivity {
	private ProjectDetailsModel projectDetailsModel;
	
	private TextView textViewProjectName;
	private TextView textViewProjectDescription;
	private TextView textViewProjectUrl;
	private Button btnSeeProject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_project_details);
		
		int projectId = this.getIntent().getExtras().getInt(BundleKey.PROJECT_ID);
		
		this.textViewProjectName = (TextView) this.findViewById(R.id.textView_projectName);
		this.textViewProjectDescription = (TextView) this.findViewById(R.id.textView_projectDescription);
		this.textViewProjectUrl = (TextView) this.findViewById(R.id.textView_projectUrl);
		this.btnSeeProject = (Button) this.findViewById(R.id.btn_seeProject);
		
		this.getProjectDetailsFromServerAndSetUpView(projectId);
	}
	
	private void getProjectDetailsFromServerAndSetUpView(int projectId) {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ProjectDetailsActivity.this.projectDetailsModel =
						ProjectDetailsActivity.this.parseProjectDetailsJsonString(data);
				
				ProjectDetailsActivity.this.setUpView();
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier
					.displayErrorMessage(ProjectDetailsActivity.this, data);
			}
		};
		
		WiredUpApp
				.getData()
				.getProjects()
				.getDetails(projectId, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private ProjectDetailsModel parseProjectDetailsJsonString(String jsonData) {
		Gson gson = new Gson();
		return gson.fromJson(jsonData, ProjectDetailsModel.class);
	}
	
	private void setUpView() {
		this.textViewProjectName
			.setText(this.projectDetailsModel.getName());
		
		this.textViewProjectDescription
			.setText("Descriotion: " + this.projectDetailsModel.getDescription());
		
		this.textViewProjectUrl
			.setText("Url: " + this.projectDetailsModel.getUrl());

		// TODO Set up the button
	}
}
