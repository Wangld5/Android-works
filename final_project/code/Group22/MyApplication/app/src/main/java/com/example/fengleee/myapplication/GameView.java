package com.example.fengleee.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    private float startX, startY, endX, endY, offX, offY;
    // 行row对应y，列colunm对应x,默认为4
    private int row = 4, colunm = 4;
    // 二维数组存储
    private BlockView[][] blockArray = new BlockView[10][10];
    // 链表方便增加删除
    private List<Point> emptyPoints = new ArrayList<Point>();
    private static GameView gameView = null;
    private MainActivity mainActivity;
    private MediaPlayer mediaPlayer;
    private boolean flag = false, flag1 = false;
    private int cw;

    // 添加构造方法，对应参分别为上下文，属性，样式
    public GameView(Context context) {
        super(context);
        gameView = this;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameView = this;
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        gameView = this;
    }

    public static GameView getGameView() {
        return gameView;
    }

    // 动态地获取卡片的宽高
    protected void onSizeChanged(int w, int h, int w_, int h_) {
        super.onSizeChanged(w, h, w_, h_);
        int blockWidth = (w - 10) / colunm;
        addBlocks(blockWidth, blockWidth);
        startGame();
    }

    public void startGame() {
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
                blockArray[x][y].setNum(0);
            }
        }
        mainActivity.initScore();
        mainActivity.clearScore();
        addRandomNum();
        addRandomNum();
        setOriginColor();
    }

    public BlockView[][] getBlockArray (){
        return blockArray;
    }

    private void addBlocks(int blockWidth, int cardHeigth) {
        BlockView c;
        cw = blockWidth;
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
                c = new BlockView(getContext());
                // 初始画0号图片
                c.setNum(0);
                addView(c, blockWidth, cardHeigth);
                blockArray[x][y] = c;
            }
        }
    }

    // 遍历方块后添加随机数
    private void addRandomNum() {
        emptyPoints.clear();
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
                if (blockArray[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y)); //传递空白块给链表
                }
            }
        }
        // 随机给一个空白块赋值，生成2和4的概率分别为9和1
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        blockArray[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    public void getTip(){
        if(checkLeft()){
            moveLeft();

        }else if(checkRight()){
            moveRight();

        }else if(checkUp()){
            moveUp();

        }else if(checkDown()){
            moveDown();

        }
    }
    private Boolean checkLeft(){
        Boolean canMerge = false;
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int x1 = x + 1; x1 < colunm; x1++) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x1][y].getNum() > 0) {
                        if (blockArray[x][y].equals(blockArray[x1][y])) {
                            blockArray[x][y].setSameColor(getResources().getColor(R.color.silver));
                            blockArray[x1][y].setSameColor(getResources().getColor(R.color.silver));
                            canMerge = true;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        return canMerge;
    }
    private Boolean checkRight(){
        Boolean canMerge = false;
        for (int y = 0; y < row; y++) {
            for (int x = colunm - 1; x >= 0; x--) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x1][y].getNum() > 0) {
                        if (blockArray[x][y].equals(blockArray[x1][y])) {
                            blockArray[x][y].setSameColor(R.color.goldenrod);
                            blockArray[x1][y].setSameColor(R.color.goldenrod);
                            canMerge = true;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        return canMerge;
    }
    private Boolean checkUp(){
        Boolean canMerge = false;
        for (int x = 0; x < colunm; x++) {
            for (int y = 0; y < row; y++) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int y1 = y + 1; y1 < row; y1++) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x][y1].getNum() > 0) {
                        if (blockArray[x][y].equals(blockArray[x][y1])) {
                            blockArray[x][y].setSameColor(R.color.paleturquoise);
                            blockArray[x][y1].setSameColor(R.color.paleturquoise);
                            canMerge = true;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        return canMerge;
    }
    private Boolean checkDown(){
        Boolean canMerge = false;
        for (int x = 0; x < colunm; x++) {
            for (int y = row - 1; y >= 0; y--) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x][y1].getNum() > 0) {
                        if (blockArray[x][y].equals(blockArray[x][y1])) {
                            blockArray[x][y].setSameColor(R.color.colorAccent);
                            blockArray[x][y1].setSameColor(R.color.colorAccent);
                            canMerge = true;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        return canMerge;
    }
    // GameView初始化
    public void initGameView() {
        mainActivity = (MainActivity) this.getContext();
        colunm = mainActivity.getColumn();
        row = mainActivity.getRow();
        // 设置表格为4列
        setColumnCount(colunm);
        setRowCount(row);

        setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();// 获取触屏的动作
                switch (action) {
                    // 记录触摸起始点
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    // 记录终止点，通过比较位移来判断滑动方向
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        endY = event.getY();
                        offX = startX - endX;
                        offY = startY - endY;
                        if (Math.abs(offX) >= Math.abs(offY)) {
                            if (offX >= 5)
                                moveLeft();
                            else if (offX < -5)
                                moveRight();
                        } else if (Math.abs(offX) <= Math.abs(offY)) {
                            if (offY >= 5)
                                moveUp();
                            else if (offY < -5)
                                moveDown();
                        }
                        break;
                }
                // 返回true执行ACTION_UP
                return true;
            }
        });
    }
    private void setOriginColor(){
        for(int y = 0; y<row; y++){
            for(int x=0; x<colunm; x++){
                blockArray[x][y].setBackgroundColor(0xff427f36);
            }
        }
    }
    private void moveLeft() {
        boolean merge = false;
        flag = false;
        flag1 = false;
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int x1 = x + 1; x1 < colunm; x1++) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x1][y].getNum() > 0) {
                        if (blockArray[x][y].getNum() <= 0) {
                            blockArray[x][y].setNum(blockArray[x1][y].getNum());
                            blockArray[x1][y].setNum(0);
                            x--;// 填补空位后，还要再次判断有没相同的可以合并的
                            merge = true;
                            if(!flag && !flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.move);
                                flag = true;
                            }
                            break;
                        } else if (blockArray[x][y].equals(blockArray[x1][y])) {
                            blockArray[x][y].setNum(blockArray[x][y].getNum() * 2);
                            blockArray[x1][y].setNum(0);
                            mainActivity.addScore(blockArray[x][y].getNum());
                            merge = true;
                            if(!flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.del);
                                flag1 = true;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        }

        mediaPlayer.start();
        if (merge) {
            setOriginColor();
            addRandomNum();
            checkComplete();
        }
    }

    private void moveRight() {
        boolean merge = false;
        flag  = false;
        flag1 = false;
        for (int y = 0; y < row; y++) {
            for (int x = colunm - 1; x >= 0; x--) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x1][y].getNum() > 0) {
                        if (blockArray[x][y].getNum() <= 0) {
                            blockArray[x][y].setNum(blockArray[x1][y].getNum());
                            blockArray[x1][y].setNum(0);
                            x++;// 填补空位后，还要再次判断有没相同的可以合并的
                            merge = true;
                            if(!flag && !flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.move);
                                flag = true;
                            }
                            break;
                        } else if (blockArray[x][y].equals(blockArray[x1][y])) {
                            blockArray[x][y].setNum(blockArray[x][y].getNum() * 2);
                            blockArray[x1][y].setNum(0);
                            mainActivity.addScore(blockArray[x][y].getNum());
                            merge = true;
                            if(!flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.del);
                                flag1 = true;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        }
        mediaPlayer.start();
        if (merge) {
            setOriginColor();
            addRandomNum();
            checkComplete();
        }

    }

    private void moveUp() {
        boolean merge = false;
        flag = false;
        flag1 = false;
        for (int x = 0; x < colunm; x++) {
            for (int y = 0; y < row; y++) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int y1 = y + 1; y1 < row; y1++) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x][y1].getNum() > 0) {
                        if (blockArray[x][y].getNum() <= 0) {
                            blockArray[x][y].setNum(blockArray[x][y1].getNum());
                            blockArray[x][y1].setNum(0);
                            y--;// 填补空位后，还要再次判断有没相同的可以合并的
                            merge = true;
                            if(!flag && !flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.move);
                                flag = true;
                            }
                            break;
                        } else if (blockArray[x][y].equals(blockArray[x][y1])) {
                            blockArray[x][y].setNum(blockArray[x][y].getNum() * 2);
                            blockArray[x][y1].setNum(0);
                            mainActivity.addScore(blockArray[x][y].getNum());
                            merge = true;
                            if(!flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.del);
                                flag1 = true;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        }
        mediaPlayer.start();
        if (merge) {
            setOriginColor();
            addRandomNum();
            checkComplete();
        }

    }

    private void moveDown() {
        boolean merge = false;
        flag = false;
        flag1 = false;
        for (int x = 0; x < colunm; x++) {
            for (int y = row - 1; y >= 0; y--) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    // 每个右边的位置只判断执行一次
                    if (blockArray[x][y1].getNum() > 0) {
                        if (blockArray[x][y].getNum() <= 0) {
                            blockArray[x][y].setNum(blockArray[x][y1].getNum());
                            blockArray[x][y1].setNum(0);
                            y++;// 填补空位后，还要再次判断有没相同的可以合并的
                            merge = true;
                            if(!flag && !flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.move);
                                flag = true;
                            }
                            break;
                        } else if (blockArray[x][y].equals(blockArray[x][y1])) {
                            blockArray[x][y].setNum(blockArray[x][y].getNum() * 2);
                            blockArray[x][y1].setNum(0);
                            mainActivity.addScore(blockArray[x][y].getNum());
                            merge = true;
                            if(!flag1) {
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.del);
                                flag1 = true;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
        }
        mediaPlayer.start();
        if (merge) {
            setOriginColor();
            addRandomNum();
            checkComplete();
        }

    }

    // 判断结束
    private void checkComplete() {
        boolean complete = true;
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
                if (blockArray[x][y].getNum() == 2048)
                    new AlertDialog.Builder(getContext())
                            .setTitle("你好")
                            .setMessage("游戏胜利")
                            .setPositiveButton("重来",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startGame();
                                        }
                                    }).show();
            }
        }

        ALL: for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
                // 如果还有空位，或者四个方向上还有相同的
                if (blockArray[x][y].getNum() == 0
                        || (x > 0 && blockArray[x][y].equals(blockArray[x - 1][y]))
                        || (x < 3 && blockArray[x][y].equals(blockArray[x + 1][y]))
                        || (y > 0 && blockArray[x][y].equals(blockArray[x][y - 1]))
                        || (y < 3 && blockArray[x][y].equals(blockArray[x][y + 1]))) {
                    complete = false;
                    break ALL;// 如果出现这种情况，跳出双重循环，只写一个break只能跳出当前循环
                }
            }
        }
        if (complete) {
            if(mainActivity.getMyDBHelper().isOverRank(mainActivity.getRank(), mainActivity.getScore(), mainActivity.getLastTime())){
                new AlertDialog.Builder(getContext())
                        .setTitle("保存成绩")
                        .setMessage("Do you want to store the score?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivity.showEditDialog();
                            }
                        })
                        .create().show();

            }
            new AlertDialog.Builder(getContext())
                    .setTitle("你好")
                    .setMessage("游戏结束")
                    .setPositiveButton("重来",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startGame();
                                }
                            }).show();
        }
    }
}
