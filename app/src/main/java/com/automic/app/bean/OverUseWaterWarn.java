package com.automic.app.bean;

import java.io.Serializable;

/**
 * 超年计划报警
 * Created by qulus on 2017/4/13 0013.
 */

public class OverUseWaterWarn implements Serializable {
    private String wellNo ;         //机井编码
    private String wellName ;       //机井名称
    private String occurTime ;      //发生时间
    private String yearPlanWater ;  //年计划取水量
    private String yearAddUpWater ; //年累计取水量

    public String getYearAddUpWater() {
        return yearAddUpWater;
    }

    public void setYearAddUpWater(String yearAddUpWater) {
        this.yearAddUpWater = yearAddUpWater;
    }

    public String getWellNo() {
        return wellNo;
    }

    public void setWellNo(String wellNo) {
        this.wellNo = wellNo;
    }

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    public String getYearPlanWater() {
        return yearPlanWater;
    }

    public void setYearPlanWater(String yearPlanWater) {
        this.yearPlanWater = yearPlanWater;
    }

    public OverUseWaterWarn(String wellNo, String wellName, String occurTime, String yearPlanWater, String yearAddUpWater) {
        this.wellNo = wellNo ;
        this.wellName = wellName ;
        this.occurTime = occurTime ;
        this.yearPlanWater = yearPlanWater ;
        this.yearAddUpWater = yearAddUpWater ;
    }
}
