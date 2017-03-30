package com.automic.app.fragment;

import android.os.Handler;
import android.os.Message;

import com.automic.app.R;
import com.automic.app.adapter.RechargeRecoderAdapter;
import com.automic.app.bean.RechargeRecoder;
import com.automic.app.view.pullpushview.PullToRefreshLayout;
import com.automic.app.view.pullpushview.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public class RechargeFragment extends BaseFragment {
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView listView;


    @Override
    public void initLayout() {
        layoutId= R.layout.fragment_recharge;
    }

    @Override
    public void initStatus() {
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pl_refresh_recharge);
        listView = (PullableListView)pullToRefreshLayout.findViewById(R.id.lv_info_recharge);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        List<RechargeRecoder>mlist=new ArrayList<RechargeRecoder>();
        for (int i=0;i<8;i++){
            mlist.add(new RechargeRecoder("1234532","屈庄站","2017-04-05","56","67","56","54"));
        }
        RechargeRecoderAdapter rechargeRecoderAdapter=new RechargeRecoderAdapter(mActivity,mlist);
        listView.setAdapter(rechargeRecoderAdapter);
        rechargeRecoderAdapter.notifyDataSetChanged();
    }
    /*
  * 下拉和上拉刷新的监听器
  */
    private class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
//            if (type.equals("noInfo")) {
//                noInfoList.clear();
//            } else
//                abWaterInfoList.clear();
//            currentPage = 1;
//            getWarnDataFormEnType(type);
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！

                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }

            }.sendEmptyMessageDelayed(0, 500);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
//            currentPage++;
//            getWarnDataFormEnType(type);
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500);
        }
    }
}
