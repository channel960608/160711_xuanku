package com.example.huangchunchun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by HuangChunchun on 2016/7/19.
 */
public class ResetpwdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_new,et_verify;
    private String new_pwd,verify_pwd;
    private Button btn_reset;
    private ImageView iv_back;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);

        et_new=(EditText)findViewById(R.id.et_new_pwd);
        et_verify=(EditText)findViewById(R.id.et_verify_pwd);
        btn_reset=(Button)findViewById(R.id.btn_reset);
        iv_back=(ImageView)findViewById(R.id.iv_resetpassword_back) ;

        btn_reset.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    public void onClick(View view){

        switch(view.getId()) {
            case R.id.iv_resetpassword_back:
                ResetpwdActivity.this.finish();
                break;
            case R.id.btn_reset:
                new_pwd = et_new.getText().toString();
                verify_pwd = et_verify.getText().toString();
                if (checkPwd()) {

                //修改密码

                //修改密码成功后跳转到登录界面
                    Intent intent = new Intent(ResetpwdActivity.this, LoginActivity.class);
                    ResetpwdActivity.this.startActivity(intent);
                }
                break;

        }
    }

    private boolean checkPwd(){
        //检查输入密码是否为空，是否一致
        if (new_pwd.equals("")){

            Toast.makeText(ResetpwdActivity.this, "密码不允许为空", Toast.LENGTH_SHORT).show();
            return false;

        }else if(!new_pwd.equals(verify_pwd)){

            Toast.makeText(ResetpwdActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;


        }
}
