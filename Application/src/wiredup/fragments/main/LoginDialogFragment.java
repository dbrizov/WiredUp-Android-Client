package wiredup.fragments.main;

import wiredup.activities.ProfileActivity;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserLoggedModel;
import wiredup.models.UserLoginModel;
import wiredup.utils.Encryptor;
import wiredup.utils.ErrorNotifier;
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

import com.google.gson.Gson;

public class LoginDialogFragment extends DialogFragment {
	private EditText editTextEmail;
	private EditText editTextPassword;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = this.getActivity().getLayoutInflater();

		View rootView = inflater.inflate(R.layout.dialog_fragment_login, null);

		this.editTextEmail = (EditText) rootView
				.findViewById(R.id.editText_email);
		this.editTextPassword = (EditText) rootView
				.findViewById(R.id.editText_password);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());

		builder.setView(rootView);
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
				String email = LoginDialogFragment.this.editTextEmail.getText()
						.toString();
				String password = LoginDialogFragment.this.editTextPassword
						.getText().toString();

				String sha1Password = Encryptor.sha1Hash(password);

				UserLoginModel user = new UserLoginModel(email, sha1Password);

				IOnSuccess onSuccess = new IOnSuccess() {
					@Override
					public void performAction(String data) {
						LoginDialogFragment.this.startProfileActivity(data);
					}
				};

				IOnError onError = new IOnError() {
					@Override
					public void performAction(String data) {
						ErrorNotifier.displayErrorMessage(
								LoginDialogFragment.this.getActivity(), data);
					}
				};

				WiredUpApp.getData().getUsers().login(user, onSuccess, onError);
			}
		});
	}

	private void startProfileActivity(String data) {
		Gson gson = new Gson();
		UserLoggedModel userLoggedModel = gson.fromJson(data,
				UserLoggedModel.class);

		WiredUpApp.setUserId(userLoggedModel.getId());
		WiredUpApp.setUserDisplayName(userLoggedModel.getDisplayName());
		WiredUpApp.setSessionKey(userLoggedModel.getSessionKey());

		Intent intent = new Intent(this.getActivity(), ProfileActivity.class);
		this.getActivity().startActivity(intent);
	}
}
