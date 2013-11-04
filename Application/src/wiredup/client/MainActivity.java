package wiredup.client;

import wiredup.http.HttpRequester;
import wiredup.http.HttpRequester.GetJsonTask;
import wiredup.http.IOnError;
import wiredup.http.IOnSuccess;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private final HttpRequester httpRequester = new HttpRequester();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button loginButton = (Button) this.findViewById(R.id.btn_showLoginDialog);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GetJsonTask getVals = httpRequester.new GetJsonTask("http://wiredup.apphb.com/api/test");
				getVals.setOnSuccess(new IOnSuccess() {
					
					@Override
					public void performAction(String data) {
						TextView textView = (TextView) MainActivity.this.findViewById(R.id.tv_welcomeMessage);
						textView.setText(data);
					}
				});
				
				getVals.setOnEror(new IOnError() {
					
					@Override
					public void performAction(String data) {
						TextView textView = (TextView) MainActivity.this.findViewById(R.id.tv_welcomeMessage);
						textView.setText(data);
					}
				});
				
				getVals.execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
