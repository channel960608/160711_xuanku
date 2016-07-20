package com.shiguang.timefarmer.myapplication3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;

/**
 * Created by lenovo on 2016/7/18.
 */
public class SettingsPageFragment extends Fragment {
    FragmentManager fm;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.settingspage,container,false);
        ImageButton back=(ImageButton)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsPageFragment.this.getActivity().finish();
            }
        });
        //为每个tablerow设置跳转
        TableRow nickname = (TableRow)view.findViewById(R.id.setNickname);
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SetNickNameFragment snn = new SetNickNameFragment();
                ft.replace(R.id.setting_layout,snn);
                ft.commit();
            }
        });

        TableRow icon = (TableRow)view.findViewById(R.id.setIcon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SetIconFragment si = new SetIconFragment();
                ft.replace(R.id.setting_layout,si);
                ft.commit();
            }
        });

        TableRow ring = (TableRow)view.findViewById(R.id.setRing);
        ring.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SetRingFragment sr = new SetRingFragment();
                ft.replace(R.id.setting_layout,sr);
                ft.commit();
            }
        });

        TableRow whitelist = (TableRow)view.findViewById(R.id.whitelist);
        whitelist.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                WhitelistFragment wl = new WhitelistFragment();
                ft.replace(R.id.setting_layout,wl);
                ft.commit();
            }
        });

        TableRow defaultFirstpage = (TableRow)view.findViewById(R.id.defaultFirstpage);
        defaultFirstpage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DefaultFirstPageFragment dfp = new DefaultFirstPageFragment();
                ft.replace(R.id.setting_layout,dfp);
                ft.commit();
            }
        });

        TableRow help = (TableRow)view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HelpFragment h1 = new HelpFragment();
                ft.replace(R.id.setting_layout,h1);
                ft.commit();
            }
        });

        TableRow feedback = (TableRow)view.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FeedbackFragment fd = new FeedbackFragment();
                ft.replace(R.id.setting_layout,fd);
                ft.commit();
            }
        });

        TableRow aboutus = (TableRow)view.findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                AboutUsFragment au = new AboutUsFragment();
                ft.replace(R.id.setting_layout,au);
                ft.commit();
            }
        });

        return view;
    }
}
