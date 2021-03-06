package wiredup.activities;

import wiredup.client.R;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class OptionsMenuActivity extends FragmentActivity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager =
				(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView =
				(SearchView) menu.findItem(R.id.menu_item_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(this
				.getComponentName()));

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_home:
			this.startProfileActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void startProfileActivity() {
		Intent intent = new Intent(this, ProfileActivity.class);
		this.startActivity(intent);
	}
}
