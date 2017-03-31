package com.automic.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.automic.app.R;
import com.automic.app.activity.LoadPictrueActivity;
import com.automic.app.activity.WellInfoActivity;
import com.automic.app.adapter.OpenBoxAdapter;
import com.automic.app.bean.OpenBoxRecoder;
import com.automic.app.utils.ToastUtils;
import com.automic.app.view.pullpushview.PullToRefreshLayout;
import com.automic.app.view.pullpushview.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public class OpenboxFragment extends BaseFragment {
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView pullableListView;
    private RelativeLayout m_title;
    private List<OpenBoxRecoder> mList;

    @Override
    public void initLayout() {
        layoutId = R.layout.fragment_openbox;
    }

    @Override
    public void initStatus() {
        m_title = (RelativeLayout) findViewById(R.id.ob_title);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pl_refresh_openbox);
        pullableListView = (PullableListView) pullToRefreshLayout.findViewById(R.id.lv_info_openbox);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullableListView.setOnItemClickListener(new itemListener());

        mList = new ArrayList<OpenBoxRecoder>();

            mList.add(new OpenBoxRecoder("111", "222", "2016年11月30日 17:00", "2016年11月30日 17:22",
                    "http://172.16.60.56:8081/monitor/irg/showImgAction.action?suffix=png&imgId=1611301726400086"));

            mList.add(new OpenBoxRecoder("222", "333", "2016年12月07日 06:50", "2016年12月07日 06:59",
                    "http://172.16.60.56:8081/monitor/irg/showImgAction.action?suffix=png&imgId=1612070704211468"));

        mList.add(new OpenBoxRecoder("222", "333", "2016年12月06日 12:22", "2016年12月07日 10:59",
                "http://172.16.60.56:8081/monitor/irg/showImgAction.action?suffix=png&imgId=1612071104111516"));


        OpenBoxAdapter openBoxAdapter = new OpenBoxAdapter(mActivity, mList);
        pullableListView.setAdapter(openBoxAdapter);
        openBoxAdapter.notifyDataSetChanged();

    }

    /**
     * 上拉和下拉刷新监听器
     */
    private class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //千万别忘了告诉控件刷新完毕了哦!
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500);
        }
    }

    private class itemListener implements PullableListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoadPictrueActivity.class);
            intent.putExtra("LoadPictrueActivity", mList.get(position).getImgPath());
            startActivity(intent);
        }
    }
}


