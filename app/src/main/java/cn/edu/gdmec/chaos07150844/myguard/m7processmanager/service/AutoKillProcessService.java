package cn.edu.gdmec.chaos07150844.myguard.m7processmanager.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;



/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class AutoKillProcessService extends Service{
    private ScreenLockReceiver receiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver =new ScreenLockReceiver();
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_SCREEN_OFF));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        receiver=null;
    }

    class ScreenLockReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ActivityManager am= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            for(ActivityManager.RunningAppProcessInfo info:am.getRunningAppProcesses()){
                String packname=info.processName;
                am.killBackgroundProcesses(packname);
            }
        }
    }
}
