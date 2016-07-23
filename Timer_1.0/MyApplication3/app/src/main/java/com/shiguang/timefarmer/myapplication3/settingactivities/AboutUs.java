package com.shiguang.timefarmer.myapplication3.settingactivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.shiguang.timefarmer.myapplication3.R;

/**
 * Created by Administrator on 2016/7/22.
 */
public class AboutUs extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutUs.this.finish();
            }
        });
    }
}
