package com.shiguang.timefarmer.myapplication3.tool;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/7/22.
 */
public class NewCustom {
    private int id,status=0,hour,minute;
    private String name;
    private boolean ifRemind=false,mon=false,tue=false,wed=false,thu=false,fri=false,sat=false,sun=false;
    public NewCustom(String name,int hour,int minute,boolean ifRemind,int status){
        this.name=name;
        this.hour=hour;
        this.minute=minute;
        this.ifRemind=ifRemind;
        this.status=status;
    }
    public void setId(int id){this.id=id;}
    public int getId(){return id;}
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setHour(int hour){
        this.hour=hour;
    }
    public int getHour(){
        return hour;
    }
    public void setMinute(int minute){
        this.minute=minute;
    }
    public int getMinute(){return minute;}
    public void setStatus(int status){
        this.status=status;
    }
    public int getStatus(){
        return status;
    }
    public void  setIfRemind(boolean ifRemind){
        this.ifRemind=ifRemind;
    }
    public boolean getIfRemind(){
        return ifRemind;
    }

    public void addToSQLite(SQLiteDatabase db){
        db.execSQL("insert into custom values(null,'"+name+"',"+hour+","+minute+","+ifRemind+","+status+")");
    }

}
