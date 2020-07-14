package com.experiment.group22.glory;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
//coded by wangld
//实现瀑布流效果
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MyRecyclerViewData> dataList; //数据

    private OnRecyclerItemClickListener mOnItemClickListener;   //单击事件
    private onRecyclerItemLongClickListener mOnItemLongClickListener;   //长按事件

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView type;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            type = (TextView) itemView.findViewById(R.id.type);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    /** 构造函数 */
    public MyRecyclerViewAdapter(Context context, ArrayList<MyRecyclerViewData> list) {
        this.context = context;
        this.dataList = list;
    }

    /** 创建item基本视图 */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item, parent, false));
        return holder;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /** 绑定数据到对应item上 */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        int photo_id = context.getResources().getIdentifier(dataList.get(position).photo,
                "mipmap", context.getPackageName());

        //根据资源id获取图片资源的原始尺寸大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), photo_id, options);
        double originalWidth = bm.getWidth();
        double originalHeight = bm.getHeight();
        double ratio = originalWidth / originalHeight;

        //获取屏幕的宽度
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        //一列的宽度 = 屏幕宽度 - 图片之间的间隙 / 2 (两列)
        float scale = context.getResources().getDisplayMetrics().density;
        int gap_px = (int) (8 * scale + 0.5f);
        int myWidth = (screenWidth - gap_px) / 2;
        //高度 = 一列的宽度 / 从数据中得到的宽高比
        int myHeight = (int) (myWidth / ratio + 0.5f);

        //填充数据
        holder.photo.setBackgroundResource(photo_id);
        ViewGroup.LayoutParams params = holder.photo.getLayoutParams();
        params.width = myWidth; params.height= myHeight;
        holder.photo.setLayoutParams(params);

        holder.type.setText(dataList.get(position).type);
        holder.name.setText(dataList.get(position).name);

        //设置单击事件
        if(mOnItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            });
        }
        //长按事件
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //这里需要获取布局中的position,不然乱序
                    mOnItemLongClickListener.onItemLongClick(v, holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    /** 点击与长按事件监听器接口 */
    interface OnRecyclerItemClickListener {
        void onItemClick(View view, int position);
    }
    interface  onRecyclerItemLongClickListener{
        void onItemLongClick(View view, int position);
    }
    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(onRecyclerItemLongClickListener onItemLongClickListener){
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /** 在最末尾添加元素 */
    public void addItem(int position, MyRecyclerViewData data) {
        if(position == -1) position = dataList.size();  //默认加到末尾

        dataList.add(position, data);//在集合中添加这条数据
        notifyItemInserted(position);//通知插入了数据
    }

    /** 移除指定位置元素 */
    public MyRecyclerViewData removeItem(int position) {
        MyRecyclerViewData data = dataList.remove(position);//在集合中删除这条数据
        notifyItemRemoved(position);//通知删除了数据
        return data;
    }

}