package com.fwtai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.fwtai.tool.ToolListOrMap;
import com.fwtai.tool.ToolString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 定义了数据库表操作对象-通用的工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年4月27日 13:14:54
 * @QQ号码 444141300
 * @Email 444141300@qq.com
 * @官网 http://www.fwtai.com
*/
public final class Dao{

	protected Context context ;
	private SQLiteDatabase data = null ;
	private DBOpenHelper helper = null ;
	
	/**
	 * 初始化DbDao对象.然后通过DbDao.createDataBase()创建数据库及数据库表
	 * @param context
	 * @作者 田应平
	 * @创建时间 2015-4-26 下午6:57:06 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public Dao(Context context){
		helper = DBOpenHelper.getInstance(context);//获取实例,然后通过DbDao.createDataBase()创建数据库及数据库表
		this.context = context ;
	}
	
	/**
	 * 创建数据库及数据库表,调用方法getWritableDatabase()才真正的创建数据库及数据库表,一般用于第一次创建表!
	 * @throws SQLiteException
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015-4-26 下午6:56:54 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final void createDataBase() throws SQLiteException{
		helper.getWritableDatabase();//创建数据库及数据库表,调用方法getWritableDatabase()才真正的创建数据库及数据库表
	}
	
	/**
	 * 关闭数据库
	 * @作者 田应平
	 * @创建时间 2015-1-13 下午9:37:07 
	 * @QQ号码 444141300 
	 * @Email 444141300@qq.com
	 * @官网 http://www.fwtai.com
	*/
	public final void closed(){
		if (data != null){
			data.close();
			data = null;
		}
	}
	
	/**
	 * 添加新增(单张表)-封装了添加-新增的方法,具有灵活性-OK,注意：传递来的hashMap的key必须要和数据库表的字段一致,否则可能添加不成功
	 * @param tableName 需要添加插入的数据库表名
	 * @param hashMapValue 要添加的数据,传递来的hashMap的key必须要和数据库表的字段一致,否则可能添加不成功
	 * @return 返回true成功，否则失败!
	 * @作者 田应平
	 * @返回值类型 boolean 返回true成功，否则失败!
	 * @创建时间 2015年4月26日 18:47:27 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	 */
	public final boolean add(final String tableName,final HashMap<String,String> hashMapValue){
		if (!ToolListOrMap.verifyEmpty(hashMapValue) || ToolString.isBlank(tableName))return false ;
		final ContentValues values = new ContentValues();
		long id = -1 ;//API里说如果是-1则是错误的发生，所以就定义为 -1 了.
		try {
			data = helper.getReadableDatabase();
			for (String key : hashMapValue.keySet()) {
				if (!ToolString.isBlank(key)) {
					values.put(key,hashMapValue.get(key));
				}
			}
			//第一个参数为 表名,第二个参数 一般情况下都为 null,第三个参数类似于Map键值对的 ContentValues
			id = data.insert(tableName, null, values);
			boolean state = id != -1 ? true : false ;
			data.close();//也可以用 this.closed();关闭
			return state;
		} catch (Exception e) {
			return false ;
		}
	}
	
