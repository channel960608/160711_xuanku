package com.shiguang.timefarmer.myapplication3;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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
public class UpdateEventActivity extends Activity {
    private int id;
    private EditText eventNameUpdate,eventDescription;
    private Switch event_type_update;
    private Button event_update_cancel,event_update_confirm,date_update_bn,time_update_bn,gotoPlant;
    private Spinner spinner_update;
    private SqlManager sqlUpdateManager;
    private Cursor eventDetail,eventRemindDetail;
    private String updatePreTime,dayofweek;
    private int updateYear,updateMonth,updateDay,updateHour,updateMinute,updatePreHour,updatePreMinute,updatePreDay,status;
    private Boolean updateType,updateIfRemind;
    private Calendar c;
    private Intent intent;
    private Bundle bundle;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_update_layout);
         intent=getIntent();
         bundle=intent.getExtras();
        id=bundle.getInt("id");
        c=Calendar.getInstance();

        eventNameUpdate=(EditText)findViewById(R.id.event_name_update);
        eventDescription=(EditText)findViewById(R.id.event_description_update);
        spinner_update=(Spinner)findViewById(R.id.spinner_update);
        event_update_confirm=(Button)findViewById(R.id.event_update_confirm);
        event_update_cancel=(Button)findViewById(R.id.event_update_cancel);
        event_type_update=(Switch)findViewById(R.id.event_type_update);
        date_update_bn=(Button)findViewById(R.id.date_update_bn);
        time_update_bn=(Button)findViewById(R.id.time_update_bn);
        gotoPlant=(Button)findViewById(R.id.goto_plant);
        sqlUpdateManager = new SqlManager(UpdateEventActivity.this);
        eventDetail=sqlUpdateManager.selectTable("schedule",UpdateEventActivity.this.id);
        eventDetail.moveToNext();
        updateYear=eventDetail.getInt(3);
        updateMonth=eventDetail.getInt(4);
        updateDay=eventDetail.getInt(5);
        updateHour=eventDetail.getInt(6);
        updateMinute=eventDetail.getInt(7);
        status=eventDetail.getInt(10);
        updateType=Boolean.parseBoolean(eventDetail.getString(8));
        if(Boolean.parseBoolean(eventDetail.getString(9))){
            eventRemindDetail=sqlUpdateManager.selectTable("eventremind",UpdateEventActivity.this.id);
            eventRemindDetail.moveToNext();
            int i;
            switch(eventRemindDetail.getString(4)){
                case "准时提醒":
                    i=1;
                    break;
                case "5分钟前":
                    i=2;
                    break;
                case "10分钟前":
                    i=3;
                    break;
                case "15分钟前":
                    i=4;
                    break;
                case "30分钟前":
                    i=5;
                    break;
                case "1小时前":
                    i=6;
                    break;
                case "1天前":
                    i=7;
                    break;
                default:
                    i=0;
                    break;
            }
            spinner_update.setSelection(i);
        }


        eventNameUpdate.setText(eventDetail.getString(1));
        eventDescription.setText(eventDetail.getString(2));
        event_update_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (event_update_confirm.getText().equals("编辑")) {
                    eventNameUpdate.setEnabled(true);
                    eventDescription.setEnabled(true);
                    event_type_update.setEnabled(true);
                    date_update_bn.setClickable(true);
                    date_update_bn.setEnabled(true);
                    time_update_bn.setEnabled(true);
                    spinner_update.setClickable(true);
                    eventNameUpdate.setFocusableInTouchMode(true);
                    eventDescription.setFocusableInTouchMode(true);
                    event_update_confirm.setText("保存");
                    gotoPlant.setClickable(false);

                } else {
                    String en = eventNameUpdate.getText().toString();
                    String ed = eventDescription.getText().toString();
                    if (en.trim().isEmpty()) {
                        Toast emptyToast = Toast.makeText(UpdateEventActivity.this, "请输入任务名称", Toast.LENGTH_SHORT);
                        emptyToast.show();
                    } else if (updateIfRemind == false) {
                        NewEvent newEvent = new NewEvent(en, ed, UpdateEventActivity.this.updateYear, UpdateEventActivity.this.updateMonth, UpdateEventActivity.this.updateDay
                                , UpdateEventActivity.this.updateHour, UpdateEventActivity.this.updateMinute);
                        newEvent.setEventType(updateType);
                        if(updateType) {
                            if(status==0){
                                newEvent.setStatus(1);
                            }
                        }
                        else{
                            if(status==1){
                                newEvent.setStatus(0);
                            }
                        }
                        newEvent.setIfRemind(false);
                        newEvent.setId(UpdateEventActivity.this.id);
                        newEvent.updateSQLite(sqlUpdateManager.getReadableDatabase());
                        newEvent.updateSQLite2(sqlUpdateManager.getReadableDatabase());
                        Toast.makeText(UpdateEventActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        sqlUpdateManager.close();
                        UpdateEventActivity.this.setResult(1,intent);
                        UpdateEventActivity.this.finish();

                    } else if (updateIfRemind == true) {
                        NewEvent newEvent = new NewEvent(en, ed, UpdateEventActivity.this.updateYear, UpdateEventActivity.this.updateMonth, UpdateEventActivity.this.updateDay
                                , UpdateEventActivity.this.updateHour, UpdateEventActivity.this.updateMinute);
                        newEvent.setIfRemind(true);
                        newEvent.setPreDay(UpdateEventActivity.this.updatePreDay);
                        newEvent.setPreHour(UpdateEventActivity.this.updatePreHour);
                        newEvent.setPreMinute(UpdateEventActivity.this.updatePreMinute);
                        newEvent.setEventType(updateType);
                        if(updateType) {
                            if(status==0){
                                newEvent.setStatus(1);
                            }
                        }
                        else{
                            if(status==1){
                                newEvent.setStatus(0);
                            }
                        }
                        newEvent.setPreTime(UpdateEventActivity.this.updatePreTime);
                        newEvent.setId(UpdateEventActivity.this.id);
                        newEvent.updateSQLite(sqlUpdateManager.getReadableDatabase());
                        newEvent.updateSQLite2(sqlUpdateManager.getReadableDatabase());
                        Toast.makeText(UpdateEventActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        sqlUpdateManager.close();
                        UpdateEventActivity.this.setResult(1,intent);
                        UpdateEventActivity.this.finish();

                    } else {

                    }

                }

            }
        });

        event_update_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlUpdateManager.close();
                UpdateEventActivity.this.finish();
            }
        });
        event_type_update.setChecked(Boolean.parseBoolean(eventDetail.getString(8)));
        event_type_update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                UpdateEventActivity.this.updateType = ischecked;
            }
        });

        c.set(Calendar.YEAR,eventDetail.getInt(3));
        c.set(Calendar.MONTH,eventDetail.getInt(4));
        c.set(Calendar.DAY_OF_MONTH,eventDetail.getInt(5));
        c.set(Calendar.HOUR_OF_DAY, eventDetail.getInt(6));
        c.set(Calendar.MINUTE, eventDetail.getInt(7));
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                UpdateEventActivity.this.dayofweek = "星期天";
                break;
            case 2:
                UpdateEventActivity.this.dayofweek = "星期一";
                break;
            case 3:
                UpdateEventActivity.this.dayofweek = "星期二";
                break;
            case 4:
                UpdateEventActivity.this.dayofweek = "星期三";
                break;
            case 5:
                UpdateEventActivity.this.dayofweek = "星期四";
                break;
            case 6:
                UpdateEventActivity.this.dayofweek = "星期五";
                break;
            case 7:
                UpdateEventActivity.this.dayofweek = "星期六";
                break;
            default:
                break;
        }
        date_update_bn.setText(eventDetail.getInt(3) + " 年 " + (eventDetail.getInt(4) + 1) + " 月 " + eventDetail.getInt(5) + " 日 " + UpdateEventActivity.this.dayofweek);
        if(eventDetail.getInt(7)<10){
            time_update_bn.setText(eventDetail.getInt(6)+" : 0"+eventDetail.getInt(7));
        }else{
            time_update_bn.setText(eventDetail.getInt(6)+" : "+eventDetail.getInt(7));
        }

        date_update_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateEventActivity.this, 3,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                UpdateEventActivity.this.updateYear = year;
                                UpdateEventActivity.this.updateMonth = month;
                                UpdateEventActivity.this.updateDay = dayOfMonth;
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, month);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                switch (c.get(Calendar.DAY_OF_WEEK)) {
                                    case 1:
                                        UpdateEventActivity.this.dayofweek = "星期天";
                                        break;
                                    case 2:
                                        UpdateEventActivity.this.dayofweek = "星期一";
                                        break;
                                    case 3:
                                        UpdateEventActivity.this.dayofweek = "星期二";
                                        break;
                                    case 4:
                                        UpdateEventActivity.this.dayofweek = "星期三";
                                        break;
                                    case 5:
                                        UpdateEventActivity.this.dayofweek = "星期四";
                                        break;
                                    case 6:
                                        UpdateEventActivity.this.dayofweek = "星期五";
                                        break;
                                    case 7:
                                        UpdateEventActivity.this.dayofweek = "星期六";
                                        break;
                                    default:
                                        break;
                                }
                                date_update_bn.setText(updateYear + " 年 " + (updateMonth + 1) + " 月 " + updateDay + " 日 " + UpdateEventActivity.this.dayofweek);
                            }
                        }, updateYear, updateMonth, updateDay).show();
            }
        });
        time_update_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(UpdateEventActivity.this,3, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        UpdateEventActivity.this.updateHour = hour;
                        UpdateEventActivity.this.updateMinute = minute;
                        if(UpdateEventActivity.this.updateMinute<10){
                            time_update_bn.setText(UpdateEventActivity.this.updateHour + ":0" + UpdateEventActivity.this.updateMinute);
                        }else{
                            time_update_bn.setText(UpdateEventActivity.this.updateHour + ":" + UpdateEventActivity.this.updateMinute);
                        }

                    }
                },updateHour, updateMinute, false).show();
            }
        });


        spinner_update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UpdateEventActivity.this.updatePreTime = adapterView.getItemAtPosition(i).toString();
                switch (adapterView.getItemAtPosition(i).toString()) {
                    case "不提醒":
                        updateIfRemind = false;
                        break;
                    case "准时提醒":
                        updateIfRemind = true;
                        updatePreDay = updateDay;
                        updatePreHour = updateHour;
                        updatePreMinute = updateMinute;
                        break;
                    case "5分钟前":
                        updateIfRemind = true;
                        if (updateMinute >= 5) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour;
                            updatePreMinute = updateMinute - 5;
                        } else if (updateMinute < 5 && updateHour > 0) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour - 1;
                            updatePreMinute = updateMinute + 55;
                        } else if (updateMinute < 5 && updateHour == 0 && updateDay > 1) {
                            updatePreDay = updateDay - 1;
                            updatePreHour = updateHour + 23;
                            updatePreMinute = updateMinute + 55;
                        } else if (updateMinute < 5 && updateHour == 0 && updateDay == 1) {

                        } else ;
                        break;
                    case "10分钟前":
                        updateIfRemind = true;
                        if (updateMinute >= 10) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour;
                            updatePreMinute = updateMinute - 10;
                        } else if (updateMinute < 10 && updateHour > 0) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour - 1;
                            updatePreMinute = updateMinute + 50;
                        } else if (updateMinute < 10 && updateHour == 0 && updateDay > 1) {
                            updatePreDay = updateDay - 1;
                            updatePreHour = updateHour + 23;
                            updatePreMinute = updateMinute + 50;
                        } else if (updateMinute < 5 && updateHour == 0 && updateDay == 1) {

                        } else ;
                        break;
                    case "15分钟前":
                        updateIfRemind = true;
                        if (updateMinute >= 15) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour;
                            updatePreMinute = updateMinute - 15;
                        } else if (updateMinute < 15 && updateHour > 0) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour - 1;
                            updatePreMinute = updateMinute + 45;
                        } else if (updateMinute < 15 && updateHour == 0 && updateDay > 1) {
                            updatePreDay = updateDay - 1;
                            updatePreHour = updateHour + 23;
                            updatePreMinute = updateMinute + 45;
                        } else if (updateMinute < 5 && updateHour == 0 && updateDay == 1) {

                        } else ;
                        break;
                    case "30分钟前":
                        updateIfRemind = true;
                        if (updateMinute >= 30) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour;
                            updatePreMinute = updateMinute - 30;
                        } else if (updateMinute < 30 && updateHour > 0) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour - 1;
                            updatePreMinute = updateMinute + 30;
                        } else if (updateMinute < 30 && updateHour == 0 && updateDay > 1) {
                            updatePreDay = updateDay - 1;
                            updatePreHour = updateHour + 23;
                            updatePreMinute = updateMinute + 30;
                        } else if (updateMinute < 30 && updateHour == 0 && updateDay == 1) {

                        } else ;
                        break;
                    case "1小时前":
                        updateIfRemind = true;
                        if (updateHour > 1) {
                            updatePreDay = updateDay;
                            updatePreHour = updateHour - 1;
                            updatePreMinute = updateMinute;
                        } else if (updateHour < 1 && updateDay > 1) {
                            updatePreDay = updateDay - 1;
                            updatePreHour = updateHour + 23;
                            updatePreMinute = updateMinute;
                        } else ;
                        break;
                    case "1天前":
                        updateIfRemind = true;
                        if (updateDay > 1) {
                            updatePreDay = updateDay - 1;
                            updatePreHour = updateHour;
                            updatePreMinute = updateMinute;
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
