package com.automic.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.adapter.WellInfoAdapter;
import com.automic.app.bean.Area;
import com.automic.app.bean.WellInfo;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.ToastUtils;
import com.automic.app.view.AddPopWindow;
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
 * Created by sujingtai on 2017/3/28 0028.
 */

public class SearchWellActivity extends AppCompatActivity implements View.OnClickListener, AddPopWindow.Choice {

    private PullableListView lv;
    private PullToRefreshLayout ppLayout;
    private TextView tvwXiang;
    private TextView tvwCun;
    private Context mContext;
    private AddPopWindow popWindowXiang;
    private AddPopWindow popWindowCun;
    private List<Area> areaXiangList;
    private List<Area> areaCunList;
    private boolean isQueryX = false;
    private int currentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_well);
        setupView();
initData();
    }


    private void setupView() {
        mContext = SearchWellActivity.this;
        ppLayout = (PullToRefreshLayout) findViewById(R.id.pp_layout);
        ppLayout.setOnRefreshListener(new MyListener());
        lv = (PullableListView) ppLayout.findViewById(R.id.lv_well_search);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        List<WellInfo> mWellList = new ArrayList<WellInfo>();
        for (int i = 0; i < 9; i++) {
            mWellList.add(new WellInfo("大宝井", "233444323", "李三三", 0, "发电机房村", 12.3, 116.6, "56", "78", "77", "34", "907"));
        }
        WellInfoAdapter wellInfoAdapter = new WellInfoAdapter(mContext, mWellList);
        lv.setAdapter(wellInfoAdapter);
        wellInfoAdapter.notifyDataSetChanged();
//二级选择
        LinearLayout wellSelect = (LinearLayout) findViewById(R.id.ll_select);
        tvwXiang = (TextView) wellSelect.findViewById(R.id.tvw_spinner_wxiang);
        tvwXiang.setOnClickListener(this);
        tvwCun = (TextView) wellSelect.findViewById(R.id.tvw_spinner_wcun);
        tvwCun.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvw_cancel:
                break;
            case R.id.tvw_spinner_wxiang:
                isQueryX = true;
                popWindowXiang.showPopupWindow(tvwXiang);
                Log.e("sjt", "Jin ru xiang");
                break;
            case R.id.tvw_spinner_wcun:
                isQueryX = false;
                popWindowCun.showPopupWindow(tvwCun);
                Log.e("sjt", "Jin ru cun");
                break;

        }
    }

    /**
     * @param msg  按popuwindow获取的位置
     */
    @Override
    public void senddata(String msg) {
        int position = Integer.parseInt(msg);
        if (isQueryX) {
            isQueryX=false;
            tvwXiang.setText(areaXiangList.get(position).getArea());
            getXiangData(false, areaXiangList.get(position).getAreaId());

        } else {
            tvwCun.setText(areaCunList.get(position).getArea());
            //按村查
           String cunId= areaCunList.get(position).getAreaId();//获取村的id，为了拿到井信息
            getWellInfoFromCun(cunId);
        }
    }

    private void initData() {
        getXiangData(true, "");

    }

    /**
     * @param isXiang 为true请求乡数据，为false请求村数据
     * @param xId     乡id
     */
    private void getXiangData(final boolean isXiang, String xId) {
        String url = BASEIP+"/area/queryArea";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("userId", "admin");
        if (!isXiang) {
            requestParams.addBodyParameter("parentAreaId", xId);
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonResult = new JSONObject(result);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        Gson gson = new Gson();
                        String data = jsonResult.getString("result");
                        LogUtils.e("sjt", "返回数据" + data);
                        if (isXiang) {
                            areaXiangList = gson.fromJson(data, new TypeToken<List<Area>>() {
                            }.getType());
                            //        String[] data = {"1乡", "2乡", "3乡"};
                            List<String> dataCache = new ArrayList<String>();
                            for (Area area : areaXiangList) {
                                dataCache.add(area.getArea());
                            }
                            popWindowXiang = new AddPopWindow(SearchWellActivity.this, dataCache);
                            popWindowXiang.setChoice(SearchWellActivity.this);
                        } else{
                            areaCunList = gson.fromJson(data, new TypeToken<List<Area>>() {
                            }.getType());
                            List<String> dataCacheCun = new ArrayList<String>();
                            for (Area area : areaCunList) {
                                dataCacheCun.add(area.getArea());
                            }
                            popWindowCun = new AddPopWindow(SearchWellActivity.this, dataCacheCun);
                            popWindowCun.setChoice(SearchWellActivity.this);
                        }

                    }else
                        ToastUtils.show(mContext,"没有数据");
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
     * @param cunId 按村查井，并分页
     */
    private void getWellInfoFromCun(String cunId) {
        String url = BASEIP+"/well/searchWellInfo";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("userId", "admin");
        requestParams.addBodyParameter("areaCode", cunId);
        requestParams.addParameter("currentPage", currentPage);
        requestParams.addBodyParameter("count", "10");

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonResult = new JSONObject(result);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        Gson gson = new Gson();
                        String data = jsonResult.getString("result");
                        LogUtils.e("sjt", "返回数据" + data);


                    }else
                        ToastUtils.show(mContext,"此村暂无水泵信息");
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.show(mContext,"查询失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
