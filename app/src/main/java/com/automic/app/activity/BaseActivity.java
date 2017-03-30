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


/**
 * Created by sujingtai on 2017/3/21 0021.
 */

public class BaseActivity extends AppCompatActivity {
    private ViewGroup rootview;
    private RelativeLayout titlebarView;
    private ImageButton imgbtnBack;
    private Button imgbtnRight;
    private TextView titleText;
    private Button btnBottombar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        rootview = (ViewGroup) findViewById(R.id.rootview);
        initTitlebar();
    }

    private void initTitlebar() {
        // TODO Auto-generated method stub
        titlebarView = (RelativeLayout) findViewById(R.id.titlebar_view);
        imgbtnBack = (ImageButton) findViewById(R.id.imgbtn_back);
        titleText = (TextView) findViewById(R.id.tvw_title);
        imgbtnRight = (Button) findViewById(R.id.imgbtn_right);
        setRightButtonVisibility(View.INVISIBLE);
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

    /**
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

    /**
     * 设置右边按钮的显示状态
     *
     * @param visibility
     */
    public void setRightButtonVisibility(int visibility) {
        imgbtnRight.setVisibility(visibility);
    }

    public void setRightButtonText(String text) {
        imgbtnRight.setVisibility(View.VISIBLE);
        if (text != null) {
            imgbtnRight.setText(text);
        }
    }

    /**
     * 设置右边按钮的点击事件（返回事件）
     *
     * @param listener
     */
    public void setOnRightClickListener(View.OnClickListener listener) {
        imgbtnRight.setVisibility(View.VISIBLE);
        imgbtnRight.setOnClickListener(listener);
    }
    /**
     * 设置标题的点击事件（返回事件）
     *
     * @param listener
     */
    public void setOnTitleTextClickListener(View.OnClickListener listener) {
        titleText.setVisibility(View.VISIBLE);
        titleText.setOnClickListener(listener);
    }
}
