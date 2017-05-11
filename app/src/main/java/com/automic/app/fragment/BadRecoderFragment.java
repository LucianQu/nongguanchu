package com.automic.app.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.automic.app.R;
import com.automic.app.activity.DeviceStatusListActivity;
import com.automic.app.adapter.BadRecoderAdapter;
import com.automic.app.bean.DeviceStatus;
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

public class BadRecoderFragment extends BaseFragment {
    private PullToRefreshLayout pullToRefreshLayout ;
    private PullableListView pullableListView ;
    private RelativeLayout m_title ;

    private List<DeviceStatus> listCache=new ArrayList<DeviceStatus>();
    private List<DeviceStatus> mList=new ArrayList<DeviceStatus>();
    private boolean isFresh=false;
    private int currentPage=1;
private int curItem=0;//当前滑动的页码
    @Override
    public void initLayout() {
        layoutId= R.layout.fragment_badrecoder;
    }

    @Override
    public void initStatus() {
        m_title = (RelativeLayout) findViewById(R.id.br_title) ;
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pl_refresh_badrecoder) ;
        pullableListView = (PullableListView) pullToRefreshLayout.findViewById(R.id.lv_info_badrecoder) ;
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullableListView.setOnItemClickListener(new itemListener());
        pullableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                curItem = firstVisibleItem;
            }
        });
      /*  ListView lv=(ListView)findViewById(R.id.lv_info_badrecoder) ;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
        //654003001006
        queryDeviceStateWarn(mActivity.getCurrentWellNo());

        //queryDeviceStateWarn("654003001006") ;
        //getWellDeviceStatusData(null, "654003001006", "deviceWorkWarn", 1);
    }
    /**
     *获取一个单井的历史工况
     */
    private void queryDeviceStateWarn(String wellNo) {
        String url = BASEIP + "/well/queryDeviceStateWarn" ;
        RequestParams request = new RequestParams(url);
        request.addBodyParameter("wellNo", wellNo);
        request.addBodyParameter("userId", "admin");
        request.addParameter("typeFlag", 1);
        request.addBodyParameter("eventType", "deviceWorkWarn");
        request.addParameter("currentPage", currentPage);
        request.addParameter("count", 10);
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    if (code == 1) {
                        if (isFresh) {
                            mList.clear();
                        }
                        String data = json.getString("result");
                        listCache = new Gson().fromJson(data, new TypeToken<List<DeviceStatus>>(){}.getType());
                        mList.addAll(listCache);
                        listCache.clear();//本次清空缓存
                        BadRecoderAdapter badRecoderAdapter = new BadRecoderAdapter(mActivity, mList);
                        pullableListView.setAdapter(badRecoderAdapter);
                        badRecoderAdapter.notifyDataSetChanged();
                    } else {
                        if (mList.size() == 0) {
                            ToastUtils.show(mActivity, "查询不到数据");
                        } else {
                            ToastUtils.show(mActivity, "已经加载到底了");
                        }
//                        //假数据
//                        for (int i=0;i<50;i++){
//                            mlist.add(new RechargeRecoder("1234532","屈庄站","2017-04-05","56","67","56","54"));
//                        }
//                        RechargeRecoderAdapter rechargeRecoderAdapter=new RechargeRecoderAdapter(mActivity, mlist);
//                        listView.setAdapter(rechargeRecoderAdapter);
//                        rechargeRecoderAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.show(mActivity, "BadRecoder-Error");
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
     * 获取工况详情
     * @param uId 用户Id
     * @param wNo 机井编码
     */
    private void getWellDeviceStatusData(String uId, String wNo, String eventType, int typeFlag) {
        if(uId == null && wNo == null || null == eventType || typeFlag < 0 && typeFlag >= 3) {
            ToastUtils.show(mActivity, "参数错误!");
            return;
        }
        String url = BASEIP + "/well/queryDeviceStateWarn" ;
        RequestParams requestParams = new RequestParams(url) ;//网络请求参数实体,设置url
        if (null != uId) {
            requestParams.addBodyParameter("userId", uId);   //添加用户Id参数至Body
        }
        if (null != wNo) {
            requestParams.addBodyParameter("wellNo", wNo);   //添加机井编码参数至Body
        }
        if (null != eventType) {
            requestParams.addBodyParameter("eventType", eventType);
        }
        if (typeFlag > 0 && typeFlag < 3) {
            requestParams.addParameter("typeFlag", typeFlag);
        }

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result) ;//根据返回字段,创建JSONObject
                    int code = jsonObject.getInt("code") ;
                    if (code == 1) {
                        Gson gson = new Gson() ;
                        String data = jsonObject.getString("result") ;
                        LogUtils.e("DeviceStatus", "返回数据" + data);
                        mList = gson.fromJson(data, new TypeToken<List<DeviceStatus>>(){}.getType()) ;
                        LogUtils.e("mList:","" + mList.size()) ;
                        BadRecoderAdapter badRecoderAdapter = new BadRecoderAdapter(mActivity, mList);
                        pullableListView.setAdapter(badRecoderAdapter);
                        badRecoderAdapter.notifyDataSetChanged();
                    }else {
                        ToastUtils.show(mActivity, "wellDeviceStatus无数据!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e("获取工况详情","数据请求失败");
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
                currentPage = 1;
                isFresh=true;
                queryDeviceStateWarn(mActivity.getCurrentWellNo());
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
                currentPage++;
                isFresh=false;
                queryDeviceStateWarn(mActivity.getCurrentWellNo());
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
         /*   Intent intent = new Intent();
            intent.putExtra("LoadPictrueActivity", mList.get(position).getImgPath());
            startActivity(intent);*/

            Intent intent = new Intent() ;
            intent.setClass(getActivity(), DeviceStatusListActivity.class) ;
            intent.putExtra("DeviceStatus", mList.get(position));
            //intent.putExtra("typeFlag", 2) ;
            startActivity(intent);
        }
    }
}
