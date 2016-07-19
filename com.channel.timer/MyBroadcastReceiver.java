package channel_test.a160713_channel_test2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by channel on 2016/7/13.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT="com.example.broadcasttest.MY_BROADCAST";
    private final String ACTION_TIMEUP="com.example.broadcasttest.TIMEUP";
    @Override
    public void onReceive(Context context, Intent intent){
        if(ACTION_BOOT.equals(intent.getAction()))
            Toast.makeText(context,"get it",Toast.LENGTH_SHORT).show();
        if(ACTION_TIMEUP.equals(intent.getAction()))
            Toast.makeText(context,"time up",Toast.LENGTH_SHORT).show();
    }

}
