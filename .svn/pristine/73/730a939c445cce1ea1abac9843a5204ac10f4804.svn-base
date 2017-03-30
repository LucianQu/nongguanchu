package com.automic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.view.AddPopWindow;
import com.automic.app.view.pullpushview.Pullable;


/**
 * Created by sujingtai on 2017/3/21 0021.
 */

public class BaseActivityWarn extends AppCompatActivity {
    private ViewGroup rootview;
    private RelativeLayout titlebarView;
    private ImageButton imgbtnBack;
    private Button imgbtnRight;
    private TextView titleText;
    private Button btnBottombar;
    public TextView tvwXiang;//第一级选择
    public TextView tvwCun;//第二级选择

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_warn);
        rootview = (ViewGroup) findViewById(R.id.rootview);
        initTitlebar();
    }

    private void initTitlebar() {
        // TODO Auto-generated method stub
        titlebarView = (RelativeLayout) findViewById(R.id.titlebar_view);
        imgbtnBack = (ImageButton) findViewById(R.id.imgbtn_back);
        RelativeLayout warnSelect=(RelativeLayout)findViewById(R.id.ll_base_warn_select);
        //二级选择
        tvwXiang = (TextView)warnSelect.findViewById(R.id.tvw_spinner_xiang);
        tvwCun = (TextView)warnSelect.findViewById(R.id.tvw_spinner_cun);
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(layoutResID, rootview);
    }

    public void onReturnClick(View v) {
        Activity parent = this.getParent();
        if (parent != null) {
            parent.finish();
        } else {
            this.finish();
        }
    }

    protected void isShowTitleBar(boolean show) {
        if (show) {
            titlebarView.setVisibility(View.VISIBLE);
        } else {
            titlebarView.setVisibility(View.GONE);
        }
    }

    protected void setTitlebarBackground() {
        // R.color.titlebarbg
        titlebarView.setBackgroundResource(R.color.titlebarbg);
    }
    /*
    *设置titlebar的文字
     */
    protected void setTitlebar(String text) {
        if (text != null) {
            titleText.setText(text);
        }
    }
    /**
     * 设置左边按钮的显示状态
     *
     * @param visibility
     */
    public void setLeftButtonVisibility(int visibility) {
        imgbtnBack.setVisibility(visibility);
    }

}
