package wiredup.fragments;

import wiredup.activities.UserActivity;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ServerResponseModel;
import wiredup.models.UserLoggedModel;
import wiredup.models.UserLoginModel;
import wiredup.utils.Encryptor;
import wiredup.utils.WiredUpApp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginDialogFragment extends DialogFragment {
	private EditText editTextEmail;
	private EditText editTextPassword;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		LayoutInflater inflater = this.getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.dialog_fragment_login, null);
		
		this.editTextEmail = (EditText) view.findViewById(R.id.editText_email);
		this.editTextPassword = (EditText) view.findViewById(R.id.editText_password);
		
		builder.setView(view);
		builder.setTitle(R.string.login);

		builder.setNegativeButton(R.string.cancel, null);
		builder.setPositiveButton(R.string.login, null);

		Dialog dialog = builder.create();
		return dialog;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		final AlertDialog dialog = (AlertDialog) this.getDialog();
		
		Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		positiveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = LoginDialogFragment.this.editTextEmail.getText().toString();
				String password = LoginDialogFragment.this.editTextPassword.getText().toString();
				
				String sha1Password = Encryptor.sha1Hash(password);
				
				UserLoginModel user = new UserLoginModel(email, sha1Password);
				
				IOnSuccess onSuccess = new IOnSuccess() {
					@Override
					public void performAction(String data) {
						LoginDialogFragment.this.startUserActivity(data);
					}
				};
				
				IOnError onError = new IOnError() {
					@Override
					public void performAction(String data) {
						LoginDialogFragment.this.displayErrorMessage(data);
					}
				};
				
				WiredUpApp.getData().getUsers().login(user, onSuccess, onError);
			}
		});
	}
	
	private void displayErrorMessage(String data) {
		Gson gson = new Gson();
		ServerResponseModel response = gson.fromJson(data, ServerResponseModel.class);
		
		Toast.makeText(
				this.getActivity(),
				response.getMessage(),
				Toast.LENGTH_LONG).show();
	}
	
	private void startUserActivity(String data) {
		Gson gson = new Gson();
		UserLoggedModel userLoggedModel = gson.fromJson(data, UserLoggedModel.class);
		
		WiredUpApp.setUserId(userLoggedModel.getId());
		WiredUpApp.setUserDisplayName(userLoggedModel.getDisplayName());
		WiredUpApp.setSessionKey(userLoggedModel.getSessionKey());
		
		Intent intent = new Intent(this.getActivity(), UserActivity.class);
		this.getActivity().startActivity(intent);
	}
}
