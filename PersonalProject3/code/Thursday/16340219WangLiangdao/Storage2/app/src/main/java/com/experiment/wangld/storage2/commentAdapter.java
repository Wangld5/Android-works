package com.experiment.wangld.storage2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class commentAdapter extends BaseAdapter {
    Context context;
    List<commentInfo> data;
    String username;
    boolean isChange = false;
    nyDB myDB;
    public commentAdapter(Context context, List<commentInfo> data, String username, nyDB myDB){
        this.context = context;
        this.data = data;
        this.username = username;
        this.myDB = myDB;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        // 重用convertView
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.commentlist, null);
            holder.comment_head = (ImageView) convertView.findViewById(R.id.head);
            holder.comment_good = (ImageView) convertView.findViewById(R.id.good);
            holder.comment_user = (TextView) convertView.findViewById(R.id.commentUser);
            holder.comment_content = (TextView) convertView.findViewById(R.id.commentContent);
            holder.comment_time = (TextView) convertView.findViewById(R.id.commentTime);
            holder.comment_goodNum = (TextView) convertView.findViewById(R.id.goodNum);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // 适配数据
        holder.comment_head.setImageDrawable(data.get(position).getHead());
        holder.comment_user.setText(data.get(position).getUsername());
        holder.comment_content.setText(data.get(position).getContent());
        holder.comment_time.setText(data.get(position).getTime());
        holder.comment_goodNum.setText(String.valueOf(myDB.getGoodNum(data.get(position).getUsername())));
        if(myDB.checkIfPeopleComment(username, data.get(position).getUsername())) {
            holder.comment_good.setImageResource(R.drawable.red);
        }else{
            holder.comment_good.setImageResource(R.drawable.white);
        }
        holder.comment_good.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!myDB.checkIfPeopleComment(username, data.get(position).getUsername())){
                    holder.comment_good.setImageResource(R.drawable.red);
                    int addgood = myDB.saveWhoComment(username, data.get(position).getUsername());
                    holder.comment_goodNum.setText(String.valueOf(addgood));
                }else{
                    holder.comment_good.setImageResource(R.drawable.white);
                    int addgood = myDB.deleteWhoComment(username, data.get(position).getUsername());
                    if (addgood < 0) addgood = 0;
                    holder.comment_goodNum.setText(String.valueOf(addgood));
                }
            }
        });

        return convertView;
    }
    public void addComment(commentInfo comment){
        data.add(comment);
        notifyDataSetChanged();
    }
    public void removeComment(commentInfo comment){
        data.remove(comment);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        ImageView comment_good;
        ImageView comment_head;
        TextView comment_user;
        TextView comment_content;
        TextView comment_time;
        TextView comment_goodNum;
    }
}
