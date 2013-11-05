package wiredup.client;

import wiredup.http.HttpActivity;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import wiredup.models.UserRegisterModel;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends HttpActivity {
	private TextView textViewWelcomeMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.textViewWelcomeMessage = (TextView) MainActivity.this
				.findViewById(R.id.tv_welcomeMessage);

		Button loginButton = (Button) this
				.findViewById(R.id.btn_showLoginDialog);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserRegisterModel model = new UserRegisterModel();
				model.setFirstName("Kriso");
				model.setLastName("Rizov");
				model.setEmail("k.b.rizov@gmail.com");
				model.setAuthCode("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				model.setConfirmAuthCode("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

				IOnSuccess onSuccess = new IOnSuccess() {

					@Override
					public void performAction(String data) {
						MainActivity.this.textViewWelcomeMessage.setText(data);
					}
				};

				IOnError onError = new IOnError() {

					@Override
					public void performAction(String data) {

						MainActivity.this.textViewWelcomeMessage.setText(data);
					}
				};

				WiredUpApp.getData().getUsers()
						.register(model, onSuccess, onError);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
