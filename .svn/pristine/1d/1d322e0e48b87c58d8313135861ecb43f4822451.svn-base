package com.automic.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.WellDeviceStatus;
import com.automic.app.utils.ToastUtils;

/**
 * Created by qulus on 2017/3/29 0029.
 */

public class DeviceStatusActivity extends BaseActivity {

    private LinearLayout m_rtuTiBackgd;
    private LinearLayout m_flowMTiBackgd;
    private LinearLayout m_electMTiBackgd;

    private TextView m_rtuTitle ;
    private TextView m_rtuNo ;
    private TextView m_rtuFactory ;
    private TextView m_rtuModel ;
    private TextView m_rtuFaultInfo ;

    private TextView m_flowMTitle;
    private TextView m_flowMNo;
    private TextView m_flowMFactory;
    private TextView m_flowMModel;
    private TextView m_flowMFaultInfo;

    private TextView m_electMTitle;
    private TextView m_electMNo;
    private TextView m_electMFactory;
    private TextView m_electMModel;
    private TextView m_electMFaultInfo;

    private WellDeviceStatus m_wdStatusBean ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicestatus);
        initData() ;
        setupView();
    }

    private void initData() {
        String[] stateType = new String[4];
        stateType[0] = "rtuBatLow" ;
        stateType[1] = "flowmeterGood" ;
        stateType[2] = "electMeterBad" ;
        stateType[3] = "pumpOff" ;
        String[] stateDetail = new String[4] ;
        stateDetail[0] = "1100006666:automic:AM2012:电池电压12V:信号差5" ;
        stateDetail[1] = "1100007777:automic:电磁流量计" ;
        stateDetail[2] = "1100008888:automic:决胜牌电表" ;
        stateDetail[3] = "1100009999:automic:水泵888" ;
        m_wdStatusBean = new WellDeviceStatus("设备状态井","设备状态编码","",stateType,stateDetail) ;
    }

    private void setupView() {
        m_rtuTiBackgd = (LinearLayout) findViewById(R.id.ds_rtuTitleBackground) ;
        m_flowMTiBackgd = (LinearLayout) findViewById(R.id.ds_flowMTitleBackground) ;
        m_electMTiBackgd = (LinearLayout) findViewById(R.id.ds_electMTitleBackground) ;
        m_rtuTitle = (TextView) findViewById(R.id.ds_rtuTitle) ;
        m_rtuNo = (TextView) findViewById(R.id.ds_rtuNo) ;
        m_rtuFactory = (TextView) findViewById(R.id.ds_rtuFactures) ;
        m_rtuModel = (TextView) findViewById(R.id.ds_rtuModel) ;
        m_rtuFaultInfo = (TextView) findViewById(R.id.ds_rtuFaultInfo) ;

        m_flowMTitle = (TextView) findViewById(R.id.ds_flowMeterTitle) ;
        m_flowMNo = (TextView) findViewById(R.id.ds_flowMeterNo) ;
        m_flowMFactory = (TextView) findViewById(R.id.ds_flowMeterFactures) ;
        m_flowMModel = (TextView) findViewById(R.id.ds_flowMeterModel) ;
        m_flowMFaultInfo = (TextView) findViewById(R.id.ds_flowMeterFaultInfo) ;

        m_electMTitle = (TextView) findViewById(R.id.ds_electMeterTitle) ;
        m_electMNo = (TextView) findViewById(R.id.ds_electMeterNo) ;
        m_electMFactory = (TextView) findViewById(R.id.ds_electMeterFactures) ;
        m_electMModel = (TextView) findViewById(R.id.ds_electMeterModel) ;
        m_electMFaultInfo = (TextView) findViewById(R.id.ds_electMeterFaultInfo) ;

        if("rtuGood".equals(m_wdStatusBean.getStateType()[0])) {
            m_rtuTiBackgd.setBackgroundResource(R.drawable.ds_title_background_black);
            //m_rtuTiBackgd.setBackground(getResources().getDrawable(R.drawable.ds_title_background_green));
            m_rtuTitle.setText(getResources().getString(R.string.rtuGood));
        }else if("rtuOffLine".equals(m_wdStatusBean.getStateType()[0])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[0].split(":");
            m_rtuTitle.setText(getResources().getString(R.string.rtuOffLine));
            adapterView(m_rtuTiBackgd, R.drawable.ds_title_background_gray,
                    m_rtuNo, m_rtuFactory, m_rtuModel, m_rtuFaultInfo,false,sArray);
        }else if("rtuLossElec".equals(m_wdStatusBean.getStateType()[0])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[0].split(":");
            m_rtuTitle.setText(getResources().getString(R.string.rtuLossElec));
            adapterView(m_rtuTiBackgd, R.drawable.ds_title_background_red,
                    m_rtuNo, m_rtuFactory, m_rtuModel, m_rtuFaultInfo,false,sArray);
        }else if("rtuBatLow".equals(m_wdStatusBean.getStateType()[0])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[0].split(":");
            m_rtuTitle.setText(getResources().getString(R.string.rtuBatLow));
            adapterView(m_rtuTiBackgd, R.drawable.ds_title_background_red,
                    m_rtuNo, m_rtuFactory, m_rtuModel, m_rtuFaultInfo,true,sArray);
        }

        if("flowmeterGood".equals(m_wdStatusBean.getStateType()[1])) {
            m_flowMTiBackgd.setBackground(getResources().getDrawable(R.drawable.ds_title_background_green));
            m_flowMTitle.setText(getResources().getString(R.string.flowmeterGood));
        }else if("flowmeterBad".equals(m_wdStatusBean.getStateType()[1])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[1].split(":");
            m_flowMTitle.setText(getResources().getString(R.string.flowmeterBad));
            adapterView(m_flowMTiBackgd, R.drawable.ds_title_background_red,
                    m_flowMNo, m_flowMFactory, m_flowMModel, m_flowMFaultInfo,false,sArray);

        }else if("fmeterVoLow".equals(m_wdStatusBean.getStateType()[1])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[1].split(":");
            m_flowMTitle.setText(getResources().getString(R.string.fmeterVoLow));
            adapterView(m_flowMTiBackgd, R.drawable.ds_title_background_red,
                    m_flowMNo, m_flowMFactory, m_flowMModel,m_flowMFaultInfo,true,sArray);
        }

        if("electMeterGood".equals(m_wdStatusBean.getStateType()[2])) {
            m_electMTiBackgd.setBackground(getResources().getDrawable(R.drawable.ds_title_background_green));
            m_electMTitle.setText(getResources().getString(R.string.electMeterGood));
        }else if("electMeterBad".equals(m_wdStatusBean.getStateType()[2])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[2].split(":");
            m_electMTitle.setText(getResources().getString(R.string.electMeterBad));
            adapterView(m_electMTiBackgd, R.drawable.ds_title_background_red,
                    m_electMNo, m_electMFactory, m_electMModel, m_electMFaultInfo,false,sArray) ;
        }

        if("pumpOff".equals(m_wdStatusBean.getStateType()[3])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[3].split(":");

        }else if("pumpOn".equals(m_wdStatusBean.getStateType()[3])) {
            String[] sArray = m_wdStatusBean.getStateDetail()[3].split(":");

        }
    }
    /**
     *适配界面
     */
    void adapterView(LinearLayout background,int imeid, TextView no, TextView factory,
                     TextView model,TextView defaultInfo, boolean isShow, String[] sArray) {
        String info = "" ;
        background.setBackground(getResources().getDrawable(imeid));
        no.setText(sArray[0]);
        factory.setText(sArray[1]);
        model.setText(sArray[2]);
        if(isShow) {
            defaultInfo.setVisibility(View.VISIBLE);
            if (sArray.length < 3) {
                ToastUtils.show(DeviceStatusActivity.this,"报警信息为空!");
            }else {
                for (int i = 3; i < sArray.length; i++) {
                    info += sArray[i] + "\n";
                }
            }
            defaultInfo.setText(info);
        }
    }
}
