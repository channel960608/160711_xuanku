package channel_test.a160713_channel_test2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by channel on 2016/7/13.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent){
       if(ACTION_BOOT.equals(intent.getAction()))
           Toast.makeText(context,"开机完毕",Toast.LENGTH_SHORT).show();

    }
}
