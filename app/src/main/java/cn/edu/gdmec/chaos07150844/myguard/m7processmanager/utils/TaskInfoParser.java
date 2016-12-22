package cn.edu.gdmec.chaos07150844.myguard.m7processmanager.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m7processmanager.entity.TaskInfo;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class TaskInfoParser {
    public static List<TaskInfo> getRunningTaskInfos(Context context){
        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm=context.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> processInfos=am.getRunningAppProcesses();
        List<TaskInfo> taskInfos=new ArrayList<TaskInfo>();
        for(ActivityManager.RunningAppProcessInfo processInfo:processInfos){
            String packname=processInfo.processName;
            TaskInfo taskInfo=new TaskInfo();
            taskInfo.packageName=packname;
            Debug.MemoryInfo[] memoryInfos=am.getProcessMemoryInfo(new int[]{processInfo.pid});
            long memsize=memoryInfos[0].getTotalPrivateDirty()*1024;
            taskInfo.appMemory=memsize;
            try{
                PackageInfo packInfo=pm.getPackageInfo(packname,0);
                Drawable icon= (Drawable) packInfo.applicationInfo.loadLabel(pm);
                taskInfo.appIcon=icon;
                String appname =packInfo.applicationInfo.loadLabel(pm).toString();
                taskInfo.appName=appname;
                if((ApplicationInfo.FLAG_SYSTEM&packInfo.applicationInfo.flags)!=0){
                    taskInfo.isUserApp=false;
                }else{
                    taskInfo.isUserApp=true;
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                taskInfo.appName=packname;
                taskInfo.appIcon=context.getResources().getDrawable(R.mipmap.ic_launcher);

            }
            taskInfos.add(taskInfo);
        }
        return taskInfos;
    }

}
