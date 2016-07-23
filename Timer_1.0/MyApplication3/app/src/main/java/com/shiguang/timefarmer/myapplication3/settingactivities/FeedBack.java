package com.shiguang.timefarmer.myapplication3.settingactivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.shiguang.timefarmer.myapplication3.R;


/**
 * Created by Administrator on 2016/7/22.
 */
public class FeedBack extends Activity {
    private EditText editText;
    private String str1;
    private String str2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               FeedBack.this.finish();
            }
        });


        editText = (EditText)findViewById(R.id.editText);

        //设置提交后弹窗
        Button confirm = (Button)findViewById(R.id.submit);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取提交的内容
                str1 = editText.getText().toString();

                if (str1.trim().equals("")) {
                    str2 = "请输入要提交内容";
                } else {
                    str2 = "哼！不管你怎么说我们是不会改的";
                }

                Toast toast = Toast.makeText(FeedBack.this, str2, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
