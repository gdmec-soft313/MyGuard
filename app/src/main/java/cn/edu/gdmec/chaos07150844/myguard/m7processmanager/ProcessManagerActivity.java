package cn.edu.gdmec.chaos07150844.myguard.m7processmanager;

import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m7processmanager.adapter.ProcessManagerAdapter;
import cn.edu.gdmec.chaos07150844.myguard.m7processmanager.entity.TaskInfo;

public class ProcessManagerActivity extends AppCompatActivity {
    private TextView mRunProcessNum;
    private TextView mMemoryTV;
    private  TextView mProcessNumTV;
    private ListView mLIstView;
    ProcessManagerAdapter adapter;
    private List<TaskInfo> runningTaskInfos;
    private List<TaskInfo> userTaskInfos=new ArrayList<TaskInfo>();
    private List<TaskInfo> sysTaskInfo=new ArrayList<TaskInfo>();
    private ActivityManager manager;
    private int runningProcessCount;
    private long totalMem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_process_manager);
        /*initView();
        fillData();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    /*private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_green));
        ImageView mLeftImgv=(ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        ImageView mRightImgv= (ImageView) findViewById(R.id.imgv_rightbtn);
        mRightImgv.setImageResource(R.drawable.processmanager_setting_icon);
        mRightImgv.setOnClickListener(this);
        ((TextView)findViewById(R.id.tv_title)).setText("进程管理");
    }*/
}
