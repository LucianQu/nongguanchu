package com.automic.app.fragment;

import android.os.Handler;
import android.os.Message;

import com.automic.app.R;
import com.automic.app.adapter.OnOffPumpAdapter;
import com.automic.app.bean.OnOffPumpRecoder;
import com.automic.app.view.pullpushview.PullToRefreshLayout;
import com.automic.app.view.pullpushview.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public class OnOffPumpFragment extends BaseFragment {

    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView listView ;

    @Override
    public void initLayout() {
        layoutId= R.layout.fragment_onoffpump;//获取布局ID
    }

    @Override
    public void initStatus() {
        //获取上下拉刷新布局
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pl_refresh_onoffpump);
        //获取刷新list
        listView = (PullableListView)pullToRefreshLayout.findViewById(R.id.lv_info_onoffpump) ;
        //布局,设置刷新监听
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        //为布局添加数据
        List<OnOffPumpRecoder> mlist = new ArrayList<OnOffPumpRecoder>() ;
        for (int i = 0; i < 50; i++) {
            mlist.add(new OnOffPumpRecoder("1111", "11111", "2017-1-1 13:25","2017-2-2 13:26","50","60","70"));
        }

        OnOffPumpAdapter onOffPumpAdapter = new OnOffPumpAdapter(mActivity, mlist) ;
        listView.setAdapter(onOffPumpAdapter);
        onOffPumpAdapter.notifyDataSetChanged();
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
