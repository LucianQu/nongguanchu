package com.automic.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.automic.app.activity.BaseActivity;

/**
 * Created by qulus on 2017/3/28 0028.
 */

public class WellInfoHomePageActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellinfo_homepage);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void setLeftButtonVisibility(int visibility) {
        super.setLeftButtonVisibility(visibility);
    }

    @Override
    public void setRightButtonVisibility(int visibility) {
        super.setRightButtonVisibility(visibility);
    }

    @Override
    public void setRightButtonText(String text) {
        super.setRightButtonText(text);
    }

    @Override
    public void setOnRightClickListener(View.OnClickListener listener) {
        super.setOnRightClickListener(listener);
    }
}
