package com.automic.app.fragment;

import android.os.Handler;
import android.os.Message;
import android.widget.AbsListView;

import com.automic.app.R;
import com.automic.app.adapter.OnOffPumpAdapter;
import com.automic.app.bean.OnOffPumpRecoder;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.ToastUtils;
import com.automic.app.view.pullpushview.PullToRefreshLayout;
import com.automic.app.view.pullpushview.PullableListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.automic.app.bean.Constant.BASEIP;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public class OnOffPumpFragment extends BaseFragment {

    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView listView ;
    private List<OnOffPumpRecoder> m_onOffBeanList= new ArrayList<OnOffPumpRecoder>() ;
    private List<OnOffPumpRecoder> listCache = new ArrayList<OnOffPumpRecoder>() ;
    private boolean isFresh = false ;
    private int currentPage = 1 ;

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
        //记住上一次滚动时的位置信息
        listView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
                //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
                //visibleItemCount：当前能看见的列表项个数（小半个也算）
                //totalItemCount：列表项共数
                curItem = firstVisibleItem;
            }
            @Override
            public void onScrollStateChanged(AbsListView view , int scrollState){
                //正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
                //回调顺序如下
                //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
                //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
                //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
            }
        });
       /* //为布局添加数据
        List<OnOffPumpRecoder> mlist = new ArrayList<OnOffPumpRecoder>() ;
        for (int i = 0; i < 50; i++) {
            mlist.add(new OnOffPumpRecoder("1111", "11111", "2017-1-1 13:25","2017-2-2 13:26","50","60","70"));
        }*/

    }

    @Override
    public void onResume() {
        super.onResume();
        getOnoffPump(mActivity.getCurrentWellNo()) ;
    }

    /**
     *获取开关泵记录,根据页面获取,每页20条数据
     * @param wellNo 用户ID
     */
    private void getOnoffPump(String wellNo) {
        if (null == wellNo ||  currentPage < 0) {
            ToastUtils.show(getActivity(), "参数为空!");
            return;
        }
        String url = BASEIP + "/well/queryWellOnOffRecoder" ;
        RequestParams requestParams = new RequestParams(url) ;
        if (null != wellNo)
            requestParams.addBodyParameter("wellNo", wellNo);

        if (currentPage >= 0 )
            requestParams.addParameter("currentPage", currentPage);//添加请求参数(根据请求谓词, 将参数加入QueryString或Body.
        requestParams.addParameter("count", 10);

        x.http().post(requestParams, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonResult = new JSONObject(result) ;
                    int code = jsonResult.getInt("code") ;
                    if (code == 1) {
                        if (isFresh) {
                            m_onOffBeanList.clear();
                        }
                        Gson gson = new Gson() ;
                        String data = jsonResult.getString("result") ;

                        LogUtils.e("onOffPumpRecoder", "返回数据" + data);
                        listCache = gson.fromJson(data,
                                new TypeToken<List<OnOffPumpRecoder>>(){}.getType()) ;
                        m_onOffBeanList.addAll(listCache) ;
                        listCache.clear();
                        OnOffPumpAdapter onOffPumpAdapter = new OnOffPumpAdapter(mActivity, m_onOffBeanList) ;
                        listView.setAdapter(onOffPumpAdapter);
                        listView.setSelection(curItem);//指定上次滚动的位置
                        onOffPumpAdapter.notifyDataSetChanged();
                    }else {
                        //ToastUtils.show(getActivity(),"开泵记录,没有数据!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.show(getActivity(),"开泵记录,onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                ToastUtils.show(getActivity(),"开泵记录,onCancelled");
            }

            @Override
            public void onFinished() {
                //ToastUtils.show(getActivity(),"onFinished");
            }
        }) ;

    }

    private int curItem=0;//当前滑动的位置

    /*
   * 下拉和上拉刷新的监听器
   */
    private class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            currentPage = 1 ;
            isFresh = true ;
            getOnoffPump(mActivity.getCurrentWellNo()) ;
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
            currentPage++;
            isFresh=false;
            getOnoffPump(mActivity.getCurrentWellNo()) ;
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
