package wiredup.fragments.profile.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.activities.MessageDetailsActivity;
import wiredup.adapters.MessagesAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.MessageModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class AllMessagesFragment extends Fragment {
	private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	private List<MessageModel> allMessages;
	private MessagesAdapter allMessagesAdapter;
	private boolean isDataLoaded;
	
	private ListView listViewAllMessages;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.allMessages = new ArrayList<MessageModel>();
		this.isDataLoaded = false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_messages_all, container, false);
		
		this.listViewAllMessages = (ListView) rootView.findViewById(R.id.listView_allMessages);
		
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
				AllMessagesFragment.this.loadMessagesData(data);
				AllMessagesFragment.this.setUpListView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						AllMessagesFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getMessages()
				.getAll(WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void loadMessagesData(String data) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
		Type listType = new TypeToken<List<MessageModel>>() {}.getType();

		this.allMessages = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "All Messages Loaded");
	}
	
	private void setUpListView() {
		this.allMessagesAdapter = new MessagesAdapter(this.getActivity(),
				R.layout.list_row_message, this.allMessages);

		this.listViewAllMessages.setAdapter(this.allMessagesAdapter);
		
		this.listViewAllMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View row,
					int rowIndex, long messageId) {
				MessageModel messageModel =
						(MessageModel) AllMessagesFragment.this.allMessagesAdapter.getItem(rowIndex);
				
				AllMessagesFragment.this
						.startMessageDetailsActivity(messageModel);
			}
		});
	}
	
	private void startMessageDetailsActivity(MessageModel messageModel) {
		Intent intent = new Intent(this.getActivity(), MessageDetailsActivity.class);
		intent.putExtra(BundleKey.MESSAGE_MODEL, messageModel);
		
		this.startActivity(intent);
	}
}
