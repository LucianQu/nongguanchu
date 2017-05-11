package com.automic.app.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.utils.PhoneUtils;

/**
 * 类注释：
 * Created by sujingtai on 2017/4/17 0017 下午 2:31
 */

public class CustomProgressDialog extends ProgressDialog {
    private Context mContext;
    private ImageView mImageView;
    private String mLoadingTip;
    private TextView mLoadingTv;
    private int count = 0;
    private String oldLoadingTip;
    private int mResid;
    private AnimationDrawable mAnimation;

//    public CustomProgressDialog(Context context) {
//        super(context);
//        this.mContext=context;
//        setCanceledOnTouchOutside(true);
//    }

    public CustomProgressDialog(Context context, String content, int id) {
        super(context);
        this.mContext = context;
        this.mResid = id;
        this.mLoadingTip = content;
        this.
        setCanceledOnTouchOutside(false);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        initView();
        initData();
    }

    private void initData() {
        mImageView.setBackgroundResource(mResid);
        mLoadingTv.setText(mLoadingTip);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();

            }
        });
    }


//    public void setContent(String str) {
//        mLoadingTv.setText(str);
//    }

    private void initView() {
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);

    }
}
