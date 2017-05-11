package com.automic.app.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.application.AppContext;
import com.automic.app.bean.JpushExtrasJson;
import com.automic.app.bean.JpushMessage;
import com.automic.app.dao.DBOpenHelper;
import com.automic.app.dao.imp.JpushMessageImp;
import com.automic.app.utils.JpushUtils;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.StringUtils;
import com.automic.app.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class JpushmessageListActivity extends InstrumentedActivity implements View.OnClickListener{

    private Context mContent;
    private static  final String TAG="JpushmessageListActivity";
    private TextView tvwMsgNo1;
    private TextView tvwMsgNo2;
    private TextView tvwMsgNo3;
    private TextView tvwMsgNo4;
    private TextView tvwMsgNo5;
    private int countTypeOne;
    private int countTypeTwo;
    private int countTypeThree;
    private int countTypeFour;
    private int countTypeFive;
private List<JpushMessage>mList1=new ArrayList<JpushMessage>();
    private static boolean isQuery=true;
    private JpushMessageImp jpMsgImp;
    private RunnableDemo queryThread;
    private RelativeLayout ll_warn1;
    private RelativeLayout ll_warn2;
    private RelativeLayout ll_warn3;
    private RelativeLayout ll_warn4;
    private RelativeLayout ll_warn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpushmessage);
        mContent = JpushmessageListActivity.this;
        registerMessageReceiver();
        setupView();

    }

    private void setupView() {
        //报警区域条目
        ll_warn1 = (RelativeLayout)findViewById(R.id.ll_warn1);
        ll_warn2 = (RelativeLayout)findViewById(R.id.ll_warn2);
        ll_warn3 = (RelativeLayout)findViewById(R.id.ll_warn3);
        ll_warn4 = (RelativeLayout)findViewById(R.id.ll_warn4);
        ll_warn5 = (RelativeLayout)findViewById(R.id.ll_warn5);
        ll_warn1.setOnClickListener(JpushmessageListActivity.this);
        ll_warn2.setOnClickListener(JpushmessageListActivity.this);
        ll_warn3.setOnClickListener(JpushmessageListActivity.this);
        ll_warn4.setOnClickListener(JpushmessageListActivity.this);
        ll_warn5.setOnClickListener(JpushmessageListActivity.this);
        //
        (ll_warn1.findViewById(R.id.imgv_log_warn)).setBackgroundResource(R.mipmap.ic_deviceworkwarn);
        (ll_warn2.findViewById(R.id.imgv_log_warn)).setBackgroundResource(R.mipmap.ic_openbox);
        (ll_warn3.findViewById(R.id.imgv_log_warn)).setBackgroundResource(R.mipmap.ic_overplanedwater);
        (ll_warn4.findViewById(R.id.imgv_log_warn)).setBackgroundResource(R.mipmap.ic_waterelecabnormal);
        (ll_warn5.findViewById(R.id.imgv_log_warn)).setBackgroundResource(R.mipmap.ic_waterelecless);
        //
        ((TextView) ll_warn1.findViewById(R.id.tvw_warn_type)).setText("设备工况报警");
        ((TextView) ll_warn2.findViewById(R.id.tvw_warn_type)).setText("开箱提醒");
        ((TextView) ll_warn3.findViewById(R.id.tvw_warn_type)).setText("超年计划水量报警");
        ((TextView) ll_warn4.findViewById(R.id.tvw_warn_type)).setText("水电配比报警");
        ((TextView) ll_warn5.findViewById(R.id.tvw_warn_type)).setText("水电量不足报警");
        //
        tvwMsgNo1 = (TextView) ll_warn1.findViewById(R.id.tvw_count_msg);
        tvwMsgNo2 = (TextView) ll_warn2.findViewById(R.id.tvw_count_msg);
        tvwMsgNo3 = (TextView) ll_warn3.findViewById(R.id.tvw_count_msg);
        tvwMsgNo4 = (TextView) ll_warn4.findViewById(R.id.tvw_count_msg);
        tvwMsgNo5 = (TextView) ll_warn5.findViewById(R.id.tvw_count_msg);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mList1.clear();//清除原来的数据
        countTypeFive=0;
        countTypeOne=0;
        countTypeTwo=0;
        countTypeThree=0;
        countTypeFour=0;
 //       isQuery=true;
//        queryThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (isQuery) {
//                    queryDbMsg();
//                }
//
//            }
//        });
//        queryThread.start();
        queryThread=  new RunnableDemo("queryMsg");
        queryThread.start();
    }
