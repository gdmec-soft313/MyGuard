package cn.edu.gdmec.chaos07150844.myguard.m7processmanager.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m4appmanager.utils.DensityUtil;
import cn.edu.gdmec.chaos07150844.myguard.m7processmanager.entity.TaskInfo;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class ProcessManagerAdapter extends BaseAdapter{
    private Context context;
    private List<TaskInfo> mUsertaskInfos;
    private List<TaskInfo> mSystaskInfos;
    private SharedPreferences mSP;

    public ProcessManagerAdapter(Context context,List<TaskInfo> userTaskInfos,List<TaskInfo> sysTaskInfo){
        super();
        this.context=context;
        this.mUsertaskInfos=userTaskInfos;
        this.mSystaskInfos=sysTaskInfo;
        mSP=context.getSharedPreferences("config",Context.MODE_PRIVATE);

    }
    @Override
    public int getCount() {
        if(mSystaskInfos.size()>0&mSP.getBoolean("showSystemProcess",true)){
            return mUsertaskInfos.size()+mSystaskInfos.size()+2;
        }else{
            return mUsertaskInfos.size();
        }

    }

    @Override
    public Object getItem(int i) {
        if(i==0||i==mUsertaskInfos.size()+1){
            return null;
        }else if(i<=mUsertaskInfos.size()){
            return mUsertaskInfos.get(i-1);
        }else{
            return mSystaskInfos.get(i-mUsertaskInfos.size()-2);
        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i==0){
            TextView tv=getTextView();
            tv.setText("用户进程："+mUsertaskInfos.size()+"个");
            return tv;
        }else if(i==mUsertaskInfos.size()+1){
            TextView tv=getTextView();
            if(mSystaskInfos.size()>0){
                tv.setText("系统进程"+mSystaskInfos.size()+"个");
                return tv;
            }
        }

        TaskInfo taskInfo=null;
        if(i<=mUsertaskInfos.size()){
            taskInfo=mUsertaskInfos.get(i-1);

        }else if(mSystaskInfos.size()>0){
            taskInfo=mSystaskInfos.get(i-mUsertaskInfos.size()-2);
        }
        ViewHolder holder=null;
        if(view!=null&&view instanceof RelativeLayout){
            holder= (ViewHolder) view.getTag();
        }else{
            view=View.inflate(context, R.layout.item_processmanager_list,null);
            holder=new ViewHolder();
            holder.mAppIconImgv= (ImageView) view.findViewById(R.id.imgv_appicon_processmana);
            holder.mAppMemoryTV= (TextView) view.findViewById(R.id.tv_appmemory_processmana);
            holder.mAppNameTV= (TextView) view.findViewById(R.id.tv_appname_processmana);
            holder.mCheckBox= (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(holder);
        }
        if(taskInfo!=null){
            holder.mAppNameTV.setText(taskInfo.appName);
            holder.mAppMemoryTV.setText("占用内存:"+ Formatter.formatFileSize(context,taskInfo.appMemory));
            holder.mAppIconImgv.setImageDrawable(taskInfo.appIcon);
            if(taskInfo.packageName.equals(context.getPackageName())){
                holder.mCheckBox.setVisibility(View.GONE);
            }else{
                holder.mCheckBox.setVisibility(View.VISIBLE);
            }
            holder.mCheckBox.setChecked(taskInfo.isChecked);
        }
        return view;
    }

    private TextView getTextView(){
        TextView tv=new TextView(context);
        tv.setBackgroundColor(context.getResources().getColor(R.color.graye5));
        tv.setPadding(DensityUtil.dip2px(context,5),DensityUtil.dip2px(context,5),DensityUtil.dip2px(context,5),DensityUtil.dip2px(context,5));
        tv.setTextColor(context.getResources().getColor(R.color.black));
        return tv;
    }

    static class ViewHolder{
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        TextView mAppMemoryTV;
        CheckBox mCheckBox;
    }
}
