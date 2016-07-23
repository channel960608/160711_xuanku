package com.shiguang.timefarmer.myapplication3.mainfragment;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.shiguang.timefarmer.myapplication3.R;
import com.shiguang.timefarmer.myapplication3.tool.SceneAnimation;

import java.lang.reflect.Field;

import static com.shiguang.timefarmer.myapplication3.R.drawable.grow1;


/**
 * Created by channel on 2016/7/19.
 */

public class TimerFragment extends Fragment {
    private String content;
    //private int min = 0;
    //private int sec=0;
    private int converse_min=0;
    private int converse_sec=20;
    private int round=0;
    //private TextView txtView;
    private TextView tv_min;
    private TextView tv_sec;
    //private Context context;
    //private Boolean isContinue=true;
    private ImageView frame_sun=null;
    private ImageView frame_grow=null;
    private AnimationDrawable anim_sun;
    private AnimationDrawable anim_grow;
    private Button btn_start;
    private Button btn_restart;
    private int tomatoNum=0;
    private TextView tv_tomatoNum;
    private SceneAnimation sceneAnimation_grow;
    final private int growNum=0;
    final private int sunNum=0;
    //
    //private Vibrator myVibrator;
    //private Context mContext;

    public void Fragment_Timer(String content) {
        this.content = content;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timertask,container,false);

        int[] pic_grow={R.drawable.grow1,R.drawable.grow2,R.drawable.grow3,R.drawable.grow4,
                R.drawable.grow1,R.drawable.grow5,R.drawable.grow6,R.drawable.grow7,
                R.drawable.grow8,R.drawable.grow9};
        int[] pic_sun={R.drawable.sun1,R.drawable.sun15,R.drawable.sun2,R.drawable.sun25,
                R.drawable.sun3,R.drawable.sun35};

        //int[] dur_grow={1000,1000,1000,1000,1000,249000,249000,249000,249000,249000};
        int[] dur_grow={1000,1000,1000,1000,1000,1000,1000,1000,1000,1000};
        int[] dur_sun={250,250,250,250,250,250};




        bindViews(view);

        sceneAnimation_grow=new SceneAnimation(frame_grow,pic_grow,dur_grow);
        //sceneAnimation_grow.play(startNum);
        SceneAnimation sceneAnimation_sun=new SceneAnimation(frame_sun,pic_sun,dur_sun);
        sceneAnimation_sun.play(sunNum);

        setTime();

        //anim_sun=(AnimationDrawable)frame_sun.getBackground();
        //anim_grow=(AnimationDrawable)frame_grow.getBackground();




//        Glide
//                .with(this)
//                .load()
//                .animate(anim_sun)
//                .centerCrop()
//                .crossFade()
//                .into(frame_sun)
//                .asBitmap();

        //anim_sun.start();





        return view;
    }
    Handler handler = new Handler();

//    private void move(int i) {
//        Drawable a = getResources().getDrawable(R.drawable.sun1);
//        Drawable b = getResources().getDrawable(R.drawable.sun15);
//        Drawable c = getResources().getDrawable(R.drawable.sun2);
//        Drawable d = getResources().getDrawable(R.drawable.sun25);
//        Drawable e = getResources().getDrawable(R.drawable.sun3);
//        Drawable f = getResources().getDrawable(R.drawable.sun35);
//
//        switch(i)
//        {
//            case 0:
//                frame_sun.setForeground(a);
//                break;
//            case 1:
//                frame_sun.setForeground(b);
//                break;
//            case 2:
//                frame_sun.setForeground(c);
//                break;
//            case 3:
//                frame_sun.setForeground(d);
//                break;
//            case 4:
//                frame_sun.setForeground(e);
//                break;
//            case 5:
//                frame_sun.setForeground(f);
//                break;
//
//        }
  private void bindViews(View view) {
      //btn_restart=(Button)view.findViewById(R.id.btn_restart);
//      ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
//          @Override
//          public void animate(View view) {
//              // if it's a custom view class, cast it here
//              // then find subviews and do the animations
//              // here, we just use the entire view for the fade animation
//              view.setAlpha( 0f );
//
//              ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
//              fadeAnim.setDuration( 2500 );
//              fadeAnim.start();
//          }
//      };

      btn_start=(Button)view.findViewById(R.id.btn_start);
      tv_min=(TextView)view.findViewById(R.id.tv_Min);
      tv_sec=(TextView)view.findViewById(R.id.tv_Sec);
      tv_tomatoNum=(TextView) view.findViewById(R.id.tv_tomatoNum);
      frame_sun=(ImageView) view.findViewById(R.id.frame_sun);
      frame_grow=(ImageView)view.findViewById(R.id.fl_grow);
//      btn_restart.setOnClickListener(new View.OnClickListener(){
//          @Override
//          public void onClick(View view) {
//
//
//              anim_grow.stop();
//              handler.removeCallbacks(runnable);
//              converse_min=25;
//              converse_sec=0;
//              setTime();
//
//
//
//
//
//
//          }
//      });
      btn_start.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String text=String.valueOf(btn_start.getText());

              switch(text) {
                  case "投下种子":
                      handler.postDelayed(runnable, 1000);
                      sceneAnimation_grow.play(growNum);
                      btn_start.setText("终止");
                      break;
                  case "终止":
                      sceneAnimation_grow.stop();
                      handler.removeCallbacks(runnable);
                      converse_min=25;
                      converse_sec=0;
                      //setTime();
                      btn_start.setText("重新投下种子");
                      break;
                  case"重新投下种子":
                      handler.postDelayed(runnable, 1000);
                      setTime();
                      sceneAnimation_grow.restart();
                      //anim_grow.start();
                      btn_start.setText("终止");
                      break;



              }



          }
      });


    }





    Runnable runnable = new Runnable() {
        @Override
        public void run() {

//            if(isButtonVisible())
//                btn_start.setVisibility(View.INVISIBLE);
//            else
//                btn_start.setVisibility(View.VISIBLE);




            round++;

            //move(round%6);

            if(0==converse_sec){

                if(0==converse_min){
                    //sendBroadcast(new Intent("com.example.broadcasttest.TIMEUP"));
                    handler.removeCallbacks(this);
                    //tomatoNum++;
                    tv_tomatoNum.setText(String.valueOf(tomatoNum));


                   // Context mContext = getApplicationContext();
                   // Vibrator vibrator = (Vibrator)mContext.getSystemService(mContext.VIBRATOR_SERVICE);
                    //vibrator.vibrate(new long[]{3000, 3000}, 0);
                }
                else{
                    converse_min=converse_min-1;
//                    converse_sec=60*4-1;
                    converse_sec=60-1;
                }

            }else{
                converse_sec=converse_sec-1;
            }

            setTime();
            //handler.postDelayed(this, 250);
            handler.postDelayed(this, 1000);

        }


    };

    private Boolean isButtonVisible(){
        Field field = null;
        try {
            field =AnimationDrawable.class
                    .getDeclaredField("mCurFrame");
            int curFrame = 0;
            try {
                curFrame = field.getInt(anim_grow);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return true;
            }
            if (curFrame != 0)
                return false;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }



    private void setTime(){
        String a="";
        String b="";
//        if(converse_sec<40)
//            a+="0";
//        if(converse_min<40)
//            b+="0";
//        tv_sec.setText(a + converse_sec/4);
//        tv_min.setText(b + converse_min/4);
        if(converse_sec<10)
            a+="0";
        if(converse_min<10)
            b+="0";
        tv_sec.setText(a + converse_sec);
        tv_min.setText(b + converse_min);

    }



}
