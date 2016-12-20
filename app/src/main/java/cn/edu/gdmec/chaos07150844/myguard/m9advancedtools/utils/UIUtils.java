package cn.edu.gdmec.chaos07150844.myguard.m9advancedtools.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by qaq on 2016/12/20.
 */
public class UIUtils {
    public static void showToast(final Activity context,final String msg){
        if("main".equals(Thread.currentThread().getName())){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }else{
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
