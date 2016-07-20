package com.shiguang.timefarmer.myapplication3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/15.
 */
public class SqlManager extends SQLiteOpenHelper {
    private String CREATE_TABLE_SCHEDULE=new String("create table schedule(_id integer primary key autoincrement,"
    +"eventName varchar(50)," +
            "description varchar(150)," +
            "year integer," +
            "month integer," +
            "day integer," +
            "hour integer," +
            "minute integer," +
            "eventType boolean," +
            "ifRemind boolean," +
            "status integer)");
    private String CREATE_TABLE_EVENTREMIND=new String("create table eventremind(scheduleid integer," +
            "preDay integer," +
            "preHour integer," +
            "preMinute integer," +
            "preTime varchar(10))");


    public SqlManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SCHEDULE);
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENTREMIND);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor selectTable(String table){
        Cursor cursor=this.getReadableDatabase().rawQuery("select * from ?",new String[]{table});
        return cursor;
    }
    public Cursor selectTable(String table,int year,int month,int day){
        String select=new String("select * from '"+table+"' where   year='"+year+"'and month='"+month+"'and day='"+day+"' order by status desc");
        Cursor cursor=this.getReadableDatabase().rawQuery(select,null);
        return cursor;
    }
    public boolean addEventRemind(int preDay,int preHour,int preMinute){
        int remindId=this.selectTable(CREATE_TABLE_SCHEDULE).getCount();
        String in=new String("insert into eventremind values("+remindId+","+preDay+","+preHour+","+preMinute+")");
        this.getReadableDatabase().execSQL(in);
        return true;
    }
    public Cursor selectTable(String table,int id){
        String select;
        if(table.equals("schedule")){
            select=new String("select * from '"+table+"' where _id='"+id+"'");
        }
        else if(table.equals("eventremind")){
            select=new String("select * from '"+table+"' where scheduleid='"+id+"'");
        }
        else select="";
        Cursor cursor=this.getReadableDatabase().rawQuery(select,null);
        return cursor;
    }
}
