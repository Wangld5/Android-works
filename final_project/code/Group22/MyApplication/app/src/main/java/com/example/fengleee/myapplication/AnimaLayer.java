package com.example.fengleee.myapplication;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ViewStubCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class AnimaLayer extends LinearLayout {
    private List<BlockView> cards = new ArrayList<BlockView>();
    public AnimaLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AnimaLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimaLayer(Context context) {
        super(context);
    }

    public void createMoveAnima(final BlockView from, final BlockView to, int fromX, int toX, int fromY, int toY){

    }
    private BlockView getCard(int num){
        BlockView c;
        if(cards.size() > 0){
            c = cards.remove(0);
        }else{
            c = new BlockView(getContext());
            addView(c);
        }
        c.setVisibility(View.VISIBLE);
        c.setNum(num);
        return c;
    }


}
