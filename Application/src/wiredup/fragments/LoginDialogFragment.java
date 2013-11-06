package wiredup.fragments;

import wiredup.client.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class LoginDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		LayoutInflater inflater = this.getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.dialog_fragment_login, null);
		builder.setView(view);
		builder.setTitle(R.string.login);

		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		builder.setPositiveButton(R.string.login,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		Dialog dialog = builder.create();
		return dialog;
	}
}
