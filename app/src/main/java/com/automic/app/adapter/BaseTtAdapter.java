package com.automic.app.adapter;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/*
使用泛型通用的adapter
 */
public abstract class BaseTtAdapter<T> extends BaseAdapter {

	 List<T> mList;//用于存放数据集
	    LayoutInflater mInflater;
	    Context context;
	    int [] viewIds;//存放控件ID
	    String[] fields;//存放ViewHolder类变量的名字
	    int layoutId;//布局文件ID
	    Class<?> classOfE;//ViewHolder类
	    //构造函数
	    public BaseTtAdapter(Context context, List<T> mList) {
	    // TODO Auto-generated constructor stub
	        mInflater = LayoutInflater.from(context);
	        this.mList = mList;
	        this.context = context;
	    }
	    public void setFields(String[] fields) {
	        this.fields = fields;
	    }
	    public void setViewIds(int[] viewIds){
	        this.viewIds = viewIds;
	    }
	    public void setClass(Class<?> e){
	        this.classOfE = e;
	    }
	    public void setLayoutId(int layoutId) {
	        this.layoutId = layoutId;
	    }
	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return mList == null ? 0 : mList.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return mList.get(position);
	    }

	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }

	    //获取布局文件,将控件初始化
	    public Object initViewHolder(View convertView){

	        try {
	            if (fields.length != viewIds.length) {
	                return null;
	            }
	            Object e = classOfE.newInstance();//调用空构造函数
	            int length = fields.length;
	            for(int i = 0;i<length;i++){
	                Field field = classOfE.getDeclaredField(fields[i]);//通过反射获取变量
	                field.setAccessible(true);//将变量设置为可访问状态
	                field.set(e,convertView.findViewById(viewIds[i]));//为变量设值
	            }
	            return e;
	        } catch (InstantiationException ex) {
	            // TODO Auto-generated catch block
	            ex.printStackTrace();
	            return null;
	        } catch (IllegalAccessException ex) {
	            // TODO Auto-generated catch block
	            ex.printStackTrace();
	            return null;
	        } catch (IllegalArgumentException ex) {
	            // TODO Auto-generated catch block
	            ex.printStackTrace();
	            return null;
	        } catch (NoSuchFieldException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	            return null;
	        }
	    }
	    //抽象方法,实现些方法用于数集的绑定
	    public abstract void addDataToView(T t,Object o);
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	        Object e = null;
	        if (convertView == null) {
	        convertView = mInflater.inflate(layoutId, null);
	        e =initViewHolder(convertView);
	        if (e!=null)
	            convertView.setTag(e);

	        } else
	            e = convertView.getTag();
	        addDataToView(mList.get(position),e);
	        return convertView;
	    }
	
}
