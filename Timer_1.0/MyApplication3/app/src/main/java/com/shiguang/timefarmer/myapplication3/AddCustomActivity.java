package com.shiguang.timefarmer.myapplication3;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shiguang.timefarmer.myapplication3.tool.SqlManager;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/21.
 */
public class AddCustomActivity extends Activity {
    private Button custom_add_cancel,custom_add_confirm,custom_add_time;
    private TextView custom_add_name;
    private Switch custom_add_ifremind;
    private String customName;
    private int customHour,customMinute,status;
    private boolean ifRemind=false;
    private SqlManager addCustomSql;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_add_layout);
        custom_add_cancel=(Button)findViewById(R.id.custom_add_cancel);
        custom_add_confirm=(Button)findViewById(R.id.custom_add_confirm);
        custom_add_name=(TextView)findViewById(R.id.custom_add_name);
        custom_add_time=(Button)findViewById(R.id.custom_add_time);
        custom_add_ifremind=(Switch)findViewById(R.id.custom_add_ifremind);
        addCustomSql=new SqlManager(AddCustomActivity.this);

        custom_add_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomSql.close();
                AddCustomActivity.this.finish();
            }
        });
        custom_add_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custom_add_name.getText().toString().trim().equals("")) {
                    Toast.makeText(AddCustomActivity.this, "请输入习惯名称", Toast.LENGTH_SHORT).show();
                } else {
                    customName = custom_add_name.getText().toString();
                    addCustomSql.addCustom(customName, customHour, customMinute, ifRemind, 0);
                    addCustomSql.close();
                    Intent intent=getIntent();
                    AddCustomActivity.this.setResult(0, intent);
                    Toast.makeText(AddCustomActivity.this,"习惯添加成功！",Toast.LENGTH_SHORT).show();
                    AddCustomActivity.this.finish();

                }
            }
        });
        Calendar c=Calendar.getInstance();
        customHour=c.get(Calendar.HOUR_OF_DAY);
        customMinute=c.get(Calendar.MINUTE);
        if (customMinute < 10) {
            custom_add_time.setText(customHour + ":0" + customMinute);
        } else {
            custom_add_time.setText(customHour + ":" + customMinute);
        }
        custom_add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddCustomActivity.this, 3, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        customHour = i;
                        customMinute = i1;
                        if (customMinute < 10) {
                            custom_add_time.setText(customHour + ":0" + customMinute);
                        } else {
                            custom_add_time.setText(customHour + ":" + customMinute);
                        }

                    }
                }, customHour, customMinute, false).show();
            }
        });
        custom_add_ifremind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ifRemind=isChecked;
            }
        });




    }
}
