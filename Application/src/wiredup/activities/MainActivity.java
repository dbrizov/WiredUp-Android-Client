package wiredup.activities;

import wiredup.client.R;
import wiredup.fragments.WelcomeScreenFragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.showWelcomeScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void showWelcomeScreen() {
		WelcomeScreenFragment welcomeScreenFragment = new WelcomeScreenFragment();
		FragmentManager manager = this.getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.layout_main, welcomeScreenFragment, "WelcomeScreen");
		transaction.commit();
	}
}
