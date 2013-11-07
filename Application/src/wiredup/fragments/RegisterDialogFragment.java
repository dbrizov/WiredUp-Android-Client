package wiredup.fragments;

import com.google.gson.Gson;

import wiredup.activities.UserActivity;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ServerResponseModel;
import wiredup.models.UserLoggedModel;
import wiredup.models.UserRegisterModel;
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

public class RegisterDialogFragment extends DialogFragment {
	private static final int PASSWORD_MIN_LENGTH = 6;
	
	private EditText editTextFirstName;
	private EditText editTextLastName;
	private EditText editTextEmail;
	private EditText editTextPassword;
	private EditText editTextConfirmPassword;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		LayoutInflater inflater = this.getActivity().getLayoutInflater();

		View rootView = inflater.inflate(R.layout.dialog_fragment_register, null);
		
		this.editTextFirstName = (EditText) rootView.findViewById(R.id.editText_firstName);
		this.editTextLastName = (EditText) rootView.findViewById(R.id.editText_lastName);
		this.editTextEmail = (EditText) rootView.findViewById(R.id.editText_email);
		this.editTextPassword = (EditText) rootView.findViewById(R.id.editText_password);
		this.editTextConfirmPassword = (EditText) rootView.findViewById(R.id.editText_confirmPassword);
		
		builder.setView(rootView);
		builder.setTitle(R.string.register);

		builder.setNegativeButton(R.string.cancel, null);
		builder.setPositiveButton(R.string.register, null);

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
				String firstName = RegisterDialogFragment.this.editTextFirstName.getText().toString();
				String lastName = RegisterDialogFragment.this.editTextLastName.getText().toString();
				String email = RegisterDialogFragment.this.editTextEmail.getText().toString();
				String password = RegisterDialogFragment.this.editTextPassword.getText().toString();
				String confirmPassword = RegisterDialogFragment.this.editTextConfirmPassword.getText().toString();
				
				StringBuilder errorMessageBuilder = new StringBuilder();
				if (!RegisterDialogFragment.this.isNameValid(firstName)) {
					errorMessageBuilder.append("'First Name' is required\n");
				}
				
				if (!RegisterDialogFragment.this.isNameValid(lastName)) {
					errorMessageBuilder.append("'Last Name' is required\n");
				}
				
				if (!RegisterDialogFragment.this.isEmailValid(email)) {
					errorMessageBuilder.append("'Email' is invalid\n");
				}
				
				if (!RegisterDialogFragment.this.isPasswordValid(password)) {
					errorMessageBuilder.append(
							String.format("'Password' must be at least %s characters\n", PASSWORD_MIN_LENGTH));
				}
				
				if (!RegisterDialogFragment.this.arePasswordsEqual(password, confirmPassword)) {
					errorMessageBuilder.append("'Passwords' don't match\n");
				}
				
				if (errorMessageBuilder.length() != 0) {
					Toast.makeText(
							RegisterDialogFragment.this.getActivity(),
							errorMessageBuilder.toString(),
							Toast.LENGTH_LONG).show();
				} else {
					String sha1Password = Encryptor.sha1Hash(password);
					
					UserRegisterModel user = new UserRegisterModel();
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
					user.setAuthCode(sha1Password);
					user.setConfirmAuthCode(sha1Password);
					
					IOnSuccess onSuccess = new IOnSuccess() {
						@Override
						public void performAction(String data) {
							RegisterDialogFragment.this.startUserActivity(data);
						}
					};
					
					IOnError onError = new IOnError() {
						@Override
						public void performAction(String data) {
							RegisterDialogFragment.this.displayErrorMessage(data);
						}
					};
					
					WiredUpApp.getData().getUsers().register(user, onSuccess, onError);
				}
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
	
	private boolean isNameValid(String name) {
		boolean isValid = true;
		if (name == null || name.trim().length() == 0) {
			isValid = false;
		}
		
		return isValid;
	}
	
	private boolean isPasswordValid(String password) {
		boolean isValid = true;
		if (password == null || password.trim().length() < PASSWORD_MIN_LENGTH) {
			isValid = false;
		}
		
		return isValid;
	}
	
	private boolean arePasswordsEqual(String password, String confirmPassword) {
		boolean areEqual = true;
		if (password.compareTo(confirmPassword) != 0) {
			areEqual = false;
		}
		
		return areEqual;
	}
	
	private boolean isEmailValid(String email) {
		boolean isValid = true;
		if (!email.matches("[a-zA-Z_.]+@[a-zA-Z_]+?\\.[a-zA-Z]{2,4}")) {
			isValid = false;
		}
		
		return isValid;
	}
}
