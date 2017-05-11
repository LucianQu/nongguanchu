package com.automic.app.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.AreaXiangCun;
import com.automic.app.utils.AppUtils;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.SharepreferenceUtils;
import com.automic.app.utils.ToastUtils;
import com.automic.app.view.AddPopWindow;
import com.automic.app.view.ClearEditText;
import com.automic.app.view.CustomProgressDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sujingtai on 2017/3/21 0021.
 */

public abstract class BaseActivityWarn extends AppCompatActivity implements View.OnClickListener, AddPopWindow.Choice {
    private ViewGroup rootview;
    private RelativeLayout titlebarView;
    private ImageButton imgbtnBack;
    private Button imgbtnRight;
    private TextView titleText;
    private Button btnBottombar;
    public TextView tvwXiang;//第一级选择
    public TextView tvwCun;//第二级选择
    private AddPopWindow popWindowXiang;
    private AddPopWindow popWindowCun;
    private List<AreaXiangCun> areaXiangList = new ArrayList<AreaXiangCun>();
    private List<AreaXiangCun> areaCunList = new ArrayList<AreaXiangCun>();
    private boolean isQueryX = false;
    private Context mContext;
    private InputMethodManager imm;
    private ClearEditText edtSearch;
    public boolean flagSearch = false;//是否触发了搜索功能
    public boolean flagQueryCun = false;//是否触发了按村查询功能
    private CustomProgressDialog dialog;//加载网络对话框
    private List<String> dataCacheXiang;
    public int netType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_warn);
        mContext = BaseActivityWarn.this;
        rootview = (ViewGroup) findViewById(R.id.rootview);
        dialog = new CustomProgressDialog(mContext, "正在加载中", R.drawable.frame);
        initTitlebar();
        initData();//获得乡的数据
    }

    @Override
    protected void onResume() {
        super.onResume();
        netType = AppUtils.checkNetworkType(mContext);
        if (netType ==AppUtils.TYPE_NET_WORK_DISABLED){
            ToastUtils.show(this,"当前没有网络连接，请连接！");
        }
    }

    /**
     * 开启dialog
     */
    public void openDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    private void initTitlebar() {
        // TODO Auto-generated method stub
        titleText = (TextView) findViewById(R.id.wellinfo_tvw_title);
        titlebarView = (RelativeLayout) findViewById(R.id.titlebar_view);
        imgbtnBack = (ImageButton) findViewById(R.id.imgbtn_back);
        //输入框
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        RelativeLayout rlSearch = (RelativeLayout) findViewById(R.id.rl_search_bar);
        edtSearch = (ClearEditText) rlSearch.findViewById(R.id.edt_search);
//        TextView tvwCancle = (TextView) rlSearch.findViewById(R.id.tvw_cancel);
//        tvwCancle.setOnClickListener(this);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    LogUtils.e("sjt", "触发了搜索");
                    // searchFlag = true;
                    searchEvent();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            BaseActivityWarn.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });

        //二级选择
        LinearLayout warnSelect = (LinearLayout) findViewById(R.id.ll_two_select_textview);
        tvwXiang = (TextView) warnSelect.findViewById(R.id.tvw_spinner_xiang_warn);
        tvwCun = (TextView) warnSelect.findViewById(R.id.tvw_spinner_cun_warn);
        tvwXiang.setOnClickListener(this);
        tvwCun.setOnClickListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(layoutResID, rootview);
    }

    public void onReturnClick(View v) {
        Activity parent = this.getParent();
        if (parent != null) {
            parent.finish();
        } else {
            this.finish();
        }
    }

    protected void isShowTitleBar(boolean show) {
        if (show) {
            titlebarView.setVisibility(View.VISIBLE);
        } else {
            titlebarView.setVisibility(View.GONE);
        }
    }

    protected void setTitlebarBackground() {
        // R.color.titlebarbg
        titlebarView.setBackgroundResource(R.color.titlebarbg);
    }

    /*
    *设置titlebar的文字
     */
    protected void setTitlebar(String text) {
        if (text != null) {
            titleText.setText(text);
        }
    }

    /**
     * 设置左边按钮的显示状态
     *
     * @param visibility
     */
    public void setLeftButtonVisibility(int visibility) {
        imgbtnBack.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.tvw_cancel:
//                finish();
//                break;
            case R.id.tvw_spinner_xiang_warn:
                //LogUtils.e("sjt", "触发了查询乡");
                // dealXiangEvent();
                isQueryX = true;
                if (dataCacheXiang!=null||dataCacheXiang.size()!=0){

                    popWindowXiang.showPopupWindow(titleText);
                }
//                if (areaXiangList.size() != 0) {
//                    popWindowXiang.showPopupWindow(tvwXiang);
//                }
                break;
            case R.id.tvw_spinner_cun_warn:
                // LogUtils.e("sjt", "触发了查询村");
                isQueryX = false;
                if (areaCunList.size() != 0) {
                    popWindowCun.showPopupWindow(titleText);
                    //popWindowCun.showPopupWindow(tvwCun);
                }
                //dealCunEvent();
                break;
        }
    }

    public void searchEvent() {
        flagSearch = true;
        flagQueryCun = false;
        edtSearch.setCursorVisible(true);//显示光标
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        String currSearchContent = edtSearch.getText().toString().trim();
        if (currSearchContent != null && !"".equals(currSearchContent)) {
//            String  sContent=Encode(currSearchContent);
//            getWellInfoByScontent(sContent);
            getWellInfoByScontent(currSearchContent);
            edtSearch.setCursorVisible(false);//隐藏光标
        }
    }

    protected abstract void getWellInfoByScontent(String sContent);
