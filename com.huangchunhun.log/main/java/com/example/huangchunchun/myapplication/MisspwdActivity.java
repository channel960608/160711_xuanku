package com.example.huangchunchun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Random;


/**
 * Created by HuangChunchun on 2016/7/18.
 */
public class MisspwdActivity extends AppCompatActivity {

    private String emailAddress,verify_code;
    private String setVerCode=null;//系统自动生成的验证码

    private EditText et_email,et_verify;
    private Button btn_send,btn_reset;
    private ImageView iv_back;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misspwd);

        et_email=(EditText)findViewById(R.id.et_mi_email) ;
        et_verify=(EditText)findViewById(R.id.et_verify_code) ;
        btn_send=(Button)findViewById(R.id.btn_mi);
        btn_reset=(Button)findViewById(R.id.btn_mi_reset);
        iv_back=(ImageView)findViewById(R.id.iv_mi_back);



        iv_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MisspwdActivity.this.finish();
            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                //发送邮件
                emailAddress=et_email.getText().toString();
                //随机生成验证码
                int ran_0=new Random().nextInt(9);
                int ran_1=new Random().nextInt(9);
                int ran_2=new Random().nextInt(9);
                int ran_3=new Random().nextInt(9);
                int ran_4=new Random().nextInt(9);
                int ran_5=new Random().nextInt(9);

                setVerCode=String.valueOf(ran_0)+String.valueOf(ran_1)+String.valueOf(ran_2)+
                        String.valueOf(ran_3)+String.valueOf(ran_4)+String.valueOf(ran_5);



                try{
                    send(setVerCode,emailAddress);
                    Toast.makeText(MisspwdActivity.this, "发送成功",
                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MisspwdActivity.this, "发送出错，请确认网络信息以及账号信息",
                            Toast.LENGTH_SHORT).show();
                };
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verify_code=et_verify.getText().toString();
                //核对验证码
                //验证码正确则可跳转到重置密码页面
                if(checkVerifyCode()) {
                    Intent intent = new Intent(MisspwdActivity.this, ResetpwdActivity.class);
                    MisspwdActivity.this.startActivity(intent);
                }
            }
        });}

        private boolean checkVerifyCode() {

               if(verify_code.equals(setVerCode))
                  return true;
               else {
                   Toast.makeText(MisspwdActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                   return false;
               }
        }

       //发送请求验证码邮件


    private void send(String number,String address){
        sendEmail sendEmail=new sendEmail(number,address);
        sendEmail.start();
    }







}
