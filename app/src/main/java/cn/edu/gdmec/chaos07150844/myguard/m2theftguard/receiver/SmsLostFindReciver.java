package cn.edu.gdmec.chaos07150844.myguard.m2theftguard.receiver;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m2theftguard.service.GPSLocationService;

public class SmsLostFindReciver extends BroadcastReceiver {
    private static final String TAG = SmsLostFindReciver.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private ComponentName componentName;


    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        boolean protecting  = sharedPreferences.getBoolean("protecting",true);
        if(protecting){
            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            for(Object obj:objs){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])obj);
                String sender = smsMessage.getOriginatingAddress();
                if(sender.startsWith("+86")){
                    sender =sender.substring(3,sender.length());
                }
                String body = smsMessage.getMessageBody();
                String safephone = sharedPreferences.getString("safephone",null);
                if(!TextUtils.isEmpty(safephone) & sender.equals(safephone)){
                    if("#*location*#".equals(body)){
                        Log.i(TAG,"返回位置信息");
                        Intent service = new Intent(context, GPSLocationService.class);
                        context.startService(service);
                        abortBroadcast();
                    }else if("#*alarm*#".equals(body)){
                        Log.i(TAG,"播放警告音乐");
                        MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                        player.setVolume(1.0f,1.0f);
                        player.start();
                        abortBroadcast();
                    }else if("#*wipedata*#".equals(body)){
                        Log.i(TAG,"远程清除数据");
                        dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                        abortBroadcast();

                    }else if("#*lockScreen*#".equals(body)){
                        Log.i(TAG,"远程锁屏");
                        dpm.resetPassword("12345",0);
                        dpm.lockNow();
                        abortBroadcast();
                    }
                }
            }
        }

    }
}
