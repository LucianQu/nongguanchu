package com.automic.app.dao;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.automic.app.utils.LogUtils;

public class DBOpenHelper extends SQLiteOpenHelper{

	private static  String DATABASE_NAME="agriculIrrigate.db" ;
	public static  String TABLENAME="T_JpushMessage" ;
	private static final String TABLE_ID = "_id";
	private static  int DATABASE_VERSION = 1;
//private String sql;
//	public DBOpenHelper(Context context, String dbName, String sql) {
//		super(context, dbName, null, DATABASE_VERSION);
//		this.sql=sql;
//		
//		// TODO Auto-generated constructor stub
//	}
//	public DBOpenHelper(Context context, String sql) {
//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		this.sql=sql;
//		
//		// TODO Auto-generated constructor stub
//	}
	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		LogUtils.e("DBOpenHelper", "waterresouce.db onCreate");
//		 sql = "create table T_JpushMessage (_id Integer primary key autoincrement,msgId varchar(30),"
//		 		+ "msgContent varchar(80),msgTime varchar(20),isReaded Integer )";  		   
//		db.execSQL(sql);
		// 创建频点模式数据库
				String sql1 = "CREATE TABLE " + TABLENAME + //
						"(" + TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + //
						"msgId" + " VARCHAR(100), " + //
						"msgContent" + " VARCHAR(400), " + //
						"msgTime" + " VARCHAR(100), " + //
						"isReaded" + " VARCHAR(2), " + 
						"type"+" VARCHAR(20) " +")";
				db.execSQL(sql1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		LogUtils.e("DBOpenHelper", "waterresouce.db onUpgrade");

		db.execSQL("DROP TABLE IF EXISTS T_JpushMessage");
		onCreate(db);
	}

}
