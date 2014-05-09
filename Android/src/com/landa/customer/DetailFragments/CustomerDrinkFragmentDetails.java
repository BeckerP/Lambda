package com.landa.customer.DetailFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landa.R;
import com.landa.customer.DrinkTabs.Tab1DrinkFragment;
import com.landa.customer.DrinkTabs.Tab2DrinkFragment;
import com.landa.customer.DrinkTabs.Tab3DrinkFragment;

public class CustomerDrinkFragmentDetails extends Fragment {
	private FragmentTabHost mTabHost;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.id.fragment_container_detail);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab1").setIndicator(
						getString(R.string.tab1_fragment_string)),
				Tab1DrinkFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab2").setIndicator(
						getString(R.string.tab2_fragment_string)),
				Tab2DrinkFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab3").setIndicator(
						getString(R.string.tab3_fragment_string)),
				Tab3DrinkFragment.class, null);
		return mTabHost;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTabHost = null;
	}
}