	/**
	 * 查询(单张表),封装了查询单1条记录,根据条件为字符串whereField值为selectionArgs的限制条件返回列名为columnName等参数的查询-具有灵活性-但如果查询单条数据的本方法应该很少用到,也不推荐使用
	 * @param tableName 要查询的表名,如:"cltxz"
	 * @param columnName 参数相当于 select 即是要查询返回的列名[如select xx,yy from tableName],如果为null则查询所有的列就是相当于 select * from tableName..
	 * @param whereClause 相当于 where条件，但不包含 where 关键字,可以有多个条件 并用and连接，如 "id=? and name=?" 如果为 null则返回所有行的记录,如当第3个参数为null时，第4个参数肯定也为null.
	 * @param whereArgs 与第三个参数whereClause的where条件语句对应的值并且要一一对应，如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","%田应平%"}
	 * @param groupBy 表示是 groupBy 分组
	 * @param having 表示是 having分组后筛选
	 * @param orderBy orderBy 一般返回单条不需要排序的
	 * @param limit 是否要分页 它的分页和mysql的分页差不多
	 * @return Map< String,String>
	 * @作者 田应平
	 * @返回值类型 Map< String,String>
	 * @创建时间 2015年4月26日 15:53:54
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final HashMap<String, String> queryById(final String tableName,final String[] columnName,final String whereClause, final String[] whereArgs, final String groupBy, final String having, final String orderBy, final String limit) {
		final HashMap<String, String> map = new HashMap<String, String>();
		SQLiteDatabase data = null;
		Cursor cursor = null;
		try {
			data = helper.getReadableDatabase();
			// 第一个参数由于查询的结果只有一条记录即返回的为唯一的记录所以设为 true,就是是否去掉重复记录
			// 第二个参数为 表名
			// 第三个参数相当于 select 返回查询的列的名字 from..,如果为空null则查询所有的列就是相当于 select * from tablename..
			// 第四个参数是 相当于 where条件，但不包含 where 关键字,可以有多个条件 并用and连接，如 "id=? and name=?" 如果为 null则返回所有行的记录
			// 第五个参数是 与第四个参数where条件语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","%田应平%"}
			// 第六个参数 表示是 groupBy 分组
			// 第七个参数表示是 having
			// 第八个参数表示是 orderBy 排序-->是否要排序，要根据哪一个列排序,填写的列名,如 "cltxzid desc"或"cltxzid asc" (这里的 desc或asc 必须是小写，和 cltxzid 之间有个空格)
			// 第九个参数表示是 是否要分页 它的分页和mysql的分页差不多
			cursor = data.query(true, tableName, columnName, whereClause, (ToolString.isBlank(whereClause)?null:whereArgs), groupBy, having, orderBy, limit);
			final Integer colsNums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				for (int i = 0; i < colsNums; i++) {
					// 提取获取列的名称
					String cols_name = cursor.getColumnName(i);
					// 提取获取列的名称对应的值
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					// 判断提取获取列的名称对应的值否为空 null，如为空则 赋值 "",就是有些记录没有值，即允许为空时;
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
			}
		} catch (Exception e) {
		} finally {
			// 在'finally'关闭数据库
			if (data != null) {
				data.close();
			}
		}
		return map;
	}
	
	/**
	 * 查询(单张表)，封装了查询单1条记录,根据条件为字符串whereField值为selectionArgs的限制条件返回列名为columnName的查询,具有灵活性-OK
	 * @param tableName 要查询的表名,如:"cltxz"
	 * @param columnName 参数相当于 select 即是要查询返回的列名[如select xx,yy from tableName],如果为null则查询所有的列就是相当于 select * from tableName..
	 * @param whereClause 相当于 where条件，但不包含 where 关键字,可以有多个条件 并用and连接，如 "id=? and name=?" 如果为 null则返回所有行的记录,当第3个参数为null时，第4个参数肯定也为null.
	 * @param whereArgs 参数where条件whereClause语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","%田应平%"}
	 * @return Map< String,String>
	 * @作者 田应平
	 * @返回值类型 Map< String,String>
	 * @创建时间 2015年4月26日 15:53:54
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final HashMap<String, String> queryById(String tableName,String[] columnName,String whereClause,String[] whereArgs) {
		final HashMap<String, String> map = new HashMap<String, String>();
		SQLiteDatabase data = null;
		Cursor cursor = null;
		try {
			data = helper.getReadableDatabase();
			// 第一个参数由于查询的结果只有一条记录即返回的为唯一的记录所以设为 true,就是是否去掉重复记录
			// 第二个参数为 表名
			// 第三个参数相当于 select 返回查询的列的名字 from..,如果为空null则查询所有的列就是相当于 select * from tablename..
			// 第四个参数是 相当于 where条件，但不包含 where 关键字,可以有多个条件 并用and连接，如 "id=? and name=?" 如果为 null则返回所有行的记录
			// 第五个参数是 与第四个参数where条件语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","%田应平%"}
			// 第六个参数 表示是 groupBy 分组
			// 第七个参数表示是 having
			// 第八个参数表示是 orderBy 排序
			// 第九个参数表示是 是否要分页 它的分页和mysql的分页差不多
			cursor = data.query(true, tableName, columnName, whereClause, (ToolString.isBlank(whereClause)?null:whereArgs), null, null, null, null);
			Integer colsNums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				for (int i = 0; i < colsNums; i++) {
					// 提取获取列的名称
					String cols_name = cursor.getColumnName(i);
					// 提取获取列的名称对应的值
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					// 判断提取获取列的名称对应的值否为空 null，如为空则 赋值 "",就是有些记录没有值，即允许为空时;
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
			}
		} catch (Exception e) {
		} finally {
			// 在'finally'关闭数据库
			if (data != null) {
				data.close();
			}
		}
		return map;
	}
	
	/**
	 * 删除(单张表)-封装了删除记录-单条,根据条件为字符串whereClause值为whereArgs的限制条件的删除,参数1为表名,参数2为where条件条件，如Id主键"cltxzid=?",当然多个条件则用and连接，如 "id=? and name=?"，参数3为 where条件Id主键 的条件值-具有灵活性OK
	 * @param tableName 表名
	 * @param whereClause 参数为where条件语句,但是不包含where关键字,如果为 null则删除所有记录行,如果是多个条件则用and连接，如 "id=? and name=?",当第2个参数为null时，第3个参数肯定也为null.
	 * @param whereArgs 参数则与where条件语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","%田应平%"}
	 * @return 返回值大于0的则表示删除成功
	 * @作者 田应平
	 * @返回值类型 int
	 * @创建时间 2015-4-26 下午3:26:35 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final int deleteById(String tableName,String whereClause,String[] whereArgs){
		//定义一个影响的行数
		int rows = 0 ;
		try {
			data = helper.getReadableDatabase();
			//第一个参数为 表名,
			//第二个参数为where条件语句,但是不包含where关键字,如果为 null则删除所有记录行,如果是多个条件则用and连接，如 "id=? and name=?"
			//第三个参数则与where条件语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","%田应平%"}
			rows = data.delete(tableName, whereClause, (ToolString.isBlank(whereClause)?null:whereArgs));
		} catch (Exception e) {
			
		}finally{
			//在'finally'关闭数据库
			if (data != null) {
				data.close();
			}
		}
		return rows;
	}
	
	/**
	 * 删除条数(单张表)-根据whereClause条件值为whereArgs的限制条件清除清空数据的条数-具有灵活性
	 * @param tableName 参数为 表名
	 * @param whereClause 参数为where条件语句,但是不包含where关键字,如果为 null则删除所有记录行,如果是多个条件则用and连接，如 "id=? and name=?",当第2个参数为null时，第3个参数肯定也为null.
	 * @param whereArgs 参数则与where条件语句对应的值并且要一一对应,相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","'%田应平%'"},第2个参数为null时,第3个参数肯定为 null.
	 * @return
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2015-4-26 下午4:57:10 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final Integer clearData(String tableName,String whereClause, String[] whereArgs) {
		//定义一个影响的行数
		int rows = 0 ;
		try {
			data = helper.getReadableDatabase();
			//第一个参数为 表名,
			//第二个参数为where条件语句,但是不包含where关键字,如果为 null则删除所有记录行,如果是多个条件则用and连接，如 "id=? and name=?"
			//第三个参数则与where条件语句对应的值并且要一一对应,相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","'%田应平%'"}
			rows = data.delete(tableName, whereClause, (ToolString.isBlank(whereClause)?null:whereArgs));
		} catch (Exception e) {
			
		}finally{
			//在'finally'关闭数据库
			if (data != null) {
				data.close();
			}
		}
		return rows;
	}
	
	/**
	 * 查询条数(单张表)-封装了查询条件为whereClause的字符串值为whereArgs的清除清空数据的条数是否有多少条记录-具有灵活性-OK
	 * @param tableName 表名
	 * @param columns 参数相当于 select 返回查询的列的名字 from..,如果为空null则查询所有的列就是相当于 select * from tablename..，一般为ID主键.如 String[] columns = {"cltxzid"};
	 * @param whereClause 参数是 相当于 where条件，但不包含 where 关键字,可以有多个条件 并用and连接，如 "id=? and name=?" 如果为 null则返回所有行的记录,当第3个参数为null时，第4个参数肯定也为null.
	 * @param whereArgs 参数where条件语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","'%田应平%'"},第3个参数为null时,第4个参数肯定为 null.
	 * @return 返回多少条数据
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2015-4-26 下午5:14:39 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final Integer findDataCount(String tableName,String[] columns, String whereClause, String[] whereArgs){
		data = helper.getReadableDatabase();
		Cursor cursor = data.query(tableName, columns, whereClause, (ToolString.isBlank(whereClause)?null:whereArgs), null, null, null);
		return cursor.getCount();
	}
	
	/**
	 * 带分页的搜索数据查询(单张表)-查询分页的数据-具有灵活性
	 * @param tableName 表名
	 * @param columnsName 需要返回的列名，如" cltxzid,cph,sjmc "最后一个不要带逗号[,]!,如写成null 则返回所有列。
	 * @param sql sql语句里不包含where关键字了,必须包含and关键字，其余可能包含的有 或 or 或 like,其实这里的sql就是关键字字符串拼接,如果没有关键字或模糊查询的话，则写String sql = ""; 即可
	 * @param orderBy 是否需要排序[为null时不排序]，要根据哪一个列排序,不包含order by关键字了,只需填写如 "cltxzid列名  desc"或"cltxzid列名  asc" (这里的 desc或asc 必须是小写，和  列名cltxzid 之间有个空格)
	 * @param currentPage 当前页
	 * @param pageSize 每页显示多少条记录
	 * @return 返回查询分页大小的数据
	 * @作者 田应平
	 * @返回值类型 ArrayList< HashMap< String,String>>
	 * @创建时间 2015-4-27 上午2:43:15 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final ArrayList<HashMap<String, String>> getPageDataOneTab(String tableName,String columnsName,String sql,String orderBy,int currentPage, int pageSize) {
		final ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase data = null ;
		Cursor cursor = null ;
		try {
			data = helper.getReadableDatabase();
			sql = "select " + (columnsName == null ?" * ":columnsName) + " from " + tableName +" where 1=1 "+sql;
			if (!ToolString.isBlank(orderBy)) {
				sql = sql + " order by " + orderBy;
			}
			sql = sql +" limit " + pageSize +" offset " + (currentPage - 1) * pageSize;
			cursor = data.rawQuery(sql, null);
			Integer colsNums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				HashMap<String,String> map = new HashMap<String, String>();	
				for(int i = 0 ; i < colsNums ; i++){
					//提取获取列的名称
					String cols_name = cursor.getColumnName(i);
					//提取获取列的名称对应的值
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					//判断提取获取列的名称对应的值否为空 null，如为空则 赋值 "",就是有些记录没有值，即允许为空时;
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				result.add(map);//在for循环的外边add添加到list
			}
			return result ;
		} catch (Exception e) {
			return result ;
		}finally{
			if (data != null) {
				data.close();
			}
		}
	}
	
	/**
	 * 带分页的搜索数据查询(单张表)-查询分页的总条数-具有灵活性
	 * @param tableName 表名 
	 * @param columnsName 需要返回的列名，如" cltxzid,cph,sjmc "最后一个不要带逗号[,]!,如写成null 则返回所有列。
	 * @param sql sql语句里不包含where关键字了,必须包含and关键字，其余可能包含的有 或 or 或 like,其实这里的sql就是关键字字符串拼接,如果没有关键字或模糊查询的话，则写String sql = ""; 即可
	 * @param orderBy 是否需要排序[为null时不排序]，要根据哪一个列排序,不包含order by关键字了,只需填写如 "cltxzid列名  desc"或"cltxzid列名  asc" (这里的 desc或asc 必须是小写，和  列名cltxzid 之间有个空格)
	 * @return 返回查询分页的总条数
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2015-4-27 上午2:42:16 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final Integer getPageCountOneTab(String tableName,String columnsName,String sql,String orderBy){
		Integer total = 0 ;
		Cursor cursor = null;
		try {
			data = helper.getReadableDatabase();
			sql = "select " + (columnsName == null ?" * ":columnsName) + " from " + tableName +" where 1=1 "+sql;
			if (!ToolString.isBlank(orderBy)) {
				sql = sql + " order by " + orderBy;
			}
			cursor = data.rawQuery(sql, null);
			total = cursor.getCount();//游标的行数
		} catch (Exception e) {
		}finally{
			if (data != null) {
				data.close();
			}
		}
		return total;
	}
	
	/**
	 * 带分页的数据查询(多张表)-查询分页的数据
	 * @param sql sql语句 不带where关键字,带必须and关键字的sql语句
	 * @return 返回查询分页大小的数据
	 * @作者 田应平
	 * @返回值类型 ArrayList< HashMap< String,String>>
	 * @创建时间 2015年4月27日 10:09:18 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final ArrayList<HashMap<String, String>> getPageForData(String sql) {
		final ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase data = null ;
		Cursor cursor = null ;
		try {
			data = helper.getReadableDatabase();
			cursor = data.rawQuery(sql, null);
			Integer colsNums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				HashMap<String,String> map = new HashMap<String, String>();	
				for(int i = 0 ; i < colsNums ; i++){
					//提取获取列的名称
					String cols_name = cursor.getColumnName(i);
					//提取获取列的名称对应的值
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					//判断提取获取列的名称对应的值否为空 null，如为空则 赋值 "",就是有些记录没有值，即允许为空时;
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				result.add(map);//在for循环的外边add添加到list
			}
			return result ;
		} catch (Exception e) {
			return result ;
		}finally{
			if (data != null) {
				data.close();
			}
		}
	}
	
	/**
	 * 带分页的数据查询(多张表)-查询分页的总条数
	 * @param sql语句 不带where关键字,带必须and关键字的sql语句
	 * @return 返回查询分页总的条数
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2015-4-27 上午10:07:05 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final Integer getPageForCount(String sql){
		Integer total = 0 ;
		Cursor cursor = null;
		try {
			data = helper.getReadableDatabase();
			cursor = data.rawQuery(sql, null);
			total = cursor.getCount();//游标的行数
		} catch (Exception e) {
		}finally{
			if (data != null) {
				data.close();
			}
		}
		return total;
	}

	/**
	 * 导出ListMap的数据(单张表)-封装了根据条件为whereClause值为whereArgs，返回的列名为columns,外加排序orderBy的限制条件.
	 * @param tableName 数据库表名
	 * @param columns 要返回的列名，如为 null则返回所有列
	 * @param whereClause 相当于 where条件，但不包含 where 关键字,可以有多个条件 并用and连接，如 "id=? and name=?" 如果为 null则返回所有行的记录,当第3个参数为null时，第4个参数肯定也为null.
	 * @param whereArgs 参数where条件语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","'%田应平%'"},第3个参数为null时,第4个参数肯定为 null.
	 * @param orderBy 是否要排序，要根据哪一个列排序,填写的列名,如 "cltxzid desc"或"cltxzid asc"(这里的 desc或asc 必须是小写，和 cltxzid 之间有个空格)
	 * @return 返回了List< Map< String,Object>>
	 * @作者 田应平
	 * @返回值类型 List< Map< String,Object>>
	 * @创建时间 2015-4-26 下午7:54:56 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final ArrayList<HashMap<String, Object>> exportListMaps(String tableName,String[] columns, String whereClause, String[] whereArgs,String orderBy){
		final ArrayList<HashMap<String,Object>> listMap = new ArrayList<HashMap<String,Object>>();
		SQLiteDatabase data = null ;
		Cursor cursor = null ;
		try {
			data = helper.getReadableDatabase();
			cursor = data.query(tableName, columns, whereClause, (ToolString.isBlank(whereClause)?null:whereArgs), null, null, orderBy, null);
			final Integer colsNums = cursor.getColumnCount();//列数
			while (cursor.moveToNext()) {
				final HashMap<String,Object> map = new LinkedHashMap<String, Object>();	
				for(int i = 0 ; i < colsNums ; i++){
					//提取获取列的名称
					String cols_name = cursor.getColumnName(i);
					//提取获取列的名称对应的值
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					//判断提取获取列的名称对应的值否为空 null，如为空则 赋值 "",就是有些记录没有值，即允许为空时;
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				listMap.add(map);//在for循环的外边add添加到list
			}
			return listMap;
		} catch (Exception e) {
			return listMap;
		}finally{
			if (data != null) {
				data.close();
			}
		}
	}
	
	/**
	 * 导出ListMap的数据(多张表)
	 * @param sql
	 * @return
	 * @作者 田应平
	 * @返回值类型 List<Map<String,Object>>
	 * @创建时间 2015-4-27 上午11:14:44 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final ArrayList<HashMap<String, Object>> exportListMaps(String sql){
		final ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String,Object>>();
		SQLiteDatabase data = null ;
		Cursor cursor = null ;
		try {
			data = helper.getReadableDatabase();
			cursor = data.rawQuery(sql, null);
			final Integer colsNums = cursor.getColumnCount();//列数
			while (cursor.moveToNext()) {
				final HashMap<String,Object> map = new LinkedHashMap<String, Object>();	
				for(int i = 0 ; i < colsNums ; i++){
					//提取获取列的名称
					String cols_name = cursor.getColumnName(i);
					//提取获取列的名称对应的值
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					//判断提取获取列的名称对应的值否为空 null，如为空则 赋值 "",就是有些记录没有值，即允许为空时;
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				listMap.add(map);//在for循环的外边add添加到list
			}
			return listMap;
		} catch (Exception e) {
			return listMap;
		}finally{
			if (data != null) {
				data.close();
			}
		}
	}
	
	/**
	 * 更新(单张表)-封装了以whereClause的字符串值为selectionArgs的限制条件的更新方法-具有灵活性-OK
	 * @param tableName 表名
	 * @param hashMapValue 要更新的数据,传递来的hashMap的key必须要和数据库表的字段一致,否则可能更新不成功
	 * @param whereClause 参数为where条件语句,但是不包含where关键字,如果为 null则更新所有记录行,如果是多个条件则用and连接，如 "id=? and name=?",当第3个参数为null时，第4个参数肯定也为null.
	 * @param whereArgs 参数where条件语句对应的值并且要一一对应，相当于 String[] whereArgs 如是多个条件则用数组个数相对应参数位置，如 new String[]{"4","'%田应平%'"},第3个参数为null时,第4个参数肯定为 null.
	 * @return 返回受影响的行数
	 * @作者 田应平
	 * @返回值类型 int 返回受影响的行数
	 * @创建时间 2015-4-26 下午5:39:28 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final int updateById(String tableName,HashMap<String, String> hashMapValue,String whereClause,String[] whereArgs) {
		if (!ToolListOrMap.verifyEmpty(hashMapValue) || ToolString.isBlank(tableName))return -1 ;
		//定义一个影响的行数
		int rows = -1 ;
		try {
			data = helper.getReadableDatabase();
			final ContentValues values = new ContentValues();
			for (String key : hashMapValue.keySet()) {
				if (!ToolString.isBlank(key)) {
					values.put(key,hashMapValue.get(key));
				}
			}
			rows = data.update(tableName, values, whereClause, (ToolString.isBlank(whereClause)?null:whereArgs));
		}catch (Exception e) {
		}finally{
			if (data != null) {
				data.close();
			}
		}
		return rows;
	}
	
	/**
	 * 自动补全(单张表)-封装了自动补全-具有灵活性
	 * @param tableName 表名
	 * @param columnsName 要返回的列名，如为 null则返回所有列.
	 * @param sql
	 * @param orderBy 是否要排序，要根据哪一个列排序,填写的列名,如 "cltxzid desc"或"cltxzid asc"(这里的 desc或asc 必须是小写，和 cltxzid 之间有个空格)
	 * @param limitSize 要返回的数据条数
	 * @作者 田应平
	 * @返回值类型 ArrayList<HashMap<String,String>>
	 * @创建时间 2015-4-27 上午10:17:05 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final ArrayList<HashMap<String,String>> autoByWord(String tableName,String columnsName,String sql,String orderBy,Integer limitSize) {
		final ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase data = null ;
		Cursor cursor = null ;
		try {
			data = helper.getReadableDatabase();
			sql = "select " + (columnsName == null ?" * ":columnsName) + " from " + tableName +" where 1=1 " + sql;
			if (!ToolString.isBlank(orderBy)) {
				sql = sql + " order by " + orderBy;
			}
			sql = sql +" limit " + limitSize;
			cursor = data.rawQuery(sql, null);
			final Integer colsNums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				HashMap<String,String> map = new HashMap<String, String>();	
				for(int i = 0 ; i < colsNums ; i++){
					//提取获取列的名称
					String cols_name = cursor.getColumnName(i);
					//提取获取列的名称对应的值
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					//判断提取获取列的名称对应的值否为空 null，如为空则 赋值 "",就是有些记录没有值，即允许为空时;
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				result.add(map);//在for循环的外边add添加到list
			}
			return result ;
		} catch (Exception e) {
			return result ;
		}finally{
			if (data != null) {
				data.close();
			}
		}
	}
}