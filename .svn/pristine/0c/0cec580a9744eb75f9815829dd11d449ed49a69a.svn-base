package com.automic.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.automic.app.R;

/**
 * Created by sujingtai on 2017/3/28 0028.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        Button btnWarn=(Button)findViewById(R.id.btnWarn);
        btnWarn.setOnClickListener(this);
        Button btnWellInfo=(Button)findViewById(R.id.btnWellInfo);
        btnWellInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.btnWarn:
intent.setClass(MainActivity.this,JpushmessageListActivity.class);
                startActivity(intent);
            break;
            case R.id.btnWellInfo:
                intent.setClass(MainActivity.this,WellInfoActivity.class);
                startActivity(intent);
            break;
        }
    }
}
