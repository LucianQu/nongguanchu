package com.automic.app.fragment;


import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.Constant;
import com.automic.app.bean.UseWaterElec;
import com.automic.app.utils.AppUtils;
import com.automic.app.utils.DateFormatUtils;
import com.automic.app.utils.LogUtils;
import com.automic.app.utils.ToastUtils;
import com.automic.app.view.OneDatePickerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.db.DbModelSelector;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public class YearMonthCountFragment extends BaseFragment implements View.OnClickListener {

    private ColumnChartView chartWater;
    private ColumnChartView chartElec;
//    private ColumnChartData data;


    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private TextView tvwYear;
    private TextView tvwMonth;
    private LinearLayout llUseWater;
    private LinearLayout llUseElec;
    private LinearLayout llWEV;
    private TextView tvwWaterChartBar;
    private TextView tvwElecChartBar;
    private static int WATER = 1;
    private static int ELEC = 0;
private List<UseWaterElec> mList=new ArrayList<UseWaterElec>();
    private TextView tvwDate;
private String type="year";//默认为按年选择
    private  String text;//选择时间控件输出
    private String contentHttpRequest;//http需要按接口格式传
    private TextView tvUseWaterValue;
    private TextView tvUseElecValue;
    private TextView tvWEValue;
    private String currentWellNo;

    @Override
    public void initLayout() {
        layoutId = R.layout.fragment_year_moth_count;
    }

    @Override
    public void initStatus() {

        LinearLayout llSelect = (LinearLayout) findViewById(R.id.ll_select_year_month);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(39, 10, 39, 0);
//
//        llSelect.setLayoutParams(lp);

        tvwYear = (TextView) llSelect.findViewById(R.id.tvKey);
        tvwYear.setText("逐月用水电量");
        tvwYear.setOnClickListener(this);
        tvwMonth = (TextView) llSelect.findViewById(R.id.tvVlue);
        tvwMonth.setOnClickListener(this);
        tvwMonth.setText("逐日用水电量");
        //选择时间
        LinearLayout llDateSelect=(LinearLayout)findViewById(R.id.ll_select_type);
        llDateSelect.setOnClickListener(this);
        tvwDate = (TextView)findViewById(R.id.tvw_spinner_wcun);
        //统计表
        LinearLayout llWater = (LinearLayout) findViewById(R.id.chart_use_water);
        tvwWaterChartBar = (TextView) llWater.findViewById(R.id.tvw_hellochart_bar);
        chartWater = (ColumnChartView) llWater.findViewById(R.id.line_chart);

        LinearLayout llElec = (LinearLayout) findViewById(R.id.chart_use_elec);
        tvwElecChartBar = (TextView) llElec.findViewById(R.id.tvw_hellochart_bar);
        chartElec = (ColumnChartView) llElec.findViewById(R.id.line_chart);

        chartWater.setOnValueTouchListener(new ValueTouchWaterListener());
        chartElec.setOnValueTouchListener(new ValueTouchEleListener());
        initUseWaterElecLayout();
        setBackgroundSelect(0);
        currentWellNo = mActivity.getCurrentWellNo();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mActivity.getNetState()!= AppUtils.TYPE_NET_WORK_DISABLED){
            //getUseWaterElecCount();
            //queryWaterAndElecCount(currentWellNo,"year","2017");
            //queryWaterAndElecCount("654003001006","year","2017");
            if (!isSelectedDate&&contentHttpRequest==null){
                LogUtils.e("sjt","contentHttpRequest==="+contentHttpRequest);
                defaultYMData(1);//按年查询
            }else if (isSelectedDate&&contentHttpRequest!=null){
                queryWaterAndElecCount(currentWellNo,type,contentHttpRequest);
            }

        }
    }

    /**
     * 给布局中的色块上色
     */
    private void initUseWaterElecLayout() {

        llUseWater = (LinearLayout) findViewById(R.id.ll_useWater);
        //llUseWater.setBackgroundColor(getResources().getColor(R.color.bg_blue));
        //llUseWater.setBackgroundResource(R.drawable.shape_top2_blue);
        llUseWater.setBackgroundResource(R.drawable.shape_top2_white);
        //用水表头
        //tvwWaterChartBar.setBackgroundColor(getResources().getColor(R.color.bg_blue));
        tvwWaterChartBar.setBackgroundResource(R.drawable.shape_top2_blue);
        //
        llUseElec = (LinearLayout) findViewById(R.id.ll_useElec);
        //llUseElec.setBackgroundColor(getResources().getColor(R.color.bg_red_less));
        llUseElec.setBackgroundColor(getResources().getColor(R.color.white));
        //用电表头
        //tvwElecChartBar.setBackgroundColor(getResources().getColor(R.color.bg_red_less));
        tvwElecChartBar.setBackgroundResource(R.drawable.shape_top2_pink);
        //
        llWEV = (LinearLayout) findViewById(R.id.waterElecValue);
        //llWEV.setBackgroundColor(getResources().getColor(R.color.white));
        llWEV.setBackgroundResource(R.drawable.shape_bottom2_white);
        //修改水电配比颜色和字体对齐方式
        ((TextView) llWEV.findViewById(R.id.tvKey)).setTextColor(getResources().getColor(R.color.black));
        ((TextView) llWEV.findViewById(R.id.tvVlue)).setTextColor(getResources().getColor(R.color.black));
        ((TextView) llWEV.findViewById(R.id.tvKey)).setGravity(Gravity.RIGHT);
        ((TextView) llWEV.findViewById(R.id.tvVlue)).setGravity(Gravity.LEFT);
        //
        ((TextView) llUseElec.findViewById(R.id.tvKey)).setTextColor(getResources().getColor(R.color.black));
        ((TextView) llUseElec.findViewById(R.id.tvVlue)).setTextColor(getResources().getColor(R.color.black));
        ((TextView) llUseElec.findViewById(R.id.tvKey)).setGravity(Gravity.RIGHT);
        ((TextView) llUseElec.findViewById(R.id.tvVlue)).setGravity(Gravity.LEFT);
        //
        ((TextView) llUseWater.findViewById(R.id.tvKey)).setTextColor(getResources().getColor(R.color.black));
        ((TextView) llUseWater.findViewById(R.id.tvVlue)).setTextColor(getResources().getColor(R.color.black));
        ((TextView) llUseWater.findViewById(R.id.tvKey)).setGravity(Gravity.RIGHT);
        ((TextView) llUseWater.findViewById(R.id.tvVlue)).setGravity(Gravity.LEFT);
    }

    /**
     * @param value 为0时，选年变绿，为1时，选月变绿
     */
    private void setBackgroundSelect(int value) {
        if (value == 0) {
            type="year";
           // tvwYear.setBackgroundColor(getResources().getColor(R.color.titlebar_green));
            tvwYear.setBackgroundResource(R.drawable.shape_left_press_green);
            tvwYear.setTextColor(getResources().getColor(R.color.white));
           // tvwMonth.setBackgroundColor(getResources().getColor(R.color.white));
            tvwMonth.setBackgroundResource(R.drawable.shape_right_unpress_green);
            tvwMonth.setTextColor(getResources().getColor(R.color.background_dark_gray));
//
            ((TextView) llUseWater.findViewById(R.id.tvKey)).setText("年用水量: ");
            ((TextView) llUseElec.findViewById(R.id.tvKey)).setText("年用电量: ");
            ((TextView) llWEV.findViewById(R.id.tvKey)).setText("水电配比: ");
//
            tvwWaterChartBar.setText("逐月用水量图");
            tvwElecChartBar.setText("逐月用电量图");
        } else if (value == 1) {
            type="month";
           // tvwMonth.setBackgroundColor(getResources().getColor(R.color.titlebar_green));
            tvwMonth.setBackgroundResource(R.drawable.shape_right_press_green);
            tvwMonth.setTextColor(getResources().getColor(R.color.white));
            //tvwYear.setBackgroundColor(getResources().getColor(R.color.white));
            tvwYear.setBackgroundResource(R.drawable.shape_left_unpress_green);
            tvwYear.setTextColor(getResources().getColor(R.color.background_dark_gray));
//
            ((TextView) llUseWater.findViewById(R.id.tvKey)).setText("月用水量: ");
            ((TextView) llUseElec.findViewById(R.id.tvKey)).setText("月用电量: ");
            ((TextView) llWEV.findViewById(R.id.tvKey)).setText("水电配比: ");
            //
            tvwWaterChartBar.setText("逐日用水量图");
            tvwElecChartBar.setText("逐日用电量图");
        }
        //初始化用水电和配比的值
        tvUseWaterValue = ((TextView) llUseWater.findViewById(R.id.tvVlue));//年月用水量的值
        tvUseElecValue = ((TextView) llUseElec.findViewById(R.id.tvVlue));//年月用电量
        tvWEValue = ((TextView) llWEV.findViewById(R.id.tvVlue));//年月用水电配比

    }

    /**
     * @param list 用水电量数据
     * @param wAndElType water elec
     * @return
     */
    private ColumnChartData generateData(List<UseWaterElec> list, int wAndElType) {
        int numSubcolumns = 1;
        int numColumns = list.size();
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
//设置y轴数据
        for (UseWaterElec useWaterElec : list) {
            List<SubcolumnValue> values = new ArrayList<SubcolumnValue>();
//            for (int j = 0; j < numSubcolumns; ++j) {
//            }
            if (wAndElType == WATER&&useWaterElec.getWaterCount()!=null) {
            float num=    Float.parseFloat(useWaterElec.getWaterCount());
                values.add(new SubcolumnValue(num/10000, Color.parseColor("#65b0e7")));
            }
            if (wAndElType == ELEC&&useWaterElec.getElecCount()!=null) {
                float numElec=    Float.parseFloat(useWaterElec.getElecCount());
                values.add(new SubcolumnValue(numElec/10000, Color.parseColor("#e6437a")));
            }
            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }
        //填充x轴数据
        ArrayList<AxisValue> axisValues = new ArrayList<AxisValue>();//x坐标轴
        for (int i = 0; i < numColumns; i++) {
            axisValues.add(new AxisValue(i).setLabel(list.get(i).getDate()));
        }
        ColumnChartData data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);//y轴
            if (hasAxesNames) {
                //axisX.setName("Axis X");
                if (wAndElType==WATER)
                axisY.setName("单位：万吨");
                if (wAndElType==ELEC)
                    axisY.setName("单位：万度");
            }
            data.setAxisXBottom(axisX);
            axisX.setTextSize(10);// 设置X轴文字大小
            axisX.setLineColor(Color.WHITE);
            axisY.setLineColor(Color.WHITE);
            axisX.setTextColor(Color.BLACK);// 设置X轴文字颜色
            axisY.setTextColor(Color.BLACK);// 设置y轴文字颜色
            axisX.setHasLines(false);// 是否显示X轴网格线
            axisY.setHasLines(false);// 是否显示Y轴网格线
            axisX.setValues(axisValues);
            data.setAxisYLeft(axisY);
            ArrayList<AxisValue> axisValuesY=new ArrayList<AxisValue>();
            for (int i=0;i<800;i+=200){
                axisValuesY.add(new AxisValue(i).setValue(i));
            }
            axisY.setValues(axisValuesY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        return data;


    }

    /**
     * 假数据
     */
    private void getUseWaterElecCount() {
        List<UseWaterElec> list = new ArrayList<UseWaterElec>();
//    for (int i=0;i<9;i++){
//list.add(new UseWaterElec("2017-1","345","456","1:1.5"));
//    }
//        list.add(new UseWaterElec("2017-1", "345", "156", "1:1.5"));
//        list.add(new UseWaterElec("2017-2", "645", "556", "1:1.5"));
//        list.add(new UseWaterElec("2017-3", "745", "656", "1:1.5"));
//        list.add(new UseWaterElec("2017-4", "845", "756", "1:1.5"));
//        list.add(new UseWaterElec("2017-5", "645", "556", "1:1.5"));
//        list.add(new UseWaterElec("2017-6", "545", "456", "1:1.5"));
//        list.add(new UseWaterElec("2017-7", "245", "256", "1:1.5"));
//        list.add(new UseWaterElec("2017-8", "745", "656", "1:1.5"));
//        list.add(new UseWaterElec("2017-9", "845", "756", "1:1.5"));
//        list.add(new UseWaterElec("2017-10", "645", "556", "1:1.5"));
//        list.add(new UseWaterElec("2017-11", "545", "456", "1:1.5"));
//        list.add(new UseWaterElec("2017-12", "245", "256", "1:1.5"));
        list.add(new UseWaterElec("1", "345", "156", "1:1.5"));
        list.add(new UseWaterElec("2", "645", "556", "1:1.5"));
        list.add(new UseWaterElec("3", "745", "656", "1:1.5"));
        list.add(new UseWaterElec("4", "845", "756", "1:1.5"));
        list.add(new UseWaterElec("5", "645", "556", "1:1.5"));
        list.add(new UseWaterElec("6", "545", "456", "1:1.5"));
        list.add(new UseWaterElec("7", "245", "256", "1:1.5"));
        list.add(new UseWaterElec("8", "745", "656", "1:1.5"));
        list.add(new UseWaterElec("9", "845", "756", "1:1.5"));
        list.add(new UseWaterElec("10", "645", "556", "1:1.5"));
        list.add(new UseWaterElec("11", "545", "456", "1:1.5"));
        list.add(new UseWaterElec("12", "245", "256", "1:1.5"));
//
        list.add(new UseWaterElec("13", "345", "156", "1:1.5"));
        list.add(new UseWaterElec("14", "11645", "11556", "1:1.5"));
        list.add(new UseWaterElec("15", "745", "656", "1:1.5"));
        list.add(new UseWaterElec("16", "845", "756", "1:1.5"));
        list.add(new UseWaterElec("17", "645", "556", "1:1.5"));
        list.add(new UseWaterElec("18", "545", "456", "1:1.5"));
        list.add(new UseWaterElec("19", "245", "256", "1:1.5"));
        list.add(new UseWaterElec("20", "745", "656", "1:1.5"));
        list.add(new UseWaterElec("21", "845", "756", "1:1.5"));
        list.add(new UseWaterElec("22", "645", "556", "1:1.5"));
        list.add(new UseWaterElec("23", "545", "456", "1:1.5"));
        list.add(new UseWaterElec("24", "245", "256", "1:1.5"));
        list.add(new UseWaterElec("25", "745", "656", "1:1.5"));
        list.add(new UseWaterElec("26", "845", "756", "1:1.5"));
        list.add(new UseWaterElec("27", "645", "556", "1:1.5"));
        list.add(new UseWaterElec("28", "545", "456", "1:1.5"));
        list.add(new UseWaterElec("29", "245", "256", "1:1.5"));
        list.add(new UseWaterElec("30", "545", "456", "1:1.5"));
        list.add(new UseWaterElec("31", "245", "256", "1:1.5"));
        chartWater.setColumnChartData(generateData(list, WATER));
        chartWater.setZoomEnabled(false);
        chartElec.setZoomEnabled(false);
        chartElec.setColumnChartData( generateData(list,ELEC));
    }

    /**
     * 按时间类型查询用水电量
     * @param wellNo
     * @param type
     * @param dateValue
     */
    private void queryWaterAndElecCount(String wellNo, final String type, String dateValue){
        LogUtils.e("sjt","数据为"+type+"   "+wellNo+"   "+"    "+dateValue);
        String url= Constant.BASEIP+"/well/queryWaterAndElecCount";
        RequestParams requestParams=new RequestParams(url);
        requestParams.addBodyParameter("wellNo",wellNo);
        requestParams.addBodyParameter("type",type);
        requestParams.addBodyParameter("dateValue",dateValue);
        requestParams.addBodyParameter("userId","admin");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json=new JSONObject(result);
                    if (json.getInt("code")==1){
                        String data=json.getString("result");
                    mList=new Gson().fromJson(data,new TypeToken<List<UseWaterElec>>(){}.getType());
                        if (mList.size()!=0){
                            chartWater.setColumnChartData(generateData(mList, WATER));
                        chartWater.setZoomEnabled(false);
                        chartElec.setZoomEnabled(false);
                        chartElec.setColumnChartData( generateData(mList,ELEC));
                            chartWater.setVisibility(View.VISIBLE);//去除无数据背景
                            chartElec.setVisibility(View.VISIBLE);
                            //统计数据
                            getCountData(mList,type);
                        }

                    }else {
                        //ToastUtils.show(mActivity,"暂无数据");
                        chartWater.setVisibility(View.INVISIBLE);//去除无数据背景
                        chartElec.setVisibility(View.INVISIBLE);
                    }
                    LogUtils.e("sjt","mList数据为"+mList.size());
//                    if (mList.size()!=0){
//                        getCountData(mList,type);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtils.e("sjt","异常数据为----"+e);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e("sjt","异常为"+ex+"isOnCallback----"+isOnCallback);
                chartWater.setVisibility(View.INVISIBLE);//去除无数据背景
                chartElec.setVisibility(View.INVISIBLE);
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
     * 按type获取总量，比如全年的或者全月的
     * @param mList
     * @param type
     */
    private void getCountData(List<UseWaterElec> mList, String type) {
       String useWater=null;
        String useElec=null;
        float countWater=0.0f;
        float countElec=0.0f;
      //  if ("year".equals(type)){
            for (UseWaterElec useWE:mList){
                useWater= useWE.getWaterCount();
                useElec=useWE.getElecCount();
                if (useWater!=null&&!useWater.equals("")){
                    countWater+=Float.parseFloat(useWater);
                }
                if (useElec!=null&&!useElec.equals("")){
                    countElec+=Float.parseFloat(useElec);
                }
            }
            //设置值
        if(countElec>10000.0)
            tvUseElecValue.setText(String.valueOf(countElec/10000)+"度");
        else
            tvUseElecValue.setText(String.valueOf(countElec)+"度");
        if (countWater>10000.0)
            tvUseWaterValue.setText(String.valueOf(countWater/10000)+"吨");
        else
            tvUseWaterValue.setText(String.valueOf(countWater)+"吨");
            //防止水电配比，电为0
            if (countElec!=0.0){
                tvWEValue.setText(String.valueOf(countWater/countElec));
            }
       // }
    }
private boolean isSelectedDate=false;
    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.tvKey:
                setBackgroundSelect(0);
                if (!isSelectedDate)
                defaultYMData(1);
                break;
            case R.id.tvVlue:
                setBackgroundSelect(1);
                if (!isSelectedDate)
                defaultYMData(0);
                break;
            case R.id.ll_select_type:
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new OneDatePickerDialog(mActivity, 0, new OneDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                        if (type.equals("year")){
                            text= String.format("%d年", startYear);
                            contentHttpRequest = String.format("%d",startYear);
                        }else if (type.equals("month")){
                            text = String.format("%d年%d月", startYear,startMonthOfYear + 1);
                            contentHttpRequest=String.format("%d-%02d",startYear,startMonthOfYear + 1);
                        }
                        tvwDate.setText(text);
                        isSelectedDate=true;
                        LogUtils.e("sjt","数据为"+contentHttpRequest);
                        queryWaterAndElecCount(currentWellNo,type,contentHttpRequest);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
        }
    }

    /**
     * 图标选择监听
     */
    private class ValueTouchWaterListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            float num=(float) (Math.round((value.getValue()*10000)*100))/100;
            ToastUtils.show(getActivity(),"当前数据："+num+"吨");
            //   Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
    private class ValueTouchEleListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            float num=(float) (Math.round((value.getValue()*10000)*100))/100;
            ToastUtils.show(getActivity(),"当前数据："+num+"度");
            //   Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
    /**
     * 获取默认的年月数据
     * @param type type 1 year
     *             type 0 month
     */
    private void defaultYMData(int type){
        String contentHttpRequest=null;
        String date=DateFormatUtils.getNowTimeYM();
        String[]dates=  date.split("-");
        int year=Integer.parseInt(dates[0]);
        int month=Integer.parseInt(dates[1]);
        if (type==1){
            tvwDate.setText(String.format("%d年", year));
            contentHttpRequest=String.valueOf(year);
        }else if (type==0){
            tvwDate.setText(String.format("%d年%d月",year,month));
            contentHttpRequest =String.format("%d-%02d",year,month);
        }
        LogUtils.e("sjt","网络时间请求数据为"+contentHttpRequest);
        queryWaterAndElecCount(currentWellNo,type==1?"year":"month",contentHttpRequest);
    }
}
