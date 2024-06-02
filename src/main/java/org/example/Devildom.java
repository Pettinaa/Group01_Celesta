package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Devildom extends GameEngine{
//    public static void main(String[] args) {
//        createGame(new Devildom());
//    }

    Image[] frames_up;
    Image[] frames_down;
    Image[] frames_left;
    Image[] frames_right;
    int currentFrame;
    double animTime;
    double fireinterval = 0;
    Point2D pos = new Point2D.Double();
    Image background;
    Image revivebackground;
    Image reviveword;
    Image Aura1;
    Image Aura;
    Image sheet;
    Image monsterleft, monsterright, monsterup, monsterdown;

    Image fire;
    Image fire1;
    Image[] fireexploding;
    double fireX, fireY;
    double fireVX, fireVY;
    double fireEndX, fireEndY;
    double fireAngle;
    boolean fireActive, fireExploding; // 因为shell有两种状态，发射过程和碰撞过程（draw的不同，update也不同）
    double fireexplosionTime; // 记录爆炸持续时间
    int fireexplosionFrame;   // 记录爆炸特效图片的第几部分

    Image waterball1;
    Image waterball;
    Image[] waterballexploding;
    double waterballX, waterballY;
    double waterballVX, waterballVY;
    double waterballEndX, waterballEndY;
    double waterballAngle;
    boolean waterballActive, waterballExploding; // 因为shell有两种状态，发射过程和碰撞过程（draw的不同，update也不同）
    double waterballexplosionTime; // 记录爆炸持续时间
    int waterballexplosionFrame;   // 记录爆炸特效图片的第几部分

    Image[] lightningexploding;
    Image[] lightningup;
    Image[] lightningdown;
    Image[] lightningleft;
    Image[] lightningright;
    double lightningX, lightningY;
    double lightningVX, lightningVY;
    double lightningEndX, lightningEndY;
    double lightningAngle;
    boolean lightningActive, lightningExploding; // 因为shell有两种状态，发射过程和碰撞过程（draw的不同，update也不同）
    double lightningexplosionTime; // 记录爆炸持续时间
    int lightningexplosionFrame;   // 记录爆炸特效图片的第几部分

    Image monstersword;
    Image  monstersword1;
    Image[] monsterswordexploding;
    double  monsterswordX,  monsterswordY;
    double  monsterswordVX,  monsterswordVY;
    double  monsterswordEndX,  monsterswordEndY;
    double  monsterswordAngle;
    boolean  monsterswordActive,  monsterswordExploding; // 因为shell有两种状态，发射过程和碰撞过程（draw的不同，update也不同）
    double  monsterswordexplosionTime; // 记录爆炸持续时间
    int  monsterswordexplosionFrame;   // 记录爆炸特效图片的第几部分
    double monsterswordinterval;


    Image princess0,princess20,princess40,princess60,princess80,princess100;
    Image monster0,monster20,monster40,monster60,monster80,monster100;

    double princessx,princessy;
    int count = 0, count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
    AudioClip Explosion, sword;
    AudioClip background1;

    boolean is_moving = false;
    boolean is_up = false;
    boolean is_down = true;
    boolean is_left = false;
    boolean is_right = false;
    boolean victory;
    boolean revive;
    boolean confirmvic = false;
    boolean getsword = false;
    boolean gamelast = true;
    boolean stopattack = false;
    boolean lightning1 = false;
    boolean waterball2 = false;
    boolean rule = true;
    int princessscore = 100;
    int monsterscore = 100;

    Image victoryword;
    Image swordendbackprompt;
    Image swordend;
    Image congraduationsword;


    public void init(){
        frames_up = new Image[3];
        frames_down = new Image[3];
        frames_left = new Image[3];
        frames_right = new Image[3];


        count4 = 0;

        Explosion = loadAudio("Devildom/explosionx.wav");
        sword = loadAudio("Devildom/sword.wav");
        background = loadImage("Devildom/devilbackground.jpg");
        revivebackground = loadImage("Midgard/giftbackground.png");
        reviveword = loadImage("Devildom/reviveword.jpg");
        Aura1 = loadImage("Devildom/Aura38.png");
        Aura = subImage(Aura1, 40, 52, 65, 90);
        sheet = loadImage("spritesheet_princess.png");
        victoryword = loadImage("Devildom/victoryword.jpg");
        swordendbackprompt = loadImage("Devildom/swordendbackprompt.png");
        swordend = loadImage("Devildom/swordend.png");
        congraduationsword = loadImage("Devildom/congraduationsword.jpg");
        background1 = loadAudio("Devildom/background.wav");


        for (int i = 0; i < 3; i++) {
            frames_up[i] = subImage(sheet, 72 * i, 0, 72, 96);
            frames_right[i] = subImage(sheet, 72 * i, 96, 72, 96);
            frames_down[i] = subImage(sheet, 72 * i, 192, 72, 96);
            frames_left[i] = subImage(sheet, 72 * i, 288, 72, 96);
        }

        initfire();
        initblood();
        initlightning();
        initmonster();
        initwaterball();
        initmonstersword();

        pos.setLocation(-5, 510);
    }

    public void initfire(){
        fireexploding = new Image[16];
        fire = loadImage("Devildom/fire.png");
        fire1 = subImage(fire, 0,0,128,128);

        fireexploding[0] = subImage(fire, 0,0,128,128);
        fireexploding[1] = subImage(fire,128,0,128,128);
        fireexploding[2] = subImage(fire,128 * 2,0,128,128);
        fireexploding[3] = subImage(fire,128 * 3,0,128,128);
        fireexploding[4] = subImage(fire,0,128,128,128);
        fireexploding[5] = subImage(fire,128,128,128,128);
        fireexploding[6] = subImage(fire,128 * 2,128,128,128);
        fireexploding[7] = subImage(fire,128 * 3,128,128,128);
        fireexploding[8] = subImage(fire,0,128 * 2,128,128);
        fireexploding[9] = subImage(fire,128,128 * 2,128,128);
        fireexploding[10] = subImage(fire,128 * 2,128 * 2,128,128);
        fireexploding[11] = subImage(fire,128 * 3,128 * 2,128,128);
        fireexploding[12] = subImage(fire,0,128 * 3,128,128);
        fireexploding[13] = subImage(fire,128,128  * 3,128,128);
        fireexploding[14] = subImage(fire,128 * 2,128 * 3,128,128);
        fireexploding[15] = subImage(fire,128 * 3,128 * 3,128,128);
    }


    public void initblood(){
        princess0 = loadImage("Devildom/princess0.jpg");
        princess20 = loadImage("Devildom/princess20.jpg");
        princess40 = loadImage("Devildom/princess40.jpg");
        princess60 = loadImage("Devildom/princess60.jpg");
        princess80 = loadImage("Devildom/princess80.jpg");
        princess100 = loadImage("Devildom/princess100.jpg");
        monster0 = loadImage("Devildom/princess0.jpg");
        monster20 = loadImage("Devildom/monster20.jpg");
        monster40 = loadImage("Devildom/monster40.jpg");
        monster60 = loadImage("Devildom/monster60.jpg");
        monster80 = loadImage("Devildom/monster80.jpg");
        monster100 = loadImage("Devildom/monster100.jpg");
    }

    public void initlightning(){
        lightningup = new Image[7];
        lightningdown = new Image[7];
        lightningleft = new Image[7];
        lightningright = new Image[7];


        lightningup[0] = loadImage("Devildom/996-1 - top.png");
        lightningup[1] = loadImage("Devildom/996-6 -top.png");
        lightningup[2] = loadImage("Devildom/996-7 - top.png");
        lightningup[3] = loadImage("Devildom/996-8 - top.png");
        lightningup[4] = loadImage("Devildom/996-9 - top.png");
        lightningup[5] = loadImage("Devildom/996-10 - top.png");
        lightningup[6] = loadImage("Devildom/996-11 - top.png");

        lightningdown[0] = loadImage("Devildom/996-1 - down.png");
        lightningdown[1] = loadImage("Devildom/996-6 - down.png");
        lightningdown[2] = loadImage("Devildom/996-7 - down.png");
        lightningdown[3] = loadImage("Devildom/996-8 - down.png");
        lightningdown[4] = loadImage("Devildom/996-9 - down.png");
        lightningdown[5] = loadImage("Devildom/996-10 - down.png");
        lightningdown[6] = loadImage("Devildom/996-11 - down.png");

        lightningleft[0] = loadImage("Devildom/996-1 - left.png");
        lightningleft[1] = loadImage("Devildom/996-6 - left.png");
        lightningleft[2] = loadImage("Devildom/996-7 - left.png");
        lightningleft[3] = loadImage("Devildom/996-8 - left.png");
        lightningleft[4] = loadImage("Devildom/996-9 - left.png");
        lightningleft[5] = loadImage("Devildom/996-10 - left.png");
        lightningleft[6] = loadImage("Devildom/996-11 - left.png");

        lightningright[0] = loadImage("Devildom/996-1-right.png");
        lightningright[1] = loadImage("Devildom/996-6-right.png");
        lightningright[2] = loadImage("Devildom/996-7-right.png");
        lightningright[3] = loadImage("Devildom/996-8-right.png");
        lightningright[4] = loadImage("Devildom/996-9-right.png");
        lightningright[5] = loadImage("Devildom/996-10-right.png");
        lightningright[6] = loadImage("Devildom/996-11-right.png");
    }

    public void initwaterball(){
        waterballexploding = new Image[13];
        waterball1 = loadImage("Devildom/waterball.png");
        waterball = subImage(waterball1,118,0,59,59);
        waterballexploding[0] = subImage(waterball1,177,0,59,59);
        waterballexploding[1] = subImage(waterball1,0,59,59,59);
        waterballexploding[2] = subImage(waterball1,59,59,59,59);
        waterballexploding[3] = subImage(waterball1,118,59,59,59);
        waterballexploding[4] = subImage(waterball1,177,59,59,59);
        waterballexploding[5] = subImage(waterball1,0,118,59,59);
        waterballexploding[6] = subImage(waterball1,59,118,59,59);
        waterballexploding[7] = subImage(waterball1,118,118,59,59);
        waterballexploding[8] = subImage(waterball1,177,118,59,59);
        waterballexploding[9] = subImage(waterball1,0,177,59,59);
        waterballexploding[10] = subImage(waterball1,59,177,59,59);
        waterballexploding[11] = subImage(waterball1,118,177,59,59);
        waterballexploding[12] = subImage(waterball1,177,177,59,59);
    }

    public void initmonstersword() {
        monsterswordexploding = new Image[10];
        monstersword = loadImage("Devildom/monstersword.png");
        monstersword1 = subImage(monstersword, 200, 0, 100, 100);
        monsterswordexploding[0] = subImage(monstersword, 300, 0, 100, 100);
        monsterswordexploding[1] = subImage(monstersword, 400, 0, 100, 100);
        monsterswordexploding[2] = subImage(monstersword, 0, 100, 100, 100);
        monsterswordexploding[3] = subImage(monstersword, 100, 100, 100, 100);
        monsterswordexploding[4] = subImage(monstersword, 200, 100, 100, 100);
        monsterswordexploding[5] = subImage(monstersword, 300, 100, 100, 100);
        monsterswordexploding[6] = subImage(monstersword, 400, 100, 100, 100);
        monsterswordexploding[7] = subImage(monstersword, 0, 200, 100, 100);
        monsterswordexploding[8] = subImage(monstersword, 100, 0, 100, 100);
        monsterswordexploding[9] = subImage(monstersword, 0, 0, 100, 100);
    }

    public void initmonster(){

        monsterleft = loadImage("Devildom/monsterleft.png");
        monsterright = loadImage("Devildom/monsterright.png");
        monsterup = loadImage("Devildom/monstertop.png");
        monsterdown = loadImage("Devildom/monsterdown.png");
    }


    public void firelauch(double x, double y) {
        // If the shell is already active
        if(fireActive || fireExploding) { // 确保在有炮弹发射时，按键不起作用
            return;
        }
        // Start shell at center bottom
        fireX = 370;
        fireY = 490;

        // Set end position
        fireEndX = x;
        fireEndY = y;

        // Calculate velocity
        fireVX = x - 300;
        fireVY = y - 500;

        fireAngle = atan2(fireVX, -fireVY) - 90;

        // Rescale velocity
        double l = length(fireVX, fireVY);
        fireVX = fireVX * 400 / l;
        fireVY = fireVY * 400 / l;

        // Set shell active
        fireActive = true;   // 点击鼠标之后，就active了，然后在draw里面判定，active才画。在update中也是记录了状态，
        fireExploding = false;

    }

    public void monsterswordlauch(double x, double y) {
        // If the shell is already active
        if(monsterswordActive || monsterswordExploding) { // 确保在有炮弹发射时，按键不起作用
            return;
        }
        // Start shell at center bottom
        monsterswordX = 370;
        monsterswordY = 490;

        // Set end position
        monsterswordEndX = x;
        monsterswordEndY = y;

        // Calculate velocity
        monsterswordVX = x - 300;
        monsterswordVY = y - 500;

        monsterswordAngle = atan2(monsterswordVX, -monsterswordVY) - 90;

        // Rescale velocity
        double l = length(monsterswordVX, monsterswordVY);
        monsterswordVX = monsterswordVX * 400 / l;
        monsterswordVY = monsterswordVY * 400 / l;

        // Set shell active
        monsterswordActive = true;   // 点击鼠标之后，就active了，然后在draw里面判定，active才画。在update中也是记录了状态，
        monsterswordExploding = false;

    }


    public void waterballlauch(double x, double y){
        if(waterballActive || waterballExploding) { // 确保在有炮弹发射时，按键不起作用
            return;
        }
        // Start shell at center bottom
        waterballX = x;
        waterballY = y;


        // Set end position
        waterballEndX = 370;
        waterballEndY = 510;

        // Calculate velocity
        waterballVX = waterballEndX - waterballX;
        waterballVY = waterballEndY - waterballY;

        waterballAngle = atan2(waterballVX, -waterballVY) - 90;

        // Rescale velocity
        double l = length(waterballVX, waterballVY);
        waterballVX = waterballVX * 400 / l;
        waterballVY = waterballVY * 400 / l;

        // Set shell active
        waterballActive = true;   // 点击鼠标之后，就active了，然后在draw里面判定，active才画。在update中也是记录了状态，
        waterballExploding = false;
    }

    public void lightninglauch(double x, double y) {
        // If the shell is already active
        if(lightningActive || lightningExploding) { // 确保在有炮弹发射时，按键不起作用
            return;
        }
        // Start shell at center bottom
        lightningX = x;
        lightningY = y;


        // Set end position
        lightningEndX = 370;
        lightningEndY = 510;

        // Calculate velocity
        lightningVX = lightningEndX - lightningX;
        lightningVY = lightningEndY - lightningY;

        lightningAngle = atan2(lightningVX, -lightningVY) - 90;

        // Rescale velocity
        double l = length(lightningVX, lightningVY);
        lightningVX = lightningVX * 400 / l;
        lightningVY = lightningVY * 400 / l;

        // Set shell active
        lightningActive = true;   // 点击鼠标之后，就active了，然后在draw里面判定，active才画。在update中也是记录了状态，
        lightningExploding = false;
    }

    int i = 0;
    @Override
    public void update(double dt) {
        if(i == 0){
            startAudioLoop(background1);
            i--;
        }
        animTime += dt;

        if(!rule){
            if(!stopattack){
                //lightning1
                if (lightningActive) {
                    // Move shell
                    lightningX += lightningVX * dt;
                    lightningY += lightningVY * dt;

                    // Check if shell has moved past its target 只要炮弹到达鼠标制定的位置后，就会爆炸
                    if ((lightningEndX - lightningX) * lightningVX < 0 || (lightningEndY - lightningY) * lightningVY < 0) {
                        // Make shell explode
                        lightningActive = false;

                        lightningexplosionTime = 0.0;  // 重置爆炸时间
                        lightningexplosionFrame = 1;   // 重置爆炸图片部分

                        lightningExploding = true;
                        playAudio(sword);
                    }
                } else if (lightningExploding) {
                    // Add to Time
                    lightningexplosionTime += dt; // 每隔dt的时间，刷新一次

                    // If explosion time reaches 1.0, stop explosion
                    if (lightningexplosionTime >= 1.0) {
                        lightningExploding = false;
                    }

                    // Calculate Radius

                    // Calculate frame from time 根据时间改变特效
                    lightningexplosionFrame = (int) (lightningexplosionTime * 6);
                    // 这里的39是指，间隔1/39s 换一个

                    // Check if explosion destroys each missile 虽然炮弹爆炸，但是这里是需要判断是否撞到了missile
                    if (distance(lightningX, lightningY, 370, 510) < 20) {
                        count1++;
                        if (count1 == 1) {
                            monsterscore = monsterscore - 20;
                        }
                    }

                }



                //fire
                if (!fireActive && !fireExploding && !revive) {
                    fireinterval = fireinterval + dt;
                    if (fireinterval > 1.5) {
                        princessx = pos.getX();
                        princessy = pos.getY();
                        firelauch(princessx, princessy);
                        count = 0;
                        fireinterval = 0;
                    }
                }


                if (fireActive) {
                    // Move shell
                    fireX += fireVX * dt;
                    fireY += fireVY * dt;

                    // Check if shell has moved past its target 只要炮弹到达鼠标制定的位置后，就会爆炸
                    if ((fireEndX - fireX) * fireVX < 0 || (fireEndY - fireY) * fireVY < 0) {
                        // Make shell explode
                        fireActive = false;

                        fireexplosionTime = 0.0;  // 重置爆炸时间
                        fireexplosionFrame = 0;   // 重置爆炸图片部分

                        fireExploding = true;
                        playAudio(Explosion);
                    }
                } else if (fireExploding) {
                    // Add to Time
                    fireexplosionTime += dt; // 每隔dt的时间，刷新一次

                    // If explosion time reaches 1.0, stop explosion
                    if (fireexplosionTime >= 1.0) {
                        fireExploding = false;
                    }

                    // Calculate Radius

                    // Calculate frame from time 根据时间改变特效
                    fireexplosionFrame = (int) (fireexplosionTime * 15);
                    // 这里的39是指，间隔1/39s 换一个

                    // Check if explosion destroys each missile 虽然炮弹爆炸，但是这里是需要判断是否撞到了missile
                    if (distance(fireEndX, fireEndY, pos.getX(), pos.getY()) < 30) {
                        count++;
                        if (count == 1) {
                            princessscore = princessscore - 20;
                        }
                    }
                }

                //monstersword
                if (!monsterswordActive && !monsterswordExploding && !revive) {
                    monsterswordinterval = monsterswordinterval + dt;
                    if (monsterswordinterval > 3.4) {
                        princessx = pos.getX();
                        princessy = pos.getY();
                        monsterswordlauch(princessx, princessy);
                        count3 = 0;
                        monsterswordinterval = 0;
                    }
                }


                if (monsterswordActive) {
                    // Move shell
                    monsterswordX += monsterswordVX * dt;
                    monsterswordY += monsterswordVY * dt;

                    // Check if shell has moved past its target 只要炮弹到达鼠标制定的位置后，就会爆炸
                    if ((monsterswordEndX - monsterswordX) * monsterswordVX < 0 || (monsterswordEndY - monsterswordY) * monsterswordVY < 0) {
                        // Make shell explode
                        monsterswordActive = false;

                        monsterswordexplosionTime = 0.0;  // 重置爆炸时间
                        monsterswordexplosionFrame = 1;   // 重置爆炸图片部分

                        monsterswordExploding = true;
                        playAudio(sword);
                    }
                } else if (monsterswordExploding) {
                    // Add to Time
                    monsterswordexplosionTime += dt; // 每隔dt的时间，刷新一次

                    // If explosion time reaches 1.0, stop explosion
                    if (monsterswordexplosionTime >= 1.0) {
                        monsterswordExploding = false;
                    }

                    // Calculate Radius

                    // Calculate frame from time 根据时间改变特效
                    monsterswordexplosionFrame = (int) (monsterswordexplosionTime * 9);
                    // 这里的39是指，间隔1/39s 换一个


                    // Check if explosion destroys each missile 虽然炮弹爆炸，但是这里是需要判断是否撞到了missile
                    if (distance(monsterswordEndX, monsterswordEndY, pos.getX(), pos.getY()) < 30) {
                        count3++;
                        if (count3 == 1) {
                            princessscore = princessscore - 20;
                        }
                    }
                }

                //waterball
                if (waterballActive) {
                    // Move shell
                    waterballX += waterballVX * dt;
                    waterballY += waterballVY * dt;

                    // Check if shell has moved past its target 只要炮弹到达鼠标制定的位置后，就会爆炸
                    if ((waterballEndX - waterballX) * waterballVX < 0 || (waterballEndY - waterballY) * waterballVY < 0) {
                        // Make shell explode
                        waterballActive = false;

                        waterballexplosionTime = 0.0;  // 重置爆炸时间
                        waterballexplosionFrame = 1;   // 重置爆炸图片部分

                        waterballExploding = true;
                        playAudio(Explosion);
                    }
                } else if (waterballExploding) {
                    // Add to Time
                    waterballexplosionTime += dt; // 每隔dt的时间，刷新一次

                    // If explosion time reaches 1.0, stop explosion
                    if (waterballexplosionTime >= 1.0) {
                        waterballExploding = false;
                    }

                    // Calculate Radius

                    // Calculate frame from time 根据时间改变特效
                    waterballexplosionFrame = (int) (waterballexplosionTime * 12);
                    // 这里的39是指，间隔1/39s 换一个

                    // Check if explosion destroys each missile 虽然炮弹爆炸，但是这里是需要判断是否撞到了missile
                    if (distance(waterballX, waterballY, 370, 510) < 20) {
                        count2++;
                        if (count2 == 1) {
                            monsterscore = monsterscore - 20;
                        }
                    }
                }
            }

        }


        if (princessscore == 0) {
            revive = true;
        }

        if(monsterscore == 0 && count4 == 0){
            victory = true;
            stopattack = true;
            count4++;
        }

        if(confirmvic){
            if(distance(pos.getX(), pos.getY(),700, 530) <= 20){
                getsword = true;
            }
        }

            //公主走路
            if (is_moving) {
                if (is_up) {
                    //drawImage(frames_up[currentFrame], princessX, princessY-5, 72, 96);
                    pos.setLocation(pos.getX(), pos.getY() - 5);
                } else if (is_down) {
                    //drawImage(frames_down[currentFrame], princessX, princessY+5, 72, 96);
                    pos.setLocation(pos.getX(), pos.getY() + 5);
                } else if (is_left) {
                    //drawImage(frames_left[currentFrame],  princessX-5, princessY,72,96);
                    pos.setLocation(pos.getX() - 5, pos.getY());
                } else if (is_right) {
                    //drawImage(frames_right[currentFrame],  princessX+5, princessY,72,96);
                    pos.setLocation(pos.getX() + 5, pos.getY());
                }

                // Ensure the princess stays within the bounds of the background
                if (pos.getX() <= 0) {
                    pos.setLocation(0, pos.getY());
                }
                if (pos.getX() >= 1197.4) {
                    pos.setLocation(1197.4, pos.getY()); // Ensure princess stays at the right boundary
                }
                if (pos.getY() <= 0) {
                    pos.setLocation(pos.getX(), 0);
                }
                if (pos.getY() >= 623.3) {
                    pos.setLocation(pos.getX(), 623.3);
                }

                currentFrame = getFrame(0.3, 3);
            } else {
                currentFrame = 0;
            }


            // 边界判定
            judgeboundary();
        }


    public void judgeboundary(){
        if(pos.getY() <= 510 && pos.getY() >= 350 && pos.getX() <= 200 && pos.getX() >=190){
            pos.setLocation(200, pos.getY());
        }
        if(pos.getX() >= 200 && pos.getX() <= 1000 && pos.getY() <= 350){
            pos.setLocation(pos.getX(), 350);
        }
        if(pos.getY() >= 525 && pos.getY() <= 800 && pos.getX() <= 235){
            pos.setLocation(235, pos.getY());
        }

        if(pos.getX() >= 0 && pos.getX() <= 180 && pos.getY() >= 520 && pos.getY() <= 530){
            pos.setLocation(pos.getX(), 520);
        }
        if(pos.getX() >= 0 && pos.getX() <= 180 && pos.getY() <= 500 && pos.getY() >= 490){
            pos.setLocation(pos.getX(), 500);
        }
    }

    @Override
    public void paintComponent() {
        clearBackground(1255, 700);

        drawImage(background, 0, 0, 1255, 700);
        rotate(-22);
        drawImage(Aura, -20, 510, 100, 125);


        if(gamelast){
            saveCurrentTransform();
            rotate(8);
            drawImage(monsterleft, 460,410,85,146);
            restoreLastTransform();
        }

        if (is_up) {
            drawImage(frames_up[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
        } else if (is_down) {
            drawImage(frames_down[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
        } else if (is_left) {
            drawImage(frames_left[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
        } else if (is_right) {
            drawImage(frames_right[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
        }


        if(!stopattack){
            if (fireActive) {
                drawImage(fire1, fireX, fireY);
            } else if (fireExploding) {
                drawImage(fireexploding[fireexplosionFrame], fireEndX, fireEndY);
            }

                if(waterballActive){
                    drawImage(waterball, waterballX, waterballY);
                }else if(waterballExploding){
                    drawImage(waterballexploding[waterballexplosionFrame], waterballEndX, waterballEndY);
                }

            if(monsterswordActive){
                drawImage(monstersword1, monsterswordX, monsterswordY);
            }else if(monsterswordExploding){
                drawImage(monsterswordexploding[monsterswordexplosionFrame], monsterswordEndX, monsterswordEndY);
            }


                if(lightningActive){
                    if(is_up){
                        drawImage(lightningup[0], lightningX,lightningY,100, 50);
                    }else if(is_down){
                        drawImage(lightningdown[0], lightningX,lightningY,100, 50);
                    }else if(is_left){
                        drawImage(lightningleft[0], lightningX,lightningY,100, 50);
                    }else if(is_right){
                        drawImage(lightningright[0], lightningX,lightningY,100, 50);
                    }
                }else if(lightningExploding){
                    if(is_up){
                        drawImage(lightningup[lightningexplosionFrame], lightningX,lightningY,100, 50);
                    }else if(is_down){
                        drawImage(lightningdown[lightningexplosionFrame], lightningX,lightningY,100, 50);
                    }else if(is_left){
                        drawImage(lightningleft[lightningexplosionFrame], lightningX,lightningY,100, 50);
                    }else if(is_right){
                        drawImage(lightningright[lightningexplosionFrame], lightningX,lightningY,100, 50);
                    }
                }
        }



        if (princessscore == 100) {
            drawImage(princess100, pos.getX(), pos.getY() - 10, 55, 5.8);
        }
        if (princessscore == 80) {
            drawImage(princess80, pos.getX(), pos.getY() - 10, 55, 5.8);
        }
        if (princessscore == 60) {
            drawImage(princess60, pos.getX(), pos.getY() - 10, 55, 5.8);
        }
        if (princessscore == 40) {
            drawImage(princess40, pos.getX(), pos.getY() - 10, 55, 5.8);
        }
        if (princessscore == 20) {
            drawImage(princess20, pos.getX(), pos.getY() - 10, 55, 5.8);
        }
        if (princessscore == 0) {
            drawImage(princess0, pos.getX(), pos.getY() - 10, 55, 5.8);
        }

        if (monsterscore == 100) {
            drawImage(monster100, 390, 450, 80, 8.4);
        }
        if (monsterscore == 80) {
            drawImage(monster80, 390, 450, 80, 8.4);
        }
        if (monsterscore == 60) {
            drawImage(monster60, 390, 450, 80, 8.4);
        }
        if (monsterscore == 40) {
            drawImage(monster40, 390, 450, 80, 8.4);
        }
        if (monsterscore == 20) {
            drawImage(monster20, 390, 450, 80, 8.4);
        }
        if(gamelast){
            if (monsterscore <= 0) {
                drawImage(monster0, 390, 450, 80, 8.4);
            }
        }


        if (revive) {
            saveCurrentTransform();
            rotate(22);
            drawImage(revivebackground, 331, 110, 700, 500);
            drawText(570, 370,"You are FAILED.","Gabriola",50);
            drawText(520, 450,"Click here to REVIVE!","Gabriola",50);
            restoreLastTransform();
        }

        if(victory){
            gamelast = false;
            saveCurrentTransform();
            rotate(22);
            drawImage(revivebackground, 331, 110, 700, 500);
            drawImage(swordendbackprompt, 600, 260, 170, 170);
            drawText(540, 455, "Please SEAL the devil","Gabriola",40);
            drawText(500, 505,"with the SWORD of victory!","Gabriola",40);
            restoreLastTransform();
        }

        if(!gamelast && confirmvic){
            saveCurrentTransform();
            rotate(9);
            drawImage(swordend, 760, 230,60, 200);
            restoreLastTransform();

            saveCurrentTransform();
            rotate(22);
            drawGif("Devildom/cheers4.gif",0,-300,1255,1255);
            restoreLastTransform();

            changeColor(white);
            drawBoldText(400,400, "Go Back to Nordria","Castellar", 20 );

            if(pos.getX()  >= 550){
                //System.out.println(1);
                createGame(new Ending());
                stopAudioLoop(background1);
                Close(this);
            }


        }

        if(rule){
            saveCurrentTransform();
            rotate(22);
            drawImage(revivebackground, 331, 110, 700, 500);
            drawText(420, 310, "The devil will attack at regular intervals,and each time","Gabriola",30);
            drawText(420,350,"he is attacked, the princess's life will be reduced by 20.","Gabriola",30);
            drawText(420,390,"The princess can press \"1\" to launch a lightning attack","Gabriola",30);
            drawText(420,430,"and \"2\" to launch a water balloon attack.","Gabriola",30);
            drawText(420,470, "Devil will lose 20 life points each time he is attacked.","Gabriola",30);
            restoreLastTransform();
        }
    }

    public int getFrame(double d, int num_frames) {
        return (int) Math.floor(((animTime % d) / d) * num_frames);
    }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                is_moving = true;
                is_up = true;
                is_down = false;
                is_left = false;
                is_right = false;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                is_moving = true;
                is_down = true;
                is_up = false;
                is_left = false;
                is_right = false;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                is_moving = true;
                is_left = true;
                is_up = false;
                is_down = false;
                is_right = false;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                is_moving = true;
                is_right = true;
                is_up = false;
                is_down = false;
                is_left = false;
            }

            if(e.getKeyCode() == KeyEvent.VK_1){
                lightning1 = true;
                count1 = 0;
                lightninglauch(pos.getX(), pos.getY());
            }

            if(e.getKeyCode() == KeyEvent.VK_2){
                waterball2 = true;
                count2 = 0;
                waterballlauch(pos.getX(), pos.getY());

            }
        }

    @Override
    public void keyReleased(KeyEvent e) {
        is_moving = false;
    }

    public void mousePressed(MouseEvent e) {
        if(revive){
            if((e.getX() >=331 && e.getX() <= 1031) && (e.getY() >= 100 && e.getY() <=600)){
                princessscore = 100;
                revive = false;
            }
        }

        if(victory){
            if((e.getX() >=331 && e.getX() <= 1031) && (e.getY() >= 100 && e.getY() <=600)){
                confirmvic = true;
                victory = false;
            }
        }

        if(rule){
            if((e.getX() >=331 && e.getX() <= 1031) && (e.getY() >= 100 && e.getY() <=600)){
               rule = false;
            }
        }
    }

}
