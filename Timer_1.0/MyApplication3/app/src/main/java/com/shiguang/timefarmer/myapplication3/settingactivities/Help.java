package com.shiguang.timefarmer.myapplication3.settingactivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.shiguang.timefarmer.myapplication3.R;

/**
 * Created by Administrator on 2016/7/22.
 */
public class Help extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Help.this.finish();
            }
        });

    }
}
