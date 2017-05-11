package com.automic.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.DeviceStatus;
import com.automic.app.utils.DateFormatUtils;

import java.util.List;

import static com.automic.app.utils.DateFormatUtils.FormatType.DateLong;
import static com.automic.app.utils.DateFormatUtils.FormatType.DateMmDdHhMm;

/**
 * 设备工况报警
 * Created by qulus on 2017/4/12 0012.
 */

public class WarnDeviceStatusAdapter extends BaseTtAdapter<DeviceStatus> {
    private Context m_context ;
    private String m_rtuType = "" ;
    private String m_flowType = "" ;
    private String m_electType = "" ;
    public WarnDeviceStatusAdapter(Context context, List<DeviceStatus> mList) {
        super(context, mList);
        this.m_context = context ;
        this.setLayoutId(R.layout.item_listview_warn_devicestate);
        int[] ids = {R.id.warn_ds_titleWellName, R.id.warn_ds_time,R.id.warn_ds_titleWellNo,R.id.warn_ds_rtuStateType,R.id.warn_ds_flowMeStateType, R.id.warn_ds_electMeStateType} ;
        String fields[] = {"wellNameTv", "occurTime","wellNoTv", "rtuStateType", "flowMeStateType", "electMeStateType"} ;
        this.setClass(ViewHolderWarnDeviceStatus.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }

    @Override
    public void addDataToView(DeviceStatus deviceStatus, Object o) {
        for (int i = 0; i < deviceStatus.getStateType().size(); i++) {
            getStateType(deviceStatus, i) ;
        }
        String occurTime=deviceStatus.getOccurTime() ;
        String ocTime=null;
        if (occurTime!=null&&!occurTime.equals("")){
            ocTime=  DateFormatUtils.ChangeFormat(occurTime, DateLong,DateMmDdHhMm);
        }
        String wellNo = deviceStatus.getWellNo() ;
        if (o instanceof ViewHolderWarnDeviceStatus) {
            ((ViewHolderWarnDeviceStatus) o).wellNameTv.setText(deviceStatus.getWellName()) ;
            ((ViewHolderWarnDeviceStatus) o).wellNoTv.setText("编码"+wellNo) ;
            ((ViewHolderWarnDeviceStatus) o).occurTime.setText(ocTime) ;
            ((ViewHolderWarnDeviceStatus) o).rtuStateType.setText(m_rtuType) ;
            ((ViewHolderWarnDeviceStatus) o).flowMeStateType.setText(m_flowType) ;
            ((ViewHolderWarnDeviceStatus) o).electMeStateType.setText(m_electType) ;
        }
    }
    static class ViewHolderWarnDeviceStatus {
        TextView wellNameTv ;
        TextView wellNoTv ;
        TextView occurTime ;
        TextView rtuStateType ;
        TextView flowMeStateType ;
        TextView electMeStateType ;
    }

    private void getStateType(DeviceStatus deviceStatus,int position) {
        if (position >= deviceStatus.getStateType().size())
            return ;
        String str = deviceStatus.getStateType().get(position) ;
        if ("rtuGood".equals(str)) {
            m_rtuType ="正常" ;
        }else if ("rtuOffLine".equals(str)) {
            m_rtuType ="离线" ;
        }else if ("rtuLossElec".equals(str)) {
            m_rtuType ="220v市电停电" ;
        }else if ("rtuBatLow".equals(str)) {
            m_rtuType ="蓄电池电压低" ;
        }else if ("flowmeterGood".equals(str)) {
            m_flowType = "正常" ;
        }else if ("flowmeterBad".equals(str)) {
            m_flowType = "故障" ;
        }else if ("fmeterVoLow".equals(str)) {
            m_flowType = "电压低" ;
        }else if ("electMeterGood".equals(str)) {
            m_electType = "正常" ;
        }else if ("electMeterBad".equals(str)) {
            m_electType = "故障" ;
        }
    }
}
