package wiredup.fragments.user.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.activities.CertificateDetailsActivity;
import wiredup.adapters.UserActivityCertificatesAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CertificateModel;
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

public class CertificatesFragment extends Fragment {
	private int userId;
	private List<CertificateModel> certificates;
	private UserActivityCertificatesAdapter certificatesAdapter;
	private boolean isDataLoaded;

	private ListView listViewCertificates;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(BundleKey.USER_ID);

		this.certificates = new ArrayList<CertificateModel>();
		this.isDataLoaded = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(
				R.layout.fragment_certificates_user_activity, container, false);

		// Find the list view
		this.listViewCertificates = (ListView) rootLayoutView
				.findViewById(R.id.listView_certificates);

		// Set up the list view
		if (!this.isDataLoaded) {
			this.getDataFromServerAndSetUpCertificatesListView();
		} else {
			this.setUpCertificatesListView();
		}

		return rootLayoutView;
	}

	private void getDataFromServerAndSetUpCertificatesListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				CertificatesFragment.this.loadCertificatesData(data);
				CertificatesFragment.this.setUpCertificatesListView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						CertificatesFragment.this.getActivity(), data);
			}
		};

		WiredUpApp
				.getData()
				.getCertificates()
				.getAllForUser(this.userId, WiredUpApp.getSessionKey(),
						onSuccess, onError);
	}

	private void loadCertificatesData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<CertificateModel>>() {
		}.getType();

		this.certificates = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "Certificates Loaded");
	}

	private void setUpCertificatesListView() {
		this.certificatesAdapter = new UserActivityCertificatesAdapter(
				this.getActivity(),
				R.layout.list_row_certificate_user_activity, this.certificates);

		this.listViewCertificates.setAdapter(this.certificatesAdapter);

		this.listViewCertificates
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long certificateId) {
						CertificatesFragment.this
								.startCertificateDetailsActivity((int) certificateId);
					}
				});
	}

	private void startCertificateDetailsActivity(int certificateId) {
		Intent intent = new Intent(this.getActivity(),
				CertificateDetailsActivity.class);
		intent.putExtra(BundleKey.CERTIFICATE_ID, certificateId);

		this.startActivity(intent);
	}
}
