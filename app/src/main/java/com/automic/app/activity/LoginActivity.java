package com.automic.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.automic.app.R;
import com.automic.app.bean.Area;
import com.automic.app.bean.AreaXiangCun;
import com.automic.app.bean.Constant;
import com.automic.app.bean.CunsByXiang;
import com.automic.app.bean.UserInfo;
import com.automic.app.utils.JpushUtils;
import com.automic.app.utils.SharepreferenceUtils;
import com.automic.app.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.automic.app.bean.Constant.BASEIP;


public class LoginActivity extends Activity implements OnClickListener {

	public static LoginActivity instance = null ;
	private Button btnLogin;

	public CheckBox cbSavePsw;

	private EditText etuser;
	private EditText etpassword;
	private List<Area> areaXiangList = new ArrayList<Area>();
	private List<Area> areaCunList = new ArrayList<Area>();
	private Context mContext;
	private UserInfo userInfo;

	private boolean issave=false;
	// 退出系统时间
			private long exitTime = 0;
	public LoginActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this ;
		mContext=LoginActivity.this;
		setContentView(R.layout.activity_login);
		setUpViews();
	}

	private void setUpViews() {
		
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);


		cbSavePsw = (CheckBox) findViewById(R.id.cb_save);
		cbSavePsw.setOnClickListener(this);
		

		etuser = (EditText) findViewById(R.id.et_user);
		etpassword = (EditText) findViewById(R.id.et_pwd);
		userInfo= SharepreferenceUtils.getUserInfo(this);
		if(!"".equals(userInfo.getUsername())){
			etuser.setText(userInfo.getUsername());
		}
		issave=SharepreferenceUtils.getPswisSave(this);
		if(issave){
			etpassword.setText(userInfo.getPassword());
			openActivity(MainActivity.class);
			finish();
		}
	}
	
	private void login(String userName,String pwd){
		//String[] saveUserPassword = Utils.getUserPushInfo(getApplicationContext());
    	if(userInfo.getUsername().equals(userName) && userInfo.getPassword().equals(pwd) 
    			&& userInfo.getUsername() != "" && userInfo.getPassword() != "")   //判断 帐号和密码
        {
			JpushUtils.setTag("AIadmin");//设置灌溉的管理员
    		openActivity(MainActivity.class);
			finish();
         }else if("".equals(userName) || "".equals(pwd))   //判断 帐号和密码
          {
	    	new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_HOLO_LIGHT)
			.setIcon(getResources().getDrawable(R.mipmap.login_error_icon))
			.setTitle("登录错误 ")
			.setMessage("帐号/密码不能为空,请输入后再登录!")
			.create().show();
          }
        else{
        	new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_HOLO_LIGHT)
			.setIcon(getResources().getDrawable(R.mipmap.login_error_icon))
			.setTitle("登录失败")
			.setMessage("帐号/密码不正确,请检查后重新输入!")
			.create().show();
        }
    	
	}
	/**
	 * 打开一个Activity
	 * @param cls
	 */
	public void openActivity(Class<?> cls){
		Intent intent =new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
	}
