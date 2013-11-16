package wiredup.activities;

import java.lang.reflect.Type;
import java.util.List;

import wiredup.adapters.ConnectionRequestsAdapter;
import wiredup.client.R;
import wiredup.models.ConnectionRequestModel;
import wiredup.utils.BundleKey;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConnectionRequestsActivity extends OptionsMenuActivity {
	private List<ConnectionRequestModel> connectionRequests;
	
	private ListView listViewConnectionRequests;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection_requests);
		
		String connectionRequestsAsJsonString =
				this.getIntent().getExtras().getString(BundleKey.CONNECTION_REQUESTS);
		
		this.connectionRequests =
				this.parseConnectionRequestsJsonString(connectionRequestsAsJsonString);
		
		this.listViewConnectionRequests =
				(ListView) this.findViewById(R.id.listView_connectionRequests);
		
		this.setUpListView();
	}
	
	private void setUpListView() {
		ConnectionRequestsAdapter adapter =
				new ConnectionRequestsAdapter(
						this, R.layout.list_row_connection_requests, this.connectionRequests);
		
		this.listViewConnectionRequests.setAdapter(adapter);
	}
	
	private List<ConnectionRequestModel> parseConnectionRequestsJsonString(String connectionRequestsJsonString) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<ConnectionRequestModel>>() {}.getType();

		List<ConnectionRequestModel> connectionRequests = gson.fromJson(connectionRequestsJsonString, listType);
		return connectionRequests;
	}
}
