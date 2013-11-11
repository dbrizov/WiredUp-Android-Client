package wiredup.fragments.profile;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SkillsFragment extends Fragment {
	private int userId;
	private boolean isUserSkillsDataLoaded;
	private boolean isAllSkillsDataLoaded;

	private List<SkillModel> skillModels;
	private SkillsAdapter listViewSkillsAdapter;

	private List<String> skillNames;
	private ArrayAdapter<String> autoCompleteSkillNamesAdapter;

	private ListView listViewSkills;
	private AutoCompleteTextView autoCompleteSkillNames;
	private Button btnAddSkill;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.skillModels = new ArrayList<SkillModel>();
		this.skillNames = new ArrayList<String>();

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

		this.autoCompleteSkillNames = (AutoCompleteTextView) rootLayoutView
				.findViewById(R.id.autoCompleteTextView_skillName);

		this.btnAddSkill = (Button) rootLayoutView
				.findViewById(R.id.btn_addSkill);

		this.btnAddSkill.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String skillName = SkillsFragment.this.autoCompleteSkillNames
						.getText().toString().trim();

				// Validate skill name
				if (skillName == null || skillName.length() == 0) {
					Toast.makeText(SkillsFragment.this.getActivity(),
							"The skill field is required", Toast.LENGTH_LONG)
							.show();
				} else {
					SkillsFragment.this.addNewSkill(skillName);
					SkillsFragment.this.autoCompleteSkillNames.setText("");
				}
			}
		});

		if (!(this.isUserSkillsDataLoaded && this.isAllSkillsDataLoaded)) {
			this.getDataFromServerAndSetUpView();
		} else {
			this.setUpListView(this.getActivity(), this.skillModels);
			this.setUpAutoCompleteForSkills(this.getActivity(), this.skillNames);
		}

		return rootLayoutView;
	}

	private void addNewSkill(String skillName) {
		SkillModel model = new SkillModel();
		model.setName(skillName);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				Gson gson = new Gson();
				SkillModel model = gson.fromJson(data, SkillModel.class);

				SkillsFragment.this.skillModels.add(0, model);
				SkillsFragment.this.listViewSkillsAdapter
						.notifyDataSetChanged();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						SkillsFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getSkills()
				.add(model, WiredUpApp.getSessionKey(), onSuccess, onError);
	}

	private void setUpListView(Context context, List<SkillModel> skills) {
		this.listViewSkillsAdapter = new SkillsAdapter(context,
				R.layout.list_row_skill, skills);

		this.listViewSkills.setAdapter(this.listViewSkillsAdapter);
	}

	private void setUpAutoCompleteForSkills(Context context, List<String> skills) {
		this.autoCompleteSkillNamesAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, skills);

		this.autoCompleteSkillNames
				.setAdapter(this.autoCompleteSkillNamesAdapter);
	}

	private void loadUserSkillsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<SkillModel>>() {}.getType();

		this.skillModels = gson.fromJson(data, listType);
		this.isUserSkillsDataLoaded = true;

		Log.d("debug", "User Skills Loaded");
	}

	private void loadAllSkillsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<SkillModel>>() {
		}.getType();

		List<SkillModel> models = gson.fromJson(data, listType);
		for (SkillModel model : models) {
			this.skillNames.add(model.getName());
		}

		this.isAllSkillsDataLoaded = true;

		Log.d("debug", "AutoComplete Loaded");
	}

	private void getDataFromServerAndSetUpView() {
		this.getUserSkillsAndSetUpListView();
		this.getAllSkillsAndSetUpAutoComplete();
	}

	private void getUserSkillsAndSetUpListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				SkillsFragment.this.loadUserSkillsData(data);
				SkillsFragment.this.setUpListView(
						SkillsFragment.this.getActivity(),
						SkillsFragment.this.skillModels);
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

	private void getAllSkillsAndSetUpAutoComplete() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				SkillsFragment.this.loadAllSkillsData(data);
				SkillsFragment.this.setUpAutoCompleteForSkills(
						SkillsFragment.this.getActivity(),
						SkillsFragment.this.skillNames);
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						SkillsFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getSkills()
				.getAll(WiredUpApp.getSessionKey(), onSuccess, onError);
	}
}
