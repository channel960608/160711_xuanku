package com.shiguang.timefarmer.myapplication3;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by HuangChunchun on 2016/7/18.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

   private String account;
   private  String pwd;
   private ImageView iv_back;
   private EditText et_user,et_pwd ;
   private  Button btn_login;
   private TextView tv_regedit,tv_forgetpwd ;

    protected  void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iv_back=(ImageView)findViewById(R.id.iv_lo_back);
        et_user=(EditText)findViewById(R.id.et_lo_account);
        et_pwd=(EditText)findViewById(R.id.et_lo_pwd);
        btn_login=(Button)findViewById(R.id.btn_lo);
        tv_regedit=(TextView)findViewById(R.id.tv_lo_reg_account);
        tv_forgetpwd=(TextView)findViewById(R.id.tv_lo_for_pwd) ;

        iv_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);//登录
        tv_regedit.setOnClickListener(this);//注册账户
        tv_forgetpwd.setOnClickListener(this);//忘记密码

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_lo_back:
                LoginActivity.this.finish();
                break;
            case R.id.btn_lo:
                account=et_user.getText().toString();
                pwd=et_pwd.getText().toString();
                login();
                break;
            case R.id.tv_lo_reg_account:
                Intent intent_for_regist=new Intent(LoginActivity.this,RegistActivity.class);
                LoginActivity.this.startActivity(intent_for_regist);
                break;
            case R.id.tv_lo_for_pwd:
                Intent intent_for_pwd =new Intent(LoginActivity.this,MisspwdActivity.class);
                LoginActivity.this.startActivity(intent_for_pwd);
                break;
        }
    }

    private void login(){


        //登录成功则跳转到主页面
        if(isLeagal()) {
            Intent intent_for_main = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent_for_main);
        }else {
            //登录失败
            Toast.makeText(LoginActivity.this, "账户或密码错误，登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    //核对登录信息是否合法
    private boolean isLeagal(){
        if(account.equals("12345")&&pwd.equals("12345")) {
            return true;
        }else {

            return false;
        }
    }


}
