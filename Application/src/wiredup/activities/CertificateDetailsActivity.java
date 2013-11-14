package wiredup.activities;

import wiredup.client.R;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.CertificateDetailsModel;
import wiredup.utils.BundleKey;
import wiredup.utils.ErrorNotifier;
import wiredup.utils.WiredUpApp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class CertificateDetailsActivity extends OptionsMenuActivity {
	private int certificateId;
	private CertificateDetailsModel certificateDetailsModel;

	private TextView textViewCertificateOwner;
	private TextView textViewCertificateName;
	private TextView textViewCertificateUrl;
	private Button btnSeeCertificate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_certificate_details);

		this.certificateId = this.getIntent().getExtras()
				.getInt(BundleKey.CERTIFICATE_ID);

		this.textViewCertificateOwner = (TextView) this
				.findViewById(R.id.textView_certificateName);
		this.textViewCertificateName = (TextView) this
				.findViewById(R.id.textView_certificateOwner);
		this.textViewCertificateUrl = (TextView) this
				.findViewById(R.id.textView_certificateUrl);
		this.btnSeeCertificate = (Button) this
				.findViewById(R.id.btn_seeCertificate);

		this.getCertificateDetailsDataFromServerAndSetUpLayout();
	}

	private void getCertificateDetailsDataFromServerAndSetUpLayout() {
		IOnSuccess onSuccess = new IOnSuccess() {
			@Override
			public void performAction(String data) {
				CertificateDetailsActivity.this
						.loadCertificateDetailsData(data);
				CertificateDetailsActivity.this.setUpLayout();
			}
		};

		IOnError onError = new IOnError() {
			@Override
			public void performAction(String data) {
				ErrorNotifier.displayErrorMessage(
						CertificateDetailsActivity.this, data);
			}
		};

		WiredUpApp
				.getData()
				.getCertificates()
				.getDetails(this.certificateId, WiredUpApp.getSessionKey(),
						onSuccess, onError);
	}

	private void loadCertificateDetailsData(String data) {
		Gson gson = new Gson();
		this.certificateDetailsModel = gson.fromJson(data,
				CertificateDetailsModel.class);
	}

	private void setUpLayout() {
		String certificateName = this.certificateDetailsModel.getName();
		this.textViewCertificateName.setText("Name: " + certificateName);

		String certificateOwner = this.certificateDetailsModel.getOwner();
		this.textViewCertificateOwner.setText("Owner: " + certificateOwner);

		final String certificateUrl = this.certificateDetailsModel.getUrl();
		this.textViewCertificateUrl.setText("Url: " + certificateUrl);

		if (certificateUrl == null || certificateUrl.length() == 0) {
			this.btnSeeCertificate.setEnabled(false);
		} else {
			this.btnSeeCertificate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CertificateDetailsActivity.this.startBrowser(certificateUrl);
				}
			});
		}
	}

	private void startBrowser(String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));

		this.startActivity(intent);
	}
}
