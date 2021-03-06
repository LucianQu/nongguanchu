package com.automic.app.bean;

import java.io.Serializable;

/**
 * Created by sujingtai on 2017/3/28 0028.
 */

public class WellInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private	String	wellName	;
    private	String	wellNo	;
    private	String	admin	;
    private	int	wellOnOff	;
    private	String	wellAddress	;
    private	double	wellLat	;
    private	double	wellLong	;
    private	String	remainElect	;
    private	String	remainWater	;
    private	double	yearAddupWater	;
    private	double	yearAddupElec	;
    private	double	YearPlanWater	;

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public String getWellNo() {
        return wellNo;
    }

    public void setWellNo(String wellNo) {
        this.wellNo = wellNo;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getWellOnOff() {
        return wellOnOff;
    }

    public void setWellOnOff(int wellOnOff) {
        this.wellOnOff = wellOnOff;
    }

    public String getWellAddress() {
        return wellAddress;
    }

    public void setWellAddress(String wellAddress) {
        this.wellAddress = wellAddress;
    }

    public double getWellLat() {
        return wellLat;
    }

    public void setWellLat(double wellLat) {
        this.wellLat = wellLat;
    }

    public double getWellLong() {
        return wellLong;
    }

    public void setWellLong(double wellLong) {
        this.wellLong = wellLong;
    }

    public String getRemainElect() {
        return remainElect;
    }

    public void setRemainElect(String remainElect) {
        this.remainElect = remainElect;
    }

    public String getRemainWater() {
        return remainWater;
    }

    public void setRemainWater(String remainWater) {
        this.remainWater = remainWater;
    }

    public double getYearAddupWater() {
        return yearAddupWater;
    }

    public void setYearAddupWater(double yearAddupWater) {
        this.yearAddupWater = yearAddupWater;
    }

    public double getYearAddupElec() {
        return yearAddupElec;
    }

    public void setYearAddupElec(double yearAddupElec) {
        this.yearAddupElec = yearAddupElec;
    }

    public double getYearPlanWater() {
        return YearPlanWater;
    }

    public void setYearPlanWater(double yearPlanWater) {
        YearPlanWater = yearPlanWater;
    }

    public WellInfo(String wellName, String wellNo, String admin, int wellOnOff, String wellAddress,
                    double wellLat, double wellLong, String remainElect, String remainWater,
                    double yearAddupWater, double yearAddupElec, double yearPlanWater) {
        this.wellName = wellName;
        this.wellNo = wellNo;
        this.admin = admin;
        this.wellOnOff = wellOnOff;
        this.wellAddress = wellAddress;
        this.wellLat = wellLat;
        this.wellLong = wellLong;
        this.remainElect = remainElect;
        this.remainWater = remainWater;
        this.yearAddupWater = yearAddupWater;
        this.yearAddupElec = yearAddupElec;
        YearPlanWater = yearPlanWater;
    }
    public WellInfo() {}
}
