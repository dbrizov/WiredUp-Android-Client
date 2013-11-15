package wiredup.fragments.profile.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.adapters.MessagesAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.MessageModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ReceivedMessagesFragment extends Fragment {
private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	private List<MessageModel> receivedMessages;
	private MessagesAdapter receivedMessagesAdapter;
	private boolean isDataLoaded;
	
	private ListView listViewReceivedMessages;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.receivedMessages = new ArrayList<MessageModel>();
		this.isDataLoaded = false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_messages_received, container, false);
		
		this.listViewReceivedMessages = (ListView) rootView.findViewById(R.id.listView_receivedMessages);
		
		if (!this.isDataLoaded) {
			this.getMessagesFromServerAndSetUpListView();
		} else {
			this.setUpListView();
		}
		
		return rootView;
	}
	
	private void getMessagesFromServerAndSetUpListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ReceivedMessagesFragment.this.loadMessagesData(data);
				ReceivedMessagesFragment.this.setUpListView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ReceivedMessagesFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getMessages()
				.getReceived(WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void loadMessagesData(String data) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		Type listType = new TypeToken<List<MessageModel>>() {}.getType();

		this.receivedMessages = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "All Messages Loaded");
	}
	
	private void setUpListView() {
		this.receivedMessagesAdapter = new MessagesAdapter(this.getActivity(),
				R.layout.list_row_message, this.receivedMessages);

		this.listViewReceivedMessages.setAdapter(this.receivedMessagesAdapter);
	}
}
