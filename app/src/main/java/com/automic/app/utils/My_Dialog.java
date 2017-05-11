package com.automic.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.automic.app.R;

/**
 * Created by qulus on 2017/4/8 0008.
 */

public class My_Dialog extends AlertDialog {

    public My_Dialog(Context context, int theme) {
        super(context, theme);
    }

    public My_Dialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        //requestWindowFeature(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    }
}
