package com.automic.app.dao.imp;

import java.util.List;



import android.content.Context;

import com.automic.app.bean.JpushMessage;
import com.automic.app.dao.SqlHelperImpl;

public class JpushMessageImp extends SqlHelperImpl<JpushMessage> {
    //private String table = "T_JpushMessage";  
    //static String sql = "create table T_JpushMessage (_id Integer primary key autoincrement,msgId varchar(30),msgContent varchar(80),msgTime varchar(20),isReaded integer )";  
   // static String[] sqlcolumns = new String[] { "_id", "wellNo","wellName","typeOne","typeTwo","typeThree","typeFour","typeFive","msgId","msgTime" ,"isReaded"};
    static String[] sqlcolumns = new String[] { "_id", "wellNo","wellName","type","msgId","msgTime" ,"isReaded"};
	public JpushMessageImp(Context context) {
		super(context, JpushMessage.class,sqlcolumns);
		// TODO Auto-generated constructor stub
	}
	@Override
	public long insert(String table, JpushMessage t) {
		// TODO Auto-generated method stub
		return super.insert(table, t);
	}
	@Override
	public int delete(String table, String columname, String value) {
		// TODO Auto-generated method stub
		return super.delete(table, columname, value);
	}
	@Override
	public List<JpushMessage> queryAll(String table) {
		// TODO Auto-generated method stub
		return super.queryAll(table);
	}
	@Override
	public List<JpushMessage> query(String table, String columname, String value) {
		// TODO Auto-generated method stub
		return super.query(table, columname, value);
	}
}
