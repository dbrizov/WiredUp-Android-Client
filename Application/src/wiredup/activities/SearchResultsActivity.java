package wiredup.activities;

import java.lang.reflect.Type;
import java.util.List;

import wiredup.adapters.UsersAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserModel;
import wiredup.models.UserSearchModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.BundleKey;
import wiredup.utils.WiredUpApp;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SearchResultsActivity extends OptionsMenuActivity {
	private List<UserModel> users;
	private UsersAdapter usersAdapter;

	private ListView listViewUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_search_results);

		this.listViewUsers = (ListView) this.findViewById(R.id.listView_users);

		this.handleIntent(this.getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		this.setIntent(intent);
		this.handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			this.searchForUsersAndSetUpListView(query);
		}
	}

	private void searchForUsersAndSetUpListView(String query) {
		UserSearchModel userSearchModel = new UserSearchModel(query);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				SearchResultsActivity.this.loadUsersData(data);
				SearchResultsActivity.this.setUpListView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(SearchResultsActivity.this, data);
			}
		};

		WiredUpApp
				.getData()
				.getUsers()
				.search(userSearchModel, WiredUpApp.getSessionKey(), onSuccess, onError);
	}

	private void loadUsersData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<UserModel>>() {}.getType();

		this.users = gson.fromJson(data, listType);
	}

	private void setUpListView() {
		this.usersAdapter = new UsersAdapter(this, R.layout.list_row_user, this.users);
		this.listViewUsers.setAdapter(this.usersAdapter);
		
		this.listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View row, int position, long userId) {
				SearchResultsActivity.this.startUserActivity((int) userId);
			}
		});
	}
	
	private void startUserActivity(int userId) {
		Intent intent = new Intent(this, UserActivity.class);
		intent.putExtra(BundleKey.USER_ID, userId);
		
		this.startActivity(intent);
	}
}
