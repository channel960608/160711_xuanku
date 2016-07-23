package com.shiguang.timefarmer.myapplication3;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shiguang.timefarmer.myapplication3.tool.SqlManager;

/**
 * Created by Administrator on 2016/7/22.
 */
public class UpdateCustomActivity extends Activity {
    private Intent intent;
    private Bundle bundle;
    private int id;
    private Button customUpdateCancel,customUpdateConfirm,customUpdateTime;
    private TextView customUpdateName;
    private SqlManager customUpdateSql;
    private Cursor cursor;
    private Switch customUpdateIfRemind;
    private int updateHour,updateMinute;
    private boolean ifRemind;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_update_layout);
        intent = getIntent();
        bundle = intent.getExtras();
        id = bundle.getInt("id");
        customUpdateSql=new SqlManager(UpdateCustomActivity.this);
        cursor=customUpdateSql.selectTable("custom",id);
        customUpdateCancel=(Button)findViewById(R.id.custom_update_cancel);
        customUpdateConfirm=(Button)findViewById(R.id.custom_update_confirm);
        customUpdateTime=(Button)findViewById(R.id.custom_update_time);
        customUpdateName=(TextView)findViewById(R.id.custom_update_name);
        customUpdateIfRemind=(Switch)findViewById(R.id.custom_update_ifremind);
        customUpdateTime.setEnabled(false);
        customUpdateName.setEnabled(false);
        customUpdateIfRemind.setEnabled(false);
        while (cursor.moveToNext()){
            if(cursor.getInt(3)<10){
                customUpdateTime.setText(cursor.getInt(2)+":0"+cursor.getInt(3));
            }else{
                customUpdateTime.setText(cursor.getInt(2)+":"+cursor.getInt(3));
            }
            customUpdateName.setText(cursor.getString(1));

            customUpdateIfRemind.setChecked(Boolean.parseBoolean(cursor.getString(4)));
        }
        customUpdateConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customUpdateConfirm.getText().equals("编辑")){
                    customUpdateTime.setClickable(true);
                    customUpdateName.setEnabled(true);
                    customUpdateIfRemind.setEnabled(true);
                    customUpdateConfirm.setText("保存");
                }
                else{
                    if(customUpdateName.getText().toString().trim().equals("")){
                        Toast.makeText(UpdateCustomActivity.this,"请输入习惯名称！",Toast.LENGTH_SHORT).show();
                    }else{
                        customUpdateSql.getReadableDatabase().execSQL("update custom set customName='"+customUpdateName.getText().toString()
                                +"',customHour="+updateHour+",customMinute="+updateMinute+",ifRemind='"+customUpdateIfRemind.isChecked()+"' where _id="+id);
                        customUpdateSql.close();
                        UpdateCustomActivity.this.setResult(1,intent);
                        UpdateCustomActivity.this.finish();
                    }
                }
            }
        });

        customUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customUpdateSql.close();
                UpdateCustomActivity.this.finish();
            }
        });

        customUpdateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(UpdateCustomActivity.this,3, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        updateHour = hour;
                        updateMinute = minute;
                        if(updateMinute<10){
                            customUpdateTime.setText(updateHour + ":0" + updateMinute);
                        }else{
                            customUpdateTime.setText(updateHour + ":" + updateMinute);
                        }

                    }
                },updateHour, updateMinute, false).show();
            }
        });




    }


}
