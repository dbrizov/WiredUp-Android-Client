package wiredup.activities;

import wiredup.client.R;
import wiredup.fragments.LoginDialogFragment;
import wiredup.fragments.RegisterDialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private Button showLoginDiaog;
	private Button showRegisterDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.showLoginDiaog = (Button) this.findViewById(R.id.btn_showLoginDialog);
		this.showLoginDiaog.setOnClickListener(this);
		
		this.showRegisterDialog = (Button) this.findViewById(R.id.btn_showRegisterDialog);
		this.showRegisterDialog.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_showRegisterDialog) {
			this.showRegisterDialog();
		} else if (v.getId() == R.id.btn_showLoginDialog) {
			this.showLoginDialog();
		}
	}

	private void showRegisterDialog() {
		RegisterDialogFragment registerDialog = new RegisterDialogFragment();
		FragmentManager manager = this.getSupportFragmentManager();

		registerDialog.show(manager, this.getString(R.string.fragment_register));
	}

	private void showLoginDialog() {
		LoginDialogFragment loginDialog = new LoginDialogFragment();
		FragmentManager manager = this.getSupportFragmentManager();

		loginDialog.show(manager, this.getString(R.string.fragment_login));
	}
}
