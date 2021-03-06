package com.automic.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.automic.app.R;
import com.automic.app.activity.LoadPictrueActivity;
import com.automic.app.activity.WellInfoActivity;
import com.automic.app.adapter.OnOffPumpAdapter;
import com.automic.app.adapter.OpenBoxAdapter;
import com.automic.app.bean.OnOffPumpRecoder;
import com.automic.app.bean.OpenBoxRecoder;
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

public class OpenboxFragment extends BaseFragment {
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView pullableListView;
    private RelativeLayout m_title;
    private List<OpenBoxRecoder> mList = new ArrayList<OpenBoxRecoder>() ;
    private List<OpenBoxRecoder> listCache = new ArrayList<OpenBoxRecoder>() ;
    private boolean isFresh = false;
    private int currentPage = 1;
    private int curItem=0;//当前滑动的位置
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
        //记住上一次滚动时的位置信息
        pullableListView.setOnScrollListener(new AbsListView.OnScrollListener(){
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
      /*  mList = new ArrayList<OpenBoxRecoder>();

            mList.add(new OpenBoxRecoder("111", "222", "2016年11月30日 17:00", "2016年11月30日 17:22",
                    "http://172.16.60.56:8081/monitor/irg/showImgAction.action?suffix=png&imgId=1611301726400086"));

            mList.add(new OpenBoxRecoder("222", "333", "2016年12月07日 06:50", "2016年12月07日 06:59",
                    "http://172.16.60.56:8081/monitor/irg/showImgAction.action?suffix=png&imgId=1612070704211468"));

        mList.add(new OpenBoxRecoder("222", "333", "2016年12月06日 12:22", "2016年12月07日 10:59",
                "http://172.16.60.56:8081/monitor/irg/showImgAction.action?suffix=png&imgId=1612071104111516"));*/

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mList.size()!=0){
            mList.clear();
        }
        // getOpenbox(null,"654003001006") ;//请求服务器开关泵信息
        getOpenbox("admin",mActivity.getCurrentWellNo());
    }

    /**
     *获取开关箱记录,根据页面获取,每页20条数据
     * @param userId 用户ID
     * @param wellNo 机井编码
     */
    private void getOpenbox(String userId, String wellNo) {
        if (null == userId && null == wellNo || currentPage < 0) {
            //ToastUtils.show(getActivity(), "参数为空!");
            return;
        }
        String url = BASEIP + "/well/queryOpenBoxRecoder" ;
        RequestParams requestParams = new RequestParams(url) ;
        if (null != userId)
            requestParams.addBodyParameter("userId", userId);
        if (null != wellNo)
            requestParams.addBodyParameter("wellNo", wellNo);
        if (currentPage >= 0 )
            requestParams.addParameter("currentPage", currentPage);//添加请求参数(根据请求谓词, 将参数加入QueryString或Body.
        requestParams.addBodyParameter("count", "10");

        x.http().post(requestParams, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonResult = new JSONObject(result) ;
                    int code = jsonResult.getInt("code") ;
                    if (code == 1) {
                        if (isFresh) {
                            mList.clear();
                        }
                        Gson gson = new Gson() ;
                        String data = jsonResult.getString("result") ;
                        LogUtils.e("openBox", "返回数据" + data);

                        listCache = gson.fromJson(data,
                                new TypeToken<List<OpenBoxRecoder>>(){}.getType()) ;
                        mList.addAll(listCache) ;
                        listCache.clear();

                        OpenBoxAdapter openBoxAdapter = new OpenBoxAdapter(mActivity, mList);
                        pullableListView.setAdapter(openBoxAdapter);
                        pullableListView.setSelection(curItem);//指定上次滚动的位置
                        openBoxAdapter.notifyDataSetChanged();
                    }else {
                        ToastUtils.show(getActivity(),"开箱记录,没有数据!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.show(getActivity(),"开箱记录,onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                ToastUtils.show(getActivity(),"开箱记录,onCancelled");
            }

            @Override
            public void onFinished() {
                //ToastUtils.show(getActivity(),"onFinished");
            }
        }) ;

    }

    /**
     * 上拉和下拉刷新监听器
     */
    private class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            currentPage = 1 ;
            isFresh = true ;
            getOpenbox(null, mActivity.getCurrentWellNo()) ;
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
            currentPage++ ;
            isFresh = false ;
            getOpenbox(null, mActivity.getCurrentWellNo()) ;
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
            intent.putExtra("wellName", mList.get(position).getWellName());
            intent.putExtra("wellNo", mList.get(position).getWellNo());
            startActivity(intent);
        }
    }
}


