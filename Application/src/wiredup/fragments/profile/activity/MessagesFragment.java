package wiredup.fragments.profile.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.TabSpec;

public class MessagesFragment extends Fragment {
	private FragmentTabHost tabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.tabHost = new FragmentTabHost(this.getActivity());
		this.tabHost.setup(this.getActivity(), this.getChildFragmentManager(), container.getId());
		
		TabSpec tabSpecForAllMessages = this.tabHost.newTabSpec("all").setIndicator("ALL");
		this.tabHost.addTab(tabSpecForAllMessages, AllMessagesFragment.class, null);
		
		TabSpec tabSpecForSentMessages = this.tabHost.newTabSpec("sent").setIndicator("SENT");
		this.tabHost.addTab(tabSpecForSentMessages, SentMessagesFragment.class, null);
		
		TabSpec tabSpecForReceivedMessages = this.tabHost.newTabSpec("received").setIndicator("RECEIVED");
		this.tabHost.addTab(tabSpecForReceivedMessages, ReceivedMessagesFragment.class, null);

		return this.tabHost;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.tabHost = null;
	}
}
