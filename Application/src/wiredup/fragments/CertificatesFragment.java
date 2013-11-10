package wiredup.fragments;

import java.lang.reflect.Type;
import java.util.List;

import wiredup.adapters.CertificatesAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CertificateModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CertificatesFragment extends Fragment {
	private int userId;
	private boolean isCertificatesDataLoaded;
	private List<CertificateModel> certificates;

	private ListView listViewCertificates;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.isCertificatesDataLoaded = false;
		this.certificates = null;

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(WiredUpApp.USER_ID_BUNDLE_KEY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(R.layout.fragment_certificates,
				container, false);

		this.listViewCertificates = (ListView) rootLayoutView
				.findViewById(R.id.listView_certificates);

		if (!this.isCertificatesDataLoaded) {
			this.getCertificatesFromServerAndSetUpListView();
		} else {
			this.setUpListView(this.getActivity(), this.certificates);
		}

		return rootLayoutView;
	}

	private void loadCertificatesData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<CertificateModel>>() {
		}.getType();

		this.certificates = gson.fromJson(data, listType);
		this.isCertificatesDataLoaded = true;

		Log.d("debug", "Certificates Loaded");
	}

	private void getCertificatesFromServerAndSetUpListView() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				CertificatesFragment.this.loadCertificatesData(data);
				CertificatesFragment.this.setUpListView(
						CertificatesFragment.this.getActivity(),
						CertificatesFragment.this.certificates);
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

	private void setUpListView(Context context,
			List<CertificateModel> certificates) {
		ListAdapter certificatesAdapter = new CertificatesAdapter(context,
				R.layout.list_row_certificate, certificates);

		this.listViewCertificates.setAdapter(certificatesAdapter);
	}
}
