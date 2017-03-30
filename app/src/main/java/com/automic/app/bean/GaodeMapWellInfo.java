package com.automic.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QLS on 3/23/2017.
 */

public class GaodeMapWellInfo implements Parcelable {
    private static final String TAG = "WellLocationBean" ;

    private String wellNo;//水井编码
    private String wellName;//水井名称
    private String wellUser;//用户
    private double wellX; //
    private double wellY; //

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

    public String getWellUser() {
        return wellUser;
    }

    public void setWellUser(String wellUser) {
        this.wellUser = wellUser;
    }

    public double getWellX() {
        return wellX;
    }

    public void setWellX(double wellX) {
        this.wellX = wellX;
    }

    public double getWellY() {
        return wellY;
    }

    public void setWellY(double wellY) {
        this.wellY = wellY;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wellNo);
        parcel.writeString(wellName);
        parcel.writeString(wellUser);
        parcel.writeDouble(wellX);
        parcel.writeDouble(wellY);
    }

    public static final Parcelable.Creator<GaodeMapWellInfo> CREATOR =
            new Creator<GaodeMapWellInfo>() {
                @Override
                public GaodeMapWellInfo createFromParcel(Parcel parcel) {
                    GaodeMapWellInfo mWellLocationBean = new GaodeMapWellInfo() ;
                    mWellLocationBean.wellNo = parcel.readString();
                    mWellLocationBean.wellName = parcel.readString() ;
                    mWellLocationBean.wellUser = parcel.readString() ;
                    mWellLocationBean.wellX = parcel.readDouble() ;
                    mWellLocationBean.wellY = parcel.readDouble() ;
                    return mWellLocationBean;
                }

                @Override
                public GaodeMapWellInfo[] newArray(int i) {
                    return new GaodeMapWellInfo[i];
                }
            };

}
