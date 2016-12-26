package cn.edu.gdmec.chaos07150844.myguard.m9advancedtools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m9advancedtools.service.AppLockService;

public class AdvancedToolsActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_advanced_tools);
        initView();
    }

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_red));
        ImageView mLeftImgv= (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView)findViewById(R.id.tv_title)).setText("高级工具");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        findViewById(R.id.advanceview_applock).setOnClickListener(this);
        findViewById(R.id.advanceview_numbelongs).setOnClickListener(this);
    }
    
    public void onClick(View v){
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.advanceview_applock:
                startActivity(AppLockService.class);
                break;
            case R.id.advanceview_numbelongs:
                startActivity(NumBelongtoActivity.class);
                break;
            case R.id.advanceview_smsbackup:
                startActivity(SMSBackupActivity.class);
                break;
            case R.id.advanceview_smsreduction:
                startActivity(SMSReducitionActivity.class);
                break ;
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent=new Intent(this,cls);
        startActivity(intent);
    }
}
