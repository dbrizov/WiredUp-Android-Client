package wiredup.fragments;

import java.lang.reflect.Type;
import java.util.List;

import wiredup.adapters.CertificatesAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CertificateModel;
import wiredup.models.ServerResponseModel;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CertificatesFragment extends Fragment {
	private boolean isDataLoaded;
	private List<CertificateModel> certificates;

	private ListView listViewCertificates;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.isDataLoaded = false;
		this.certificates = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(R.layout.fragment_certificates,
				container, false);

		this.listViewCertificates = (ListView) rootLayoutView
				.findViewById(R.id.listView_certificates);

		int userId = WiredUpApp.getUserId();
		String sessionKey = WiredUpApp.getSessionKey();

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				CertificatesFragment.this.loadCertificatesData(data);
				CertificatesFragment.this.initializeListView();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				CertificatesFragment.this.displayErrorMessage(data);
			}
		};

		if (!this.isDataLoaded) {
			WiredUpApp.getData().getCertificates()
					.getAll(userId, sessionKey, onSuccess, onError);
		} else {
			this.initializeListView();
		}

		return rootLayoutView;
	}

	private void loadCertificatesData(String data) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<CertificateModel>>() {
		}.getType();

		this.certificates = gson.fromJson(data, listType);
		this.isDataLoaded = true;

		Log.d("debug", "Certificates Loaded");
	}

	private void initializeListView() {
		ListAdapter certificatesAdapter = new CertificatesAdapter(
				this.getActivity(), R.layout.list_row_certificate,
				this.certificates);

		this.listViewCertificates.setAdapter(certificatesAdapter);
	}

	private void displayErrorMessage(String data) {
		Gson gson = new Gson();
		ServerResponseModel response = gson.fromJson(data,
				ServerResponseModel.class);

		Toast.makeText(this.getActivity(), response.getMessage(),
				Toast.LENGTH_LONG).show();
	}
}