/*	private String getUrl(String userName,String pwd){
		String url = "http://"+AppUtils.BASEIP+"/servlet/user/login.do?usercode="+userName+"&pass="+pwd+"&app=1&imei=asdhfjldfjlkj";
		return url;
	}*/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			String usercode = etuser.getText().toString();
			String password = etpassword.getText().toString();
			
			//String[] saveUserPassword = Utils.getUserPushInfo(getApplicationContext());
			//UserInfo userPasswordSave = new UserInfo();
			if("".equals(userInfo.getUsername()) || "".equals(userInfo.getPassword())) {
				if (usercode.equals(Constant.userName) || password.equals(Constant.userPwd)) {
					//Utils.saveUserPushInfo(getApplicationContext(), "admin", "password");
					userInfo.setUsername("admin");
					userInfo.setPassword("password");
					SharepreferenceUtils.saveUserInfo(LoginActivity.this, userInfo);
					//Toast.makeText(this, "第一次登陆！", Toast.LENGTH_SHORT).show();
					login(usercode, password);
					//showToast("用户名和密码不能为空");
				}else {
					//openDialog("正在登录,请稍等...");
					login(usercode, password);
				}
			}else{
					login(usercode, password);
			}
			break;
		case R.id.cb_save:
			CheckBox cb=(CheckBox)v;
			usercode = etuser.getText().toString();
			password = etpassword.getText().toString();
			//saveUserPassword = Utils.getUserPushInfo(getApplicationContext());
			if(cb.isChecked()){
				if("".equals(userInfo.getUsername()) || "".equals(userInfo.getPassword())) {
					if (usercode.equals("admin") && password.equals("password")) {
						SharepreferenceUtils.savePswisSave(getApplicationContext(), true);
					}else {
						cb.setChecked(false);
		        		new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_HOLO_LIGHT)
						.setIcon(getResources().getDrawable(R.mipmap.login_error_icon))
						.setTitle("记住密码错误")
						.setMessage("输入正确的用户名和密码,才能记住密码!！")
						.create().show();
					}
				}else{
					if (usercode.equals(userInfo.getUsername()) && password.equals(userInfo.getPassword())) {
						SharepreferenceUtils.savePswisSave(getApplicationContext(), true);
					}else {
						cb.setChecked(false);
		        		new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_HOLO_LIGHT)
						.setIcon(getResources().getDrawable(R.mipmap.login_error_icon))
						.setTitle("记住密码错误")
						.setMessage("输入正确的用户名和密码,才能记住密码!")
						.create().show();
					}
				}
			}else{
				SharepreferenceUtils.savePswisSave(getApplicationContext(), false);
			}
			break;
		default:
			break;
		}
	}
	public void setCbSaveStatus(boolean status) {

		SharepreferenceUtils.savePswisSave(getApplicationContext(), status);
		if(!status) {
			cbSavePsw.setChecked(false);
		}
	}
	/**
	 * 功能描述: 添加返回按钮，弹出是否退出应用程序对话框
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			break;
		}
		return false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		//获得乡的数据并保存
		//getXiangData(true,"","");
		//请求一次，将所有的数据拿回
		getCunsByXiang();
	}

	/**
	 * @param isXiang 为true请求乡数据，为false请求村数据
	 * @param xId     乡id
	 */
	private void getXiangData(final boolean isXiang, String xId, final String xiangSn) {
		String url = BASEIP + "/area/queryArea";
		RequestParams requestParams = new RequestParams(url);
		requestParams.addBodyParameter("userId", "admin");
		if (!isXiang) {
			requestParams.addBodyParameter("parentAreaId", xId);
		}
		x.http().post(requestParams, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonResult = new JSONObject(result);
					int code = jsonResult.getInt("code");
					if (code == 1) {
						Gson gson = new Gson();
						StringBuilder sb=new StringBuilder();
						String data = jsonResult.getString("result");
						if (isXiang) {
							areaXiangList = gson.fromJson(data, new TypeToken<List<Area>>() {
							}.getType());
							List<String> dataCache = new ArrayList<String>();
							for (Area area : areaXiangList) {
								sb.append(area.getArea()+",");
								//dataCache.add(area.getArea());
							}
							//LogUtils.e("sjt","xiangdata===="+sb.toString());
							SharepreferenceUtils.setXiangInfo(mContext,sBToStirng(sb));
							sb.setLength(0);//清空sb
							//获得村的数据并保存
							int size=areaXiangList.size();
							if (areaXiangList.size()!=0){
								for (int i = 0; i < size; i++) {
									getXiangData(false, areaXiangList.get(i).getAreaId(),String.valueOf(i));
								}

							}
						}else{
							SharepreferenceUtils.setCunInfo(mContext,xiangSn,data);//把result直接存进去
							//LogUtils.e("sjt","cunJosnData===="+data);
						}

					} else {
						//ToastUtils.show(mContext,"应用初始化失败");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
				}

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				//ToastUtils.show(mContext,"查询失败");
				//ToastUtils.show(mContext, "应用初始化数据失败");
			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});
	}
	private String sBToStirng(StringBuilder sb) {
		String dataXiang = sb.toString();
		int length = dataXiang.length();
		if (length > 0) {
			dataXiang = dataXiang.substring(0, length - 1);
			return dataXiang;
		} else
			return null;
	}

	/**
	 * 一次性从网络断获取行政区域
	 */
	private void getCunsByXiang(){
		String url=BASEIP+"/area/QueryAllAreaInfo";
		RequestParams rp=new RequestParams(url);
		rp.addBodyParameter("userId","admin");
		rp.addBodyParameter("flagAllArea","1");
		x.http().post(rp, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonData=new JSONObject(result);
					int code=jsonData.getInt("code");
					if (code==1){
						Gson gson=new Gson();
						String data=jsonData.getString("result");
					List<CunsByXiang> mBlist=gson.fromJson(data,new TypeToken<List<CunsByXiang>>(){}.getType());
						mBlist.size();
						StringBuilder sb=new StringBuilder();
						for (CunsByXiang cBx:mBlist){
						String xiang=	cBx.getCuns().get(0).getParentArea();
							sb.append(xiang+",");
						}
						SharepreferenceUtils.setXiangInfoOneTime(mContext,sBToStirng(sb));
						sb.setLength(0);//清空sb
						int i=0;
						for (CunsByXiang cbx:mBlist){
							String oneXiangCunJson=new Gson().toJson(cbx.getCuns());
							SharepreferenceUtils.setCunInfoOneTime(mContext,String.valueOf(i),oneXiangCunJson);//把result直接存进去
							i++;
						}

					}else {

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {

			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
