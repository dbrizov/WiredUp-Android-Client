package wiredup.fragments;

import wiredup.client.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class RegisterDialogFragment extends DialogFragment {
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

		View view = inflater.inflate(R.layout.dialog_fragment_register, null);
		
		this.editTextFirstName = (EditText) view.findViewById(R.id.editText_firstName);
		this.editTextLastName = (EditText) view.findViewById(R.id.editText_lastName);
		this.editTextEmail = (EditText) view.findViewById(R.id.editText_email);
		this.editTextPassword = (EditText) view.findViewById(R.id.editText_password);
		this.editTextConfirmPassword = (EditText) view.findViewById(R.id.editText_confirmPassword);
		
		builder.setView(view);
		builder.setTitle(R.string.register);

		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		builder.setPositiveButton(R.string.register,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		Dialog dialog = builder.create();
		return dialog;
	}
}
