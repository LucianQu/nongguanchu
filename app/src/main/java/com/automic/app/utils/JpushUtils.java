package com.automic.app.utils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class JpushUtils {
	 public static final String PREFS_NAME = "JPUSH_EXAMPLE";
	    public static final String PREFS_DAYS = "JPUSH_EXAMPLE_DAYS";
	    public static final String PREFS_START_TIME = "PREFS_START_TIME";
	    public static final String PREFS_END_TIME = "PREFS_END_TIME";
	    public static final String KEY_APP_KEY = "JPUSH_APPKEY";
		private static final String TAG = "JpushUtils";

	    
	    // 校验Tag Alias 只能是数字,英文字母和中文
	    public static boolean isValidTagAndAlias(String s) {
	        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
	        Matcher m = p.matcher(s);
	        return m.matches();
	    }

	    // 取得AppKey
	    public static String getAppKey(Context context) {
	        Bundle metaData = null;
	        String appKey = null;
	        try {
	            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
	                    context.getPackageName(), PackageManager.GET_META_DATA);
	            if (null != ai)
	                metaData = ai.metaData;
	            if (null != metaData) {
	                appKey = metaData.getString(KEY_APP_KEY);
	                if ((null == appKey) || appKey.length() != 24) {
	                    appKey = null;
	                }
	            }
	        } catch (NameNotFoundException e) {

	        }
	        return appKey;
	    }
	    
	    // 取得版本号
	    public static String GetVersion(Context context) {
			try {
				PackageInfo manager = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);
				return manager.versionName;
			} catch (NameNotFoundException e) {
				return "Unknown";
			}
		}

	
	    
	    public static boolean isConnected(Context context) {
	        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo info = conn.getActiveNetworkInfo();
	        return (info != null && info.isConnected());
	    }
	    
		public static String getImei(Context context, String imei) {
			try {
				TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				imei = telephonyManager.getDeviceId();
			} catch (Exception e) {
				Log.e(JpushUtils.class.getSimpleName(), e.getMessage());
			}
			return imei;
		}
	    public static String getDeviceId(Context context) {
	        String deviceId = JPushInterface.getUdid(context);
	        return deviceId;
	    }
	    
	    
	    public static <T> T fromJson(String str, Class<T> type) {  
	        Gson gson = new Gson();
	        return gson.fromJson(str, type);  
	    }    
}
