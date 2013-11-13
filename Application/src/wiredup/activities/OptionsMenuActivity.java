package wiredup.activities;

import wiredup.client.R;
import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
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

		searchView.setSubmitButtonEnabled(true);

		return true;
	}
}
