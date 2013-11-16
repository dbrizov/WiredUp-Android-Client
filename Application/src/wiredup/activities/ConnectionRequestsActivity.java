package wiredup.activities;

import java.util.List;

import wiredup.client.R;
import wiredup.models.ConnectionRequestModel;
import android.os.Bundle;
import android.widget.ListView;

public class ConnectionRequestsActivity extends OptionsMenuActivity {
	private String connectionRequestsAsJsonString;
	private List<ConnectionRequestModel> connectionRequests;
	
	private ListView listViewConnectionRequests;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection_requests);
		
		this.listViewConnectionRequests = (ListView) this.findViewById(R.id.listView_connectionRequests);
	}
}
