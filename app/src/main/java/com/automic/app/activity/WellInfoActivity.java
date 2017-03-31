package com.automic.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.ActionItem;
import com.automic.app.bean.GaodeMapWellInfo;
import com.automic.app.bean.WellInfo;
import com.automic.app.utils.LogUtils;
import com.automic.app.view.TitlePopup;
import com.automic.app.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.automic.app.bean.Constant.BASEIP;

/**
 * Created by sujingtai on 2017/3/24 0024.
 * 井信息界面
 */

public class WellInfoActivity extends BaseActivity {
    private Context m_Context;
    private TitlePopup m_titlePopup ;

    private TextView m_wellNo ;
    private TextView m_wellName ;
    private TextView m_wellUser ;

    private TextView m_wellAddress ;
    private RelativeLayout m_wellInfoMapTitle ;

    private TextView m_waterSurplus ;
    private TextView m_electSurplus ;
    private TextView m_yearElectConsum ;
    private TextView m_yearWaterConsum ;
    private TextView m_yearPlanWaterConsum ;
    private ImageView m_pumpOperate ;

    private ImageView m_rtuStatusIme ;
    private TextView m_rtuStatusTitle ;
    private TextView m_rtuStatus ;
    private ImageView m_flowStatusIme ;
    private TextView m_flowStatusTitle ;
    private TextView m_flowMeterStatus ;
    private ImageView m_electStatusIme ;
    private TextView m_electStatusTitle ;
    private TextView m_electMeterStatus ;

    private RelativeLayout m_wellInfoDeviceStatusTitle ;
    private LinearLayout wellinfo_deviceStatusContent;
    private List<WellInfo> m_wIBeanList ;

