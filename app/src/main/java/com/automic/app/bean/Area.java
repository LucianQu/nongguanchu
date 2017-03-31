package com.automic.app.bean;

/**
 * 类注释：乡镇数据
 * Created by sujingtai on 2017/3/30 0030 下午 4:53
 */

public class Area {
    private String area;
    private String areaId;
    private String areaCd;
    private int levl;
    private String parentAreaId;

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public int getLevl() {
        return levl;
    }

    public void setLevl(int levl) {
        this.levl = levl;
    }

    public String getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }


    public Area() {
    }
}
