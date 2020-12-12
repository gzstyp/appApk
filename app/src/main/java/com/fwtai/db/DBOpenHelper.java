package com.fwtai.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 对数据库及数据库表的创建和修改数据库表字段的一系列操作,数据库采用的是单例模式
 * @作者 田应平
 * @创建时间 2014年6月8日 12:36:25
 * @QQ号码 444141300
 * @主页 www.fwtai.com
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	/**
	 * 数据库的名称.
	*/
	private static final String name = "yinlzapp.db" ;// 数据库名
	/**
	 * 数据库的版本号,版本号一般都是从1开始的.
	*/
	private static final int version = 1 ;
	private static DBOpenHelper instance = null;
	/**
	 * 对外提供获取单例模式方法
	 * @param context
	 * @return 
	 * @作者 田应平 
	 * @创建时间 2014-9-12 
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	 */
	public static DBOpenHelper getInstance(Context context){
		if (instance == null ) {
			instance = new DBOpenHelper(context);
		}
		return instance;
	}
	
	/**
	 * 单例模式
	 * @param context 
	 * @作者 田应平 
	 * @创建时间 2014-9-12 
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	 */
	private DBOpenHelper(Context context) {
		super(context, name, null, version);
		//这个是构造方法，一般都是给属性初始化赋值,创建数据库[名]
	}

	/**
	 * 当数据库创建的时候,是第一次被执行,一般用于数据库表的创建,建表的语句及执行都在这里执行
	 * @支持的数据类型 [integer,varchar,text]，如 name varchar(64) not null,address text not null,id integer primary key autoincrement not null
	*/
	@Override
	public void onCreate(SQLiteDatabase db){
		/**
		 * 声明一个创建表的sql语句,支持的数据类型:整型类型;字符串类型[integer,varchar,text];日期类型;二进制数据类型;备注 如是boolean的false或true做字符串类型[varchar,text]来使用
		*/
		//String sql_table_users = "create table users (sysid integer primary key autoincrement not null,usid text not null, uname text not null,uslatime text not null,CONSTRAINT weiyi_uname UNIQUE (uname),CONSTRAINT weiyi_usid UNIQUE (usid))";
		final String sql_notes = "CREATE TABLE notes (notesid TEXT primary key NOT NULL,notestitle TEXT,notesdate TEXT,notescontent TEXT,usid TEXT NOT NULL,notesupdate TEXT)" ;
		//完成对数据库表的创建
		db.execSQL(sql_notes);
	}

	/**
	 * 本方法用于数据库表字段的修改,对表的修改而不是对表的记录修改，如要添加或更改某个列但是原先的数据库表的记录不会改变
	 * @注意 执行本方法时要注意的是 newVersion版本号要比oldVersion要大要高！否则不会执行本方法!!
	 * @本方法的功能 1:该方法函数在数据库需要升级时被调用，2:一般用来删除旧的数据库表，并将数据转移到新版本的数据库表中。
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//String sql_alter = "alters table person add sex varchar(4) " ;
		//db.execSQL(sql_alter);
	}
}