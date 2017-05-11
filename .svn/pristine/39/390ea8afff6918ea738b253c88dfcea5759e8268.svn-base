package com.automic.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.activity.OnOffInputPwActivity;
import com.automic.app.activity.WellInfoActivity;

import java.util.ArrayList;

/**
 * Created by qulus on 2017/4/10 0010.
 */

public class KeyboardUtil {
    private Activity m_Activity ;
    private KeyboardView m_keyboardView ;
    private Keyboard m_key_number ;

    private ArrayList<ImageView> m_listIme ;
    private String m_thisPwText = "" ;
    private EditText m_passWord ;
    private TextView m_pWerrorTv ;


    public KeyboardUtil(Activity activity) {
        this.m_Activity = activity ;
        m_key_number = new Keyboard(activity, R.xml.number_key) ;
        m_passWord = (EditText) m_Activity.findViewById(R.id.etPwdText_setLockPwd) ;
        m_keyboardView = (KeyboardView) m_Activity.findViewById(R.id.keyboard_view) ;
        m_pWerrorTv = (TextView) m_Activity.findViewById(R.id.pump1_pw_err) ;
        m_keyboardView.setKeyboard(m_key_number);
        m_keyboardView.setEnabled(true);
        m_keyboardView.setPreviewEnabled(false);
        m_keyboardView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE ) {
                m_thisPwText = "" ;
                //Intent intent = new Intent() ;
                //intent.setClass(m_Activity, WellInfoActivity.class) ;
                m_Activity.finish();
               // m_Activity.startActivity(intent);
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (m_thisPwText != null && m_thisPwText.length() >= 1) {
                    m_thisPwText = m_thisPwText.substring(0, m_thisPwText.length() -1) ;
                    //System.out.println("密码" + m_thisPwText);
                    int len = m_thisPwText.length() ;
                    if (len <= 7) {
                        m_listIme.get(len).setImageResource(R.mipmap.input_pw_round);
                    }
                }
            }else {
                m_pWerrorTv.setVisibility(View.INVISIBLE);
                m_thisPwText = m_thisPwText + (char) primaryCode ;
                //System.out.println("密码" + m_thisPwText);
                int len = m_thisPwText.length() ;
                if (len <= 8) {
                    m_listIme.get(len - 1).setImageResource(R.mipmap.input_pw_point);
                    if (len == 8) {
                        //返回值,并清理本次记录,自动进入下次
                        //m_listIme.get()
                        m_passWord.setText(m_thisPwText);
                        m_thisPwText = "" ;
                    }
                }
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public void setListIme(ArrayList<ImageView> imeList) {
        this.m_listIme = imeList ;
    }

    public void showKeyboard() {
        int visibility = m_keyboardView.getVisibility() ;
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            m_keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = m_keyboardView.getVisibility() ;
        if (visibility == View.VISIBLE) {
            m_keyboardView.setVisibility(View.INVISIBLE);
        }
    }
}
