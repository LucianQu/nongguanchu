package com.automic.app.utils;

/**
 * 类注释：  保存部分数据
 * Created by sujingtai on 2017/4/12 0012 下午 3:22
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.automic.app.bean.UserInfo;

import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SharepreferenceUtils {
    public static final String PREF_USERID_CHANELID = "Preference_Userid_Channelid";
    public static final String PREF_USER_INFO = "Preference_User_Info";
    public static final  String XIANG_INFO="xiang_info";
    public static final String CUN_INFO="cun_info";
    public static final  String XIANG_INFO_ONETIME="xiang_info_OneTime";
    public static final String CUN_INFO_ONETIME="cun_info_OneTime";


    public static void savePswisSave(Context context, boolean issave) {
        SharedPreferences preference = context.getSharedPreferences(
                "preference_psw_save", 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean("issave", issave);
        editor.commit();
    }

    public static boolean getPswisSave(Context context) {
        SharedPreferences preference = context.getSharedPreferences(
                "preference_psw_save", 0);
        boolean issave = preference.getBoolean("issave", false);
        return issave;
    }

    public static void savePumpOpResult(Context context, boolean isOpen) {
        SharedPreferences preferences = context.getSharedPreferences("preference_pump_status", 0) ;
        SharedPreferences.Editor editor = preferences.edit() ;
        editor.putBoolean("pumpstatus", isOpen) ;
        editor.commit() ;
    }
    public static boolean getPumpOpResult(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("preference_pump_status", 0) ;
        boolean isOpen = preferences.getBoolean("pumpstatus", false) ;
        return isOpen ;
    }

    public static void saveUserInfo(Context context, UserInfo user) {
        SharedPreferences preference = context.getSharedPreferences(
                PREF_USER_INFO, Context.MODE_PRIVATE);
        Editor editor = preference.edit();
        editor.putString("usercode", user.getUsername());
        editor.putString("pass", user.getPassword());
        editor.putString("app", user.getApp());
        editor.putString("adId", user.getAdId());
        editor.putString("unitid", user.getUnitid());
        editor.putString("deviceId", user.getDeviceId());
        editor.putString("roleId", user.getRoleId());
        editor.putString("realName", user.getRealName());
        editor.commit();
    }

    public static UserInfo getUserInfo(Context context) {
        SharedPreferences preference = context.getSharedPreferences(
                PREF_USER_INFO, Context.MODE_PRIVATE);
        String usercode = preference.getString("usercode", "");
        String pass = preference.getString("pass", "");
        String app = preference.getString("app", "");
        String adId = preference.getString("adId", "");
        String unitid = preference.getString("unitid", "");
        String deviceId = preference.getString("deviceId", "");
        String roleId = preference.getString("roleId", "");
        String realName = preference.getString("realName", "");

        UserInfo user = new UserInfo();
        user.setUsername(usercode);
        user.setPassword(pass);
        user.setApp(app);
        user.setAdId(adId);
        user.setUnitid(unitid);
        user.setDeviceId(deviceId);
        user.setRoleId(roleId);
        user.setRealName(realName);
        return user;
    }
    public static void setXiangInfo(Context context,String info){
        SharedPreferences preference=context.getSharedPreferences(XIANG_INFO,Context.MODE_PRIVATE);
        Editor editor=preference.edit();
        editor.putString("xiang",info);
        editor.commit();

    }
    public static  String getXiangInfo(Context context){
        SharedPreferences preference=context.getSharedPreferences(XIANG_INFO,Context.MODE_PRIVATE);
        return  preference.getString("xiang","");
    }
    public static void setCunInfo(Context context,String xiangSn,String cunInfo){
        SharedPreferences preference=context.getSharedPreferences(CUN_INFO,Context.MODE_PRIVATE);
        Editor editor=preference.edit();
        if (xiangSn.equals("0"))
            editor.putString("cunXOne",cunInfo);
        if (xiangSn.equals("1"))
            editor.putString("cunXTwo",cunInfo);
        if (xiangSn.equals("2"))
            editor.putString("cunXThree",cunInfo);
        if (xiangSn.equals("3"))
            editor.putString("cunXFour",cunInfo);
        if (xiangSn.equals("4"))
            editor.putString("cunXFive",cunInfo);
        if (xiangSn.equals("5"))
            editor.putString("cunXSix",cunInfo);
        if (xiangSn.equals("6"))
            editor.putString("cunXSeven",cunInfo);
        editor.commit();
    }
    public static String getCunInfo(Context  context,String XiangSn){
        SharedPreferences preference=context.getSharedPreferences(CUN_INFO,Context.MODE_PRIVATE);
        if (XiangSn.equals("0")){
            return   preference.getString("cunXOne","");
        } else if(XiangSn.equals("1")){
            return  preference.getString("cunXTwo","");
        }else if (XiangSn.equals("2")){
            return preference.getString("cunXThree","");
        }else if (XiangSn.equals("3")){
            return preference.getString("cunXFour","");
        }else if (XiangSn.equals("4")){
            return preference.getString("cunXFive","");
        }else if (XiangSn.equals("5")){
            return preference.getString("cunXSix","");
        }else if (XiangSn.equals("6")){
            return preference.getString("cunXSeven","");
        }
        return null;
    }
    /****************************************************************************************************/
    public static void setXiangInfoOneTime(Context context,String info){
        SharedPreferences preference=context.getSharedPreferences(XIANG_INFO_ONETIME,Context.MODE_PRIVATE);
        Editor editor=preference.edit();
        editor.putString("xiang",info);
        editor.commit();

    }
    public static  String getXiangInfoOneTime(Context context){
        SharedPreferences preference=context.getSharedPreferences(XIANG_INFO_ONETIME,Context.MODE_PRIVATE);
        return  preference.getString("xiang","");
    }
    public static void setCunInfoOneTime(Context context,String xiangSn,String cunInfo){
        SharedPreferences preference=context.getSharedPreferences(CUN_INFO_ONETIME,Context.MODE_PRIVATE);
        Editor editor=preference.edit();
        if (xiangSn.equals("0"))
            editor.putString("cunXOne",cunInfo);
        if (xiangSn.equals("1"))
            editor.putString("cunXTwo",cunInfo);
        if (xiangSn.equals("2"))
            editor.putString("cunXThree",cunInfo);
        if (xiangSn.equals("3"))
            editor.putString("cunXFour",cunInfo);
        if (xiangSn.equals("4"))
            editor.putString("cunXFive",cunInfo);
        if (xiangSn.equals("5"))
            editor.putString("cunXSix",cunInfo);
        if (xiangSn.equals("6"))
            editor.putString("cunXSeven",cunInfo);
        editor.commit();
    }
    public static String getCunInfoOneTime(Context  context,String XiangSn){
        SharedPreferences preference=context.getSharedPreferences(CUN_INFO_ONETIME,Context.MODE_PRIVATE);
        if (XiangSn.equals("0")){
            return   preference.getString("cunXOne","");
        } else if(XiangSn.equals("1")){
            return  preference.getString("cunXTwo","");
        }else if (XiangSn.equals("2")){
            return preference.getString("cunXThree","");
        }else if (XiangSn.equals("3")){
            return preference.getString("cunXFour","");
        }else if (XiangSn.equals("4")){
            return preference.getString("cunXFive","");
        }else if (XiangSn.equals("5")){
            return preference.getString("cunXSix","");
        }else if (XiangSn.equals("6")){
            return preference.getString("cunXSeven","");
        }
        return null;
    }
    /*****************************************************************************************************/
    public static class Screen {
        public int widthPixels;
        public int heightPixels;

        public Screen(int widthPixels, int heightPixels) {
            this.widthPixels = widthPixels;
            this.heightPixels = heightPixels;
        }

        public String toString() {
            return "(" + this.widthPixels + "," + this.heightPixels + ")";
        }
    }
}