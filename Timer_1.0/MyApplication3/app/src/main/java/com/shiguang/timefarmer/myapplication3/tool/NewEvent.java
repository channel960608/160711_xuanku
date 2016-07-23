package com.shiguang.timefarmer.myapplication3.tool;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/15.
 */
public class NewEvent {
    private int id;
    private String eventName;
    private String description;
    private int year,month,day,hour,minute;
    private int EVENT_WAITIMP=1,EVENT_WAIT=0,EVENT_FINISHED=-1;
    private int status=EVENT_WAIT;
    private boolean ifRemind=false;
    private int preDay,preHour,preMinute;
    private boolean eventType;
    private String preTime;


    public  NewEvent(String eventName,String description,int year,int month,int day,int hour,int minute){
        this.eventName=eventName;
        this.description=description;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.minute=minute;

    }
    public NewEvent(String eventName,int year,int month,int day,int hour,int minute){
        this.eventName=eventName;
        this.description=eventName;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.minute=minute;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String eventName){
        this.eventName=eventName;
    }
    public String getName(){
        return this.eventName;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
    }
    public void setYear(int year){
        this.year=year;
    }
    public int getYear(){
        return this.year;
    }
    public void setMonth(int month){
        this.month=month;
    }
    public int getMonth(){return this.month;}
    public void setDay(int day){
        this.day=day;
    }
    public int getDay(){return this.day;}
    public void setHour(int hour){
        this.hour=hour;
    }
    public int getHour(){return this.hour;}
    public void setMinute(int minute){
        this.minute=minute;
    }
    public int getMinute(){return this.minute;}
    public void setStatus(int status){
        this.status=status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setIfRemind(boolean ifRemind){
        this.ifRemind=ifRemind;
    }
    public boolean getIfRemind(){
        return this.ifRemind;
    }

    public void setPreDay(int preDay)
    {
        this.preDay=preDay;
    }
    public void setPreHour(int preHour){
        this.preHour=preHour;
    }
    public void setPreMinute(int preMinute){
        this.preMinute=preMinute;
    }
    public void setPreTime(String preTime){this.preTime=preTime;}
    public String getPreTime(){return preTime;}
    public void setEventType(boolean eventType){
        this.eventType=eventType;
    }
    public boolean getEventType(){
        return eventType;
    }
    public boolean addToSQLite(SQLiteDatabase db){
        try{
            String in=new String("insert into schedule values(null,'"+this.eventName+"','"+this.description
            +"','"+this.year+"','"+this.month+"','"+this.day+"','"+this.hour+"','"+this.minute+"','"+this.eventType+"','" +
                    this.ifRemind+"','"+this.status+"')");
            db.execSQL(in);

        }catch (SQLException e){
            System.out.println("新事件插入错误");
        }
        return true;
    }
    public boolean addToSQLite2(SQLiteDatabase db){
        try{
            Cursor c=db.rawQuery("select MAX(_id) from schedule", null);
            int scheduleid;
            if(c.moveToFirst()){
                scheduleid=c.getInt(0);
                String in2=new String("insert into eventremind values('"+scheduleid+"','" +
                       preDay+"','"+
                        preHour+"','" +
                        preMinute+"','"+
                        preTime+"')");
                db.execSQL(in2);
            }
        }catch (SQLException r){
            System.out.println("新事件插入错误2");
        }
        return true;
    }
    public boolean updateSQLite(SQLiteDatabase db){
        try{
            db.execSQL("update schedule set eventName='"+this.eventName+"',description='"+this.description+"',year='"+
                    this.year+"',month='"+this.month+"',day='"+this.day+"',hour='"+this.hour+"',minute='"+this.minute+
                    "',eventType='"+this.eventType+"',ifRemind='"+this.ifRemind+"',status='"+this.status+"' where _id='"+this.id+"'");
        }catch(SQLException r){
            System.out.println("事件更新错误");
        }
        return true;
    }
    public boolean updateSQLite2(SQLiteDatabase db){
        try{
            Cursor c1=db.rawQuery("select * from eventremind where scheduleid='"+id+"'",null);
            if(c1.moveToNext()){
                if(ifRemind){
                    db.execSQL("update eventremind set preDay='"+this.preDay+"',preHour='"+this.preHour+"',preMinute='"+this.preMinute+"',preTime='"+this.preTime+"' where scheduleid='"+this.id+"'");

                }
                else{
                    db.execSQL("delete from eventremind where scheduleid='" + id + "'");

                }
            }else{
                if(ifRemind){
                    db.execSQL("insert into  eventremind values('"+this.id+"','"+this.preDay+"','"+this.preHour+"','"+this.preMinute+"','"+this.preTime+"') ");
                }
                else{

                }
            }
        }catch (SQLException r) {
            System.out.println("事件更新错误2");
        }
        return true;
    }


}
