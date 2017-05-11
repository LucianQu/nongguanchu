package com.automic.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 水电配比报警查询
 * Created by qulus on 2017/4/13 0013.
 */

public class WaterElecRatioWarn implements Serializable {
    private String wellNo ;             //机井编码
    private String wellName ;           //机井名称
    private String occurTime ;          //发生时间
    private String currentValue ;       //现在水电配比值
   // private List<String> noramlValue ;  //正常配比范围
    private String normalValue;

    public String getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(String normalValue) {
        this.normalValue = normalValue;
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

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

//    public List<String> getNoramlValue() {
//        return noramlValue;
//    }
//
//    public void setNoramlValue(List<String> noramlValue) {
//        this.noramlValue = noramlValue;
//    }

    public WaterElecRatioWarn() {
    }

    public WaterElecRatioWarn(String wellNo, String wellName, String occurTime, String eventType,
                              String currentValue, String noramlValue) {
        this.wellNo = wellNo ;
        this.wellName = wellName ;
        this.occurTime = occurTime ;
        this.currentValue = currentValue ;
        this.normalValue = noramlValue ;
    }
}
