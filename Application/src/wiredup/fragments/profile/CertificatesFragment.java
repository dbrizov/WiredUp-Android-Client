package wiredup.fragments.profile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wiredup.adapters.CertificatesAdapter;
import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CertificateAddModel;
import wiredup.models.CertificateModel;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.Keys;
import wiredup.utils.WiredUpApp;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CertificatesFragment extends Fragment {
	private int userId;
	private boolean isCertificatesDataLoaded;
	private List<CertificateModel> certificates;
	private CertificatesAdapter certificatesAdapter;

	private ListView listViewCertificates;
	private EditText editTextCertificateName;
	private EditText editTextCertificateUrl;
	private Button btnAddCertificate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.certificates = new ArrayList<CertificateModel>();

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(Keys.BUNDLE_KEY_USER_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootLayoutView = inflater.inflate(R.layout.fragment_certificates,
				container, false);

		this.listViewCertificates = (ListView) rootLayoutView
				.findViewById(R.id.listView_certificates);
		this.editTextCertificateName = (EditText) rootLayoutView
				.findViewById(R.id.editText_certificateName);
		this.editTextCertificateUrl = (EditText) rootLayoutView
				.findViewById(R.id.editText_certificateUrl);
		this.btnAddCertificate = (Button) rootLayoutView
				.findViewById(R.id.btn_addCertificate);

		this.btnAddCertificate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String certificateName = CertificatesFragment.this.editTextCertificateName
						.getText().toString().trim();
				String certificateUrl = CertificatesFragment.this.editTextCertificateUrl
						.getText().toString().trim();

				// Validate certificate name
				if (certificateName == null || certificateName.length() == 0) {
					Toast.makeText(CertificatesFragment.this.getActivity(),
							"The certificate name is required",
							Toast.LENGTH_LONG).show();
				} else {
					CertificatesFragment.this.addNewCertificate(
							certificateName, certificateUrl);
				}
			}
		});

		if (!this.isCertificatesDataLoaded) {
			this.getCertificatesFromServerAndSetUpListView();
		} else {
			this.setUpListView(this.getActivity(), this.certificates);
		}

		return rootLayoutView;
	}

	private void addNewCertificate(String name, String url) {
		CertificateAddModel model = new CertificateAddModel(name, url);

		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				Gson gson = new Gson();
				CertificateModel model = gson.fromJson(data,
						CertificateModel.class);

				CertificatesFragment.this.certificates.add(0, model);
				CertificatesFragment.this.certificatesAdapter
						.notifyDataSetChanged();

				CertificatesFragment.this.editTextCertificateName.setText("");
				CertificatesFragment.this.editTextCertificateUrl.setText("");
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						CertificatesFragment.this.getActivity(), data);
			}
		};

		WiredUpApp.getData().getCertificates()
				.add(model, WiredUpApp.getSessionKey(), onSuccess, onError);
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
		this.certificatesAdapter = new CertificatesAdapter(context,
				R.layout.list_row_certificate, certificates);

		this.listViewCertificates.setAdapter(this.certificatesAdapter);
	}
}
