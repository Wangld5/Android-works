# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 16级 | 专业（方向） | 数字媒体 |
| 学号 | 16340087 | 姓名 | 黄悦 |
| 电话 | 18784863679 | Email | 1103210406@qq.com |
| 开始日期 | 2018.1.1 | 完成日期 | 2018.1.19 |

---

## 一、实验题目
## 期末项目
## 2048

---

## 二、实现内容
## 项目内容要求
* 运用综合知识编写功能比较完善的android项目
* **2019.1.12前完成所有功能开发，额外两周完成项目文档，2019.1.26前提交所有内容**

## 实现内容
* 本游戏是仿照2048小游戏的运行模式开发的一个具有模式切换的2048小游戏
* 在本游戏开始界面会出现游戏模式的选择，玩家可以选择4* 4,5* 5,6* 6的游戏模式
* 进入游戏模式之后会有操作界面和排行榜
* 退出游戏会有是否保存当前分数的提示
* 游戏操作界面设置了音乐播放功能
* 排行榜中的信息使用的是数据库的保存

## 我负责的内容
* 游戏界面布局（activity_main.xml），包括分数、计时的显示控件，音乐播放、重启按钮等  
* 自定义BlockView、GameView控件（BlockView.java，GameView.java），用于实现2048的游戏逻辑以及数字块移动界面
---

