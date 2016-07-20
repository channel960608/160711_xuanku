package com.shiguang.timefarmer.myapplication3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/18.
 */
public class myBaseAdapter extends BaseAdapter {
    private ArrayList<NewEvent> eventArrayList;
    private LayoutInflater inflater;
    private ScheduleFragment sf;
    SqlManager sm;
    Cursor eventResult;
    public void setArrayList(ArrayList<NewEvent> eventArrayList){
        this.eventArrayList=eventArrayList;

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
            View itemView = this.inflater.inflate(R.layout.event_layout, null);
            LinearLayout eventItem = (LinearLayout) itemView.findViewById(R.id.event_item);
            CheckBox eventItemStatus = (CheckBox) itemView.findViewById(R.id.event_item_status);
            TextView eventItemName = (TextView) itemView.findViewById(R.id.event_item_name);
            TextView eventItemTime = (TextView) itemView.findViewById(R.id.event_item_time);
            CheckBox eventItemType=(CheckBox)itemView.findViewById(R.id.event_item_type);

            eventItemType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    System.out.print("<><><><><<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><1");
                    sm=new SqlManager(sf.getActivity(),"shiguangDatabase.db", null, 1);
                    eventArrayList.get(position).setEventType(b);
                    if(eventArrayList.get(position).getStatus()==0&&b==true){
                        eventArrayList.get(position).setStatus(1);
                        sm.getReadableDatabase().execSQL("update schedule set status='1' where _id='"+eventArrayList.get(position).getId()+"'");
                    }else if(eventArrayList.get(position).getStatus()==1&&b==false){
                        eventArrayList.get(position).setStatus(0);
                        sm.getReadableDatabase().execSQL("update schedule set status='0' where _id='"+eventArrayList.get(position).getId()+"'");
                    }
                    else{}
                   // myBaseAdapter.this.notifyDataSetChanged();
                    sm.getReadableDatabase().execSQL("update schedule set eventType='"+b+"' where _id='"+eventArrayList.get(position).getId()+"'");
                    eventResult = sm.selectTable("schedule", eventArrayList.get(position).getYear(), eventArrayList.get(position).getMonth(),eventArrayList.get(position).getDay());
                    eventArrayList.clear();
                    while (eventResult != null && eventResult.moveToNext()) {
                        NewEvent a = new NewEvent(eventResult.getString(1), eventResult.getString(2), eventResult.getInt(3), eventResult.getInt(4), eventResult.getInt(5), eventResult.getInt(6), eventResult.getInt(7));
                        a.setId(eventResult.getInt(0));
                        a.setEventType(Boolean.parseBoolean(eventResult.getString(8)));
                        a.setIfRemind(Boolean.parseBoolean(eventResult.getString(9)));
                        a.setStatus(eventResult.getInt(10));
                        eventArrayList.add(a);
                    }
                    myBaseAdapter.this.setArrayList(eventArrayList);
                    myBaseAdapter.this.notifyDataSetChanged();


                }
            });
            eventItemStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    System.out.println("<><><><><<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><1");
                    if(b){
                        eventArrayList.get(position).setStatus(-1);
                        sm=new SqlManager(sf.getActivity(),"shiguangDatabase.db", null, 1);
                        sm.getReadableDatabase().execSQL("update schedule set status='-1' where _id='" + eventArrayList.get(position).getId() + "'");
                    }else{
                        if(eventArrayList.get(position).getEventType()){
                            eventArrayList.get(position).setStatus(1);
                            sm=new SqlManager(sf.getActivity(),"shiguangDatabase.db", null, 1);
                            sm.getReadableDatabase().execSQL("update schedule set status='1' where _id='" + eventArrayList.get(position).getId() + "'");
                        }
                        else{
                            eventArrayList.get(position).setStatus(0);
                            sm=new SqlManager(sf.getActivity(),"shiguangDatabase.db", null, 1);
                            sm.getReadableDatabase().execSQL("update schedule set status='0' where _id='" + eventArrayList.get(position).getId() + "'");
                        }
                    }
                    eventResult = sm.selectTable("schedule", eventArrayList.get(position).getYear(), eventArrayList.get(position).getMonth(),eventArrayList.get(position).getDay());
                    eventArrayList.clear();
                    while (eventResult != null && eventResult.moveToNext()) {
                        NewEvent a = new NewEvent(eventResult.getString(1), eventResult.getString(2), eventResult.getInt(3), eventResult.getInt(4), eventResult.getInt(5), eventResult.getInt(6), eventResult.getInt(7));
                        a.setId(eventResult.getInt(0));
                        a.setEventType(Boolean.parseBoolean(eventResult.getString(8)));
                        a.setIfRemind(Boolean.parseBoolean(eventResult.getString(9)));
                        a.setStatus(eventResult.getInt(10));
                        eventArrayList.add(a);
                    }
                    myBaseAdapter.this.setArrayList(eventArrayList);
                     myBaseAdapter.this.notifyDataSetChanged();

                }
            });

            if (eventArrayList.get(position).getStatus() == 0) {

            } else if (eventArrayList.get(position).getStatus() == -1) {
                eventItemStatus.setChecked(true);
            } else {

            }

            eventItemName.setText(eventArrayList.get(position).getName());
            eventItemTime.setText(eventArrayList.get(position).getHour() + ":" + eventArrayList.get(position).getMinute());
            if (eventArrayList.get(position).getEventType()) {
                eventItemType.setChecked(true);
            } else {
            }

            final Bundle bundle=new Bundle();
            bundle.putInt("id", eventArrayList.get(position).getId());

            eventItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = sf.getFragmentManager();
                    FragmentTransaction transactionaef = fm.beginTransaction();
                    Fragment updateEventFragment = new UpdateEventFragment();
                    updateEventFragment.setArguments(bundle);
                    transactionaef.replace(R.id.ly_content, updateEventFragment);
                    transactionaef.commit();

                }
            });

            return itemView;
        }


    }

}
