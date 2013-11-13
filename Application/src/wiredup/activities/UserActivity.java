package wiredup.activities;

import wiredup.client.R;
import wiredup.utils.BundleKeys;
import android.os.Bundle;
import android.util.Log;

public class UserActivity extends OptionsMenuActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		int userId = this.getIntent().getExtras().getInt(BundleKeys.USER_ID);
		Log.d("debug", "userId " + userId);
	}
}
