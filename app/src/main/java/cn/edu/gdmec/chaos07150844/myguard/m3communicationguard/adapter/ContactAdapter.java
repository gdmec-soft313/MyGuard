package cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.db.dao.BlackNumberDao;
import cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.entity.BlackContactInfo;
import cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.entity.ContactInfo;

/**
 * Created by flora on 2016/12/20.
 */
public class ContactAdapter extends BaseAdapter{

    private List<ContactInfo> contactInfos;
    private Context context;

    public ContactAdapter(List<ContactInfo> systemContacts, Context context){
        super();
        this.contactInfos = systemContacts;
        this.context = context;

    }
    @Override
    public int getCount() {
        return contactInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder =null;
        if(convertView ==null){
            convertView = View.inflate(context, R.layout.item_list_contact_select,null);
            holder = new ViewHolder();
            holder.mNameTV = (TextView)convertView.findViewById(R.id.tv_name);
            holder.mPhoneTV = (TextView)convertView.findViewById(R.id.tv_phone);
            holder.mContactImgv = convertView.findViewById(R.id.view1);
            convertView.setTag(holder);


        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mNameTV.setText(contactInfos.get(position).name);
        holder.mPhoneTV.setText(contactInfos.get(position).phone);
        holder.mNameTV.setTextColor(context.getResources().getColor(
                R.color.bright_purple
        ));
        holder.mPhoneTV.setTextColor(context.getResources().getColor(
                R.color.bright_purple
        ));
        holder.mContactImgv.setBackgroundResource(R.drawable.brightpurple_contact_icon);

            return convertView;
    }

    static class ViewHolder{
        TextView mNameTV;
        TextView mPhoneTV;
        View mContactImgv;
    }
}
