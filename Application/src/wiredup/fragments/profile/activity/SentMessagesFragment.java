package wiredup.fragments.profile.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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

public class SentMessagesFragment extends Fragment {
private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	private List<MessageModel> sentMessages;
	private MessagesAdapter sentMessagesAdapter;
	private boolean isDataLoaded;
	
	private ListView listViewSentMessages;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.sentMessages = new ArrayList<MessageModel>();
		this.isDataLoaded = false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_messages_sent, container, false);
		
		this.listViewSentMessages = (ListView) rootView.findViewById(R.id.listView_sentMessages);
		
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
				SentMessagesFragment.this.loadMessagesData(data);
				SentMessagesFragment.this.setUpListView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						SentMessagesFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getMessages()
				.getSent(WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void loadMessagesData(String data) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		Type listType = new TypeToken<List<MessageModel>>() {}.getType();

		this.sentMessages = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "Sent Messages Loaded");
	}
	
	private void setUpListView() {
		this.sentMessagesAdapter = new MessagesAdapter(this.getActivity(),
				R.layout.list_row_message, this.sentMessages);

		this.listViewSentMessages.setAdapter(this.sentMessagesAdapter);
	}
}
