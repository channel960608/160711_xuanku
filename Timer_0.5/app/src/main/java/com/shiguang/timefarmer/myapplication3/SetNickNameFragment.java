package com.shiguang.timefarmer.myapplication3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by lenovo on 2016/7/18.
 */
public class SetNickNameFragment extends Fragment {
    FragmentManager fm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.setnickname,container,false);

        //设置返回按钮
        ImageButton back = (ImageButton)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SettingsPageFragment sp = new SettingsPageFragment();
                ft.replace(R.id.setting_layout,sp);
                ft.commit();
            }
        });

        return view;
    }
}
