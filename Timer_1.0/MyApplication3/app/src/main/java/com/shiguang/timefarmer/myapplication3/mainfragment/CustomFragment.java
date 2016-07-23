package com.shiguang.timefarmer.myapplication3.mainfragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.shiguang.timefarmer.myapplication3.AddCustomActivity;
import com.shiguang.timefarmer.myapplication3.R;
import com.shiguang.timefarmer.myapplication3.UpdateCustomActivity;
import com.shiguang.timefarmer.myapplication3.external.BaseSwipeListViewListener;
import com.shiguang.timefarmer.myapplication3.external.SwipeListView;
import com.shiguang.timefarmer.myapplication3.tool.CustomAdapter;
import com.shiguang.timefarmer.myapplication3.tool.SqlManager;

/**
 * Created by Administrator on 2016/7/20.
 */
public class CustomFragment extends Fragment {
    private View customView;
    private SwipeListView customList;
    public static int deviceWidth ;
    public CustomFragment(){}
    private SqlManager customSqlManager;
    private CustomAdapter baseAdapter;
    private Cursor customResult;
    private ImageButton menu,addCustom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        customView=inflater.inflate(R.layout.custom_fragment_layout,container, false);
        customList=(SwipeListView)customView.findViewById(R.id.custom_list);
        menu=(ImageButton)customView.findViewById(R.id.menu);
        addCustom=(ImageButton)customView.findViewById(R.id.addcustom);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) CustomFragment.this.getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        addCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomFragment.this.getActivity(),AddCustomActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        customSqlManager=new SqlManager(CustomFragment.this.getActivity());
        customResult=customSqlManager.selectTable("custom");

        baseAdapter=new CustomAdapter(inflater,CustomFragment.this,customList,0);
        customList.setAdapter(baseAdapter);

       // Configuration cfg = getResources().getConfiguration();
        //deviceWidth=cfg.screenWidthDp;
        deviceWidth=getDeviceWidth();
        customList.setSwipeListViewListener(new TestBaseSwipeListViewListener());
        reload();
        return customView;
    }
    private void reload() {
//      mSwipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
//      mSwipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
//      mSwipeListView.setSwipeActionRight(settings.getSwipeActionRight());
        //滑动时向左偏移量，根据设备的大小来决定偏移量的大小
        customList.setOffsetLeft(deviceWidth * 4/ 5);
        customList.setOffsetRight(deviceWidth * 4/ 5);
//      mSwipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        //设置动画时间
        customList.setAnimationTime(30);
        customList.setSwipeOpenOnLongPress(false);
    }
    class TestBaseSwipeListViewListener extends BaseSwipeListViewListener {

        //点击每一项的响应事件

        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
            Intent intent=new Intent(CustomFragment.this.getActivity(),UpdateCustomActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("id", (int)baseAdapter.getItemId(position));
            intent.putExtras(bundle);
            CustomFragment.this.startActivityForResult(intent, 1);
        }
       /*
        //关闭事件
        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                listItems.remove(position);
                int id=Integer.valueOf(listItems.get(position).get("id").toString());
                customSqlManager.getReadableDatabase().execSQL("delete from custom where _id='"+id+"'");

            }
            simpleAdapter.notifyDataSetChanged();
            Toast.makeText(CustomFragment.this.getActivity(), "alalalalal", Toast.LENGTH_SHORT).show();
        }
    */
    }

    private int getDeviceWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==0&&resultCode==0){
            baseAdapter.setCustomList();
            baseAdapter.notifyDataSetChanged();

        }else if(requestCode==1&&resultCode==1){
            baseAdapter.setCustomList();
            baseAdapter.notifyDataSetChanged();
        }
    }
}
