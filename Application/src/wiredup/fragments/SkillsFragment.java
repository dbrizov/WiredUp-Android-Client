package wiredup.fragments;

import java.lang.reflect.Type;
import java.util.List;

import wiredup.adapters.SkillsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.SkillModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SkillsFragment extends Fragment {
	private int userId;
	private boolean isDataLoaded;
	private List<SkillModel> skills;
	private SkillsAdapter skillsAdapter;

	private ListView listViewSkills;
	private Button btnAddSkill;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.isDataLoaded = false;
		this.skills = null;

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(WiredUpApp.USER_ID_BUNDLE_KEY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootLayoutView = inflater.inflate(R.layout.fragment_skills,
				container, false);

		this.listViewSkills = (ListView) rootLayoutView
				.findViewById(R.id.listView_skills);
		this.btnAddSkill = (Button) rootLayoutView
				.findViewById(R.id.btn_addSkill);

		this.attachEventForAddSkillButton();

		if (!this.isDataLoaded) {
			this.getDataFromServerAndInitializeListView();
		} else {
			this.initializeListView(this.getActivity(), this.skills);
		}

		return rootLayoutView;
	}

	private void initializeListView(Context context, List<SkillModel> skills) {
		this.skillsAdapter = new SkillsAdapter(context,
				R.layout.list_row_skill, skills);

		this.listViewSkills.setAdapter(this.skillsAdapter);
	}

	private void loadSkillsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<SkillModel>>() {
		}.getType();

		this.skills = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "Skills Loaded");
	}

	private void getDataFromServerAndInitializeListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				SkillsFragment.this.loadSkillsData(data);
				SkillsFragment.this.initializeListView(
						SkillsFragment.this.getActivity(),
						SkillsFragment.this.skills);
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

	private void attachEventForAddSkillButton() {
		this.btnAddSkill.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SkillsFragment.this.showAddSkillDialog();
			}
		});
	}

	private void showAddSkillDialog() {
		DialogFragment dialog = new AddSkillDialogFragment();
		FragmentManager manager = this.getActivity()
				.getSupportFragmentManager();

		dialog.show(manager,
				this.getActivity().getString(R.string.fragment_add_skill));
	}
}
