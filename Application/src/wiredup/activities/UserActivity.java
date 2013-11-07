package wiredup.activities;

import wiredup.client.R;
import wiredup.utils.WiredUpApp;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class UserActivity extends Activity {
	private TextView textViewUserInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		this.textViewUserInfo = (TextView) this.findViewById(R.id.textView_userInfo);
		
		int userId = WiredUpApp.getUserId();
		String userDisplayName = WiredUpApp.getUserDisplayName();
		String sessionKey = WiredUpApp.getSessionKey();
		
		this.textViewUserInfo.setText(userId + " " + userDisplayName + " " + sessionKey);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

}
