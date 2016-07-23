package com.shiguang.timefarmer.myapplication3.settingactivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import com.shiguang.timefarmer.myapplication3.R;

/**
 * Created by Administrator on 2016/7/22.
 */
public class DefaultFirstPage extends Activity {
    private String[] strs = {"我的任务","我的番茄","我的习惯"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defaultfirstpage);
        //获取listview对象，设置adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                DefaultFirstPage.this,android.R.layout.simple_list_item_multiple_choice,strs);
        ListView listview = (ListView)findViewById(R.id.listView3);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(adapter);

        //设置返回按钮
        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultFirstPage.this.finish();
            }
        });
    }
}