    public List<GaodeMapWellInfo> m_wIMapBeanList = new ArrayList<>() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellinfo_homepage);
        setupView();
    }

    private void setupView() {
        m_Context = WellInfoActivity.this ;
        m_wellNo = (TextView) findViewById(R.id.wellInfo_No) ;
        m_wellName = (TextView) findViewById(R.id.wellInfo_Name) ;
        m_wellUser = (TextView) findViewById(R.id.wellInfo_User) ;

        m_wellAddress = (TextView) findViewById(R.id.wellinfo_wellAddress) ;

        m_waterSurplus = (TextView) findViewById(R.id.wi_waterSurplus) ;
        m_electSurplus = (TextView) findViewById(R.id.wi_electSurplus) ;
        m_yearElectConsum = (TextView) findViewById(R.id.wi_yearElectConsum) ;
        m_yearWaterConsum = (TextView) findViewById(R.id.wi_yearWaterConsum) ;
        m_yearPlanWaterConsum = (TextView) findViewById(R.id.wi_yearPlanWaterConsum) ;

        m_rtuStatusIme = (ImageView) findViewById(R.id.wi_deviceStatusRtuIme) ;
        m_rtuStatusTitle = (TextView) findViewById(R.id.wi_deviceStatusRtuTitle) ;
        m_rtuStatus = (TextView) findViewById(R.id.wi_deviceStatusRtu) ;

        m_flowStatusIme = (ImageView) findViewById(R.id.wi_deviceStatusFlowIme) ;
        m_flowStatusTitle = (TextView) findViewById(R.id.wi_deviceStatusFlowTitle) ;
        m_flowMeterStatus = (TextView) findViewById(R.id.wi_deviceStatusFlowMeter) ;

        m_electStatusIme = (ImageView) findViewById(R.id.wi_deviceStatusElectIme) ;
        m_electStatusTitle = (TextView) findViewById(R.id.wi_deviceStatusElectTitle) ;
        m_electMeterStatus = (TextView) findViewById(R.id.wi_deviceStatusElectMeter) ;

        getWellDetailsData(null, "654003001006") ;



        /*m_rtuStatusIme.setVisibility(View.VISIBLE);
        m_rtuStatusTitle.setVisibility(View.VISIBLE);
        m_rtuStatus.setText("00000000");
        m_rtuStatus.setVisibility(View.VISIBLE);*/

        m_flowStatusIme.setVisibility(View.VISIBLE);
        m_flowStatusTitle.setVisibility(View.VISIBLE);
        m_flowMeterStatus.setText("RTU掉电/蓄电池电压低");
        m_flowMeterStatus.setVisibility(View.VISIBLE);

        m_electStatusIme.setVisibility(View.VISIBLE);
        m_electStatusTitle.setVisibility(View.VISIBLE);
        m_electMeterStatus.setText("流量计电压低");
        m_electMeterStatus.setVisibility(View.VISIBLE);

        m_rtuStatusIme.setVisibility(View.VISIBLE);
        m_rtuStatusTitle.setVisibility(View.VISIBLE);
        m_rtuStatus.setText("故障");
        m_rtuStatus.setVisibility(View.VISIBLE);

                //泵操作获取图标
        m_pumpOperate = (ImageView) findViewById(R.id.wi_pump_operate) ;
        m_pumpOperate.setOnClickListener(new onClickPumpOperateListener());

        //设置地图标题监听
        m_wellInfoMapTitle = (RelativeLayout) findViewById(R.id.wellinfo_wellMapTitle) ;
        m_wellInfoMapTitle.setOnClickListener(new onClickMapTitleListener());

        //设置设备状态标题监听
        m_wellInfoDeviceStatusTitle = (RelativeLayout) findViewById(R.id.wellinfo_deviceStatusTitle) ;
        m_wellInfoDeviceStatusTitle.setOnClickListener(new onClickDeviceStatusTitleListener());

        //设置设备状态内容监听
        wellinfo_deviceStatusContent = (LinearLayout) findViewById(R.id.wi_deviceStatusContent) ;
        wellinfo_deviceStatusContent.setOnClickListener(new onClickDeviceStatusContentListener());

        //设置+号按钮可见,并添加监听
        setRightButtonVisibility(View.VISIBLE);
        setOnRightClickListener(new onClickRightListener());

        //设置标题文本监听
        setOnTitleTextClickListener(new onClickTitleTextListener());

        //初始化+号视图,设置+号监听
        m_titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindows_initView() ;
        m_titlePopup.setItemOnClickListener(new onClickPopupListener());

    }

    /**
     *获取地图Bean List,进入地图
     */
    public void entryGaodeMap() {

        m_wIMapBeanList.clear();

        GaodeMapWellInfo wLBean = new GaodeMapWellInfo() ;
        wLBean.setWellNo("机井编码:" + m_wIBeanList.get(0).getWellNo());
        wLBean.setWellName("机井名称:" + m_wIBeanList.get(0).getWellName());
        wLBean.setWellUser("机井户主:" + m_wIBeanList.get(0).getAdmin());
        wLBean.setWellX(m_wIBeanList.get(0).getWellLat());
        wLBean.setWellY(m_wIBeanList.get(0).getWellLong());
        m_wIMapBeanList.add(wLBean);

        Intent intent = new Intent() ;
        intent.setClass(m_Context, GaodeMapActivity.class) ;
        intent.putParcelableArrayListExtra("GaodeMapWellInfo",
                (ArrayList<? extends Parcelable>) m_wIMapBeanList);
        startActivity(intent);

    }

    public void openNewActivity(Class<?> cls ,String name, String position) {
        Intent intent = new Intent() ;
        intent.setClass(m_Context,cls);
        if (null != position) {
            intent.putExtra(name, position) ;
        }
        startActivity(intent);
    }

    /**
     *标题文本监听
     */
    public class onClickTitleTextListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ToastUtils.show(m_Context, "onClickTitleTextListener");
        }
    }

    /**
     *泵操作监听
     */
    public class onClickPumpOperateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ToastUtils.show(m_Context, "开泵");
        }
    }

    /**
     *地图标题监听
     */
    public class onClickMapTitleListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            entryGaodeMap();
        }
    }

    /**
     *设备状态标题监听
     */
    public class onClickDeviceStatusTitleListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //ToastUtils.show(WellInfoActivity.this, "m_wellInfoDeviceStatusTitle");
            openNewActivity(DeviceStatusActivity.class, null, null) ;
        }
    }

    /**
     *设备状态内容监听
     */
    public class onClickDeviceStatusContentListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //ToastUtils.show(WellInfoActivity.this, "m_wellInfoDeviceStatusContent");
            openNewActivity(DeviceStatusActivity.class, null, null) ;
        }
    }

    /**
     *onClickRight + 号 监听
     */
    public class onClickRightListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            m_titlePopup.show(v);
        }
    }


    /**
     *popupWindows窗口点击项监听
     */
    public class onClickPopupListener implements TitlePopup.OnItemOnClickListener {
        @Override
        public void onItemClick(ActionItem item, int position) {
            {
                switch (position) {
                    case 0:
                        //开泵记录

                        openNewActivity(RecoderFragmentActivity.class,"RecoderFragmentActivity","0") ;
                        break;
                    case 1:
                        //开箱记录
                        openNewActivity(RecoderFragmentActivity.class,"RecoderFragmentActivity","1") ;
                        break;
                    case 2:
                        //充值记录
                        openNewActivity(RecoderFragmentActivity.class,"RecoderFragmentActivity","2") ;
                        break;
                    case 3:
                        //故障记录
                        openNewActivity(RecoderFragmentActivity.class,"RecoderFragmentActivity","3") ;
                        break;
                    case 4:
                        //地图定位
                        openNewActivity(GaodeMapActivity.class,null,null) ;
                        break;
                    case 5:
                        //年月用量
                        openNewActivity(RecoderFragmentActivity.class,"RecoderFragmentActivity","4") ;
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     *popupWindows初始化视图,添加item
     */
    private void popupWindows_initView() {
        m_titlePopup.addAction(new ActionItem(this, getResources().getString(R.string.openPumpRecord),
                R.mipmap.openpump_record));
        m_titlePopup.addAction(new ActionItem(this, getResources().getString(R.string.openBoxRecord),
                R.mipmap.openbox_record));
        m_titlePopup.addAction(new ActionItem(this, getResources().getString(R.string.rechargeRecord),
                R.mipmap.recharge_record));
        m_titlePopup.addAction(new ActionItem(this, getResources().getString(R.string.faultRecord),
                R.mipmap.fault_record));
        m_titlePopup.addAction(new ActionItem(this, getResources().getString(R.string.mapLocation),
                R.mipmap.map_location));
        m_titlePopup.addAction(new ActionItem(this, getResources().getString(R.string.yearMonthUse),
                R.mipmap.yearmonth_use));
    }

    /**
     * 获取机井详情数据
     * @param uId 用户Id
     * @param wNo 机井编码
     */
   private void getWellDetailsData(String uId, String wNo) {
       String url = BASEIP + "/well/queryWellInfo" ;
       RequestParams requestParams = new RequestParams(url) ;//网络请求参数实体,设置url
       if (null != uId) {
           requestParams.addBodyParameter("userId", uId);   //添加用户Id参数至Body
       }
       if (null != wNo) {
           requestParams.addBodyParameter("wellNo", wNo);   //添加机井编码参数至Body
       }
       if(uId == null && wNo == null) {
           ToastUtils.show(m_Context, "用户Id与机井编码为空!");
           return;
       }

       x.http().post(requestParams, new Callback.CommonCallback<String>() {
           @Override
           public void onSuccess(String result) {
               try {
                   JSONObject jsonObject = new JSONObject(result) ;//根据返回字段,创建JSONObject
                   int code = jsonObject.getInt("code") ;
                   if (code == 1) {
                       Gson gson = new Gson() ;
                       String data = jsonObject.getString("result") ;
                       LogUtils.e("wellInfoDetails", "返回数据" + data);
                       m_wIBeanList = gson.fromJson(data, new TypeToken<List<WellInfo>>(){}.getType()) ;
                       LogUtils.e("wIBeanList:","" + m_wIBeanList.size());
                       setWellInfoDetail();//设置机井详情界面
                   }else {
                       ToastUtils.show(m_Context, "无数据!");
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               } finally {

               }
           }

           @Override
           public void onError(Throwable ex, boolean isOnCallback) {
               LogUtils.e("sjt","error");
           }

           @Override
           public void onCancelled(CancelledException cex) {

           }

           @Override
           public void onFinished() {

           }
       });

   }

    private void setWellInfoDetail() {
        m_wellNo.setText(m_wIBeanList.get(0).getWellNo());
        m_wellName.setText(m_wIBeanList.get(0).getWellName());
        m_wellUser.setText(m_wIBeanList.get(0).getAdmin());
        m_wellAddress.setText(m_wIBeanList.get(0).getWellAddress());
        m_waterSurplus.setText(m_wIBeanList.get(0).getRemainWater());
        m_electSurplus.setText(m_wIBeanList.get(0).getRemainElect());
        m_yearElectConsum.setText(m_wIBeanList.get(0).getYearAddupElec());
        m_yearWaterConsum.setText(m_wIBeanList.get(0).getYearAddupWater());
        m_yearPlanWaterConsum.setText(m_wIBeanList.get(0).getYearPlanWater());
    }

}
