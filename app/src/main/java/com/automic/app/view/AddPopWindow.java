package com.automic.app.view;

import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.automic.app.R;
import com.automic.app.adapter.PopuwindowAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class AddPopWindow extends PopupWindow {
private View conentView;
	private final int h;
	private final int w;

	public interface Choice{
void senddata(String msg);
}
private  Choice choice;
Activity mActivity;
//private List<String> options;
	public AddPopWindow(final Activity context,final List<String> data) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conentView = inflater.inflate(R.layout.popuwindow_dialog, null);
		mActivity=context;
		//Log.e("sjt","jijnru");
		h = context.getWindowManager().getDefaultDisplay().getHeight();
		w = context.getWindowManager().getDefaultDisplay().getWidth();
		// 设置SelectPicPopupWindow的View
		this.setContentView(conentView);
//		// 设置SelectPicPopupWindow弹出窗体的宽
//		this.setWidth(w/2+50);
//		// 设置SelectPicPopupWindow弹出窗体的高
//		this.setHeight(LayoutParams.WRAP_CONTENT);
//		//this.setHeight(h*2/3);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
		 //this.setAnimationStyle(android.R.style.Animation_Dialog);
		
		// 设置SelectPicPopupWindow弹出窗体动画效果
		//this.setAnimationStyle(R.style.AnimationPreview);
		this.setChoice(choice);
		ListView lView=(ListView)conentView.findViewById(R.id.pp_listview);
		// options = Arrays.asList(data);
		PopuwindowAdapter adapter=new PopuwindowAdapter(context, data);
		 //ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data);  
		 lView.setAdapter(adapter);
		lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				//choice.senddata(data[arg2]);
				choice.senddata(String.valueOf(arg2));//把位置传过去
				AddPopWindow.this.dismiss();
				//弹框消失，回复原来的颜色
				 WindowManager.LayoutParams params=mActivity.getWindow().getAttributes();  
			      params.alpha=1f;  
			      mActivity.getWindow().setAttributes(params); 
			}
		});
		

	
	}

//	/**
//	 * 显示popupWindow
//	 *
//	 * @param parent
//	 */
//	public void showPopupWindow(View parent) {
//		if (!this.isShowing()) {
//			// 设置弹出框外为黑色
//			WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
//			params.alpha = 0.7f;
//			mActivity.getWindow().setAttributes(params);// 设置SelectPicPopupWindow弹出窗体的宽
//		this.setWidth(parent.getWidth());
//		// 设置SelectPicPopupWindow弹出窗体的高
//		this.setHeight(LayoutParams.WRAP_CONTENT);
//			// 以下拉方式显示popupwindow
//			this.showAsDropDown(parent, 0, 0);
//			//this.showAtLocation(parent, Gravity.CENTER, 0, 0);
//		} else {
//			this.dismiss();
//		}
//	}

	/**
	 * 全屏弹出dialog 居中显示
	 *
	 */
	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 设置弹出框外为黑色
			WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
			params.alpha = 0.7f;
			mActivity.getWindow().setAttributes(params);
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.MATCH_PARENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(h*2/3);
			// 居中显示popupwindow
			this.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		} else {
			this.dismiss();
		}
	}
	public void setChoice(Choice choice){
		this.choice=choice;
	}
	@Override
		public void dismiss() {
			// TODO Auto-generated method stub
			super.dismiss();
			//弹框消失，回复原来的颜色
			 WindowManager.LayoutParams params=mActivity.getWindow().getAttributes();  
		      params.alpha=1f;  
		      mActivity.getWindow().setAttributes(params); 
		}
}
