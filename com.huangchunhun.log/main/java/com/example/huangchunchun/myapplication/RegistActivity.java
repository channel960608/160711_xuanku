package com.example.huangchunchun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
;

/**
 * Created by HuangChunchun on 2016/7/18.
 */
public class RegistActivity  extends AppCompatActivity implements View.OnClickListener{

    private  String user,email,pwd;

    private EditText et_user,et_email,et_pwd;
    private ImageView iv_back;
    private Button btn_regedit;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regedit);

        iv_back=(ImageView)findViewById(R.id.iv_re_back);
        et_user=(EditText)findViewById(R.id.et_re_user);
        et_email=(EditText)findViewById(R.id.et_re_email);
        et_pwd=(EditText)findViewById(R.id.et_re_pwd);
        btn_regedit=(Button)findViewById(R.id.btn_re);

        iv_back.setOnClickListener(this);
        btn_regedit.setOnClickListener(this);

    }

    public  void onClick(View view){



        switch ((view.getId())){
            case R.id.iv_re_back:
                RegistActivity.this.finish();
                break;
            case R.id.btn_re:
                user=et_user.getText().toString();
                email=et_email.getText().toString();
                pwd=et_pwd.getText().toString();
                regist();
                break;

        }
    }

    private void regist(){

        if(checkEdit()){
            //注册用户信息

            //注册成功则跳转到登录界面
            Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
            RegistActivity.this.startActivity(intent);
        }

    }

    //检查注册信息是否合理，如密码位数等
    private boolean checkEdit(){

        return true;
    }



}