## 三、实验结果
### (1)实验截图
1. 开始界面  
![输入图片说明](https://images.gitee.com/uploads/images/2019/0117/225039_dab2c60e_2165059.jpeg "1.jpg")  
2. 模式选择  
![输入图片说明](https://images.gitee.com/uploads/images/2019/0117/225052_19b565b0_2165059.jpeg "2.jpg")    
3. 游戏界面  
![输入图片说明](https://images.gitee.com/uploads/images/2019/0117/225104_8626be1c_2165059.jpeg "3.jpg")  
4. 保存分数  
![输入图片说明](https://images.gitee.com/uploads/images/2019/0117/225121_334897fa_2165059.jpeg "4.jpg")  
5. 排行榜  
![输入图片说明](https://images.gitee.com/uploads/images/2019/0117/225134_ff6e1ff7_2165059.png "5.png")  

---
### (2)实验步骤以及关键代码  
* 游戏界面布局设置    
    1. 最外层使用DrawerLayout，这样方便实现侧滑菜单显示排行榜。
    ```
    <android.support.v4.widget.DrawerLayout
        android:id="@+id/drawer"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </android.support.v4.widget.DrawerLayout>
    ```  
    2. 主界面最外层为纵向的LinearLayout，第一个子控件为一个横向的LinearLayout来实现分数、最高纪录和计时显示的布局
    * 分数和最高纪录都是又使用了一个纵向的LinearLayout，包含两个TextView分别用来指示说明和数字显示
    ```
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:layout_marginTop="20dp"
        android:layout_marginBottom="30dp">
        <LinearLayout
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:orientation="vertical"
            android:layout_marginLeft="30dp"
            android:layout_marginRight="60dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="0dp"
                android:layout_weight="2"
                android:text="@string/current_score"
                android:layout_gravity="center"
                android:layout_marginTop="5dp"
                android:textColor="#fdfda1"
                android:textSize="15sp"/>

            <TextView
                android:id="@+id/tvScore"
                android:layout_width="wrap_content"
                android:layout_height="0dp"
                android:layout_weight="3"
                android:textSize="20sp"
                android:text="0"
                android:layout_gravity="center"
                android:textColor="#fdfda1" />
            </LinearLayout>
    </LinearLayout>
    ```  
    * 计时器用到了一个Chronometer控件，方便进行计时操作
    ```
     <Chronometer
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:format="00:00:00"
        android:textSize="20sp"
        android:textColor="#fdfda1"
        android:layout_gravity="center"
        android:id="@+id/lastTime"/>
    ```
    3. 第二个子控件为自定义的GameView，这个控件实现2048游戏的主要逻辑部分。外层套了一个LinearLayout是为了让它占据界面的宽高更合适。  
    ```
    <LinearLayout
        android:id="@+id/mylinearlaout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="40">
        <com.example.fengleee.myapplication.GameView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/gameView">
        </com.example.fengleee.myapplication.GameView>
    </LinearLayout>
    ```   

    4. 第三个子控件仍然是用了一个横向的LinearLayout，包含一个Button和一个ImageButton，用于实现重新开始功能和音乐暂停播放功能。  
    ```
    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center"
            android:layout_marginTop="30dp"
            android:layout_marginBottom="30dp"
            android:layout_weight="1"
            android:paddingLeft="130dp">
            <Button
                android:id="@+id/restartBtn"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:text="Restart"
                android:textColor="@android:color/white"
                android:background="@drawable/shape"
                android:alpha="0.7" />

            <ImageButton
                android:id="@+id/controlBtn"
                android:layout_width="55dp"
                android:layout_height="match_parent"
                android:src="@mipmap/vol"
                android:layout_marginRight="5dp"
                android:layout_marginLeft="100dp"
                android:background="@drawable/shape"/>

        </LinearLayout>
    </LinearLayout>
    ```  

    5. 左侧侧滑菜单使用一个ListView来实现排行榜的游戏记录显示  
    ```
    <LinearLayout
        android:layout_width="320dp"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="@android:color/white"
        android:orientation="vertical">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/score_rank"
            android:layout_gravity="center"
            android:gravity="center"
            android:textColor="@android:color/black"
            android:textSize="20dp"/>
        <ListView
            android:id="@+id/listView1"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </ListView>
    </LinearLayout>
    ```
    6. 最上方使用Toolbar实现一个工具栏，点击时出现侧滑菜单显示排行榜
    ```
    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:title="2048"
        app:titleTextColor="#fdfda1"
        android:background="#427f36"
        android:alpha="0.8"/>
    ```

* 自定义GameView控件  
    这个控件主要实现了游戏的逻辑部分，以及使用网格布局来显示用到的数字方块  
    1. 自定义一个BlockView类，它继承了FrameLayout，用来表示每一个数字块，里面包含一个数字和一个图片数组。
    * 构造函数中设置填充父类容器显示，使之占满一个格点。这个格点的宽高是在之后设置的。
    * 在setNum(int num)函数中可以设置该数字块的数字以及图片内容，数字的大小即作为下标方便操作。
    ```
    public class BlockView extends FrameLayout {
        private int num = 0;
        private ImageView pic;
        private int[] picArray = new int[3000];
        private MainActivity mainActivity;
        private int model = 1;
        private LayoutParams lp;

        public BlockView(Context context) {
            super(context);
            putPic();
            pic = new ImageView(getContext());
            lp = new LayoutParams(-1, -1);// 填充完父类容器的意思
            lp.setMargins(10, 10, 0, 0);// 用来设置边框
            addView(pic, lp);// 添加imageView
            setNum(0);
            setBackgroundColor(0xff427f36); // 设置背景
        }

        //把数字逻辑实现的2048转化为图片逻辑，只需要把数字定位数组序数，数字对应图片，并保持一一对应关系
        public void putPic() {
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

        // 数字作为图片id
        public int getNum() {
            return num;
        }
        public void setNum(int num) {
            this.num = num;
            // 实现不同的模式
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
        // 判断数字是否相同
        public boolean equals(BlockView cv) {
            return getNum() == cv.getNum();
        }
    }
    ```
    2. 自定义GameView类，继承GridLayout，实现数字方块的网格布局  
    ```
    public class GameView extends GridLayout {
        private static GameView gameView = null;
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
    }
    ```
    * 属性中包含一个私有的BlockView[][]二维数组，存储数字块，一个List<Point>存储其中的空白块，也就是非2048的数字块  
    ```
    // 二维数组存储
    private BlockView[][] blockArray = new BlockView[10][10];
    // 链表方便增加删除
    private List<Point> emptyPoints = new ArrayList<Point>();
    ```
    * initGameView()函数初始化这一控件，从mainactivity中获取行数和列数，从而得知有多少数字块  
    ```
    public void initGameView() {
        mainActivity = (MainActivity) this.getContext();
        colunm = mainActivity.getColumn();
        row = mainActivity.getRow();
        // 设置表格为4列
        setColumnCount(colunm);
        setRowCount(row);
    }
    ```  
    * startGame()函数中初始化游戏，将所有数字块设为0，在onSizeChanged()函数中调用它，以便在布局大小改变时重新开始游戏  
    ```
    // 动态地获取布局的宽高
    protected void onSizeChanged(int w, int h, int w_, int h_){
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
    }
    ```
    * addRandomNum()函数获取所有空白块后遍历，随机将一个块的数字赋值为2或4  
    ```
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
    ```
    * 在initGameView()函数中添加setOnTouchListener()监听触屏事件，根据起始点和终止点来判断手势方向，然后调用相应的函数。一共上下左右四个方向，逻辑都是类似的。  
    ```
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
    ```
    * 如果方向为左，执行moveLeft()函数中的操作，其它三个方向类似。
    * 首先合并该方向上的可以合并的块，包括0和2048数字合并，相同的数字合并。如果合并的是0，那么需要再次判断这个块。然后调用addRandomNum()函数生成新的随机块  
    ```
    private void moveLeft() {
        boolean merge = false;
        flag = false;
        flag1 = false;
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < colunm; x++) {
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
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
    ```
---
### (3)实验遇到的困难以及解决思路
1. 一开始实现排行榜功能想的是添加一个控件设为不可见，然后，点击时可见，操作起来不方便，后来发现了DrawerLayout可以实现侧滑菜单，布局就比较清楚了。  
2. 一开始GameView的布局宽高总是设置不好，在显示网格时总是会多出一截，后来外层套了一个LinearLayout，然后设置GameView宽高为matchparent，再在GameView类里设置宽高相等，以及不设背景解决问题。 
3. 2048的游戏逻辑实现还是比较复杂的，有参考一些已有的2048的逻辑实现。每个数字块都是一个控件，然后使用网格布局来实现界面的显示。 
---

## 四、个人总结与个人贡献评分  
* 我主要负责的是游戏界面的UI设计以及游戏的逻辑部分。  
* 在界面的布局上，我们用到了之前没用过的DrawerLayout控件，实现一个侧滑菜单，来显示排行榜。然后计时器使用了Chronometer控件，操作起来还是比较方便的。  
* 然后就是自定义的GameView控件了，它继承了一个GridLayout类，实现一个网格布局。每一个网格类是另一个自定义控件BlockView，用于实现数字和图片的匹配。然后游戏的逻辑部分就比较复杂了，主要是用一个空链表存储空白块，每次随机选取一个块赋值。接着监听触摸事件，根据方向来遍历每一个块并合并，0和所有数字合并，相同数字合并，再随机给一个空白块赋值。  
#### 个人评分：90
## 五、思想感悟  

   这次的期末实验有了期中的经验，做起来更顺利一点。
   在项目内容上，我们比较快地选择了游戏，又确定了实现一个先前比较热门的2048小游戏，再把课上学习的一些东西运用起来。大家各有分工，我主要负责的是游戏界面的UI设计以及游戏的逻辑部分。  
   在界面的布局上，我们用到了之前没用过的DrawerLayout控件，实现一个侧滑菜单，来显示排行榜。然后计时器使用了Chronometer控件，操作起来还是比较方便的。  
   然后就是自定义的GameView控件了，这一部分逻辑参考了现有的2048游戏的逻辑，再将其运用到这次的项目中。  
   这次我们在配置文件上没有出现问题，大家各自上传xml以及java文件，没有出现配置文件冲突。  
   最后的成品还是大家一起完成的，实现了具有不同模式切换的2048小游戏。队友的配合比较默契，后端和UI的交互理解起来并不困难。总的来说，这次任务还是努力完成了。对于所学的android知识也有了更多认识和理解。将学到的知识实践起来，也在实践中学到了一些新的方法。    

---
