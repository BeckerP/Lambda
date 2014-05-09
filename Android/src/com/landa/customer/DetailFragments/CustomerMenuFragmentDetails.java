package com.landa.customer.DetailFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landa.R;
import com.landa.customer.MenuTabs.Tab1MenuFragment;
import com.landa.customer.MenuTabs.Tab2MenuFragment;
import com.landa.customer.MenuTabs.Tab3MenuFragment;
import com.landa.customer.MenuTabs.Tab4MenuFragment;
import com.landa.customer.MenuTabs.Tab5MenuFragment;
import com.landa.customer.MenuTabs.Tab6MenuFragment;

//TODO This POS is going to need it's own array adapter and custom listview. fuuuuuuuuuuu
//Also a tab interface
public class CustomerMenuFragmentDetails extends Fragment {
	private FragmentTabHost mTabHost;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.id.fragment_container_detail);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab1").setIndicator(
						getString(R.string.tab1_menu_fragment_string)),
				Tab1MenuFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab2").setIndicator(
						getString(R.string.tab2_menu_fragment_string)),
				Tab2MenuFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab3").setIndicator(
						getString(R.string.tab3_menu_fragment_string)),
				Tab3MenuFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab4").setIndicator(
						getString(R.string.tab4_menu_fragment_string)),
				Tab4MenuFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab5").setIndicator(
						getString(R.string.tab5_menu_fragment_string)),
				Tab5MenuFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab6").setIndicator(
						getString(R.string.tab6_menu_fragment_string)),
				Tab6MenuFragment.class, null);
		return mTabHost;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTabHost = null;
	}
}
