package com.automic.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.automic.app.R;
import com.automic.app.bean.AreaXiangCun;
import com.automic.app.bean.WellInfo;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.SharepreferenceUtils;
import com.automic.app.utils.ToastUtils;
import com.automic.app.view.AddPopWindow;
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
 * Created by qulus on 2017/4/1 0001.
 */

public class GaodeMapActivity extends AppCompatActivity implements View.OnClickListener,
        AMap.OnMapLoadedListener,AMap.InfoWindowAdapter,AddPopWindow.Choice,AMap.OnMarkerClickListener{
    //地图
    private Context mContext ;
    private MapView mapView ;
    private AMap aMap ;
    private UiSettings mUiSettings ;
    private MarkerOptions markerOptions ;
    public static LatLng curWellPos; //LatLng latLng = new LatLng(39.906901,116.397972);
    public static String curWellNo;
    //镇村井
    private TextView m_tvXiang ;
    private AddPopWindow m_ppwXiang ;
    private TextView m_tvCun ;
    private AddPopWindow m_ppwCun ;
    private List<AreaXiangCun> m_areaBeanXiangList = new ArrayList<>();
    private List<AreaXiangCun> m_areaBeanCunList = new ArrayList<>();
    public  List<WellInfo> m_wLBeanList = new ArrayList<>();

    private List<String> dataCacheXiang ;

    private boolean isQueryX = false ;
    private int currentPage = 1 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        mContext = GaodeMapActivity.this ;
        initAmap(savedInstanceState) ;//地图初始化
        setupTitleListener() ;//设置地图title监听
        getExtrasMapData();//获取机井信息详情页面传送来的机井数据
        initXiangCunData() ;
        //getXiangCunData(true, "");//后台获取乡数据
    }

    /**
     *获取机井信息详情页面传送来的机井数据
     */
    private void getExtrasMapData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(null == bundle) {
            ToastUtils.show(GaodeMapActivity.this,"getExtrasMapData() == null!");
        }else {
            //m_wLBeanList = bundle.getParcelableArrayList("GaodeMapWellInfo") ;
            m_wLBeanList.clear();
            aMap.clear();
            m_wLBeanList = (List<WellInfo>) bundle.getSerializable("GaodeMapWellInfo") ;
            bundle.remove("GaodeMapWellInfo");
            addWellPositionMap(m_wLBeanList.get(0),true, true);
            locToCenter(new LatLng(m_wLBeanList.get(0).getWellLat(),m_wLBeanList.get(0).getWellLong()));
        }
    }

    /**
     *设置地图title监听
     */
    private void setupTitleListener() {
        m_tvXiang = (TextView) findViewById(R.id.tvw_spinner_xiang) ;
        m_tvXiang.setOnClickListener(this);
        m_tvCun = (TextView) findViewById(R.id.tvw_spinner_cun) ;
        m_tvCun.setOnClickListener(this);
    }

    /**
     *初始化地图
     */
    private void initAmap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map) ;
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(true);
            mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        }
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gaode_well_green));
                marker.showInfoWindow();
                for (int i = 0; i < aMap.getMapScreenMarkers().size(); i++) {
                    if (marker.equals(aMap.getMapScreenMarkers().get(i))) {
                        aMap.getMapScreenMarkers().get(i).
                                setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gaode_well_green));
                    }else {
                        aMap.getMapScreenMarkers().get(i).
                                setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gaode_well_red));
                    }
                }
                return true;
            }
        });
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent me) {
            }
        });
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                for (int i = 0; i < aMap.getMapScreenMarkers().size(); i++) {
                    aMap.getMapScreenMarkers().get(i).hideInfoWindow();
                }
            }
        });
    }

    /**
     *地图视图移动到指定位置
     */
    public void locToCenter(LatLng lnglat) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lnglat, 10));
    }

    /**
     *必须重写
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    /**
     *必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     *必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     *必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //beanList清空
        //获取地图数据
    }
    /**
     *获取自定义窗口
     */
    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = GaodeMapActivity.this.getLayoutInflater().
                inflate(R.layout.activity_gaodemap_infowindows, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        String title = marker.getTitle();
        String[] sArray = title.split(":");
        TextView wellNoText = ((TextView) view.findViewById(R.id.markerWellNo));
        TextView wellNameText = ((TextView) view.findViewById(R.id.markerWellName));
        TextView wellUserText = ((TextView) view.findViewById(R.id.markerWellUser));
        if (sArray[0] != null) {
            SpannableString noText = new SpannableString(sArray[0]);
            wellNoText.setText(noText);
        } else {
            wellNoText.setText("");
        }
        if (sArray[1] != null) {
            SpannableString nameText = new SpannableString(sArray[1]);
            wellNameText.setText(nameText);
        } else {
            wellNameText.setText("");
        }
        if (sArray[2] != null) {
            SpannableString userText = new SpannableString(sArray[2]);
            wellUserText.setText(userText);
        } else {
            wellUserText.setText("");
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
    /**
     *加载进地图时的位置
     */
    @Override
    public void onMapLoaded() {
        //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(, ), 10));
    }

    /**
     * @param v 点击返回
     */
    public void onReturnClick(View v) {
        Activity parent = this.getParent();
        if (parent != null) {
            parent.finish();
        } else {
            this.finish();
        }
    }

    /**
     *增加marker到地图
     */
    private void addWellPositionMap(WellInfo wellInfo, boolean isShow,boolean isGreen) {
        double lon = wellInfo.getWellLong() ;
        double lat = wellInfo.getWellLat() ;
        if (lat != 0 && lon != 0) {
            LatLng pt = new LatLng(lat, lon) ;
            markerOptions = new MarkerOptions() ;
            markerOptions.position(pt) ;
            markerOptions.draggable(false) ;
            markerOptions.title(wellInfo.getWellNo() +":" +
                                 wellInfo.getWellName()+":" +
                                wellInfo.getAdmin());
            markerOptions.snippet("Lat:" + wellInfo.getWellLat() + "\n" +
                                "Long:" + wellInfo.getWellLong()) ;
            if (isGreen)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.gaode_well_green)) ;
            else
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.gaode_well_red)) ;
            Marker marker = aMap.addMarker(markerOptions) ;
            marker.setObject(wellInfo);
            if (isShow) {
                marker.showInfoWindow();
            }
        }
    }
    /**
     *展示marker视图弹框,根据机井位置展示
     */
    private void showInfoWindow() {
        if (curWellPos != null) {
            locToCenter(curWellPos);
            for (int i = 0; i < aMap.getMapScreenMarkers().size(); i++) {
                WellInfo location = (WellInfo) aMap.getMapScreenMarkers().get(i).getObject() ;
                if (location.getWellNo().equals(curWellNo)) {
                    aMap.getMapScreenMarkers().get(i).showInfoWindow();
                }
            }
        }
    }

    /**
     *标题点击,获取数据
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvw_cancel:
                finish();
                break;
            case R.id.tvw_spinner_xiang:
                isQueryX = true;
                if ( dataCacheXiang != null){
                    if (dataCacheXiang.size() != 0 )
                        m_ppwXiang.showPopupWindow(m_tvXiang);
                }
                break;
            case R.id.tvw_spinner_cun:
                isQueryX = false ;
                if (m_areaBeanCunList.size() != 0 )
                    m_ppwCun.showPopupWindow(m_tvCun);
                break;
        }
    }

    /**
     *根据选择位置设置文本显示,并获取乡,村井数据
     */
    @Override
    public void senddata(String msg) {
        int position = Integer.parseInt(msg) ;
        if (isQueryX) {
            isQueryX = false ;
            m_tvXiang.setText(dataCacheXiang.get(position));
            String cData = SharepreferenceUtils.getCunInfoOneTime(mContext, msg) ;
            if (cData != null) {
                m_areaBeanCunList = new Gson().fromJson(cData, new TypeToken<List<AreaXiangCun>>() {}.getType()) ;
                if (m_areaBeanCunList.size() != 0) {
                    List<String> dataCacheCun = new ArrayList<String>() ;
                    for (AreaXiangCun area : m_areaBeanCunList) {
                        dataCacheCun.add(area.getArea()) ;
                    }
                    String xiangReleCun = dataCacheCun.get(0) ;
                    m_tvCun.setText(xiangReleCun);
                    m_ppwCun = new AddPopWindow(GaodeMapActivity.this, dataCacheCun) ;
                    m_ppwCun.setChoice(GaodeMapActivity.this);
                }
            }
            //getXiangCunData(false, m_areaBeanXiangList.get(position).getAreaId());
        }else {

            m_tvCun.setText(m_areaBeanCunList.get(position).getArea());
            getWellInfoData(m_areaBeanCunList.get(position).getAreaId());
        }
    }

    /**
     *从本地缓存里面获取乡数据,不需要网络请求
     */
    private void initXiangCunData() {
        String xData = SharepreferenceUtils.getXiangInfoOneTime(mContext) ;
        if (xData != null) {
            String[] data = xData.split(",") ;
            dataCacheXiang = new ArrayList<String>() ;
            for (int i = 0; i < data.length; i++) {
                dataCacheXiang.add(data[i]) ;
            }
            m_ppwXiang = new AddPopWindow(GaodeMapActivity.this, dataCacheXiang) ;
            m_ppwXiang.setChoice(GaodeMapActivity.this);
        }
    }

    /**
     *后台获取村井数据
     *
     */
    private void getWellInfoData(String areaCode) {
        String url = BASEIP + "/well/searchWellInfo" ;
        RequestParams requestParams = new RequestParams(url) ;
        requestParams.addBodyParameter("userId", "admin");
        requestParams.addBodyParameter("areaCode", areaCode);
        requestParams.addParameter("currentPage", currentPage);
        requestParams.addBodyParameter("count", "10");

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonResult = new JSONObject(result) ;
                    int code = jsonResult.getInt("code") ;
                    if (code == 1) {
                        Gson gson = new Gson() ;
                        String data = jsonResult.getString("result") ;
                        LogUtils.e("GaodeMapActivity_WellInfo", "返回数据" + data);
                        m_wLBeanList.clear();
                        aMap.clear();
                        m_wLBeanList = gson.fromJson(data,
                                new TypeToken<List<WellInfo>>() {}.getType()) ;
                        addWellMarkList();

                    }else {
                        ToastUtils.show(mContext,"井数据为空!");
                        m_wLBeanList.clear();
                        aMap.clear();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.show(mContext,"查询失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    /**
     *根据机井BeanList设置地图marker,并显示最后一个机井的信息到地图视图中央
     */
    private void addWellMarkList() {
        for (int i = 0; i < m_wLBeanList.size(); i++) {
            if (i != m_wLBeanList.size() - 1 )
                addWellPositionMap(m_wLBeanList.get(i), false, false);
            else
                addWellPositionMap(m_wLBeanList.get(i), false, true);
        }
        locToCenter(new LatLng(m_wLBeanList.get(m_wLBeanList.size()-1).getWellLat(),
                m_wLBeanList.get(m_wLBeanList.size()-1).getWellLong()));
        curWellPos = new LatLng(m_wLBeanList.get(m_wLBeanList.size()-1).getWellLat(),
                m_wLBeanList.get(m_wLBeanList.size()-1).getWellLong()) ;
        curWellNo = m_wLBeanList.get(m_wLBeanList.size()-1).getWellNo() ;
        showInfoWindow();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
