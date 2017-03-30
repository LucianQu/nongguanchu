package com.automic.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.automic.app.R;
import com.automic.app.adapter.FrgtPagerAdapter;
import com.automic.app.fragment.BadRecoderFragment;
import com.automic.app.fragment.BaseFragment;
import com.automic.app.fragment.OpenWellFragment;
import com.automic.app.fragment.OpenboxFragment;
import com.automic.app.fragment.RechargeFragment;
import com.automic.app.fragment.YearMonthCountFragment;

import java.util.ArrayList;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public class RecoderFragmentActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private FrgtPagerAdapter pagerAdapter;
    private ArrayList<RadioButton> tabList;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentactivity_recoder);
        setupView();
        initTabBar();
    }

    private void initTabBar() {
        tabList = new ArrayList<RadioButton>();
        tabList.add((RadioButton)findViewById(R.id.radio_open_well));
        tabList.add((RadioButton)findViewById(R.id.radio_openbox_recoder));
        tabList.add((RadioButton)findViewById(R.id.radio_recharge_recoder));
        tabList.add((RadioButton)findViewById(R.id.radio_bad_recoder));
        tabList.add((RadioButton)findViewById(R.id.radio_year_month));
for (RadioButton rb: tabList){
    rb.setOnClickListener(this);
}
    }

    private void setupView() {
        viewPager = (ViewPager)findViewById(R.id.recoder_tab_pager);
        ArrayList<BaseFragment> fragments = getFragments();
        pagerAdapter = new FrgtPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(5);
    }

    private ArrayList<BaseFragment> getFragments() {
ArrayList<BaseFragment>list=new ArrayList<BaseFragment>();
        OpenboxFragment openboxFragment=new OpenboxFragment();
        OpenWellFragment openwellFragment=new OpenWellFragment();
        BadRecoderFragment badFragment=new BadRecoderFragment();
        RechargeFragment rechargeFragment=new RechargeFragment();
        YearMonthCountFragment ymCountFragment=new YearMonthCountFragment();
        list.add(openwellFragment);
        list.add(openboxFragment);
        list.add(rechargeFragment);
        list.add(badFragment);
        list.add(ymCountFragment);
              return  list;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
viewPager.setCurrentItem(position);
        tabList.get(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.radio_bad_recoder:
        viewPager.setCurrentItem(3);
        tabList.get(3).setChecked(true);
        break;
      case R.id.radio_recharge_recoder:
          viewPager.setCurrentItem(2);
          tabList.get(2).setChecked(true);
        break;
      case R.id.radio_open_well:
          viewPager.setCurrentItem(0);
          tabList.get(0).setChecked(true);
        break;
      case R.id.radio_openbox_recoder:
          viewPager.setCurrentItem(1);
          tabList.get(1).setChecked(true);
        break;
      case R.id.radio_year_month:
          viewPager.setCurrentItem(4);
          tabList.get(4).setChecked(true);
        break;

}
    }
}