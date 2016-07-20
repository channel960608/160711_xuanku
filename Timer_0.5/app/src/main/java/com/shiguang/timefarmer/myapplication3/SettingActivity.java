package com.shiguang.timefarmer.myapplication3;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class SettingActivity extends Activity {
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_layout);
        fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        SettingsPageFragment sp=new SettingsPageFragment();
        ft.add(R.id.setting_layout,sp);
        ft.commit();
    }
}