private void updateMsg(String type){
    for(JpushMessage jsp:mList1){
        if (type.equals(jsp.getType())){
            jsp.setIsReaded("1");
            try {
               int num= jpMsgImp.update(DBOpenHelper.TABLENAME,jsp);
                LogUtils.e(TAG,"更新的行数"+num);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e(TAG,"报警数据更新异常"+e);
            }
        }
    }

}
    private void queryDbMsg() {
        LogUtils.e(TAG,"进入查询");
        mList1.clear();//清除原来的数据
        countTypeFive=0;
        countTypeOne=0;
        countTypeTwo=0;
        countTypeThree=0;
        countTypeFour=0;
        try {
            jpMsgImp = new JpushMessageImp(JpushmessageListActivity.this);
            mList1=  jpMsgImp.query(DBOpenHelper.TABLENAME,"isReaded","0");
            for (JpushMessage jsp:mList1){
                if ( "deviceWorkWarn".equals(jsp.getType()))
                countTypeOne++;
                if ( "openBox".equals(jsp.getType()))
                    countTypeTwo++;
                if ( "overPlanedWater".equals(jsp.getType()))
                    countTypeThree++;
                if ( "waterElecAbnormal".equals(jsp.getType()))
                    countTypeFour++;
                if ( "waterElecless".equals(jsp.getType()))
                    countTypeFive++;
            }
            //isQuery=false;//关闭线程
            queryThread.suspend();
            handler.sendEmptyMessage(0x09);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG,"查询数据库异常"+e);
        }
    }
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what==0x09){
            tvwMsgNo1.setText(String.valueOf(countTypeOne)+"条未读信息");
            tvwMsgNo2.setText(String.valueOf(countTypeTwo)+"条未读信息");
            tvwMsgNo3.setText(String.valueOf(countTypeThree)+"条未读信息");
            tvwMsgNo4.setText(String.valueOf(countTypeFour)+"条未读信息");
            tvwMsgNo5.setText(String.valueOf(countTypeFive)+"条未读信息");
        }
    }
};
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
switch (v.getId()){
    case R.id.ll_warn1:
        intent.setClass(JpushmessageListActivity.this,DeviceWorkWarnActivity.class);
        gotoOtherActivity(intent,"deviceWorkWarn");
        break;
    case R.id.ll_warn2:
        intent.setClass(JpushmessageListActivity.this,OpenBoxWarnActivity.class);
        gotoOtherActivity(intent,"openBox");
        break;
     case R.id.ll_warn3:
         intent.setClass(JpushmessageListActivity.this,OverPlanedWaterWarnActivity.class);
         gotoOtherActivity(intent,"overPlanedWater");
        break;
     case R.id.ll_warn4:
         intent.setClass(JpushmessageListActivity.this,WaterElecRatioWarnActivity.class);
         gotoOtherActivity(intent,"waterElecAbnormal");
        break;
     case R.id.ll_warn5:
         intent.setClass(JpushmessageListActivity.this,WaterEleclessWarnActivity.class);
         gotoOtherActivity(intent,"waterElecless");
        break;

}

    }

    /**
     * 将onclick 中的intent.set注释掉，并将此方法中的intent.sets反注释，当消息为0时，就不跳转了
     * @param intent
     * @param type
     */
    private void gotoOtherActivity(Intent intent, String type) {
        String wellNos = null;
        if (countTypeOne!=0&&type.equals("deviceWorkWarn")){
            wellNos=getWellnosByType("deviceWorkWarn");
            //intent.setClass(JpushmessageListActivity.this,DeviceWorkWarnActivity.class);
        }
        if (countTypeTwo!=0&&type.equals("openBox")){
           wellNos=getWellnosByType("openBox");
           // intent.setClass(JpushmessageListActivity.this,OpenBoxWarnActivity.class);
        }
        if (countTypeThree!=0&&type.equals("overPlanedWater")){
            wellNos=getWellnosByType("overPlanedWater");
            //intent.setClass(JpushmessageListActivity.this,OverPlanedWaterWarnActivity.class);
        }
        if (countTypeFour!=0&&type.equals("waterElecAbnormal")){
            wellNos=getWellnosByType("waterElecAbnormal");
           // intent.setClass(JpushmessageListActivity.this,WaterElecRatioWarnActivity.class);
        }
        if (countTypeFive!=0&&type.equals("waterElecless")){
            wellNos=getWellnosByType("waterElecless");
            //intent.setClass(JpushmessageListActivity.this,WaterEleclessWarnActivity.class);
        }
        if (wellNos!=null){
            LogUtils.e(TAG,"wellnos===="+wellNos);
            intent.putExtra("wellNos",wellNos);
            updateMsg(type);
        }
        startActivity(intent);

    }
    private String getWellnosByType(String type){
        StringBuilder sb = new StringBuilder();
        if (mList1.size()!=0){
            for (JpushMessage jsp:mList1){
                if ( type.equals(jsp.getType())) {
                    sb.append(jsp.getWellNo()+",");
                }
            }
            String wellNos=  sb.toString();
            LogUtils.e(TAG,"mellnos===="+wellNos);
            //return   wellNos.substring(0,wellNos.length()-2);
            return   wellNos;

        }else
            return null;
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
//                StringBuilder showMsg = new StringBuilder();
//                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                //解析extras
//                if (!StringUtils.isEmpty(extras)) {
//
//                    try {
//                        JpushExtrasJson jpushJson = new Gson().fromJson(extras, JpushExtrasJson.class);
//                List<String> typeList=       jpushJson.getType();
//                        for (String str:typeList){
//                            if ( "deviceWorkWarn".equals(str))
//                                countTypeOne++;
//                            if ( "openBox".equals(str))
//                                countTypeTwo++;
//                            if ( "overPlanedWater".equals(str))
//                                countTypeThree++;
//                            if ( "waterElecAbnormal".equals(str))
//                                countTypeFour++;
//                            if ( "waterElecless".equals(str))
//                                countTypeFive++;
//                        }
//                        handler.sendEmptyMessage(0x09);
//                    } catch (JsonSyntaxException e) {
//                        e.printStackTrace();
//                    }
//
//                }
                //查询数据库
                boolean isHasNewMsg=intent.getBooleanExtra("isHasNewMsg",false);
                if (isHasNewMsg){
//                    isQuery=true;
LogUtils.e(TAG,"是否收到信息"+isHasNewMsg);
                    queryThread.resume();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isQuery=false;
   unregisterReceiver(mMessageReceiver);
    }
    public void onReturnClick(View v) {
        Activity parent = this.getParent();
        if (parent != null) {
            parent.finish();
        } else {
            this.finish();
        }
    }
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.automic.app.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
        //JpushUtils.setTag("AIadmin");//设置灌溉的管理员
    }

    class RunnableDemo implements Runnable {
        public Thread t;
        private String threadName;
        boolean suspended = false;

        RunnableDemo( String name){
            threadName = name;
            System.out.println("Creating " +  threadName );
        }
        public void run() {
            System.out.println("Running " +  threadName );
            try {
                for(int i = 10; i > 0; i--) {
                   // System.out.println("Thread: " + threadName + ", " + i);
                    // Let the thread sleep for a while.
                  queryDbMsg();
                    synchronized(this) {
                        while(suspended) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Thread " +  threadName + " interrupted.");
            }
            System.out.println("Thread " +  threadName + " exiting.");
        }

        public void start ()
        {
            System.out.println("Starting " +  threadName );
            if (t == null)
            {
                t = new Thread (this, threadName);
                t.start ();
            }
        }
        void suspend() {
            suspended = true;
        }
        synchronized void resume() {
            suspended = false;
            notify();
        }
    }


}
