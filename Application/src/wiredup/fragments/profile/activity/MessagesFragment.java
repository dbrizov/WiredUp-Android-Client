package wiredup.fragments.profile.activity;

import wiredup.utils.BundleKey;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessagesFragment extends Fragment {
	private int userId;
	private String allMessagesAsJsonString;
	private String sentMessagesAsJsonString;
	private String receivedMessagesAsJsonString;
	
	private FragmentTabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getArguments();
		this.userId = bundle.getInt(BundleKey.USER_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.tabHost = new FragmentTabHost(this.getActivity());
		this.tabHost.setup(this.getActivity(), this.getChildFragmentManager(),
				container.getId());

		this.tabHost.addTab(this.tabHost.newTabSpec("all").setIndicator("ALL"),
				AllMessagesFragment.class, null);
		
		this.tabHost.addTab(this.tabHost.newTabSpec("sent").setIndicator("SENT"),
				SentMessagesFragment.class, null);
		
		this.tabHost.addTab(this.tabHost.newTabSpec("received").setIndicator("RECEIVED"),
				ReceivedMessagesFragment.class, null);

		return this.tabHost;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.tabHost = null;
	}
	
	private void getMessagesFromServer() {
		
	}
}
