package com.experiment.wangld.listviewexperience;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {
    ArrayList<food> foods;

    public MyAdapter(ArrayList<food> foods) {
        this.foods = foods;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ItemViewHolder(v);
    }

    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final food myfood = foods.get(position);
        System.out.print(myfood.getName());
        holder.label.setText(myfood.getLabel());
        holder.name.setText((myfood.getName()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), food_detail.class);
                Bundle data = new Bundle();
                data.putInt("foodIndex", position);
                data.putString("name", myfood.getName());
                data.putString("type", myfood.getType());
                data.putString("cover", myfood.getCover());
                data.putString("nutrition", myfood.getNutrition());
                data.putInt("color", myfood.getColor());
                data.putBoolean("isInFoodList", true);


                intent.putExtras(data);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ((Activity)view.getContext()).startActivityForResult(intent, 0);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                foods.remove(position);
                MyAdapter.this.notifyDataSetChanged();
                Toast.makeText(view.getContext(), "移除第" + position + "个食品", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public int getItemCount() {
        return foods == null ? 0 : foods.size();
    }

    public food getFood(int pos) {
        return foods.get(pos);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public TextView name;
        public ItemViewHolder(View itemView) {
            super(itemView);
            this.label = itemView.findViewById(R.id.label);
            this.name = (TextView)itemView.findViewById(R.id.name);
        }
    }
}
