package com.automic.app.dao;

import java.util.List;

public interface SqlHelper<T> {
	    /** 
	     * 添加 
	     * @param table 添加对应的表 
	     * @param t 表对应的对象 
	     * @return 插入的位置索引 
	     */  
	    long insert(String table, T t);
	    /** 
	     * 删除 
	     * @param table 删除对应的表 
	     * @param columname 通过的字段 
	     * @param vaule 字段对应的值 
	     * @return 删除影响的行数 
	     */  
	    int delete(String table, String columname, String vaule);
	    /** 
	     * 修改 
	     * @param table 修改对应的表 
	     * @param t 修改的表对应的对象 
	     * @return 修改影响的行数 
	     */  
	    int update(String table, T t);
	    /** 
	     * 查询一个 
	     * @param table 需要查询的表 
	     * @param columname 需要查询的字段名 
	     * @param vaule 需要查询的字段对应的值 
	     * @return 表对应的对象 
	     */  
	    List<T> query(String table, String columname, String vaule);
	      
	    /** 
	     * 查询一页 
	     * @param table 需要查询的表 
	     * @param startIndex 查询的起始索引 
	     * @param pageSize 查询的页面条数 
	     * @return page数据 
	     */  
	    List<T> queryPage(String table, int startIndex, int pageSize);
	      
	    /** 
	     * @param table 需要查询的表 
	     * @return 所有数据 
	     */  
	    List<T> queryAll(String table);  

	/**
	 * @param table
	 *            需要查询的表
	 * @return 存在的maxId
	 */
	int findMaxId(String table);

	/**
	 * @param table
	 *            需要查询的表
	 * @return 删除的行数
	 */
	int deleteAll(String table);
}
