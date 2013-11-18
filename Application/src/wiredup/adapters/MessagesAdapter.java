package wiredup.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.MessageModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessagesAdapter extends BaseAdapter {
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final int MESSAGE_CONTENT_LENGTH_LIMIT = 35;
	
	private Context context;
	private int rowLayoutId;
	private List<MessageModel> messages;
	
	public MessagesAdapter(Context context, int rowLayoutId, List<MessageModel> messages) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.messages = messages;
	}
	
	@Override
	public int getCount() {
		return this.messages.size();
	}

	@Override
	public Object getItem(int position) {
		return this.messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.messages.get(position).getId();
	}
	
	public int getSenderId(int position) {
		return this.messages.get(position).getSenderId();
	}
	
	public int getReceiverId(int position) {
		return this.messages.get(position).getReceiverId();
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View listRow = convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = inflater.inflate(this.rowLayoutId, parent, false);
		}
		
		String messageContent = this.messages.get(position).getContent();
		if (messageContent.length() > MESSAGE_CONTENT_LENGTH_LIMIT) {
			messageContent = messageContent.substring(0, MESSAGE_CONTENT_LENGTH_LIMIT - 1) + "...";
		}
		
		TextView textViewMessageContent = (TextView) listRow.findViewById(R.id.textView_messageContent);
		textViewMessageContent.setText(messageContent);

		String from = "From: " + this.messages.get(position).getSenderName();
		TextView textViewSenderName = (TextView) listRow.findViewById(R.id.textView_senderName);
		textViewSenderName.setText(from);

		String to = "To: " + this.messages.get(position).getReceiverName();
		TextView textViewReceiverName = (TextView) listRow.findViewById(R.id.textView_receiverName);
		textViewReceiverName.setText(to);

		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String postDateAsString = dateFormat.format(this.messages.get(position).getPostDate());
		TextView textViewPostDate = (TextView) listRow.findViewById(R.id.textView_postDate);
		textViewPostDate.setText(postDateAsString);
		
		ImageView deleteButton = (ImageView) listRow.findViewById(R.id.imageView_deleteButton);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MessagesAdapter.this.showDeleteMessageDialog(position);
			}
		});

		return listRow;
	}
	
	private void deleteMessage(final int rowIndex) {
		int messageId = (int) this.getItemId(rowIndex);
		
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				MessagesAdapter.this.messages.remove(rowIndex);
				MessagesAdapter.this.notifyDataSetChanged();
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(MessagesAdapter.this.context, data);
			}
		};
		
		WiredUpApp.getData().getMessages().delete(messageId, WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void showDeleteMessageDialog(final int rowIndex) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MessagesAdapter.this.context);
		
		dialogBuilder.setTitle("Delete this message?");
		dialogBuilder.setNegativeButton(R.string.btn_no, null);
		dialogBuilder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MessagesAdapter.this.deleteMessage(rowIndex);
			}
		});
		
		Dialog dialog = dialogBuilder.create();
		dialog.show();
	}
}























