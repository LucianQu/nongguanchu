package com.automic.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.automic.app.R;
import com.automic.app.bean.GaodeMapWellInfo;
import com.automic.app.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qulus on 2017/3/27 0027.
 */

public class GaodeMapActivity extends AppCompatActivity implements AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener,AMap.InfoWindowAdapter,AMap.OnMapClickListener {
    private String TAG = "GaodeMapActivity" ;
    private MapView m_mapView;
    private AMap m_aMap;
    private View m_markerInfoWindow = null;

    public GaodeMapWellInfo m_wLBean;

    private ArrayList<MarkerOptions> m_mOptionsList = new ArrayList<>();
    public List<GaodeMapWellInfo> m_wLBeanList = new ArrayList<>();
    private ArrayList<Marker> m_markerList = new ArrayList<>();

    private String m_wellNo = "";
    private String m_wellName = "";
    private String m_wellUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_show_map);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        m_wLBeanList = bundle.getParcelableArrayList("GaodeMapWellInfo");

        m_mapView = (MapView) findViewById(R.id.map);//初始化地图控件
        m_mapView.onCreate(savedInstanceState);//必须要写
        m_aMap = m_mapView.getMap();

        m_aMap.getUiSettings().setMyLocationButtonEnabled(false);
        m_aMap.setMyLocationEnabled(false);

        removeMarkerList();
        addMarkerList(WellLocatBeanList2MarkerOptionsList(m_wLBeanList, m_mOptionsList), m_wLBeanList.size() - 1);

        m_aMap.setOnMarkerClickListener(markerClickListener);
        m_aMap.setInfoWindowAdapter(infoWindowAdapter);
        m_aMap.setOnInfoWindowClickListener(infoWindowListener);

    }

    /**
     *@declare  把从其他activity获取的wellBeanList转换成标记集合
     *@param    wellBeanList,markerOptionsList
     *@return   标记集合;
     */
    public ArrayList<MarkerOptions> WellLocatBeanList2MarkerOptionsList(List<GaodeMapWellInfo> wellBeanList,
                                                                        ArrayList<MarkerOptions> markerOptionsList) {
        if (wellBeanList == null || wellBeanList.size() == 0) {
            Toast.makeText(GaodeMapActivity.this, "WellListBean集合为空!",Toast.LENGTH_SHORT).show();
            return null;
        }

        for (int i = 0; i < wellBeanList.size(); i++) {
            m_wLBean = wellBeanList.get(i);
            WellInfoWindowSet(m_wLBean.getWellNo(), m_wLBean.getWellName(),
                    m_wLBean.getWellUser());

            markerOptionsList.add(customMarker(m_wLBean.getWellNo(), m_wLBean.getWellName(),
                    R.mipmap.gaode_well_64_, m_wLBean.getWellX(),m_wLBean.getWellY()));

            LogUtils.e(TAG, "机井标记信息:" + "   机井编码 " + m_wellNo + ",   机井名称 " + m_wellName +",   用户 "
            + m_wellUser + ",   纬度 " + m_wLBean.getWellX() +",   经度 " + m_wLBean.getWellY());
        }
        return markerOptionsList;
    }

    /**
     *@declare  markerOptions属性设置
     *@param    title:标记的标题, snippet:标记内容, icon:标记图标, v:纬度 v1:经度
     *@return   标记
     */
    public MarkerOptions customMarker(String title, String snippet, int icon, double v, double v1) {
        MarkerOptions markerOptions = new MarkerOptions() ;
        markerOptions.position(new LatLng(v,v1)) ;//在地图上标记位置的经纬度值
        markerOptions.title(title) ;//点标记的标题
        markerOptions.snippet(snippet) ;//点标记的内容
        //markerOptions.perspective(true) ;
        markerOptions.visible(true);//点标记是否可见
        markerOptions.draggable(true) ;//点标记是否可拖拽
        //markerOptions.setFlat(true) ;//设置Marker贴地显示,可以双指下拉地图查看效果
        markerOptions.icon(BitmapDescriptorFactory.fromResource(icon)) ;
        //markerOptions.setInfoWindowOffset(30,0) ;
        return markerOptions;
    }

    /**
     *@declare  把marker list添加到地图中,并设置显示某个marker信息窗口
     *@param    markerOptionsList:marker list, defultMarkPosition: 默认marker位置
     *@return
     */
    public void addMarkerList(ArrayList<MarkerOptions> markerOptionsList , int defaultMarkPosition) {
        if (markerOptionsList == null) {
            return;
        }

        for (int i = 0; i < markerOptionsList.size(); i++) {
            m_markerList.add(m_aMap.addMarker(markerOptionsList.get(i)));
        }

        //m_markerList = m_aMap.addMarkers(markerOptionsList,true) ;
        setDefaultMarkDisplayInfo(defaultMarkPosition) ;
    }
    /**
     *@declare  设置默认marker显示信息窗口
     *@param    position:marker在marker集合中的位置
     *@return
     */
    public void setDefaultMarkDisplayInfo(int position) {
        if(position < 0)
            return;
        WellInfoWindowSet(m_markerList.get(position).getTitle(),m_markerList.get(position).getSnippet(),
                m_wLBeanList.get(position).getWellUser());


        m_markerList.get(position).showInfoWindow();

        m_aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(m_markerList.get(position).
                getPosition().latitude, m_markerList.get(position).getPosition().longitude), 10));

    }
    /**
     *@declare  移除地图上所有marker
     *@param
     *@return
     */
    public void removeMarkerList() {
        List<Marker> saveMarkerList = m_aMap.getMapScreenMarkers() ;
        if(saveMarkerList == null || saveMarkerList.size() <= 0)
            return;
        for (Marker marker : saveMarkerList) {
            marker.remove();
        }
    }
    /**
     *@declare  自定义marker信息窗口
     *@param    marker:当前marker
     *@return   自定义的信息窗口视图
     */
    AMap.InfoWindowAdapter infoWindowAdapter = new AMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            m_markerInfoWindow = GaodeMapActivity.this.getLayoutInflater().
                    inflate(R.layout.activity_gaodemap_infowindows,null) ;//自定义视图layout
            Render(m_markerInfoWindow) ;
            return m_markerInfoWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    };

    /**
     *@declare  获取机井信息Layout,并赋值
     *@param    view:m_markerInfoWindow
     *@return
     */
    public void Render(View view) {
        TextView wellNo = (TextView) view.findViewById(R.id.markerWellNo) ;
        TextView wellName = (TextView) view.findViewById(R.id.markerWellName) ;
        TextView wellUser = (TextView) view.findViewById(R.id.markerWellUser) ;

        wellNo.setText(m_wellNo);
        wellName.setText(m_wellName);
        wellUser.setText(m_wellUser);
    }

    /**
     *@declare  机井信息赋值
     *@param    wellNo:机井编码,wellName:机井名称,wellUser:机井户主
     *@return
     */
    public  void WellInfoWindowSet(String wellNo, String wellName, String wellUser) {
        m_wellNo = wellNo ;
        m_wellName = wellName ;
        m_wellUser = wellUser ;
    }

    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener(){
        @Override
        public boolean onMarkerClick(Marker marker) {
            WellInfoWindowSet(marker.getTitle(),marker.getSnippet(),getWellUser(marker)) ;
            return false ;
        }
    };

    /**
     *@declare  获取用户信息,wellNo放在marker Title中,wellName放在marker Snippet中,wellUser从wLBeanList中获取
     *@param
     *@return   用户
     */
    private String getWellUser(Marker marker){
        boolean isExist = false ;
        int markerPosition = 0;
        String userName ;
        for (int i = 0; i < m_markerList.size(); i++) {
            if(marker.equals(m_markerList.get(i))) {
                isExist = true ;
                markerPosition = i ;
            }
        }
        if(isExist) {
            userName = m_wLBeanList.get(markerPosition).getWellUser() ;
        }else {
            userName = "" ;
        }
        return userName ;
    }

    /**
     *@declare  信息窗口监听
     *@param
     */
    AMap.OnInfoWindowClickListener infoWindowListener = new AMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            //WellInfoWindowSet(marker.getTitle(),marker.getSnippet(),getWellUser(marker)) ;
        }
    };

    /**
     *@declare  onResume方法必须重写
     *@param
     */
    @Override
    protected void onResume() {
        super.onResume();
        m_mapView.onResume();
    }

    /**
     *@declare  onPause方法必须重写
     *@param
     */
    @Override
    protected void onPause() {
        super.onPause();
        m_mapView.onPause();
    }
    /**
     *@declare  onSaveInstanceState方法必须重写
     *@param
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        m_mapView.onSaveInstanceState(outState);
    }

    /**
     *@declare  onDestroy方法必须重写
     *@param
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_mapView.onDestroy();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        WellInfoWindowSet(marker.getTitle(),marker.getSnippet(),getWellUser(marker)) ;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        m_markerInfoWindow = GaodeMapActivity.this.getLayoutInflater().
                inflate(R.layout.activity_gaodemap_infowindows,null) ;//自定义视图layout
        Render(m_markerInfoWindow) ;
        return m_markerInfoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }

    @Override
    public void onMapClick(LatLng latLng) {}

    @Override
    public boolean onMarkerClick(Marker marker) { return false;}
}
