package com.experiment.wangld.httpapi;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.internal.util.ArrayListSupplier;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ItemCardViewHolder>{

    private ArrayList<RecyclerObj> objs;

    public RecycleAdapter(ArrayList<RecyclerObj> objs){
        this.objs = objs;
    }
    @Override
    public ItemCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ItemCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent,false));
    }
    public void updateData(ArrayList<RecyclerObj> list){
        this.objs = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ItemCardViewHolder holder, int position){
        startwaiting(holder);
        holder.playContent.setText(String.valueOf(objs.get(position).getIdata().getPlay()));
        holder.commentContent.setText(String.valueOf(objs.get(position).getIdata().getVideo_review()));
        holder.timeContent.setText(objs.get(position).getIdata().getDuration());
        holder.createTimeContent.setText(objs.get(position).getIdata().getCreate());
        holder.titleContent.setText(objs.get(position).getIdata().getTitle());
        holder.content.setText(objs.get(position).getIdata().getContent());
        stopwaiting(holder, position);
    }

    @Override
    public int getItemCount() {
        return objs == null ? 0 : objs.size();
    }
    private void startwaiting(final ItemCardViewHolder holder){
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.myImageView.setVisibility(View.INVISIBLE);
    }
    private void stopwaiting(final ItemCardViewHolder holder, int position){
        holder.myImageView.setImageURL(objs.get(position).getIdata().getCover(), holder.progressBar);
    }


    public static class ItemCardViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView_pic;
        private CardView cardView_con;
        private ProgressBar progressBar;
        private MyImageView myImageView;
        private TextView playContent, commentContent, timeContent, createTimeContent, titleContent, content;

        public ItemCardViewHolder(View itemView){
            super(itemView);
            cardView_pic = (CardView) itemView.findViewById(R.id.pic);
            cardView_con = (CardView) itemView.findViewById(R.id.cont);
            myImageView = (MyImageView) itemView.findViewById(R.id.picture);
            playContent = (TextView) itemView.findViewById(R.id.playcontent);
            commentContent = (TextView) itemView.findViewById(R.id.commentcontent);
            timeContent = (TextView) itemView.findViewById(R.id.timecontent);
            createTimeContent = (TextView) itemView.findViewById(R.id.createTimeContent);
            titleContent = (TextView) itemView.findViewById(R.id.titleContent);
            content = (TextView) itemView.findViewById(R.id.picContent);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
