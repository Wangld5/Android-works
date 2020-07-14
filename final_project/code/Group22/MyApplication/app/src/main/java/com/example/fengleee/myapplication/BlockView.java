package com.example.fengleee.myapplication;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BlockView extends FrameLayout {
    private int num = 0;
    // 只显示数字的话可以用Textview,要是用图片的话要用imview
    private TextView label;
    private ImageView pic;
    private int[] picArray = new int[3000];
    private MainActivity mainActivity;
    private int model = 1;
    private LayoutParams lp;

    public BlockView(Context context) {
        super(context);
        putPic();
        pic = new ImageView(getContext());
        lp = new LayoutParams(-1, -1);// -1,-1就是填充完父类容器的意思
        lp.setMargins(10, 10, 0, 0);// 用来设置边框很管用
        addView(pic, lp);// 把imageView加到CardView上
        setNum(0);
        setBackgroundColor(0xff427f36);
    }

    //把数字逻辑实现的2048转化为图片逻辑，只需要把数字定位数组序数，数字对应图片，并保持一一对应关系
    public void putPic() {

//        // 数字版+0
        picArray[0] = R.drawable.bleach0;
        picArray[2] = R.drawable.num2;
        picArray[4] = R.drawable.num4;
        picArray[8] = R.drawable.num8;
        picArray[16] = R.drawable.num16;
        picArray[32] = R.drawable.num32;
        picArray[64] = R.drawable.num64;
        picArray[128] = R.drawable.num128;
        picArray[256] = R.drawable.num256;
        picArray[512] = R.drawable.num512;
        picArray[1024] = R.drawable.num1024;
        picArray[2048] = R.drawable.num2048;
    }

    // 数字:数字相当于图片id
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        mainActivity = (MainActivity) this.getContext();
        model = mainActivity.getModel();
        switch (model) {
            case 1:// 普通模式
                pic.setBackgroundResource(picArray[num]);
                break;
            case 2:// **模式
                pic.setBackgroundResource(picArray[num + 17]);
                break;
        }
    }

    public void setSameColor(int color){
        setBackgroundColor(color);
    }

    // 判断数字是否相同
    public boolean equals(BlockView cv) {
        return getNum() == cv.getNum();
    }
}
