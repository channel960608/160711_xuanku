package com.shiguang.timefarmer.myapplication3;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.usage.UsageEvents;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/15.
 */
public class ScheduleFragment extends Fragment {
    Button timebn;
    ImageButton addbn;
    View scheduleView;
    ListView eventListView;
    private int position;
    SqlManager dbSelectEventHelper;
    Cursor eventResult;
    private int year;
    private int month;
    private int day;
    private int dayOfweek;
    ArrayList<NewEvent> eventArrayList;
    myBaseAdapter baseAdapter;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dayOfweek = c.get(Calendar.DAY_OF_WEEK);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scheduleView = inflater.inflate(R.layout.fragment_schedule, container, false);
        timebn = (Button) scheduleView.findViewById(R.id.timesetbn);
        addbn = (ImageButton) scheduleView.findViewById(R.id.addEvent);
        dbSelectEventHelper = new SqlManager(ScheduleFragment.this.getActivity(), "shiguangDatabase.db", null, 1);
        eventResult=dbSelectEventHelper.selectTable("schedule", ScheduleFragment.this.year, ScheduleFragment.this.month, ScheduleFragment.this.day);
        eventListView = (ListView) scheduleView.findViewById(R.id.eventListView);
        eventArrayList = new ArrayList<NewEvent>();
        while (eventResult != null && eventResult.moveToNext()) {
            NewEvent a = new NewEvent(eventResult.getString(1), eventResult.getString(2), eventResult.getInt(3), eventResult.getInt(4), eventResult.getInt(5), eventResult.getInt(6), eventResult.getInt(7));
            a.setId(eventResult.getInt(0));
            a.setEventType(Boolean.parseBoolean(eventResult.getString(8)));
            a.setIfRemind(Boolean.parseBoolean(eventResult.getString(9)));
            a.setStatus(eventResult.getInt(10));
            eventArrayList.add(a);

        }
        baseAdapter=new myBaseAdapter();
        baseAdapter.setArrayList(eventArrayList);
        baseAdapter.setSf(ScheduleFragment.this);
        baseAdapter.setInflater(inflater);
        eventListView.setAdapter(baseAdapter);



        String text = (month + 1) + "/" + day + " " + dayOfweek;
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
                                ScheduleFragment.this.year = year;
                                ScheduleFragment.this.month = month;
                                ScheduleFragment.this.day = dayOfMonth;
                                String text = (ScheduleFragment.this.month + 1) + "/" + day + " " + dayOfweek;
                                timebn.setText(text);

                                eventResult = dbSelectEventHelper.selectTable("schedule", ScheduleFragment.this.year, ScheduleFragment.this.month, ScheduleFragment.this.day);
                                eventArrayList.clear();
                                while (eventResult != null && eventResult.moveToNext()) {
                                    NewEvent a = new NewEvent(eventResult.getString(1), eventResult.getString(2), eventResult.getInt(3), eventResult.getInt(4), eventResult.getInt(5), eventResult.getInt(6), eventResult.getInt(7));
                                    a.setId(eventResult.getInt(0));
                                    a.setEventType(Boolean.parseBoolean(eventResult.getString(8)));
                                    a.setIfRemind(Boolean.parseBoolean(eventResult.getString(9)));
                                    a.setStatus(eventResult.getInt(10));
                                    eventArrayList.add(a);
                                }
                                baseAdapter.setArrayList(eventArrayList);
                                baseAdapter.notifyDataSetChanged();
                            }
                        }, year
                        , month
                        , day);


                dpd.show();
            }
        });
        addbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addEventFragment = new AddEventFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transactionaef = fm.beginTransaction();
                transactionaef.replace(R.id.ly_content, addEventFragment);
                transactionaef.commit();

            }
        });

        dbSelectEventHelper.close();
        return scheduleView;
    }



}
