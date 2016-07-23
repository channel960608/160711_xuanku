package com.shiguang.timefarmer.myapplication3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shiguang.timefarmer.myapplication3.mainfragment.CustomFragment;
import com.shiguang.timefarmer.myapplication3.mainfragment.ScheduleFragment;
import com.shiguang.timefarmer.myapplication3.mainfragment.TimerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private ScheduleFragment fg1;
    private TimerFragment fg2;
    private CustomFragment fg3;
    private FragmentManager fManager;
    private View MyInformation;
    private ImageButton bn_head;
    private int position;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.setDrawerListener();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fManager = getFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_channel = (RadioButton) findViewById(R.id.rb_task);
        rb_channel.setChecked(true);


        MyInformation=MainActivity.this.getLayoutInflater().inflate(R.layout.nav_header_main,null);
        TextView t1=(TextView)navigationView.getHeaderView(0).findViewById(R.id.textView123);
        t1.setText("请登录");
        bn_head=(ImageButton)navigationView.getHeaderView(0).findViewById(R.id.bn_head);
        bn_head.setImageResource(R.drawable.head1);

        bn_head=(ImageButton)navigationView.getHeaderView(0).findViewById(R.id.bn_head);
        bn_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tomato_statistics) {
            // Handle the camera action
        } else if (id == R.id.future_task) {


        } else if (id == R.id.takeout_discount) {

        } else if (id == R.id.system_setting) {
            Intent systemSetting=new Intent(MainActivity.this,SettingActivity.class);
            startActivity(systemSetting);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_task:
                if(fg1 == null){
                    fg1 = new ScheduleFragment();
                    fTransaction.add(R.id.ly_content, fg1);
                    position=1;
                }else{
                    fTransaction.show(fg1);
                    position=1;
                }
                break;
            case R.id.rb_tomato:
                if(fg2==null){
                    fg2 = new TimerFragment();
                    fTransaction.add(R.id.ly_content, fg2);
                    position=2;
                }else{
                    fTransaction.show(fg2);
                    position=2;
                }
                break;
            case R.id.rb_custom:
                if(fg3==null){
                    fg3 = new CustomFragment();
                    fTransaction.add(R.id.ly_content, fg3);
                    position=3;
                }else{
                    fTransaction.show(fg3);
                    position=3;
                }
                break;
            default:
                break;

        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){

        if(requestCode==0&&resultCode==0){
        }
        else{
        }
    }

    }
