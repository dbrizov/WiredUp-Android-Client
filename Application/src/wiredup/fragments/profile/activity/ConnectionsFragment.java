package wiredup.fragments.profile.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.activities.UserActivity;
import wiredup.adapters.ConnetionsAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.ConnectionModel;
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
import com.google.gson.reflect.TypeToken;

public class ConnectionsFragment extends Fragment {
	private List<ConnectionModel> connections;
	private boolean isDataLoaded;
	
	private ListView listViewConnections;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.connections = new ArrayList<ConnectionModel>();
		this.isDataLoaded = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_connections, container, false);
		
		// Find the list view
		this.listViewConnections = (ListView) rootView.findViewById(R.id.listView_connections);
		
		// Set up the list view
		if (!this.isDataLoaded) {
			this.getDataFromServerAndSetUpListView();
		} else {
			this.setUpListView();
		}
		
		return rootView;
	}
	
	private void setUpListView() {
		final ConnetionsAdapter adapter = new ConnetionsAdapter(this.getActivity(),
				R.layout.list_row_connection, this.connections);

		this.listViewConnections.setAdapter(adapter);
		
		this.listViewConnections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View row, int rowIndex, long connectionId) {
				int userId = adapter.getUserId(rowIndex);
				ConnectionsFragment.this.startUserActivity(userId);
			}
		});
	}
	
	private void loadConnectionsData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<ConnectionModel>>() {}.getType();

		this.connections = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "Connections Loaded");
	}
	
	private void getDataFromServerAndSetUpListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				ConnectionsFragment.this.loadConnectionsData(data);
				ConnectionsFragment.this.setUpListView();
			}
		};
		
		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						ConnectionsFragment.this.getActivity(), data);
			}
		};
		
		WiredUpApp.getData().getConnections().getAll(WiredUpApp.getSessionKey(), onSuccess, onError);
	}
	
	private void startUserActivity(int userId) {
		Intent intent = new Intent(this.getActivity(), UserActivity.class);
		intent.putExtra(BundleKey.USER_ID, userId);
		
		this.startActivity(intent);
	}
}
