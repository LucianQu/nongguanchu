package com.automic.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.WaterEleclessWarn;
import com.automic.app.utils.DateFormatUtils;

import java.util.List;

import static com.automic.app.utils.DateFormatUtils.FormatType.DateLong;
import static com.automic.app.utils.DateFormatUtils.FormatType.DateMmDdHhMm;

/**
 * 水电不足报警
 * Created by qulus on 2017/4/12 0012.
 */

public class WarnWaterEleclessAdapter extends BaseTtAdapter<WaterEleclessWarn> {
    private Context m_context ;
    public WarnWaterEleclessAdapter(Context context, List<WaterEleclessWarn> mList) {
        super(context, mList);
        this.m_context = context ;
        this.setLayoutId(R.layout.item_listview_warn_waterelec_less);
        int[] ids = {R.id.warn_wEl_wellName, R.id.warn_wEl_time,R.id.warn_wEl_titleWellNo, R.id.warn_wEl_reaminWater, R.id.warn_wEl_remainElec} ;
        String fields[] = {"wellNameTv","occurTime","wellNoTv","remainWater", "remainElect"} ;
        this.setClass(ViewHolderWarnWaterElecless.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }

    @Override
    public void addDataToView(WaterEleclessWarn waterEleclessWarn, Object o) {
        String wellNo = waterEleclessWarn.getWellNo() ;
        String remainWater = waterEleclessWarn.getRemainWater() ;
        String remainElec = waterEleclessWarn.getRemainElect() ;
        String occurTime=waterEleclessWarn.getOccurTime() ;
        String ocTime=null;
        if (occurTime!=null&&!occurTime.equals("")){
            ocTime=  DateFormatUtils.ChangeFormat(occurTime, DateLong,DateMmDdHhMm);
        }
        if (o instanceof ViewHolderWarnWaterElecless) {
            ((ViewHolderWarnWaterElecless) o).wellNoTv.setText("编码"+wellNo) ;
            ((ViewHolderWarnWaterElecless) o).wellNameTv.setText(waterEleclessWarn.getWellName()) ;
            ((ViewHolderWarnWaterElecless) o).occurTime.setText(ocTime) ;
            ((ViewHolderWarnWaterElecless) o).remainWater.setText(remainWater) ;
            ((ViewHolderWarnWaterElecless) o).remainElect.setText(remainElec) ;
        }
    }
    static class ViewHolderWarnWaterElecless {
        TextView wellNoTv ;
        TextView wellNameTv ;
        TextView occurTime ;
        TextView remainWater ;
        TextView remainElect ;
    }
}
