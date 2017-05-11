package com.automic.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.utils.AppUtils;
import com.automic.app.utils.KeyboardUtil;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.SharepreferenceUtils;
import com.automic.app.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import static com.automic.app.bean.Constant.BASEIP;

/**
 * Created by qulus on 2017/4/10 0010.
 */

public class OnOffInputPwActivity extends Activity {
    private View backView ;
    static OnOffInputPwActivity instance ;
    private ImageView imePwOne, imePwTwo, imePwThree, imePwFour, imePwFive, imePwSix, imePwSeven,
    imePwEight ;
    private KeyboardUtil kbUtil ;
    private EditText m_password ;
    private TextView m_pWerrorTv ;
    private TextView m_pFailCause ;
    private int m_onOffPumpResult ;
    private String m_onOffPumpMess ;
    private Context m_Context ;

    private LinearLayout m_pump_input_page ;
    private LinearLayout m_pump_wait_page ;
    private LinearLayout m_pump_success_page ;
    private LinearLayout m_pump_fail_page ;
    private TextView m_onOff_result ;
    private String m_currentWellNo ;

    private TextView m_pumpAlarmInfo ;
    private boolean m_pumpOpNoResult = false;
    public boolean m_pumpStatus =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_number);
        getExtrasMapData() ;
        setview();
        setListener() ;
        initData() ;

    }

    private void getExtrasMapData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(null == bundle) {
            ToastUtils.show(m_Context,"井号为空");
        }else {
            m_currentWellNo = (String) bundle.get("OnOffInputPwActivity") ;
            bundle.remove("OnOffInputPwActivity");
        }
    }

    private void initData() {
        m_Context = OnOffInputPwActivity.this ;
        instance = this ;
        kbUtil = new KeyboardUtil(OnOffInputPwActivity.this) ;
        ArrayList<ImageView> imeList = new ArrayList<ImageView>() ;
        imeList.add(imePwOne) ;
        imeList.add(imePwTwo) ;
        imeList.add(imePwThree) ;
        imeList.add(imePwFour) ;
        imeList.add(imePwFive) ;
        imeList.add(imePwSix) ;
        imeList.add(imePwSeven) ;
        imeList.add(imePwEight) ;
        kbUtil.setListIme(imeList);
    }

    private void backToActivity() {
        //Intent intent = new Intent() ;
       // intent.setClass(m_Context, WellInfoActivity.class) ;
        OnOffInputPwActivity.instance.finish();

        //startActivity(intent);
    }

    private void setview() {
        imePwOne = (ImageView) findViewById(R.id.etPwdOne_setLockPwd) ;
        imePwTwo = (ImageView) findViewById(R.id.etPwdTwo_setLockPwd) ;
        imePwThree = (ImageView) findViewById(R.id.etPwdThree_setLockPwd) ;
        imePwFour = (ImageView) findViewById(R.id.etPwdFour_setLockPwd) ;
        imePwFive = (ImageView) findViewById(R.id.etPwdFive_setLockPwd) ;
        imePwSix = (ImageView) findViewById(R.id.etPwdSix_setLockPwd) ;
        imePwSeven = (ImageView) findViewById(R.id.etPwdSeven_setLockPwd) ;
        imePwEight = (ImageView) findViewById(R.id.etPwdEight_setLockPwd) ;
        m_password = (EditText) findViewById(R.id.etPwdText_setLockPwd) ;

        m_pumpAlarmInfo = (TextView) findViewById(R.id.pump1_alarmInfo) ;
        if (WellInfoActivity.instance.getPumpStatus()) {
            m_pumpAlarmInfo.setText(R.string.offPumpAlarm);
        }else {
            m_pumpAlarmInfo.setText(R.string.onPumpAlarm);
        }
        m_pWerrorTv = (TextView) findViewById(R.id.pump1_pw_err) ;
        m_pump_input_page = (LinearLayout) findViewById(R.id.pump1_input_page) ;
        m_pump_wait_page = (LinearLayout) findViewById(R.id.pump1_wait_page) ;
        m_onOff_result = (TextView) findViewById(R.id.pump1_onOffResult) ;
        m_pump_success_page = (LinearLayout) findViewById(R.id.pump1_success_page) ;
        m_pump_success_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToActivity() ;
            }
        });
        m_pump_fail_page = (LinearLayout) findViewById(R.id.pump1_fail_page) ;
        m_pFailCause = (TextView) findViewById(R.id.pump1_failCause) ;
        m_pump_fail_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToActivity() ;
            }
        });
    }

    private void setListener() {
        m_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (m_password.getText() != null && m_password.getText().toString().length() >= 1) {
                    String passWord = m_password.getText().toString() ;
                        //ToastUtils.show(OnOffInputPwActivity.this, "密码:12345678");
                    if (m_currentWellNo != null) {
                        if (WellInfoActivity.instance.getPumpStatus()) {
                            m_pump_input_page.setVisibility(View.GONE);
                            m_pump_wait_page.setVisibility(View.VISIBLE);
                            onOffPumpOperate(m_currentWellNo, "0", passWord) ;//如果处于开泵状态,关泵操作
                        }else {
                            m_pump_input_page.setVisibility(View.GONE);
                            m_pump_wait_page.setVisibility(View.VISIBLE);
                            onOffPumpOperate(m_currentWellNo, "1", passWord) ;//如果处于关泵状态,开泵操作
                        }
                        noResultHandler.postDelayed(noResultDetection, 90000);
                    } else {
                        //ToastUtils.show(m_Context, "井号为空,返回!");
                        //backToActivity() ;
                    }
                }
            }
        });

        m_onOff_result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if ("1".equals(s.toString())) {
                    //ToastUtils.show(m_Context, "开启成功!");
                    SharepreferenceUtils.savePumpOpResult(m_Context, true);
                    m_pumpOpNoResult = true ;
                    WellInfoActivity.instance.setPumpStatus();
                    m_pump_input_page.setVisibility(View.GONE);
                    m_pump_wait_page.setVisibility(View.GONE);
                    m_pump_fail_page.setVisibility(View.GONE);
                    m_pump_success_page.setVisibility(View.VISIBLE);
                }else if("2".equals(s.toString())){
                    //ToastUtils.show(m_Context, "密码错误!");
                    SharepreferenceUtils.savePumpOpResult(m_Context, false);
                    m_pumpOpNoResult = true ;
                    WellInfoActivity.instance.setPumpStatus();
                    m_pump_input_page.setVisibility(View.VISIBLE);
                    m_pump_wait_page.setVisibility(View.GONE);
                    m_pump_fail_page.setVisibility(View.GONE);
                    m_pump_success_page.setVisibility(View.GONE);
                    m_pWerrorTv.setVisibility(View.VISIBLE);
                    imePwOne.setImageResource(R.mipmap.input_pw_round);
                    imePwTwo.setImageResource(R.mipmap.input_pw_round);
                    imePwThree.setImageResource(R.mipmap.input_pw_round);
                    imePwFour.setImageResource(R.mipmap.input_pw_round);
                    imePwFive.setImageResource(R.mipmap.input_pw_round);
                    imePwSix.setImageResource(R.mipmap.input_pw_round);
                    imePwSeven.setImageResource(R.mipmap.input_pw_round);
                    imePwEight.setImageResource(R.mipmap.input_pw_round);
                    m_password.setText("");
                }else if ("0".equals(s.toString())) {
                    SharepreferenceUtils.savePumpOpResult(m_Context, false);
                    m_pumpOpNoResult = true ;
                    WellInfoActivity.instance.setPumpStatus();
                    //ToastUtils.show(m_Context, "开启失败!");
                    m_pFailCause.setText("原因: " + m_onOffPumpMess);
                    m_pump_input_page.setVisibility(View.GONE);
                    m_pump_wait_page.setVisibility(View.GONE);
                    m_pump_fail_page.setVisibility(View.VISIBLE);
                    m_pump_success_page.setVisibility(View.GONE);
                }else if ("3".equals(s.toString())) {
                    SharepreferenceUtils.savePumpOpResult(m_Context, false);
                    m_pumpOpNoResult = true ;
                    WellInfoActivity.instance.setPumpStatus();
                    //ToastUtils.show(m_Context, "通信网络异常!");
                    m_pFailCause.setText("原因: " + m_onOffPumpMess);
                    m_pump_input_page.setVisibility(View.GONE);
                    m_pump_wait_page.setVisibility(View.GONE);
                    m_pump_success_page.setVisibility(View.GONE);
                    m_pump_fail_page.setVisibility(View.VISIBLE);
                }else if ("请求错误".equals(s.toString())) {
                    SharepreferenceUtils.savePumpOpResult(m_Context, false);
                    m_pumpOpNoResult = true ;
                    WellInfoActivity.instance.setPumpStatus();
                    //ToastUtils.show(m_Context, "请求错误!");
                    m_pFailCause.setText("原因: " + m_onOffPumpMess);
                    m_pump_input_page.setVisibility(View.GONE);
                    m_pump_wait_page.setVisibility(View.GONE);
                    m_pump_fail_page.setVisibility(View.VISIBLE);
                    m_pump_success_page.setVisibility(View.GONE);
                }
            }
        });
    }

    private Runnable noResultDetection = new Runnable() {
        @Override
        public void run() {
            if (!m_pumpOpNoResult) {
                noResultHandler.removeCallbacks(noResultDetection);
            } else {
                noResultHandler.sendEmptyMessage(0x89);
            }
        }
    };
    Handler noResultHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x89) {
                SharepreferenceUtils.savePumpOpResult(m_Context, false);
                WellInfoActivity.instance.setPumpStatus();
                //ToastUtils.show(m_Context, "开启失败!");
                m_pFailCause.setText("原因:命令无响应!");
                m_pump_input_page.setVisibility(View.GONE);
                m_pump_wait_page.setVisibility(View.GONE);
                m_pump_fail_page.setVisibility(View.VISIBLE);
                m_pump_success_page.setVisibility(View.GONE);
            }
        }
    };

    /**
     * 操作开关泵
     *http://172.16.60.135:8888/appService/command/command?wellNo=654003001006&commandFlag=2&openPassword=12345678
     *修改ip和端口
     *m_onOffPumpResult 命令结果
     */
    private void onOffPumpOperate(String wNo, String commandFlag, String openPassWord) {
        if(commandFlag == null && wNo == null || null == openPassWord || null == openPassWord) {
            ToastUtils.show(m_Context, "参数错误!");
            return;
        }
        String url = BASEIP + "/command/pump/openClosePump" ;
        RequestParams requestParams = new RequestParams(url) ;//网络请求参数实体,设置url
        if (null != commandFlag) {
            requestParams.addBodyParameter("commandFlag", commandFlag);   //添加用户Id参数至Body
        }
        if (null != wNo) {
            requestParams.addBodyParameter("wellNo", wNo);   //添加机井编码参数至Body
        }
        if (null != openPassWord) {
            requestParams.addBodyParameter("openPassword", openPassWord);
        }

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result) ;//根据返回字段,创建JSONObject
                    m_onOffPumpResult = jsonObject.getInt("flag") ;
                    m_onOffPumpMess = "" ;
                    m_onOffPumpMess = jsonObject.getString("mess") ;
                    if (m_onOffPumpResult == 1) {
                        //ToastUtils.show(m_Context, "开启成功!");
                        m_onOff_result.setText("" + m_onOffPumpResult);
                    }else if(m_onOffPumpResult == 2){
                        //ToastUtils.show(m_Context, "验证密码失败!");
                        m_onOff_result.setText("" + m_onOffPumpResult);
                    }else if (m_onOffPumpResult == 3) {
                        //ToastUtils.show(m_Context, "通信网络异常!");
                        m_onOff_result.setText("" + m_onOffPumpResult);
                    }else if (m_onOffPumpResult == 0){
                        //ToastUtils.show(m_Context, "开启失败!");
                        m_onOff_result.setText("" + m_onOffPumpResult);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e("开关泵","请求错误!");
                m_onOff_result.setText("请求错误");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                LogUtils.e("开关泵操作","finish!");
            }
        });
    }

}
