package com.shiguang.timefarmer.myapplication3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by channel on 2016/7/19.
 */

public class TimerFragment extends Fragment {
    private String content;
    private int min = 0;
    private int sec=0;
    private int converse_min=0;
    private int converse_sec=80;
    private int round=0;
    //private TextView txtView;
    private TextView tv_min;
    private TextView tv_sec;
    private Context context;
    private Boolean isContinue=true;
    private FrameLayout frame_sun=null;

    public void Fragment_Timer(String content) {
        this.content = content;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timertask,container,false);
        tv_min=(TextView)view.findViewById(R.id.tv_Min);
        tv_sec=(TextView)view.findViewById(R.id.tv_Sec);
        frame_sun=(FrameLayout)view.findViewById(R.id.frame_sun);
        setTime();

        handler.postDelayed(runnable, 1000);

        return view;
    }
    Handler handler = new Handler();

    private void move(int i) {
        Drawable a = getResources().getDrawable(R.drawable.sun1);
        Drawable b = getResources().getDrawable(R.drawable.sun15);
        Drawable c = getResources().getDrawable(R.drawable.sun2);
        Drawable d = getResources().getDrawable(R.drawable.sun25);
        Drawable e = getResources().getDrawable(R.drawable.sun3);
        Drawable f = getResources().getDrawable(R.drawable.sun35);

        switch(i)
        {
            case 0:
                frame_sun.setForeground(a);
                break;
            case 1:
                frame_sun.setForeground(b);
                break;
            case 2:
                frame_sun.setForeground(c);
                break;
            case 3:
                frame_sun.setForeground(d);
                break;
            case 4:
                frame_sun.setForeground(e);
                break;
            case 5:
                frame_sun.setForeground(f);
                break;

        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            round++;

            move(round%6);

            if(0==converse_sec){

                if(0==converse_min){
                    //sendBroadcast(new Intent("com.example.broadcasttest.TIMEUP"));
                    handler.removeCallbacks(this);
                }
                else{
                    converse_min=converse_min-1;
                    converse_sec=60*4-1;
                }

            }else{
                converse_sec=converse_sec-1;
            }

            setTime();
            handler.postDelayed(this, 250);

        }


    };

    Runnable runnable2 = new Runnable(){
        @Override
        public void run() {

        }
    };

    private void setTime(){
        String a="";
        String b="";
        if(converse_sec<40)
            a+="0";
        if(converse_min<40)
            b+="0";
        tv_sec.setText(a + converse_sec/4);
        tv_min.setText(b + converse_min/4);
    }

}
