package com.automic.app.view;/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automic.app.R;

import java.lang.reflect.Field;

/**
 * A simple dialog containing an {@link DatePicker}.
 *
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 * </p>
 */
public class OneDatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {

	private static final String START_YEAR = "start_year";
	private static final String START_MONTH = "start_month";
	private static final String START_DAY = "start_day";
	private DatePicker mDatePicker_start;
	private final OnDateSetListener mCallBack;
	private final int mYear;
	private final int mMonthOfYear;
	private final int mDayOfMonth;
	private final boolean mIsDayVisible;


	/**
	 * The callback used to indicate the user is done filling in the date.
	 */
	public interface OnDateSetListener {


		/**
		 * @param startDatePicker
		 * @param startYear
		 * @param startMonthOfYear
		 * @param startDayOfMonth
		 */
		void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth);
	}

	/**
	 * @param context
	 *            The context the dialog is to run in.
	 * @param callBack
	 *            How the parent is notified that the date is set.
	 * @param year
	 *            The initial year of the dialog.
	 * @param monthOfYear
	 *            The initial month of the dialog.
	 * @param dayOfMonth
	 *            The initial day of the dialog.
	 */
//	public OneDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
//		this(context, 0, callBack, year, monthOfYear, dayOfMonth);
//	}

//	public OneDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
//							   int dayOfMonth) {
//		this(context,0, callBack, year, monthOfYear, dayOfMonth, true);
//	}

	/**
	 * @param context
	 *            The context the dialog is to run in.
	 * @param theme
	 *            the theme to apply to this dialog
	 * @param callBack
	 *            How the parent is notified that the date is set.
	 * @param year
	 *            The initial year of the dialog.
	 * @param monthOfYear
	 *            The initial month of the dialog.
	 * @param dayOfMonth
	 *            The initial day of the dialog.
	 */
	public OneDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
							   int dayOfMonth, boolean isDayVisible) {
		super(context);
		mCallBack = callBack;
		mYear = year;
		mMonthOfYear = monthOfYear;
		mDayOfMonth = dayOfMonth;
		mIsDayVisible = isDayVisible;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_date_picker_dialog);
		Button btn_cancel=(Button)findViewById(R.id.btn_cancel);//取消按钮
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		Button btn_comfirm=(Button)findViewById(R.id.btn_comfirm);//确定按钮
		btn_comfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tryNotifyDateSet();
			}
		});
		//居中显示
		Window win =getWindow();
		WindowManager.LayoutParams layoutParams=win.getAttributes();
		win.setGravity(Gravity.CENTER_HORIZONTAL);
		layoutParams.alpha=0.7f;//设置透明度
		win.setAttributes(layoutParams);

		//取消或确定按钮监听事件处理
		mDatePicker_start = (DatePicker)findViewById(R.id.datePickerStart);
		initDatePicker(mYear, mMonthOfYear, mDayOfMonth,mIsDayVisible);
		}

	private void initDatePicker(int year, int monthOfYear, int dayOfMonth, boolean isDayVisible) {
		mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
		if (!isDayVisible) {
			hidDay(mDatePicker_start);}
	}


	/**
	 * 隐藏DatePicker中的日期显示
	 *
	 * @param mDatePicker
	 */
	private void hidDay(DatePicker mDatePicker) {
		Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
		for (Field datePickerField : datePickerfFields) {
			if ("mDaySpinner".equals(datePickerField.getName())) {
				datePickerField.setAccessible(true);
				Object dayPicker = new Object();
				try {
					dayPicker = datePickerField.get(mDatePicker);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				// datePicker.getCalendarView().setVisibility(View.GONE);
				((View) dayPicker).setVisibility(View.GONE);
			}
		}
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// Log.d(this.getClass().getSimpleName(), String.format("which:%d",
		// which));
		// 如果是“取 消”按钮，则返回，如果是“确 定”按钮，则往下执行
//		if (which == R.id.btn_comfirm)
//			tryNotifyDateSet();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		if (view.getId() == R.id.datePickerStart)
			mDatePicker_start.init(year, month, day, this);
		// updateTitle(year, month, day);
	}

	/**
	 * 获得开始日期的DatePicker
	 *
	 * @return The calendar view.
	 */
	public DatePicker getDatePickerStart() {
		return mDatePicker_start;
	}



	/**
	 * Sets the start date.
	 *
	 * @param year
	 *            The date year.
	 * @param monthOfYear
	 *            The date month.
	 * @param dayOfMonth
	 *            The date day of month.
	 */
	public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
		mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
	}


	private void tryNotifyDateSet() {
		if (mCallBack != null) {
			mDatePicker_start.clearFocus();
			mCallBack.onDateSet(mDatePicker_start, mDatePicker_start.getYear(), mDatePicker_start.getMonth(),
					mDatePicker_start.getDayOfMonth());
			dismiss();
		}
	}

	@Override
	protected void onStop() {
		// tryNotifyDateSet();
		super.onStop();
	}

	@Override
	public Bundle onSaveInstanceState() {
		Bundle state = super.onSaveInstanceState();
		state.putInt(START_YEAR, mDatePicker_start.getYear());
		state.putInt(START_MONTH, mDatePicker_start.getMonth());
		state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
		return state;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int start_year = savedInstanceState.getInt(START_YEAR);
		int start_month = savedInstanceState.getInt(START_MONTH);
		int start_day = savedInstanceState.getInt(START_DAY);
		mDatePicker_start.init(start_year, start_month, start_day, this);


	}
}
