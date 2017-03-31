package com.automic.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.automic.app.R;
import com.automic.app.utils.NormalLoadPictrue;
import com.automic.app.utils.ToastUtils;

/**
 * Created by qulus on 2017/3/31 0031.
 */

public class LoadPictrueActivity extends BaseActivity {
    private ImageView m_loadIme ;
    public String picUrl = "" ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadpicture);
        setupView() ;
    }
    private void setupView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(null == bundle) {
            ToastUtils.show(LoadPictrueActivity.this,"图片地址为空!");
        }else {
            picUrl = bundle.getString("LoadPictrueActivity");
            m_loadIme = (ImageView) findViewById(R.id.ob_imeload) ;
            new NormalLoadPictrue().getPicture(picUrl, m_loadIme);
        }

    }
}
