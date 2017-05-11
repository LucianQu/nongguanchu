package com.automic.app.bean;

/**
 * 类注释：
 * Created by sujingtai on 2017/4/12 0012 下午 3:20
 */
public class UserInfo
{
    private String username;
    private String password;
    private String roleId;
    private String realName;
    private String deviceId;
    private String adId;
    private String app;
    private String unitid;

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAdId() {
        return this.adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUnitid() {
        return this.unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }
}
