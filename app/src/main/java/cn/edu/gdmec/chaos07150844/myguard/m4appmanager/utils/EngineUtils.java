package cn.edu.gdmec.chaos07150844.myguard.m4appmanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

import cn.edu.gdmec.chaos07150844.myguard.m4appmanager.entity.AppInfo;

/**
 * Created by Chaos丶 on 2016/12/20.
 */
public class EngineUtils {

    public static void  shareApplication(Context context, AppInfo appInfo){
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                "推荐您使用一款软件，名称叫："
                        +appInfo.appName
                        +"下载路径：https://play.google.com/store/apps/details?id="
                        +appInfo.packageName);
        context.startActivity(intent);
    }

    public static void starApplication(Context context,AppInfo appInfo){
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(appInfo.packageName);
        if(intent!=null){
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"该应用没有启动界面",Toast.LENGTH_SHORT).show();
        }
    }

    public static void SettingAppDetail(Context context,AppInfo appInfo){
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:"+appInfo.packageName));
        context.startActivity(intent);
    }

    public static void uninstallApplication(Context context,AppInfo appInfo){
        if (appInfo.isUserApp){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:"+appInfo.packageName));
            context.startActivity(intent);
        }else {
            if (!RootTools.isRootAvailable()){
                Toast.makeText(context,"卸载系统应用，必须要root权限",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                if (!RootTools.isAccessGiven()){
                    Toast.makeText(context,"请授权黑马小护卫root权限",Toast.LENGTH_SHORT).show();
                    return;
                }
                RootTools.sendShell("mount -o remount ,rw/system",3000);
                RootTools.sendShell("rm -r"+appInfo.apkPath,3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
