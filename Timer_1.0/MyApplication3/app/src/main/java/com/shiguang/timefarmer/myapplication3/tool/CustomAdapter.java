package com.shiguang.timefarmer.myapplication3.tool;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shiguang.timefarmer.myapplication3.mainfragment.CustomFragment;
import com.shiguang.timefarmer.myapplication3.R;
import com.shiguang.timefarmer.myapplication3.external.SwipeListView;
import com.shiguang.timefarmer.myapplication3.UpdateCustomActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/21.
 */
public class CustomAdapter extends BaseAdapter {


    private LayoutInflater mInflater ;
    private ArrayList<NewCustom> customList=new ArrayList<NewCustom>();
    private SwipeListView mSwipeListView ;
    private ImageView imageView;
    private TextView customName,customTime;
    private Button mBackDelete,mBackDaka;
    private boolean ifRemind=true;
    private SqlManager customSQLManager;
    private CustomFragment cf;
    private String name;
    private int hour,minute,status=0,dayOfWeek;
    private Cursor customResult;


    public CustomAdapter(LayoutInflater mInflater,CustomFragment cf,SwipeListView mSwipeListView,int dayOfWeek) {
        this.mInflater=mInflater;
        this.cf=cf;
        this.mSwipeListView=mSwipeListView;
        this.dayOfWeek=dayOfWeek;
        customSQLManager=new SqlManager(cf.getActivity());
        setCustomList();
    }
    public void setCustomList(){
        customResult=customSQLManager.selectTable("custom");
        customList.clear();
        while (customResult.moveToNext()){
            NewCustom a=new NewCustom(customResult.getString(1),customResult.getInt(2),customResult.getInt(3),Boolean.parseBoolean(customResult.getString(4)),customResult.getInt(5));
            a.setId(customResult.getInt(0));
            customList.add(a);
        }
    }

    @Override
    public int getCount() {
        return customList.size();
    }

    @Override
    public Object getItem(int position) {
        return customList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (customList.isEmpty()) {
            return 0;
        } else
            return customList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=mInflater.inflate(R.layout.custom_item,parent,false);
        FrameLayout eventItem = (FrameLayout) convertView.findViewById(R.id.custom_item);
        imageView=(ImageView)convertView.findViewById(R.id.custom_status);
        customName=(TextView)convertView.findViewById(R.id.custom_name);
        customTime=(TextView)convertView.findViewById(R.id.custom_time);
        mBackDaka=(Button)convertView.findViewById(R.id.mBackDaka);
        mBackDelete=(Button)convertView.findViewById(R.id.mBackDelete);
        imageView.setImageResource(R.drawable.empty_circle);
        if(customList.get(position).getStatus()==0){

        }else{
            imageView.setImageResource(R.drawable.circle);
        }
        customName.setText(customList.get(position).getName());
        if(customList.get(position).getMinute()<10){
            customTime.setText(customList.get(position).getHour()+":0"+customList.get(position).getMinute());
        }
        else{
            customTime.setText(customList.get(position).getHour()+":"+customList.get(position).getMinute());
        }

        mBackDaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customList.get(position).getStatus()==0){
                    customSQLManager.getReadableDatabase().execSQL("update custom set status="+1+" where _id="+customList.get(position).getId());
                    imageView.setImageResource(R.drawable.circle);
                    Toast.makeText(mBackDaka.getContext(),"打卡成功",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(mBackDaka.getContext(),"今天已经打过卡了",Toast.LENGTH_LONG).show();
                }
                CustomAdapter.this.setCustomList();
                CustomAdapter.this.notifyDataSetChanged();
            }
        });
        mBackDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭动画
                mSwipeListView.closeAnimate(position);
                //调用dismiss方法删除该项（这个方法在MainActivity中）
                customSQLManager.getReadableDatabase().execSQL("delete from custom where _id="+customList.get(position).getId());
                CustomAdapter.this.setCustomList();
                CustomAdapter.this.notifyDataSetChanged();
                //mSwipeListView.dismiss(position);
            }
        });

        eventItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><>可以更新");
                Bundle bundle=new Bundle();
                bundle.putInt("id", customList.get(position).getId());
                Intent intent=new Intent(cf.getActivity(), UpdateCustomActivity.class);
                intent.putExtras(bundle);
                cf.startActivityForResult(intent,1);
            }
        });



        return convertView;
    }


}
