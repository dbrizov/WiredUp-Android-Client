package wiredup.adapters;

import java.util.List;

import wiredup.models.CountryModel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountriesAdapter extends BaseAdapter {
	private Context context;
	private int rowLayoutId;
	private List<CountryModel> countries;

	public CountriesAdapter(Context context, int rowLayoutId, List<CountryModel> countries) {
		this.context = context;
		this.rowLayoutId = rowLayoutId;
		this.countries = countries;
	}

	@Override
	public int getCount() {
		return this.countries.size();
	}

	@Override
	public Object getItem(int position) {
		return this.countries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.countries.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView listRow = (TextView) convertView;
		if (listRow == null) {
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			listRow = (TextView) inflater.inflate(this.rowLayoutId, parent, false);
		}

		listRow.setText(this.countries.get(position).getName());

		return listRow;
	}

}
