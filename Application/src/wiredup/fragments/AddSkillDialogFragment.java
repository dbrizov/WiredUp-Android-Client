package wiredup.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.SkillModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AddSkillDialogFragment extends DialogFragment {
	private AutoCompleteTextView authCompleteTextViewSkillName;
	private List<String> skills;
	private boolean isDataLoaded;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.skills = new ArrayList<String>();
		this.isDataLoaded = false;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = this.getActivity().getLayoutInflater();

		View rootView = inflater.inflate(R.layout.dialog_fragment_add_skill,
				null);

		this.authCompleteTextViewSkillName = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView_skillName);

		if (!this.isDataLoaded) {
			this.getAllSkillsFromServerAndSetSkillsAdapter();
		} else {
			this.setSkillsAdapter();
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		
		builder.setView(rootView);
		builder.setTitle(R.string.title_add_new_skill);
		
		builder.setNegativeButton(R.string.cancel, null);
		builder.setPositiveButton(R.string.btn_add, null);

		Dialog dialog = builder.create();
		return dialog;
	}

	private void getAllSkillsFromServerAndSetSkillsAdapter() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				AddSkillDialogFragment.this.loadSkillsData(data);
				AddSkillDialogFragment.this.setSkillsAdapter();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						AddSkillDialogFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getSkills()
				.getAll(WiredUpApp.getSessionKey(), onSuccess, onError);
	}

	private void loadSkillsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<SkillModel>>() {}.getType();

		List<SkillModel> skillModels = gson.fromJson(data, listType);
		for (SkillModel model : skillModels) {
			this.skills.add(model.getName());
		}
		
		Log.d("debug", "AutoComplete Loaded");

		this.isDataLoaded = true;
	}
	
	private void setSkillsAdapter() {
		ArrayAdapter<String> skillsAdapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				this.skills);
		
		this.authCompleteTextViewSkillName.setAdapter(skillsAdapter);
	}
}
