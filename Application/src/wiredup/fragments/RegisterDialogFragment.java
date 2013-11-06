package wiredup.fragments;

import wiredup.client.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

		builder.setNegativeButton(R.string.cancel, null);
		builder.setPositiveButton(R.string.register, null);

		Dialog dialog = builder.create();
		return dialog;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AlertDialog dialog = (AlertDialog) this.getDialog();
		
		Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		positiveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(RegisterDialogFragment.this.getActivity(), "toast", Toast.LENGTH_SHORT).show();
				
				//dialog.dismiss();
			}
		});
	}
}
