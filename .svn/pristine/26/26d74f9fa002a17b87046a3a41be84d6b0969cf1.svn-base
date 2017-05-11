package com.automic.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.DeviceStatus;
import com.automic.app.bean.OverUseWaterWarn;
import com.automic.app.utils.DateFormatUtils;

import java.util.List;

import static com.automic.app.utils.DateFormatUtils.FormatType.DateLong;
import static com.automic.app.utils.DateFormatUtils.FormatType.DateMmDdHhMm;

/**
 * 超计划用水报警
 * Created by qulus on 2017/4/12 0012.
 */

public class WarnOverPlanedWaterAdapter extends BaseTtAdapter<OverUseWaterWarn> {
    private Context m_context ;
    public WarnOverPlanedWaterAdapter(Context context, List<OverUseWaterWarn> mList) {
        super(context, mList);
        this.m_context = context ;
        this.setLayoutId(R.layout.item_listview_warn_overplan_water);
        int[] ids = {R.id.warn_opw_wellName, R.id.warn_opw_time,R.id.warn_opw_titleWellNo, R.id.warn_opw_yearUse, R.id.warn_opw_yearPlan} ;
        String fields[] = {"wellNameTv", "occurTime", "wellNoTv","yearAddUpWater", "yearPlanWater"} ;
        this.setClass(ViewHolderOverPlanWater.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }

    @Override
    public void addDataToView(OverUseWaterWarn overUseWaterWarn, Object o) {

        String wellNo = overUseWaterWarn.getWellNo() ;
        String yearAddUpWater = overUseWaterWarn.getYearAddUpWater() ;//年累计用水量
        String yearPlanWater = overUseWaterWarn.getYearPlanWater() ;//年计划用水量
        String occurTime=overUseWaterWarn.getOccurTime();
        String ocTime=null;
        if (occurTime!=null&&!occurTime.equals("")){
            ocTime=  DateFormatUtils.ChangeFormat(occurTime, DateLong,DateMmDdHhMm);
        }
        if (o instanceof ViewHolderOverPlanWater) {
            ((ViewHolderOverPlanWater) o).wellNameTv.setText(overUseWaterWarn.getWellName()) ;
            ((ViewHolderOverPlanWater) o).wellNoTv.setText(wellNo) ;
            ((ViewHolderOverPlanWater) o).occurTime.setText(ocTime) ;
            ((ViewHolderOverPlanWater) o).yearAddUpWater.setText(yearAddUpWater) ;
            ((ViewHolderOverPlanWater) o).yearPlanWater.setText(yearPlanWater) ;
        }
    }
    static class ViewHolderOverPlanWater {
        TextView wellNameTv;
        TextView occurTime ;
        TextView wellNoTv ;
        TextView yearAddUpWater ;
        TextView yearPlanWater ;
    }

}
