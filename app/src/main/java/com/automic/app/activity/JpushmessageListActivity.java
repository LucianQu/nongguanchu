package com.automic.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;

import com.automic.app.R;
import com.automic.app.application.AppContext;
import com.automic.app.utils.JpushUtils;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.StringUtils;
import com.automic.app.utils.ToastUtils;



import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class JpushmessageListActivity extends InstrumentedActivity {

    private Context mContent;
    private static  final String TAG="mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpushmessage);
        mContent = JpushmessageListActivity.this;
        registerMessageReceiver();
    }



    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
        setTag("AIadmin");//设置灌溉的管理员
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!StringUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                ToastUtils.show(mContent,showMsg.toString());
            }
        }
    }
    private void setTag(String tag) {

        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            // Toast.makeText(PushSetActivity.this, R.string.error_tag_empty,
            // Toast.LENGTH_SHORT).show();
            return;
        }

        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!JpushUtils.isValidTagAndAlias(sTagItme)) {
                LogUtils.e(TAG, "error_tag_gs_empty");
                return;
            }
            tagSet.add(sTagItme);
        }
        // 调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
    }

    private void setAlias(String alias) {
        if (TextUtils.isEmpty(alias)) {

            return;
        }
        if (!JpushUtils.isValidTagAndAlias(alias)) {
            LogUtils.e(TAG, "error_alias_empty");
            return;
        }

        // 调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String Log;
            switch (code) {
                case 0:
                    Log = "Set tag and alias success";
                    LogUtils.i(TAG, Log);
                    break;

                case 6002:
                    Log = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    LogUtils.i(TAG, Log);
                    if (JpushUtils.isConnected(AppContext.getInstance())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        LogUtils.i(TAG, "No network");
                    }
                    break;

                default:
                    Log = "Failed with errorCode = " + code;
                    LogUtils.e(TAG, Log);
            }

        }

    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String Log;
            switch (code) {
                case 0:
                    Log = "Set tag and alias success";
                    LogUtils.i(TAG, Log);
                    break;

                case 6002:
                    Log = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    LogUtils.i(TAG, Log);
                    if (JpushUtils.isConnected(AppContext.getInstance())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        LogUtils.i(TAG, "No network");
                    }
                    break;

                default:
                    Log = "Failed with errorCode = " + code;
                    LogUtils.e(TAG, Log);

            }

        }

    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    LogUtils.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(AppContext.getInstance(), (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    LogUtils.d(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(AppContext.getInstance(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    LogUtils.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
   unregisterReceiver(mMessageReceiver);
    }

}