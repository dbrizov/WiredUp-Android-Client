package wiredup.fragments.user.activity;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.MessageSendModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageDialogFragment extends DialogFragment {
	private int receiverId;
	private EditText editTextMessageContent;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View rootView = inflater.inflate(R.layout.dialog_fragment_send_message,
				null);
		
		this.receiverId = this.getArguments().getInt(BundleKey.RECEIVER_ID);

		this.editTextMessageContent = (EditText) rootView
				.findViewById(R.id.editText_messageContent);

		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setView(rootView);
		builder.setTitle(R.string.title_new_message);
		builder.setNegativeButton(R.string.cancel, null);
		builder.setPositiveButton(R.string.send, null);

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
				String messageContent = SendMessageDialogFragment.this.editTextMessageContent
						.getText().toString().trim();

				if (!SendMessageDialogFragment.this
						.isMessageContentValid(messageContent)) {
					Toast.makeText(
							SendMessageDialogFragment.this.getActivity(),
							"The message content is required", Toast.LENGTH_LONG)
							.show();
				} else {
					SendMessageDialogFragment.this.sendMessage(messageContent);
				}
			}
		});
	}

	private boolean isMessageContentValid(String messageContent) {
		boolean isValid = true;
		if (messageContent == null || messageContent.length() == 0) {
			isValid = false;
		}

		return isValid;
	}

	private void sendMessage(String messageContent) {
		MessageSendModel message = new MessageSendModel(this.receiverId,
				messageContent);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				Toast.makeText(SendMessageDialogFragment.this.getActivity(),
						"Message sent successfully", Toast.LENGTH_LONG).show();

				SendMessageDialogFragment.this.dismiss();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						SendMessageDialogFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getMessages()
				.send(message, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
}
