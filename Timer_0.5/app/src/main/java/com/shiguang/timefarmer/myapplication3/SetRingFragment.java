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
public class SetRingFragment extends Fragment {
    FragmentManager fm;
    String[] rings = {"钢琴","华尔兹","数码","吉他","淘气","前进","清晨","爱的呼唤","新生活","心跳","旅途","家","布鲁斯","晚安","翱翔"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.setrings,container,false);

        //设置listview显示内容
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_multiple_choice,rings);
        ListView listview = (ListView)view.findViewById(R.id.listView);
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
