package com.shiguang.timefarmer.myapplication3;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/16.
 */
public class AddEventFragment extends Fragment  {
    View addEventView;
    private int year,month,day,hour,minute;
    private String dayofweek,preTime="不提醒";
    private Boolean ifRemind=false,eventType=false;
    private  int preMonth,preDay,preHour,preMinute;
    SqlManager dbHelper;
    Calendar calendar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化刚打开新建任务页面时的日期、时间
        calendar=Calendar.getInstance();
        this.year=calendar.get(Calendar.YEAR);
        this.month=calendar.get(Calendar.MONTH);
        this.day=calendar.get(Calendar.DAY_OF_MONTH);
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
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
        dbHelper=new SqlManager(AddEventFragment.this.getActivity(),"shiguangDatabase.db",null,1);



    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        addEventView =inflater.inflate(R.layout.event_add_layout, container, false);
        //日期选择按钮
        final Button datebn=(Button)addEventView.findViewById(R.id.datebn);
        String dbn=new String(AddEventFragment.this.year + " 年 " + (AddEventFragment.this.month+1) + " 月 " + AddEventFragment.this.day + " 日 "
                + AddEventFragment.this.dayofweek);
        datebn.setText(dbn);
        //时间选择按钮
        final Button timebn=(Button)addEventView.findViewById(R.id.timebn);
        if(AddEventFragment.this.minute<10){
            timebn.setText(AddEventFragment.this.hour + ":0" + AddEventFragment.this.minute);
        }else{
            timebn.setText(AddEventFragment.this.hour + ":" + AddEventFragment.this.minute);
        }

        datebn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEventFragment.this.getActivity(),
                        3,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                AddEventFragment.this.year = year;
                                AddEventFragment.this.month = month;
                                AddEventFragment.this.day = dayOfMonth;
                                calendar.set(Calendar.YEAR,year);
                                calendar.set(Calendar.MONTH,month);
                                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                                    case 1:
                                        AddEventFragment.this.dayofweek = "星期天";
                                        break;
                                    case 2:
                                        AddEventFragment.this.dayofweek = "星期一";
                                        break;
                                    case 3:
                                        AddEventFragment.this.dayofweek = "星期二";
                                        break;
                                    case 4:
                                        AddEventFragment.this.dayofweek = "星期三";
                                        break;
                                    case 5:
                                        AddEventFragment.this.dayofweek = "星期四";
                                        break;
                                    case 6:
                                        AddEventFragment.this.dayofweek = "星期五";
                                        break;
                                    case 7:
                                        AddEventFragment.this.dayofweek = "星期六";
                                        break;
                                    default:
                                        break;
                                }
                                datebn.setText(AddEventFragment.this.year + " 年 " + (AddEventFragment.this.month + 1) + " 月 " + AddEventFragment.this.day + " 日 "
                                        + AddEventFragment.this.dayofweek);
                            }
                        }, year
                        , month
                        , day).show();


            }
        });
        timebn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new TimePickerDialog(AddEventFragment.this.getActivity(),3, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        AddEventFragment.this.hour = i;
                        AddEventFragment.this.minute = i1;
                        if(AddEventFragment.this.minute<10){
                            timebn.setText(AddEventFragment.this.hour + ":0" + AddEventFragment.this.minute);
                        }else{
                            timebn.setText(AddEventFragment.this.hour + ":" + AddEventFragment.this.minute);
                        }

                    }
                }, hour, minute, false).show();

            }
        });

        //任务类型开关
        Switch switch1=(Switch)addEventView.findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                eventType=isChecked;
            }
        });

        //任务名称和详情描述编辑框
        final EditText eventName=(EditText)addEventView.findViewById(R.id.event_name);
        final EditText eventDes=(EditText)addEventView.findViewById(R.id.event_description);

        //确认和取消按钮
        Button confirm=(Button)addEventView.findViewById(R.id.event_add_confirm);
        Button cancel=(Button)addEventView.findViewById(R.id.event_add_cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String en=eventName.getText().toString();
                String ed=eventDes.getText().toString();
                if(en.trim().isEmpty()&&ed.trim().isEmpty()){
                    Toast emptyToast=Toast.makeText(AddEventFragment.this.getActivity(),"请输入任务名称或描述",Toast.LENGTH_SHORT);
                    emptyToast.show();
                }
                else if(ifRemind==false){
                    NewEvent newEvent=new NewEvent(en,ed,AddEventFragment.this.year,AddEventFragment.this.month,AddEventFragment.this.day
                    ,AddEventFragment.this.hour,AddEventFragment.this.minute);
                    newEvent.setEventType(eventType);
                    newEvent.addToSQLite(dbHelper.getReadableDatabase());

                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction transactionaef=fm.beginTransaction();
                    Fragment scheduleFragment=new ScheduleFragment();
                    transactionaef.replace(R.id.ly_content, scheduleFragment);
                    transactionaef.commit();
                    dbHelper.close();


                }else if(ifRemind==true){
                    NewEvent newEvent=new NewEvent(en,ed,AddEventFragment.this.year,AddEventFragment.this.month,AddEventFragment.this.day
                            ,AddEventFragment.this.hour,AddEventFragment.this.minute);
                    newEvent.setIfRemind(true);
                    newEvent.setPreDay(AddEventFragment.this.preDay);
                    newEvent.setPreHour(AddEventFragment.this.preHour);
                    newEvent.setPreMinute(AddEventFragment.this.preMinute);
                    newEvent.setEventType(eventType);
                    newEvent.setPreTime(AddEventFragment.this.preTime);
                    newEvent.addToSQLite(dbHelper.getReadableDatabase());
                    newEvent.addToSQLite2(dbHelper.getReadableDatabase());

                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction transactionaef=fm.beginTransaction();
                    Fragment scheduleFragment=new ScheduleFragment();
                    transactionaef.replace(R.id.ly_content, scheduleFragment);
                    transactionaef.commit();
                    dbHelper.close();


                }else{

                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction transactionaef=fm.beginTransaction();
                Fragment scheduleFragment=new ScheduleFragment();
                transactionaef.replace(R.id.ly_content, scheduleFragment);
                transactionaef.commit();
                dbHelper.close();
            }
        });
        //提醒方式选择
        Spinner spinner=(Spinner)addEventView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AddEventFragment.this.preTime=adapterView.getItemAtPosition(i).toString();
                switch (adapterView.getItemAtPosition(i).toString()) {
                    case "不提醒":
                        ifRemind=false;
                        break;
                    case "准时提醒":
                        ifRemind=true;
                        preDay = day;
                        preHour = hour;
                        preMinute = minute;
                        break;
                    case "5分钟前":
                        ifRemind=true;
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
                        ifRemind=true;
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
                        ifRemind=true;
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
                        ifRemind=true;
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
                        ifRemind=true;
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
                        ifRemind=true;
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

        return addEventView;
    }


}
