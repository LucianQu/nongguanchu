package com.automic.app.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.automic.app.fragment.BaseFragment;

public class FrgtPagerAdapter extends FragmentPagerAdapter{

	private ArrayList<BaseFragment> fragments;

	public FrgtPagerAdapter(FragmentManager frgtManager,
			ArrayList<BaseFragment> fragments) {
		super(frgtManager);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
}
