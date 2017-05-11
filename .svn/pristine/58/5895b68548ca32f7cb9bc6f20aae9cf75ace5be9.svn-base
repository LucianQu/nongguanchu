package com.automic.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.adapter.WellInfoAdapter;
import com.automic.app.bean.Area;
import com.automic.app.bean.AreaXiangCun;
import com.automic.app.bean.CunsByXiang;
import com.automic.app.bean.WellInfo;
import com.automic.app.utils.AppUtils;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.SharepreferenceUtils;
import com.automic.app.utils.ToastUtils;
import com.automic.app.view.AddPopWindow;
import com.automic.app.view.ClearEditText;
import com.automic.app.view.pullpushview.PullToRefreshLayout;
import com.automic.app.view.pullpushview.PullableListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.automic.app.bean.Constant.BASEIP;
import static com.automic.app.bean.Constant.SEARCH_WELL_RESULT_CODE;

/**
 * Created by sujingtai on 2017/3/28 0028.
 */

public class SearchWellOneTimeActivity extends AppCompatActivity implements View.OnClickListener, AddPopWindow.Choice {

    private PullableListView lv;
    private PullToRefreshLayout ppLayout;
    private TextView tvwXiang;
    private TextView tvwCun;
    private Context mContext;
    private AddPopWindow popWindowXiang;
    private AddPopWindow popWindowCun;
    private List<AreaXiangCun> areaCunList;
    private boolean isQueryX = false;
    private int currentPage = 1;
private List<WellInfo>mList=new ArrayList<WellInfo>();
    private Intent intentFromWellInfoA;
    private InputMethodManager imm;
    private ClearEditText edtSearch;
    private int mNetType;
    private boolean flagSearch;
    private boolean flagQueryCun;
    private String sContent=null;
    private String cunId;
    private boolean isFresh=false;
    private List<WellInfo> listCache;//每次请求的缓存
    private WellInfoAdapter wellInfoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_well);
        intentFromWellInfoA = getIntent();
        setupView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNetType = AppUtils.checkNetworkType(this);
        if (mNetType ==AppUtils.TYPE_NET_WORK_DISABLED){
            ToastUtils.show(this,"当前没有网络连接，请连接！");
        }
    }

    private void setupView() {
        mContext = SearchWellOneTimeActivity.this;
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        edtSearch = (ClearEditText)findViewById(R.id.edt_search);
        TextView tvwCancle=(TextView)findViewById(R.id.tvw_cancel);
        tvwCancle.setOnClickListener(this);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    LogUtils.e("sjt", "触发了搜索");
                    // searchFlag = true;
                    searchEvent();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            SearchWellOneTimeActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        ppLayout = (PullToRefreshLayout) findViewById(R.id.pp_layout);
        ppLayout.setOnRefreshListener(new MyListener());
        lv = (PullableListView) ppLayout.findViewById(R.id.lv_well_search);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (mList.size()!=0){
                   Bundle bundle=new Bundle();
                   bundle.putSerializable("wellInfo",mList.get(position));
                   intentFromWellInfoA.putExtras(bundle);
                   setResult(SEARCH_WELL_RESULT_CODE,intentFromWellInfoA);
                   finish();
               }


            }
        });
