package wiredup.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import wiredup.client.R;
import wiredup.fragments.user.activity.SendMessageDialogFragment;
import wiredup.models.MessageModel;
import wiredup.utils.BundleKey;
import wiredup.utils.WiredUpApp;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetailsActivity extends OptionsMenuActivity {
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	
	private TextView textViewSenderName;
	private TextView textViewReceiverName;
	private TextView textViewMessageContent;
	private TextView textViewPostDate;
	private Button btnMessageReply;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_message_details);
		
		final MessageModel messageModel = 
				(MessageModel) this.getIntent().getExtras().getSerializable(BundleKey.MESSAGE_MODEL);
		
		// Set up the sub views
		this.textViewSenderName = (TextView) this.findViewById(R.id.textView_senderName);
		this.textViewSenderName.setText("From: " + messageModel.getSenderName());
		
		this.textViewReceiverName = (TextView) this.findViewById(R.id.textView_receiverName);
		this.textViewReceiverName.setText("To: " + messageModel.getReceiverName());
		
		this.textViewMessageContent = (TextView) this.findViewById(R.id.textView_messageContent);
		this.textViewMessageContent.setText(messageModel.getContent());
		
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String postDateAsString = dateFormat.format(messageModel.getPostDate());
		this.textViewPostDate = (TextView) this.findViewById(R.id.textView_postDate);
		this.textViewPostDate.setText(postDateAsString);
		
		this.btnMessageReply = (Button) this.findViewById(R.id.btn_messageReply);
		if (WiredUpApp.getUserDisplayName().equals(messageModel.getSenderName())) {
			// The current logged user is the sender of the message
			this.btnMessageReply.setVisibility(View.GONE);
		} else {
			this.btnMessageReply.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// The receiver of the message will be the
					// sender of the messageModel
					MessageDetailsActivity.this.showSendMessageDialog(
							messageModel.getSenderId(),
							messageModel.getSenderName());
				}
			});
		}
	}
	
	private void showSendMessageDialog(int receiverId, String receiverName) {
		SendMessageDialogFragment dialog = new SendMessageDialogFragment();
		
		Bundle bundle = new Bundle();
		bundle.putInt(BundleKey.RECEIVER_ID, receiverId);
		bundle.putString(BundleKey.RECEIVER_NAME, receiverName);
		
		dialog.setArguments(bundle);
		
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		dialog.show(fragmentManager, this.getString(R.string.fragment_send_message));
	}
}
