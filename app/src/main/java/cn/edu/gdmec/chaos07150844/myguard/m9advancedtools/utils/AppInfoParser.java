package cn.edu.gdmec.chaos07150844.myguard.m9advancedtools.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.chaos07150844.myguard.m4appmanager.entity.AppInfo;

/**
 * Created by qaq on 2016/12/20.
 */
public class AppInfoParser {
    public static List<AppInfo> getAppInfos(Context context){
        PackageManager pm=context.getPackageManager();
        List<PackageInfo> packaInfos=pm.getInstalledPackages(0);
        List<AppInfo> appinfos=new ArrayList<AppInfo>();
        for (PackageInfo packaInfo:packaInfos){
            AppInfo appinfo=new AppInfo();
            String packname=packaInfo.packageName;
            appinfo.packageName=packname;
            Drawable icon=packaInfo.applicationInfo.loadIcon(pm);
            appinfo.icon=icon;
            String appname=packaInfo.applicationInfo.loadLabel(pm).toString();
            appinfo.appName=appname;
            String apkpath=packaInfo.applicationInfo.sourceDir;
            appinfo.apkPath=apkpath;
            appinfos.add(appinfo);
            appinfo=null;
        }
        return appinfos;
    }
}
