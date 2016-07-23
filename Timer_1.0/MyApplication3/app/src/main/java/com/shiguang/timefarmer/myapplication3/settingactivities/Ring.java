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
public class Ring extends Activity {
    private String[] rings = {"钢琴","华尔兹","数码","吉他","淘气","前进","清晨","爱的呼唤"};
    //,"新生活","心跳","旅途","家","布鲁斯","晚安","翱翔"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setrings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Ring.this,android.R.layout.simple_list_item_multiple_choice,rings);
        ListView listview = (ListView)findViewById(R.id.listView);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(adapter);

        //设置返回按钮
        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ring.this.finish();
            }
        });

    }
}
