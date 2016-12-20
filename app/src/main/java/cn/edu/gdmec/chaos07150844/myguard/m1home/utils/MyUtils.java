package cn.edu.gdmec.chaos07150844.myguard.m1home.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
public class MyUtils {
    public static String getVersion(Context context){
        PackageManager manager=context.getPackageManager();
        try{
            PackageInfo packageInfo=manager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return "";
        }
    }

    public static  void installApk(Activity activity){
        Intent intent=new Intent("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobilesafe2.0.apk")),"application/vnd.android.package-archive");
        activity.startActivityForResult(intent,0);
    }
}
