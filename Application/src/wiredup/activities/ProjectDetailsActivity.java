package wiredup.activities;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ProjectDetailsModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

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
				ProjectDetailsActivity.this.loadProjectDetailsData(data);
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
	
	private void loadProjectDetailsData(String jsonData) {
		Gson gson = new Gson();
		this.projectDetailsModel = 
				gson.fromJson(jsonData, ProjectDetailsModel.class);
	}
	
	private void setUpView() {
		String projectName = this.projectDetailsModel.getName();
		this.textViewProjectName.setText(projectName);
		
		String projectDescription = this.projectDetailsModel.getDescription();
		this.textViewProjectDescription.setText("Descriotion: " + projectDescription);
		
		final String projectUrl = this.projectDetailsModel.getUrl();
		this.textViewProjectUrl.setText("Url: " + projectUrl);

		if (projectUrl == null || projectUrl.length() == 0) {
			this.btnSeeProject.setEnabled(false);
		} else {
			this.btnSeeProject.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ProjectDetailsActivity.this.startBrowser(projectUrl);
				}
			});
		}
	}
	
	private void startBrowser(String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));

		this.startActivity(intent);
	}
}
