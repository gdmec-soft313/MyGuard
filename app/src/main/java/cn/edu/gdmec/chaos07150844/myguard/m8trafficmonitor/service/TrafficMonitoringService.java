package cn.edu.gdmec.chaos07150844.myguard.m8trafficmonitor.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.gdmec.chaos07150844.myguard.m8trafficmonitor.db.dao.TrafficDao;

/**
 * Created by Chaos丶 on 2016/12/21.
 */
public class TrafficMonitoringService extends Service{

    private long mOldRxBytes;
    private long mOldTxBytes;
    private TrafficDao dao;
    private SharedPreferences msp;
    private long usedFlow;
    boolean flag = true;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mOldRxBytes = TrafficStats.getMobileRxBytes();
        mOldTxBytes = TrafficStats.getMobileTxBytes();
        dao = new TrafficDao(this);
        msp = getSharedPreferences("config",MODE_PRIVATE);
        mThread.start();
    }
    private Thread mThread = new Thread(){
        @Override
        public void run() {
            while (flag){
                try {
                    Thread.sleep(2000*60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateTodayGPRS();
            }
        }
        private void updateTodayGPRS(){
            usedFlow = msp.getLong("usedflow",0);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (calendar.DAY_OF_MONTH == 1 & calendar.HOUR_OF_DAY == 0 & calendar.MINUTE < 1 & calendar.SECOND < 30){
                usedFlow = 0;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataString = sdf.format(date);
            long moblieGPRS = dao.getMoblieGPRS(dataString);
            long moblieRxBytes = TrafficStats.getMobileRxBytes();
            long moblieTxBytes = TrafficStats.getMobileTxBytes();
            long newGprs = (moblieRxBytes + moblieTxBytes)- mOldRxBytes - mOldTxBytes;
            mOldRxBytes = moblieRxBytes;
            mOldTxBytes = moblieTxBytes;
            if (newGprs < 0){
                newGprs = moblieRxBytes + moblieTxBytes;
            }
            if (moblieGPRS == -1){
                dao.insertTodayGPRS(newGprs);
            }else {
                if (moblieGPRS<0){
                    moblieGPRS=0;
                }
                dao.UpdateTodayGPRS(moblieGPRS+newGprs);
            }
            usedFlow = usedFlow + newGprs;
            SharedPreferences.Editor editor = msp.edit();
            editor.putLong("userflow",usedFlow);
            editor.commit();
        }
    };

    @Override
    public void onDestroy() {
        if (mThread != null & !mThread.interrupted()){
            flag = false;
            mThread.interrupt();
            mThread = null;
        }
        super.onDestroy();
    }
}
