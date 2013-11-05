package wiredup.client;

import wiredup.http.HttpActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends HttpActivity {
	private TextView textViewWelcomeMessage;
	private Button btnShowLogin;
	private Button btnShowRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.textViewWelcomeMessage = (TextView) MainActivity.this
				.findViewById(R.id.tv_welcomeMessage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
