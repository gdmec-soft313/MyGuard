package cn.edu.gdmec.chaos07150844.myguard.m1home;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m1home.adapter.HomeAdapter;
import cn.edu.gdmec.chaos07150844.myguard.m2theftguard.LostFindActivity;
import cn.edu.gdmec.chaos07150844.myguard.m2theftguard.dialog.InterPasswordDialog;
import cn.edu.gdmec.chaos07150844.myguard.m2theftguard.dialog.SetUpPasswordDialog;
import cn.edu.gdmec.chaos07150844.myguard.m2theftguard.receiver.MyDeviceAdminReciever;
import cn.edu.gdmec.chaos07150844.myguard.m2theftguard.utils.MD5Utils;
import cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.SecurityPhoneActivity;
import cn.edu.gdmec.chaos07150844.myguard.m4appmanager.AppManagerActivity;
import cn.edu.gdmec.chaos07150844.myguard.m9advancedtools.AdvancedToolsActivity;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_home;
    private SharedPreferences msharedPreferences;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        msharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        gv_home = (GridView)findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));



        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(isSetUpPassword()){
                            showInterPswdDialog();
                        }else{
                            showSetUpPswDialog();
                        }
                        break;
                    case 1://通讯卫士
                        startActivity(SecurityPhoneActivity.class);
                        break;
                    case 2://软件管家
                        startActivity(AppManagerActivity.class);
                        break;
                    case 3://手机杀毒
                        //startActivity(VirusScanActivity.class);
                        break;
                    case 4://缓存清理
                        //startActivity(CacheClearListActivity.class);
                        break;
                    case 5://进程管理
                        //startActivity(ProcessManagerActivity.class);
                        break;
                    case 6://流量统计
                        //startActivity(TrafficMonitoringActivity.class);
                        break;
                    case 7://高级工具
                        startActivity(AdvancedToolsActivity.class);
                        break;
                    case 8://设置中心
                        //startActivity(SettingsActivity.class);
                        break;
                }
            }
        });

        policyManager= (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName=new ComponentName(this,MyDeviceAdminReciever.class);
        boolean active=policyManager.isAdminActive(componentName);
        if(!active){
            Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"获取超级管理员权限，用于远程锁屏和清理数据");
            startActivity(intent);
        }


    }

    private void showSetUpPswDialog(){
        final SetUpPasswordDialog setUpPasswordDialog=new SetUpPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack(){
            public void ok(){
                String firstPwsd=setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd=setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if(!TextUtils.isEmpty(firstPwsd)&&!TextUtils.isEmpty(affirmPwsd)){
                    if(firstPwsd.equals(affirmPwsd)){
                        savePswd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                        showInterPswdDialog();
                    }else{
                        Toast.makeText(HomeActivity.this,"两次密码不一致！",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(HomeActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();

                }
            }

            public void cancle(){
                setUpPasswordDialog.dismiss();
            }
        });

        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }

    private void showInterPswdDialog(){
        final String password=getPassword();
        final InterPasswordDialog mInPswdDialog=new InterPasswordDialog(HomeActivity.this);
        mInPswdDialog.setCallBack(new InterPasswordDialog.MyCallBack(){
            public void confirm(){
                if(TextUtils.isEmpty(mInPswdDialog.getPassword())){
                    Toast.makeText(HomeActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
                }else if(password.equals(MD5Utils.encode(mInPswdDialog.getPassword()))){
                    mInPswdDialog.dismiss();
                    startActivity(LostFindActivity.class);
                }else{
                    mInPswdDialog.dismiss();
                    Toast.makeText(HomeActivity.this,"密码有误，请重新输入！",Toast.LENGTH_SHORT).show();
                }
            }

            public void cancle(){
                mInPswdDialog.dismiss();
            }
        });
        mInPswdDialog.setCancelable(true);
        mInPswdDialog.show();
    }

    private void savePswd(String affirmPswd){
        SharedPreferences.Editor editor=msharedPreferences.edit();
        editor.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPswd));
        editor.commit();
    }

    private String getPassword(){
        String password=msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password)){
            return "";
        }
        return password;
    }

    private boolean isSetUpPassword(){
        String password=msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }

    public void startActivity(Class<?> cls){
        Intent intent=new Intent(HomeActivity.this,cls);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis()-mExitTime)>2000){
                Toast.makeText(this,"再一次退出程序",Toast.LENGTH_SHORT).show();
                mExitTime=System.currentTimeMillis();
            }else{
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
