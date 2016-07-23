package com.shiguang.timefarmer.myapplication3.mainfragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;

import com.shiguang.timefarmer.myapplication3.AddEventActivity;
import com.shiguang.timefarmer.myapplication3.R;
import com.shiguang.timefarmer.myapplication3.tool.EventAdapter;
import com.shiguang.timefarmer.myapplication3.tool.SqlManager;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/15.
 */
public class ScheduleFragment extends Fragment {
    private Button timebn;
    private ImageButton addbn,menu;
    private View scheduleView;
    private ListView eventListView;
    private int position;
    private SqlManager dbSelectEventHelper;
    private Cursor eventResult;
    private int year;
    private int month;
    private int day;
    private int dayOfweek;
    private EventAdapter baseAdapter;
    private Calendar c;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c= Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        ScheduleFragment.this.dayOfweek=c.get(Calendar.DAY_OF_WEEK);
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scheduleView = inflater.inflate(R.layout.schedule_fagment_layout, container, false);
        timebn = (Button) scheduleView.findViewById(R.id.timesetbn);
        addbn = (ImageButton) scheduleView.findViewById(R.id.addEvent);
        menu=(ImageButton)scheduleView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) ScheduleFragment.this.getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        eventListView = (ListView) scheduleView.findViewById(R.id.eventListView);

        baseAdapter=new EventAdapter(inflater,ScheduleFragment.this,year,month,day);
        eventListView.setAdapter(baseAdapter);



        String text = (month + 1) + "/" + day;
        //日期选择按钮的监听事件
        timebn.setText(text);
        timebn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //日期选择器
                DatePickerDialog dpd = new DatePickerDialog(ScheduleFragment.this.getActivity(),
                        0,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year1,
                                                  int month, int dayOfMonth) {
                                ScheduleFragment.this.year = year1;
                                ScheduleFragment.this.month = month;
                                ScheduleFragment.this.day = dayOfMonth;
                                c.set(Calendar.YEAR,year1);
                                c.set(Calendar.MONTH,month);
                                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                ScheduleFragment.this.dayOfweek=c.get(Calendar.DAY_OF_WEEK);
                                String text = (ScheduleFragment.this.month + 1) + "/" + day ;
                                timebn.setText(text);
                                baseAdapter.setDate(ScheduleFragment.this.year,ScheduleFragment.this.month,ScheduleFragment.this.day);
                                baseAdapter.notifyDataSetChanged();
                            }
                        }, year
                        , month
                        , day);


                dpd.show();
            }
        });
        //添加事件
        addbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScheduleFragment.this.getActivity(), AddEventActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("year",ScheduleFragment.this.year);
                bundle.putInt("month",ScheduleFragment.this.month);
                bundle.putInt("day",ScheduleFragment.this.day);
                bundle.putInt("dayofWeek", ScheduleFragment.this.dayOfweek);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);

            }
        });

        //dbSelectEventHelper.close();
        return scheduleView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);

        if (!hidden) {
            //Toast.makeText(ScheduleFragment.this.getActivity(),"显示出来了",Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(ScheduleFragment.this.getActivity(),"隐藏掉了",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){

        if(requestCode==0&&resultCode==0){
            baseAdapter.setArrayList();
            baseAdapter.notifyDataSetChanged();
            //Toast.makeText(ScheduleFragment.this.getActivity(),"跳转回来了",Toast.LENGTH_LONG).show();

        }
        else if(requestCode==1&&resultCode==1){
            baseAdapter.setArrayList();
            baseAdapter.notifyDataSetChanged();
            //Toast.makeText(ScheduleFragment.this.getActivity(),"跳转回来了2",Toast.LENGTH_LONG).show();
        }
    }



}
