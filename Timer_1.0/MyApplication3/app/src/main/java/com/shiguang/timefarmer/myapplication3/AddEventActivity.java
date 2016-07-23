package com.shiguang.timefarmer.myapplication3;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shiguang.timefarmer.myapplication3.tool.NewEvent;
import com.shiguang.timefarmer.myapplication3.tool.SqlManager;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/21.
 */
public class AddEventActivity extends Activity {
    private int year,month,day,hour,minute;
    private String dayofweek,preTime="不提醒";
    private Boolean ifRemind=false,eventType=false;
    private  int preDay,preHour,preMinute;
    private SqlManager dbHelper;
    private Calendar calendar;
    private Intent intent;
    private Bundle bundle;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add_layout);
        intent=getIntent();
        bundle=intent.getExtras();
        calendar=Calendar.getInstance();
        this.year=bundle.getInt("year");
        this.month=bundle.getInt("month");
        this.day=bundle.getInt("day");
        switch(bundle.getInt("dayofWeek")){
            case 1:
                this.dayofweek="星期天";
                break;
            case 2:
                this.dayofweek="星期一";
                break;
            case 3:
                this.dayofweek="星期二";
                break;
            case 4:
                this.dayofweek="星期三";
                break;
            case 5:
                this.dayofweek="星期四";
                break;
            case 6:
                this.dayofweek="星期五";
                break;
            case 7:
                this.dayofweek="星期六";
                break;
            default:
                break;
        }
        this.hour=calendar.get(Calendar.HOUR_OF_DAY);
        this.minute=calendar.get(Calendar.MINUTE);
        dbHelper=new SqlManager(AddEventActivity.this,"shiguangDatabase.db",null,1);
        //日期选择按钮
        final Button datebn=(Button)findViewById(R.id.datebn);
        String dbn=new String(AddEventActivity.this.year + " 年 " + (AddEventActivity.this.month+1) + " 月 " + AddEventActivity.this.day + " 日 "
                + AddEventActivity.this.dayofweek);
        datebn.setText(dbn);
        //时间选择按钮
        final Button timebn=(Button)findViewById(R.id.timebn);
        if(AddEventActivity.this.minute<10){
            timebn.setText(AddEventActivity.this.hour + ":0" + AddEventActivity.this.minute);
        }else{
            timebn.setText(AddEventActivity.this.hour + ":" + AddEventActivity.this.minute);
        }
        //日期设置
        datebn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEventActivity.this,
                        3,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                AddEventActivity.this.year = year;
                                AddEventActivity.this.month = month;
                                AddEventActivity.this.day = dayOfMonth;
                                calendar.set(Calendar.YEAR,year);
                                calendar.set(Calendar.MONTH,month);
                                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                                    case 1:
                                        AddEventActivity.this.dayofweek = "星期天";
                                        break;
                                    case 2:
                                        AddEventActivity.this.dayofweek = "星期一";
                                        break;
                                    case 3:
                                        AddEventActivity.this.dayofweek = "星期二";
                                        break;
                                    case 4:
                                        AddEventActivity.this.dayofweek = "星期三";
                                        break;
                                    case 5:
                                        AddEventActivity.this.dayofweek = "星期四";
                                        break;
                                    case 6:
                                        AddEventActivity.this.dayofweek = "星期五";
                                        break;
                                    case 7:
                                        AddEventActivity.this.dayofweek = "星期六";
                                        break;
                                    default:
                                        break;
                                }
                                datebn.setText(AddEventActivity.this.year + " 年 " + (AddEventActivity.this.month + 1) + " 月 " + AddEventActivity.this.day + " 日 "
                                        + AddEventActivity.this.dayofweek);
                            }
                        }, year
                        , month
                        , day).show();


            }
        });
        //时间设置
        timebn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new TimePickerDialog(AddEventActivity.this,3, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        AddEventActivity.this.hour = i;
                        AddEventActivity.this.minute = i1;
                        if(AddEventActivity.this.minute<10){
                            timebn.setText(AddEventActivity.this.hour + ":0" + AddEventActivity.this.minute);
                        }else{
                            timebn.setText(AddEventActivity.this.hour + ":" + AddEventActivity.this.minute);
                        }

                    }
                }, hour, minute, false).show();

            }
        });

        //任务类型开关
        Switch switch1=(Switch)findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                eventType=isChecked;
            }
        });

        //任务名称和详情描述编辑框
        final EditText eventName=(EditText)findViewById(R.id.event_name);
        final EditText eventDes=(EditText)findViewById(R.id.event_description);

        //确认和取消按钮
        Button confirm=(Button)findViewById(R.id.event_add_confirm);
        Button cancel=(Button)findViewById(R.id.event_add_cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String en=eventName.getText().toString();
                String ed=eventDes.getText().toString();
                if(en.trim().isEmpty()){
                    Toast emptyToast=Toast.makeText(AddEventActivity.this,"请输入任务名称",Toast.LENGTH_SHORT);
                    emptyToast.show();
                }
                else if(ifRemind==false){
                    NewEvent newEvent=new NewEvent(en,ed,AddEventActivity.this.year,AddEventActivity.this.month,AddEventActivity.this.day
                            ,AddEventActivity.this.hour,AddEventActivity.this.minute);
                    newEvent.setEventType(eventType);
                    if(eventType){newEvent.setStatus(1);}
                    else{newEvent.setStatus(0);}
                    newEvent.addToSQLite(dbHelper.getReadableDatabase());
                    dbHelper.close();
                    Intent intent=getIntent();
                    AddEventActivity.this.setResult(0,intent);
                    Toast.makeText(AddEventActivity.this,"任务添加成功！",Toast.LENGTH_SHORT).show();
                    AddEventActivity.this.finish();

                }else if(ifRemind==true){
                    NewEvent newEvent=new NewEvent(en,ed,AddEventActivity.this.year,AddEventActivity.this.month,AddEventActivity.this.day
                            ,AddEventActivity.this.hour,AddEventActivity.this.minute);
                    newEvent.setIfRemind(true);
                    if(eventType){newEvent.setStatus(1);}
                    else{newEvent.setStatus(0);}
                    newEvent.setPreDay(AddEventActivity.this.preDay);
                    newEvent.setPreHour(AddEventActivity.this.preHour);
                    newEvent.setPreMinute(AddEventActivity.this.preMinute);
                    newEvent.setEventType(eventType);

                    newEvent.setPreTime(AddEventActivity.this.preTime);
                    newEvent.addToSQLite(dbHelper.getReadableDatabase());
                    newEvent.addToSQLite2(dbHelper.getReadableDatabase());

                    dbHelper.close();
                    Intent intent=getIntent();
                    AddEventActivity.this.setResult(0, intent);
                    Toast.makeText(AddEventActivity.this,"任务添加成功！",Toast.LENGTH_SHORT).show();
                    AddEventActivity.this.finish();

                }else{

                }


            }
        });
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.close();
                AddEventActivity.this.finish();

            }
        });
        //提醒方式选择
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AddEventActivity.this.preTime = adapterView.getItemAtPosition(i).toString();
                switch (adapterView.getItemAtPosition(i).toString()) {
                    case "不提醒":
                        ifRemind = false;
                        break;
                    case "准时提醒":
                        ifRemind = true;
                        preDay = day;
                        preHour = hour;
                        preMinute = minute;
                        break;
                    case "5分钟前":
                        ifRemind = true;
                        if (minute >= 5) {
                            preDay = day;
                            preHour = hour;
                            preMinute = minute - 5;
                        } else if (minute < 5 && hour > 0) {
                            preDay = day;
                            preHour = hour - 1;
                            preMinute = minute + 55;
                        } else if (minute < 5 && hour == 0 && day > 1) {
                            preDay = day - 1;
                            preHour = hour + 23;
                            preMinute = minute + 55;
                        } else if (minute < 5 && hour == 0 && day == 1) {

                        } else ;
                        break;
                    case "10分钟前":
                        ifRemind = true;
                        if (minute >= 10) {
                            preDay = day;
                            preHour = hour;
                            preMinute = minute - 10;
                        } else if (minute < 10 && hour > 0) {
                            preDay = day;
                            preHour = hour - 1;
                            preMinute = minute + 50;
                        } else if (minute < 10 && hour == 0 && day > 1) {
                            preDay = day - 1;
                            preHour = hour + 23;
                            preMinute = minute + 50;
                        } else if (minute < 5 && hour == 0 && day == 1) {

                        } else ;
                        break;
                    case "15分钟前":
                        ifRemind = true;
                        if (minute >= 15) {
                            preDay = day;
                            preHour = hour;
                            preMinute = minute - 15;
                        } else if (minute < 15 && hour > 0) {
                            preDay = day;
                            preHour = hour - 1;
                            preMinute = minute + 45;
                        } else if (minute < 15 && hour == 0 && day > 1) {
                            preDay = day - 1;
                            preHour = hour + 23;
                            preMinute = minute + 45;
                        } else if (minute < 5 && hour == 0 && day == 1) {

                        } else ;
                        break;
                    case "30分钟前":
                        ifRemind = true;
                        if (minute >= 30) {
                            preDay = day;
                            preHour = hour;
                            preMinute = minute - 30;
                        } else if (minute < 30 && hour > 0) {
                            preDay = day;
                            preHour = hour - 1;
                            preMinute = minute + 30;
                        } else if (minute < 30 && hour == 0 && day > 1) {
                            preDay = day - 1;
                            preHour = hour + 23;
                            preMinute = minute + 30;
                        } else if (minute < 30 && hour == 0 && day == 1) {

                        } else ;
                        break;
                    case "1小时前":
                        ifRemind = true;
                        if (hour > 1) {
                            preDay = day;
                            preHour = hour - 1;
                            preMinute = minute;
                        } else if (hour < 1 && day > 1) {
                            preDay = day - 1;
                            preHour = hour + 23;
                            preMinute = minute;
                        } else ;
                        break;
                    case "1天前":
                        ifRemind = true;
                        if (day > 1) {
                            preDay = day - 1;
                            preHour = hour;
                            preMinute = minute;
                        } else ;
                        break;
                    default:
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }

}
