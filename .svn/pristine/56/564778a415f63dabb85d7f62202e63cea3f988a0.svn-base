package com.automic.app.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/3/21 0021.
 */

public class AppContext extends Application {
    private static AppContext mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

    }

    public static AppContext getInstance() {
        return mInstance;
    }
}
