package com.automic.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.adapter.WellInfoAdapter;
import com.automic.app.bean.WellInfo;
import com.automic.app.view.AddPopWindow;
import com.automic.app.view.pullpushview.PullToRefreshLayout;
import com.automic.app.view.pullpushview.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sujingtai on 2017/3/28 0028.
 */

public class SearchWellActivity extends AppCompatActivity implements View.OnClickListener,AddPopWindow.Choice{

    private PullableListView lv;
    private PullToRefreshLayout ppLayout;
    private TextView tvwXiang;
    private TextView tvwCun;
    private Context mContext;
    private AddPopWindow popWindowXiang;
    private AddPopWindow popWindowCun;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_well);
        setupView();
    }

    private void setupView() {
        mContext = SearchWellActivity.this;
        ppLayout = (PullToRefreshLayout)findViewById(R.id.pp_layout);
        ppLayout.setOnRefreshListener(new MyListener());
        lv = (PullableListView) ppLayout.findViewById(R.id.lv_well_search);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        List<WellInfo> mWellList=new ArrayList<WellInfo>();
        for (int i=0;i<9;i++){
            mWellList.add(new WellInfo("大宝井","233444323","李三三",0,"发电机房村",12.3,116.6,"56","78","77","34","907"));
        }
        WellInfoAdapter wellInfoAdapter=new WellInfoAdapter(mContext,mWellList);
        lv.setAdapter(wellInfoAdapter);
        wellInfoAdapter.notifyDataSetChanged();
//二级选择
        LinearLayout wellSelect=(LinearLayout)findViewById(R.id.ll_select);
        tvwXiang = (TextView)wellSelect.findViewById(R.id.tvw_spinner_wxiang);
        tvwXiang.setOnClickListener(this);
        tvwCun = (TextView)wellSelect.findViewById(R.id.tvw_spinner_wcun);
        tvwCun.setOnClickListener(this);
        String[]data={"1乡","2乡","3乡"};
        String[]dataCun={"1村","2村","3村"};
        popWindowXiang = new AddPopWindow(this,data);
        popWindowCun = new AddPopWindow(this,dataCun);
        popWindowXiang.setChoice(this);
        popWindowCun.setChoice(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tvw_cancel:
                break;
            case  R.id.tvw_spinner_wxiang:
popWindowXiang.showPopupWindow(tvwXiang);
                Log.e("sjt","Jin ru xiang");
                break;
            case  R.id.tvw_spinner_wcun:
popWindowCun.showPopupWindow(tvwCun);
                Log.e("sjt","Jin ru cun");
                break;

        }
    }

    @Override
    public void senddata(String msg) {

    }

    /*
  * 下拉和上拉刷新的监听器
  */
    private class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
//            if (type.equals("noInfo")) {
//                noInfoList.clear();
//            } else
//                abWaterInfoList.clear();
//            currentPage = 1;
//            getWarnDataFormEnType(type);
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！

                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }

            }.sendEmptyMessageDelayed(0, 500);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
//            currentPage++;
//            getWarnDataFormEnType(type);
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 500);
        }
    }
}
