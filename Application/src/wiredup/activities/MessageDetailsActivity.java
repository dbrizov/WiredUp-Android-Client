package wiredup.activities;

import wiredup.client.R;
import wiredup.models.MessageModel;
import wiredup.utils.BundleKey;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageDetailsActivity extends OptionsMenuActivity {
	private MessageModel messageModel;
	
	private TextView textViewSenderName;
	private TextView textViewMessageContent;
	private TextView textViewPostDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_message_details);
		
		this.messageModel = 
				(MessageModel) this.getIntent().getExtras().getSerializable(BundleKey.MESSAGE_MODEL);
		Log.d("debug", Integer.toString(this.messageModel.getSenderId()));
		
		this.textViewSenderName = (TextView) this.findViewById(R.id.textView_senderName);
		this.textViewMessageContent = (TextView) this.findViewById(R.id.textView_messageContent);
		this.textViewPostDate = (TextView) this.findViewById(R.id.textView_postDate);
	}
}