//二级选择
        LinearLayout wellSelect = (LinearLayout) findViewById(R.id.ll_select);
        tvwXiang = (TextView) wellSelect.findViewById(R.id.tvw_spinner_wxiang);
        tvwXiang.setOnClickListener(this);
        tvwCun = (TextView) wellSelect.findViewById(R.id.tvw_spinner_wcun);
        tvwCun.setOnClickListener(this);

    }

    private void searchEvent() {
        // TODO Auto-generated method stub
        flagSearch = true;
        flagQueryCun = false;
        currentPage=1;
        mList.clear();
        // 显示或者隐藏输入法
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        sContent = edtSearch.getText().toString().trim();
        if (sContent != null && !"".equals(sContent)) {
            //sContent = Encode(sContent);
            mList.clear();//搜索之前要清除缓存
            getWellInfo(sContent,false,true);
        }
    }
    /**
     * 文字转UTF-8编码
     *
     * @param content 搜索内容
     * @return
     */
    public static String Encode(String content) {
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvw_cancel:
                finish();
                break;
            case R.id.tvw_spinner_wxiang:
                isQueryX = true;
                if (dataCacheXiang!=null&&dataCacheXiang.size()!=0) {
                    popWindowXiang.showPopupWindow(edtSearch);
                }
                break;
            case R.id.tvw_spinner_wcun:
                isQueryX = false;
                if (areaCunList!=null) {
                    if (areaCunList.size() != 0){
                        popWindowCun.showPopupWindow(edtSearch);
                    }
                }
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
            isQueryX = false;
            tvwXiang.setText(dataCacheXiang.get(position));
            String cData =SharepreferenceUtils.getCunInfoOneTime(mContext,msg);
            if (cData!=null){
                areaCunList = new Gson().fromJson(cData, new TypeToken<List<AreaXiangCun>>() {}.getType());
                if (areaCunList.size()!=0){
                    List<String> dataCacheCun = new ArrayList<String>();
                    for (AreaXiangCun area : areaCunList) {
                        dataCacheCun.add(area.getArea());
                    }
                    String xiangReleCun = dataCacheCun.get(0);
                    tvwCun.setText(xiangReleCun);
                    popWindowCun = new AddPopWindow(SearchWellOneTimeActivity.this, dataCacheCun);
                    popWindowCun.setChoice(SearchWellOneTimeActivity.this);
                }

            }else{
                //ToastUtils.show(mContext,"请重新登录，刷新初始化数据");
            }

        } else {
            tvwCun.setText(areaCunList.get(position).getArea());
            //按村查
            flagSearch = false;
            flagQueryCun = true;
            String cunId = areaCunList.get(position).getAreaId();//获取村的id，为了拿到井信息
            getWellInfoByCunId(cunId);

        }
    }

    private void getWellInfoByCunId(String cunId) {
        mList.clear();
        currentPage=1;
        this.cunId=cunId;
        getWellInfo(cunId,true,false);
    }

    private List<String> dataCacheXiang;
    private void initData() {
        //getXiangData(true, "");
        String xData=  SharepreferenceUtils.getXiangInfoOneTime(mContext);
        if (xData!=null){
            String[] data=  xData.split(",");
            dataCacheXiang = new ArrayList<String>();
            for (int i=0;i<data.length;i++){
                //LogUtils.e("sjt","数据为"+data[i]);
                dataCacheXiang.add(data[i]);
            }
            popWindowXiang = new AddPopWindow(SearchWellOneTimeActivity.this, dataCacheXiang);
            popWindowXiang.setChoice(SearchWellOneTimeActivity.this);
        }
    }

    /**
     * 查询井信息
     * @param content 查询字段
     * @param fromCunid 按村查井，并分页
     * @param fromScontent 按所搜框输入内容查井，并分页
     */
    private void getWellInfo(String content,boolean fromCunid,boolean fromScontent) {
        String url = BASEIP+"/well/searchWellInfo";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("userId", "admin");
        if (fromCunid){
            requestParams.addBodyParameter("areaCode", content);
        }
        if (fromScontent){
            requestParams.addBodyParameter("sContent", content);
        }
        requestParams.addParameter("currentPage", currentPage);
        requestParams.addBodyParameter("count", "10");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonResult = new JSONObject(result);
                    int code = jsonResult.getInt("code");
                    LogUtils.e("sjt", "返回数据" + result);
                    if (code == 1) {
                        if (isFresh){
                            mList.clear();
                            isFresh=false;
                        }
                        Gson gson = new Gson();
                        String data = jsonResult.getString("result");
                        listCache=gson.fromJson(data,new TypeToken<List<WellInfo>>(){}.getType());
                        mList.addAll(listCache);
                        listCache.clear();
                        wellInfoAdapter = new WellInfoAdapter(mContext, mList);
                        lv.setAdapter(wellInfoAdapter);
                        wellInfoAdapter.notifyDataSetChanged();
                    }else{
                        if (mList.size()==0){
                            wellInfoAdapter = new WellInfoAdapter(mContext, mList);
                            lv.setAdapter(wellInfoAdapter);
                            wellInfoAdapter.notifyDataSetChanged();
                            ToastUtils.show(mContext,"查询不到数据");
                        }else{
                            ToastUtils.show(mContext,"已经加载到底了");
                        }                    }
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

    /**
     * 下拉和上拉刷新的监听器
     */
    private class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            if (mList.size()!=0){
                currentPage = 1;
                isFresh=true;
                if (flagQueryCun)
                    getWellInfo(cunId,true,false);
                if (flagSearch)
                    getWellInfo(sContent,false,true);
            }
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
            if (mList.size()!=0){
                currentPage++;
                isFresh=false;
                if (flagQueryCun)
                    getWellInfo(cunId,true,false);
                if (flagSearch)
                    getWellInfo(sContent,false,true);
            }
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