//    public abstract void  dealXiangEvent();
//    public abstract void  dealCunEvent();

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

    /**
     * @param msg 按popuwindow获取的位置
     */
    @Override
    public void senddata(String msg) {
        int position = Integer.parseInt(msg);
        if (isQueryX) {
            isQueryX = false;
            //tvwXiang.setText(areaXiangList.get(position).getArea());
            //getXiangData(false, areaXiangList.get(position).getAreaId());
            tvwXiang.setText(dataCacheXiang.get(position));
            String cData =SharepreferenceUtils.getCunInfoOneTime(mContext,msg);
            if (cData!=null){
                areaCunList = new Gson().fromJson(cData, new TypeToken<List<AreaXiangCun>>() {}.getType());
                if (areaCunList.size()!=0){
                    List<String> dataCacheCun = new ArrayList<String>();
                    for (AreaXiangCun area : areaCunList) {
                        dataCacheCun.add(area.getArea());
                    }
                    popWindowCun = new AddPopWindow(BaseActivityWarn.this, dataCacheCun);
                    popWindowCun.setChoice(BaseActivityWarn.this);
                }

            }

        } else {
            tvwCun.setText(areaCunList.get(position).getArea());
            //按村查
            flagSearch = false;
            flagQueryCun = true;
            String cunId = areaCunList.get(position).getAreaId();//获取村的id，为了拿到井信息
            //getCunId(cunId);
            getWellInfoByCunId(cunId);
            //getWellInfo(cunId,true,false);
        }
    }

    protected abstract void getWellInfoByCunId(String cunId);

//    /**
//     * 从父类拿到村id
//     * @param cunId
//     */
//    protected abstract void getCunId(String cunId);

//    /**
//     * 点击村获得相应的查询数据
//     */
//    protected abstract void getWellInfo(String content,boolean fromCunid,boolean fromScontent);

    /**
     * 从sharepreference中获取乡镇信息
     */
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
            popWindowXiang = new AddPopWindow(BaseActivityWarn.this, dataCacheXiang);
            popWindowXiang.setChoice(BaseActivityWarn.this);
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x04) {
                tvwXiang.setOnClickListener(BaseActivityWarn.this);
                tvwCun.setOnClickListener(BaseActivityWarn.this);
            } else {
                //数据异常
            }
        }
    };

    /**
     * @param isXiang 为true请求乡数据，为false请求村数据
     * @param xId     乡id
     */
//    private void getXiangData(final boolean isXiang, String xId) {
//        String url = BASEIP + "/area/queryArea";
//        RequestParams requestParams = new RequestParams(url);
//        requestParams.addBodyParameter("userId", "admin");
//        if (!isXiang) {
//            requestParams.addBodyParameter("parentAreaId", xId);
//        }
//        x.http().post(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    JSONObject jsonResult = new JSONObject(result);
//                    int code = jsonResult.getInt("code");
//                    if (code == 1) {
//                        Gson gson = new Gson();
//                        String data = jsonResult.getString("result");
//                        LogUtils.e("sjt", "返回数据" + data);
//                        if (isXiang) {
//                            areaXiangList = gson.fromJson(data, new TypeToken<List<Area>>() {
//                            }.getType());
//                            //        String[] data = {"1乡", "2乡", "3乡"};
//                            List<String> dataCache = new ArrayList<String>();
//                            for (Area area : areaXiangList) {
//                                dataCache.add(area.getArea());
//                            }
//                            popWindowXiang = new AddPopWindow(BaseActivityWarn.this, dataCache);
//                            popWindowXiang.setChoice(BaseActivityWarn.this);
//                        } else {
//                            areaCunList = gson.fromJson(data, new TypeToken<List<Area>>() {
//                            }.getType());
//                            List<String> dataCacheCun = new ArrayList<String>();
//                            for (Area area : areaCunList) {
//                                dataCacheCun.add(area.getArea());
//                            }
//                            popWindowCun = new AddPopWindow(BaseActivityWarn.this, dataCacheCun);
//                            popWindowCun.setChoice(BaseActivityWarn.this);
//                        }
//                        handler.sendEmptyMessage(0x04);
//                    } else {
//                        //ToastUtils.show(mContext,"行政区信息不存在");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } finally {
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                //ToastUtils.show(mContext,"查询失败");
//                ToastUtils.show(mContext, "获取行政区信息失败");
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }

}
