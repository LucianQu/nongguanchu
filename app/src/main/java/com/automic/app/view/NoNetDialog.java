package com.automic.app.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.automic.app.R;

/**
 * 类注释：
 * Created by sujingtai on 2017/4/17 0017 下午 2:31
 */

public class NoNetDialog extends AlertDialog {
    private Context mContext;
    private ImageView mImageView;
    private TextView mLoadingTv;
    private View.OnClickListener mListener;
    private Button mBtnLoad;



public NoNetDialog(Context context, View.OnClickListener listener){
    super(context);
    this.mContext=context;
    this.mListener=listener;
    this.setCanceledOnTouchOutside(false);
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_net_dialog);
        initView();
    }



    private void initView() {
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
        mBtnLoad = (Button)findViewById(R.id.btn_load_again);
        mBtnLoad.setOnClickListener(mListener);
    }

}
