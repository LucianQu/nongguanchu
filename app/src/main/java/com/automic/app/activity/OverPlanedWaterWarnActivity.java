package com.automic.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.automic.app.R;
import com.automic.app.adapter.WarnOverPlanedWaterAdapter;
import com.automic.app.bean.OverUseWaterWarn;
import com.automic.app.utils.AppUtils;
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
 * 超年计划报警
 * Created by qulus on 2017/4/12 0012.
 */

public class OverPlanedWaterWarnActivity extends BaseActivityWarn {
    private List<OverUseWaterWarn> m_oUw_list = new ArrayList<OverUseWaterWarn>() ;
    private List<OverUseWaterWarn> m_listCache = new ArrayList<OverUseWaterWarn>() ;

    private int m_currentPage = 1 ;
    private boolean m_isFresh = false ;
    private String m_cunId ;
    private String m_sContent ;
    private Context m_Context ;

    private PullToRefreshLayout m_pullToRefreshLayout ;
    private PullableListView m_pullableListView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_warn_overplan_water);
        m_Context = OverPlanedWaterWarnActivity.this ;
        setupView() ;
        setTitlebar("超年计划水量报警");
    }
    @Override
    protected void onResume() {
        super.onResume();
        String wellNos=  getIntent().getStringExtra("wellNos");
        LogUtils.e("OverPlanedWater","wellnos==="+wellNos);
        if (wellNos!=null&&netType!= AppUtils.TYPE_NET_WORK_DISABLED){
            queryWarnWellByType(wellNos,"overPlanedWater");
        }
        //openDialog();
    }
    private void setupView() {
        m_pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pl_refresh_warn_overPlanWater) ;
        m_pullToRefreshLayout.setOnRefreshListener(new MyListener());
        m_pullableListView = (PullableListView) m_pullToRefreshLayout.findViewById(R.id.lv_info_warn_overPlanWater) ;
    }

    @Override
    protected void getWellInfoByScontent(String sContent) {
        this.m_sContent = sContent ;
        m_oUw_list.clear();
        m_currentPage = 1;
        getWellInfo(m_sContent, false, true);
    }

    @Override
    protected void getWellInfoByCunId(String cunId) {
        this.m_cunId = cunId;
        m_oUw_list.clear();
        m_currentPage = 1;
        getWellInfo(cunId, true, false);
    }



    protected void getWellInfo(String content, boolean fromCunid, boolean fromScontent) {
        String url = BASEIP + "/well/queryOverUseWaterWarn" ;
        RequestParams requestParams = new RequestParams(url) ;
        requestParams.addBodyParameter("userId", "admin");
        if (fromCunid) {
            requestParams.addBodyParameter("areaCode", content);
        }else if (fromScontent) {
            requestParams.addBodyParameter("SContent", content);
        }
        requestParams.addParameter("currentPage", m_currentPage);
        requestParams.addParameter("count", 20);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getOverPlanWaterSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.show(m_Context, "服务器请求失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });

    }

    /**
     * 按报警类型查询报警列表（井列表）
     * 接口为开发（平台）
     */
    private void queryWarnWellByType(String wellNos,String type){
        String url=BASEIP+"/well/queryWarnWellByType";
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("userId","admin");
        params.addBodyParameter("wellNoS",wellNos);//机井编码集合
        params.addBodyParameter("type",type);
        params.addParameter("currentPage",1);
        params.addParameter("count",20);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getOverPlanWaterSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.show(m_Context,"服务器请求失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getOverPlanWaterSuccess(String result) {
        try {
            JSONObject jsonResult = new JSONObject(result) ;
            int code = jsonResult.getInt("code") ;
            if (code == 1) {
                if (m_isFresh)
                    m_oUw_list.clear();
                Gson gson = new Gson() ;
                String data = jsonResult.getString("result") ;
                LogUtils.e("DeviceWorkWarnActivity", "返回数据" + data);
                m_listCache = gson.fromJson(data, new TypeToken<List<OverUseWaterWarn>>(){}.getType()) ;
                m_oUw_list.addAll(m_listCache) ;
                m_listCache.clear();
                WarnOverPlanedWaterAdapter deviceStatusAdapter = new WarnOverPlanedWaterAdapter(m_Context, m_oUw_list) ;
                m_pullableListView.setAdapter(deviceStatusAdapter);
                deviceStatusAdapter.notifyDataSetChanged();
            }else {
                if (m_oUw_list.size() == 0) {
                    ToastUtils.show(m_Context, "查询不到数据!");
                }else {
                    ToastUtils.show(m_Context, "数据加载到底!");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class MyListener implements PullToRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    m_currentPage = 1 ;
                    m_isFresh = true ;
                    if (flagQueryCun)
                        getWellInfo(m_cunId,true,false);
                    if (flagSearch)
                        getWellInfo(m_sContent,false,true);
                    //告诉控件,刷新完毕!
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500) ;
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    m_currentPage++ ;
                    m_isFresh = false ;
                    if (flagQueryCun)
                        getWellInfo(m_cunId,true,false);
                    if (flagSearch)
                        getWellInfo(m_sContent,false,true);
                    //告诉控件加载完毕
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500) ;
        }
    }
}
