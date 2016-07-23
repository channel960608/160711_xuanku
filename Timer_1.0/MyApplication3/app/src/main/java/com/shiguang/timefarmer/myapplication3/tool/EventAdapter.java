package com.shiguang.timefarmer.myapplication3.tool;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shiguang.timefarmer.myapplication3.R;
import com.shiguang.timefarmer.myapplication3.mainfragment.ScheduleFragment;
import com.shiguang.timefarmer.myapplication3.UpdateEventActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/18.
 */
public class EventAdapter extends BaseAdapter {
    private ArrayList<NewEvent> eventArrayList=new ArrayList<NewEvent>();
    private LayoutInflater inflater;
    private ScheduleFragment sf;
    private int year,month,day;
    private SqlManager sm;
    private Cursor eventResult;


    public EventAdapter(LayoutInflater inflater, ScheduleFragment sf, int year, int month, int day){
        this.inflater=inflater;
        this.sf=sf;
        this.year=year;
        this.month=month;
        this.day=day;
        sm=new SqlManager(sf.getActivity());
        setArrayList();

    }
    public void setArrayList(){
        eventResult = sm.selectTable("schedule",year,month, day);
            eventArrayList.clear();

        while (eventResult.moveToNext()) {
            NewEvent a = new NewEvent(eventResult.getString(1), eventResult.getString(2), eventResult.getInt(3), eventResult.getInt(4), eventResult.getInt(5), eventResult.getInt(6), eventResult.getInt(7));
            a.setId(eventResult.getInt(0));
            a.setEventType(Boolean.parseBoolean(eventResult.getString(8)));
            a.setIfRemind(Boolean.parseBoolean(eventResult.getString(9)));
            a.setStatus(eventResult.getInt(10));
            eventArrayList.add(a);
        }

    }
    public void setDate(int year,int month,int day){
        this.year=year;
        this.month=month;
        this.day=day;
        setArrayList();
    }

    public void setInflater(LayoutInflater inflater){
        this.inflater=inflater;
    }
    public void setSf(ScheduleFragment sf){this.sf=sf;}
    @Override
    public int getCount() {
        return eventArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (eventArrayList.isEmpty()) {
            return 0;
        } else
            return eventArrayList.get(position).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (eventArrayList.isEmpty()) {
            return null;

        } else {
            View itemView = this.inflater.inflate(R.layout.event_itemt, null);
            LinearLayout eventItem = (LinearLayout) itemView.findViewById(R.id.event_item);
            CheckBox eventItemStatus = (CheckBox) itemView.findViewById(R.id.event_item_status);
            TextView eventItemName = (TextView) itemView.findViewById(R.id.event_item_name);
            TextView eventItemTime = (TextView) itemView.findViewById(R.id.event_item_time);
            CheckBox eventItemType=(CheckBox)itemView.findViewById(R.id.event_item_type);

            eventItemType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (eventArrayList.get(position).getStatus() == 0 && b == true) {
                        sm.getReadableDatabase().execSQL("update schedule set status='1' where _id='" + eventArrayList.get(position).getId() + "'");
                    } else if (eventArrayList.get(position).getStatus() == 1 && b == false) {
                        sm.getReadableDatabase().execSQL("update schedule set status='0' where _id='" + eventArrayList.get(position).getId() + "'");
                    } else {
                    }
                    sm.getReadableDatabase().execSQL("update schedule set eventType='" + b + "' where _id='" + eventArrayList.get(position).getId() + "'");

                    EventAdapter.this.setArrayList();
                    EventAdapter.this.notifyDataSetChanged();


                }
            });
            eventItemStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        sm.getReadableDatabase().execSQL("update schedule set status='-1' where _id='" + eventArrayList.get(position).getId() + "'");
                    } else {
                        if (eventArrayList.get(position).getEventType()) {
                            sm.getReadableDatabase().execSQL("update schedule set status='1' where _id='" + eventArrayList.get(position).getId() + "'");
                        } else {
                            sm.getReadableDatabase().execSQL("update schedule set status='0' where _id='" + eventArrayList.get(position).getId() + "'");
                        }
                    }
                    EventAdapter.this.setArrayList();
                    EventAdapter.this.notifyDataSetChanged();

                }
            });

            if (eventArrayList.get(position).getStatus() == 0) {

            } else if (eventArrayList.get(position).getStatus() == -1) {
                eventItemStatus.setChecked(true);
            } else {
            }

            eventItemName.setText(eventArrayList.get(position).getName());
            if(eventArrayList.get(position).getMinute()<10){
                eventItemTime.setText(eventArrayList.get(position).getHour() + ":0" + eventArrayList.get(position).getMinute());
            }else{
                eventItemTime.setText(eventArrayList.get(position).getHour() + ":" + eventArrayList.get(position).getMinute());
            }


            if (eventArrayList.get(position).getEventType()) {
                eventItemType.setChecked(true);
            } else {
            }

            final Bundle bundle=new Bundle();
            bundle.putInt("id", eventArrayList.get(position).getId());

            eventItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(sf.getActivity(), UpdateEventActivity.class);
                    intent.putExtras(bundle);
                    sf.startActivityForResult(intent, 1);
                }
            });
            eventItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    initPopWindow(view,position);

                    return false;
                }
            });

            return itemView;
        }


    }
    private void initPopWindow(View v,final int position) {
        View view = inflater.inflate(R.layout.item_popip, null, false);
        Button btn_eventdelete = (Button) view.findViewById(R.id.btn_eventdelete);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 150, 0);

        //设置popupWindow里的按钮的事件
        btn_eventdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sm.getReadableDatabase().execSQL("delete from schedule where _id='" + eventArrayList.get(position).getId() + "'");
                EventAdapter.this.setArrayList();
                EventAdapter.this.notifyDataSetChanged();
                popWindow.dismiss();
            }
        });

    }

}
