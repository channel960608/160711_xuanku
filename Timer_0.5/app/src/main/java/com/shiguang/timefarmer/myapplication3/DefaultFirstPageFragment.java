package com.shiguang.timefarmer.myapplication3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by lenovo on 2016/7/18.
 */
public class DefaultFirstPageFragment extends Fragment {

    FragmentManager fm;
    String[] strs = {"我的任务","我的番茄","我的习惯"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.defaultfirstpage,container,false);

        //获取listview对象，设置adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(),android.R.layout.simple_list_item_checked,strs);
        ListView listview = (ListView)view.findViewById(R.id.listView3);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(adapter);

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
