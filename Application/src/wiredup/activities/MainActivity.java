package wiredup.activities;

import wiredup.client.R;
import wiredup.fragments.main.LoginDialogFragment;
import wiredup.fragments.main.RegisterDialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private Button btnShowLoginDiaog;
	private Button btnShowRegisterDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.btnShowLoginDiaog = (Button) this.findViewById(R.id.btn_showLoginDialog);
		this.btnShowLoginDiaog.setOnClickListener(this);
		
		this.btnShowRegisterDialog = (Button) this.findViewById(R.id.btn_showRegisterDialog);
		this.btnShowRegisterDialog.setOnClickListener(this);
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
