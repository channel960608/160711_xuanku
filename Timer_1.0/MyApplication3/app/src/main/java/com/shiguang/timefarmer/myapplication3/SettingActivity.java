package com.shiguang.timefarmer.myapplication3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import com.shiguang.timefarmer.myapplication3.settingactivities.AboutUs;
import com.shiguang.timefarmer.myapplication3.settingactivities.DefaultFirstPage;
import com.shiguang.timefarmer.myapplication3.settingactivities.FeedBack;
import com.shiguang.timefarmer.myapplication3.settingactivities.Help;
import com.shiguang.timefarmer.myapplication3.settingactivities.Ring;

public class SettingActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_layout);
        ImageButton back=(ImageButton)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
        //为每个tablerow设置跳转



        TableRow ring = (TableRow)findViewById(R.id.setRing);
        ring.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                intent=new Intent(SettingActivity.this,Ring.class);
                startActivity(intent);

            }
        });



        TableRow defaultFirstpage = (TableRow)findViewById(R.id.defaultFirstpage);
        defaultFirstpage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                intent=new Intent(SettingActivity.this,DefaultFirstPage.class);
                startActivity(intent);
            }
        });

        TableRow help = (TableRow)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(SettingActivity.this,Help.class);
                startActivity(intent);
            }
        });

        TableRow feedback = (TableRow)findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                intent=new Intent(SettingActivity.this,FeedBack.class);
                startActivity(intent);
            }
        });

        TableRow aboutus = (TableRow)findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                intent=new Intent(SettingActivity.this,AboutUs.class);
                startActivity(intent);
            }
        });
    }
}
