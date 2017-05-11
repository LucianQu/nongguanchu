package com.automic.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.DeviceStatus;
import com.automic.app.bean.WaterElecRatioWarn;
import com.automic.app.utils.DateFormatUtils;

import java.util.List;

import static com.automic.app.utils.DateFormatUtils.FormatType.DateLong;
import static com.automic.app.utils.DateFormatUtils.FormatType.DateMmDdHhMm;

/**
 * 水电配比报警
 * Created by qulus on 2017/4/12 0012.
 */

public class WarnWaterElecRatioAdapter extends BaseTtAdapter<WaterElecRatioWarn> {
    private Context m_context ;
    public WarnWaterElecRatioAdapter(Context context, List<WaterElecRatioWarn> mList) {
        super(context, mList);
        this.m_context = context ;
        this.setLayoutId(R.layout.item_listview_warn_waterelec_ratio);
        int[] ids = {R.id.warn_wEr_name,R.id.warn_wEr_time,R.id.warn_wEr_titleWellNo,  R.id.warn_wEr_currRatio, R.id.warn_wEr_abnomalRatio} ;
        String fields[] = {"wellNameTv", "occurTime","wellNoTv", "currentRatio", "normalRatio"} ;
        this.setClass(ViewHolderWaterElecRatio.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }
    @Override
    public void addDataToView(WaterElecRatioWarn waterElecRatioWarn, Object o) {
        String occurtime = waterElecRatioWarn.getOccurTime() ;
        String wellName = waterElecRatioWarn.getWellName();
        String wellNo = waterElecRatioWarn.getWellNo();
        String currentRatio = waterElecRatioWarn.getCurrentValue() ;
        String normalRatio = waterElecRatioWarn.getNormalValue();
        String occurTime=waterElecRatioWarn.getOccurTime() ;
        String ocTime=null;
        if (occurTime!=null&&!occurTime.equals("")){
            ocTime=  DateFormatUtils.ChangeFormat(occurTime, DateLong,DateMmDdHhMm);
        }
        if (o instanceof ViewHolderWaterElecRatio) {
            ((ViewHolderWaterElecRatio) o).wellNoTv.setText(wellName == null ? "" : "编码"+wellNo) ;
            ((ViewHolderWaterElecRatio) o).wellNameTv.setText(wellName == null ? "" : wellName) ;

//            if (null == wellName) {
//                ((ViewHolderWaterElecRatio) o).wellNoTv.setTextColor(Color.RED) ;
//            }
            ((ViewHolderWaterElecRatio) o).occurTime.setText(occurtime == null ? "" : ocTime) ;
//            if (null == occurtime) {
//                ((ViewHolderWaterElecRatio) o).occurTime.setTextColor(Color.RED) ;
//            }
            ((ViewHolderWaterElecRatio) o).currentRatio.setText(currentRatio == "" ? "" : currentRatio) ;
//            if ("" == currentRatio) {
                ((ViewHolderWaterElecRatio) o).currentRatio.setTextColor(Color.RED) ;
//            }
            ((ViewHolderWaterElecRatio) o).normalRatio.setText(normalRatio== "" ? "" : normalRatio) ;
//            if ("" == normalRatio) {
                ((ViewHolderWaterElecRatio) o).normalRatio.setTextColor(Color.BLACK) ;
//            }
        }
    }
    static class ViewHolderWaterElecRatio {
       TextView wellNameTv;
        TextView occurTime ;
        TextView wellNoTv ;
        TextView currentRatio ;
        TextView normalRatio ;
    }

}
