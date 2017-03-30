package com.automic.app.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlHelperImpl<T> implements SqlHelper<T> {

	  /** 
     * 反射得到字节码文件 
     */  
    private Class clazz;  
  
    /** 
     * 对应表的映射字段（操作表的所有属性） 
     */  
    private String[] sqlcolumns;  
  
    /** 
     * 创建数据库的帮助对象 
     */  
    private DBOpenHelper dbOpenHelper;  
  
    /** 
     * 构造函数，初始化工作 
     *  
     * @param context 
     *            上下文 
     * @param clazz 
     *            表对应bean对象的字节码文件 
     * @param dbName 
     *            数据库名称 
     * @param sql 
     *            创建表语句 
     * @param sqlcolumns 
     *            对应表的映射字段（操作表的所有属性） 
     */  
//    public SqlHelperImpl(Context context, Class clazz, String dbName,  
//            String sql, String[] sqlcolumns) {  
//        this.clazz = clazz;  
//        this.sqlcolumns = sqlcolumns;  
//        dbOpenHelper = new DBOpenHelper(context, dbName, sql);  
//    }  
//    //只建一个db
//    public SqlHelperImpl(Context context, Class clazz,  
//            String sql, String[] sqlcolumns) {  
//        this.clazz = clazz;  
//        this.sqlcolumns = sqlcolumns;  
//        dbOpenHelper = new DBOpenHelper(context,sql);  
//    } 
    public SqlHelperImpl(Context context, Class clazz,  
            String[] sqlcolumns) {  
        this.clazz = clazz;  
        this.sqlcolumns = sqlcolumns;  
        dbOpenHelper = new DBOpenHelper(context);  
    } 
    @Override  
    public long insert(String table, T t) {  
        try {  
            SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
            if (sqlcolumns != null && sqlcolumns.length > 0) {  
                ContentValues values = new ContentValues();  
                for (int i = 1; i < sqlcolumns.length; i++) {  
                    try {  
                        Field field = t.getClass().getDeclaredField(  
                                sqlcolumns[i]);  
                        field.setAccessible(true);  
                        Object objVaule = field.get(t);  
                        if (objVaule != null) {  
                            values.put(sqlcolumns[i], (String) objVaule);  
                        }  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
                long insertIndex = database.insert(table, null, values);  
                database.close();  
                return insertIndex;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return -1;  
    }  
  
    @Override  
    public int delete(String table, String columname, String value) {  
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
        int rows = database.delete(table, columname + "=?",  
                new String[] { value });  
        database.close();  
        return rows;  
    }  
    @Override  
    public int deleteAll(String table) {  
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
        int rows = database.delete(table, null ,null);  
        database.close();  
        return rows;  
    }  
    @Override  
    public int update(String table, T t) {  
        try {  
            SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
            String whereValue = null;  
            if (sqlcolumns != null && sqlcolumns.length > 0) {  
                ContentValues values = new ContentValues();  
                for (int i = 0; i < sqlcolumns.length; i++) {  
                    try {  
                        Field field = t.getClass().getDeclaredField(  
                                sqlcolumns[i]);  
                        field.setAccessible(true);  
                        Object objVaule = field.get(t);  
                        if (objVaule != null) {  
                            if (i == 0) {  
                                whereValue = objVaule.toString();  
                            } else {  
                                values.put(sqlcolumns[i], (String) objVaule);  
                            }  
                        }  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
                int rows = database.update(table, values, sqlcolumns[0] + "=?",  
                        new String[] { whereValue });  
                database.close();  
                return rows;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return -1;  
    }  
  
    @Override  
    public List<T> query(String table, String columname, String value) {  
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
        Cursor cursor = database.query(table, sqlcolumns,columname + "=?",  
                new String[] { value }, null, null, null,null);  
        List<T> list = new ArrayList<T>();  
        while (cursor.moveToNext()) {  
            try {  
                Object obj = clazz.newInstance();  
                for (int i = 0; i < sqlcolumns.length; i++) {  
                    try {  
                        Field field = clazz.getDeclaredField(sqlcolumns[i]);  
                        field.setAccessible(true);  
  
                        int type = cursor.getType(i);  
                        Object objValue = null;  
                        switch (type) {  
                        case Cursor.FIELD_TYPE_STRING:  
                            objValue = cursor.getString(i);  
                            break;  
                        case Cursor.FIELD_TYPE_INTEGER:  
                            objValue = cursor.getInt(i);  
                            break;  
                        case Cursor.FIELD_TYPE_FLOAT:  
                            objValue = cursor.getFloat(i);  
                            break;  
                        case Cursor.FIELD_TYPE_BLOB:  
                            objValue = cursor.getBlob(i);  
                            break;  
  
                        default:  
                            break;  
                        }  
  
                        field.set(obj, objValue);  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
                T t = (T) obj;  
                list.add(t);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        cursor.close();  
        database.close();  
        return list;  
    }  
  
    @Override  
    public List<T> queryPage(String table, int startIndex, int pageSize) {  
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
        Cursor cursor = database.query(table, sqlcolumns, null, null, null,  
                null, sqlcolumns[0] + " desc", startIndex + "," + pageSize);  
        List<T> list = new ArrayList<T>();  
        while (cursor.moveToNext()) {  
            try {  
                Object obj = clazz.newInstance();  
                for (int i = 0; i < sqlcolumns.length; i++) {  
                    try {  
                        Field field = clazz.getDeclaredField(sqlcolumns[i]);  
                        field.setAccessible(true);  
  
                        int type = cursor.getType(i);  
                        Object objValue = null;  
                        switch (type) {  
                        case Cursor.FIELD_TYPE_STRING:  
                            objValue = cursor.getString(i);  
                            break;  
                        case Cursor.FIELD_TYPE_INTEGER:  
                            objValue = cursor.getInt(i);  
                            break;  
                        case Cursor.FIELD_TYPE_FLOAT:  
                            objValue = cursor.getFloat(i);  
                            break;  
                        case Cursor.FIELD_TYPE_BLOB:  
                            objValue = cursor.getBlob(i);  
                            break;  
  
                        default:  
                            break;  
                        }  
  
                        field.set(obj, objValue);  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
                T t = (T) obj;  
                list.add(t);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        cursor.close();  
        database.close();  
        return list;  
    }  
  
    @Override  
    public List<T> queryAll(String table) {  
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
        Cursor cursor = database.query(table, sqlcolumns, null, null, null,  
                null, null, null);  
        List<T> list = new ArrayList<T>();  
        while (cursor.moveToNext()) {  
            try {  
                Object obj = clazz.newInstance();  
                for (int i = 0; i < sqlcolumns.length; i++) {  
                    try {  
                        Field field = clazz.getDeclaredField(sqlcolumns[i]);  
                        field.setAccessible(true);  
                        int type = cursor.getType(i);  
                        Object objValue = null;  
                        switch (type) {  
                        case Cursor.FIELD_TYPE_STRING:  
                            objValue = cursor.getString(i);  
                            break;  
                        case Cursor.FIELD_TYPE_INTEGER:  
                            objValue = cursor.getInt(i);  
                            break;  
                        case Cursor.FIELD_TYPE_FLOAT:  
                            objValue = cursor.getFloat(i);  
                            break;  
                        case Cursor.FIELD_TYPE_BLOB:  
                            objValue = cursor.getBlob(i);  
                            break;  
  
                        default:  
                            break;  
                        }  
                        field.set(obj, objValue);  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
                T t = (T) obj;  
                list.add(t);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        cursor.close();  
        database.close();  
        return list;  
    }
	@Override
	public int findMaxId(String table) {
		// TODO Auto-generated method stub
	    SQLiteDatabase database = dbOpenHelper.getWritableDatabase();  
        Cursor cursor = database.query(table, null, null, null, null, null, " _id DESC");
        int maxid = 0;
        if(cursor.moveToNext()){
        	maxid = cursor.getInt(cursor.getColumnIndex("_id"));
          // 这个id就是最大值
        } 
		return maxid;
	}  
  
}
