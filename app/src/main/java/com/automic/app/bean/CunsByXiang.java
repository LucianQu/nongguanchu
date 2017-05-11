package com.automic.app.bean;

import java.util.List;

/**
 * 类注释：
 * Created by sujingtai on 2017/4/20 0020 下午 2:47
 */

public class CunsByXiang {

    public List<AreaXiangCun> getCuns() {
        return cuns;
    }

    public void setCuns(List<AreaXiangCun> cuns) {
        this.cuns = cuns;
    }

    List<AreaXiangCun> cuns;

    public CunsByXiang() {
    }
}
