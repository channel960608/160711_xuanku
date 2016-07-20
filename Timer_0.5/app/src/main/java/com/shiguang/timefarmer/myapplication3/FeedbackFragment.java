package com.shiguang.timefarmer.myapplication3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by lenovo on 2016/7/18.
 */
public class FeedbackFragment extends Fragment {

    FragmentManager fm;
    String str1 = "df";
    String str2 = "";
    EditText editText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.feedback,container,false);

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


        editText = (EditText)view.findViewById(R.id.editText);

        //设置提交后弹窗
        Button confirm = (Button)view.findViewById(R.id.submit);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取提交的内容
                str1 = editText.getText().toString();

                if(str1.trim().equals("")) {str2 = "请输入要提交内容";}
                else {str2 = "哼！不管你怎么说我们是不会改的";}

                Toast toast = Toast.makeText(FeedbackFragment.this.getActivity(),str2,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    return view;
    }
}
