package wiredup.activities;

import wiredup.client.R;
import wiredup.utils.BundleKey;
import android.os.Bundle;

public class UserPostDetailsActivity extends OptionsMenuActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_user_post_details);
		
		int postId = this.getIntent().getExtras().getInt(BundleKey.POST_ID);
	}
}
