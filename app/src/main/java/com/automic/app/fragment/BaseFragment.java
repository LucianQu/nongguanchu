package com.automic.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.automic.app.activity.RecoderFragmentActivity;
import com.automic.app.application.AppContext;
import com.automic.app.utils.LogUtils;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public abstract class BaseFragment extends Fragment {
    private String tag = "BaseFragment";
    protected RecoderFragmentActivity mActivity = null;
    protected AppContext mApplication = null;
    protected View mLayout = null;
    protected ViewGroup container = null;
    protected int layoutId;

    @Override
    public void onAttach(Activity activity) {
        LogUtils.i(tag, "[BaseFragment#onAttach()] enter");
        super.onAttach(activity);
        LogUtils.i(tag, "[BaseFragment#onAttach()] exit");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(tag, "[BaseFragment#onCreate(savedInstanceState = " + savedInstanceState + ")] enter");
        this.mActivity = (RecoderFragmentActivity) getActivity();
        this.mApplication = (AppContext) mActivity.getApplication();
        initLayout();
        LogUtils.i(tag, "[BaseFragment#onCreate()] exit");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(tag, "[BaseFragment#onCreateView(inflater = " + inflater + ", container = " + container
                + ", savedInstanceState = " + savedInstanceState + ")] enter");
        this.container = container;
        this.mLayout = inflater.inflate(layoutId, container, false);
        if (null == this.mLayout) {
            LogUtils.e(tag, "[BaseFragment#onCreate()#this.layout is null.] error");
            return null;
        } else {
            initStatus();
        }
        LogUtils.i(tag, "[BaseFragment#onCreateView()] exit");
        return this.mLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtils.i(tag, "[BaseFragment#onActivityCreated(savedInstanceState = " + savedInstanceState + ")] enter");
        super.onActivityCreated(savedInstanceState);
        // new Thread(mInitDataRunnable).start();
        LogUtils.i(tag, "[BaseFragment#onActivityCreated()] exit");
    }

    public View findViewById(int id) {
        LogUtils.i(tag, "[BaseFragment#getRootView(id = " + id + ")] enter");
        LogUtils.i(tag, "[BaseFragment#getRootView()] exit");
        return mLayout.findViewById(id);
    }

    public View getRootView() {
        LogUtils.i(tag, "[BaseFragment#getRootView()] enter");
        LogUtils.i(tag, "[BaseFragment#getRootView()] exit");
        return mLayout;
    }

    @Override
    public void onStart() {
        LogUtils.i(tag, "[BaseFragment#onStart()] enter");
        super.onStart();
        LogUtils.i(tag, "[BaseFragment#onStart()] exit");
    }

    @Override
    public void onResume() {
        LogUtils.i(tag, "[BaseFragment#onResume()] enter");
        super.onResume();
        LogUtils.i(tag, "[BaseFragment#onResume()] exit");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.i(tag, "[BaseFragment#setUserVisibleHint()] enter");
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            initData();
        } else {
            //donothing
        }
        LogUtils.i(tag, "[BaseFragment#setUserVisibleHint()] exit");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtils.i(tag, "[BaseFragment#onHiddenChanged(hidden = " + hidden + ")] enter");
        super.onHiddenChanged(hidden);
        LogUtils.i(tag, "[BaseFragment#onHiddenChanged()] exit");
    }

    @Override
    public void onStop() {
        LogUtils.i(tag, "[BaseFragment#onStop()] enter");
        super.onStop();
        LogUtils.i(tag, "[BaseFragment#onStop()] exit");
    }

    @Override
    public void onDestroyView() {
        LogUtils.i(tag, "[BaseFragment#onDestroyView()] enter");
        super.onDestroyView();
        LogUtils.i(tag, "[BaseFragment#onDestroyView()] exit");
    }

    @Override
    public void onDetach() {
        LogUtils.i(tag, "[BaseFragment#onDetach()] enter");
        super.onDetach();
        LogUtils.i(tag, "[BaseFragment#onDetach()] exit");
    }

    @Override
    public void onDestroy() {
        LogUtils.i(tag, "[BaseFragment#onDestroy()] enter");
        super.onDestroy();
        LogUtils.i(tag, "[BaseFragment#onDestroy()] exit");
    }

    public abstract void initLayout();

    public abstract void initStatus();

    public void initData() {

    }
}

