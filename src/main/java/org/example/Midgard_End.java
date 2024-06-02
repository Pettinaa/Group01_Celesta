package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Midgard_End extends GameEngine{

//    public static void main(String[] args) {
//
//        //createGame(new Midgard_End());
//        Midgard_End midgardEnd = new Midgard_End();
//        System.out.println(midgardEnd);
//        createGame(midgardEnd);
//
//        while(!trig5){
//            System.out.println(" ");
//        }
//
//        //System.out.println(1);
//        if(trig5){
//            //System.out.println(trig4);
//            //alfheim.setclose(true);
//            //createGame(alfheim);
//            Close(midgardEnd);
//            System.out.println(midgardEnd);
//            //System.exit();
//            createGame(new Alfheim());
////            System.out.println(1);
//        }
//    }


    Image background;
    Image sheet;
    Image[] frames_up;
    Image[] frames_down;
    Image[] frames_left;
    Image[] frames_right;
    int currentFrame;
    double animTime;
    Point2D pos = new Point2D.Double();

    //初始化npc-------------------------------------------------------------------------------
    Image npc;
    Image cat_sprite;
    Image cat;
    public void initnpc(){
        npc = loadImage("Midgard/Wizard.png");
        cat_sprite = loadImage("Midgard/spritesheet_cat.png");
        cat = subImage(cat_sprite, 0, 0, 16, 16);
    }

    //画npc----------------------------------------------------------------
    public void drawnpc(){
        drawImage(npc, 600, 250, 57.6, 76.8);
        drawImage(cat, 600, 300, 50, 50);
    }

    //画F-------------------------------------------------------------------------------------
    public void drawReminder() {
        if (distance(600, 250, pos.getX(), pos.getY()) < 75) {
            changeColor(new Color(255, 255, 255, 255));
            drawText(540, 295, "Press F  ", "Arial", 20);

        }
    }


    //初始化公主与小精灵的对话内容--------------------------------------------------
    int c1 = 1;
    int c2 = 1;
    public void drawconversation1(){
        drawImage(npc_talk, 0, 550, 1255, 200);
    }

    public void drawconversation2(){
        drawImage(Princess_talk, 0, 550, 1255, 200);
    }

    //初始化对话框的图片--------------------------------------------------------
    Image Princess_talk;
    Image npc_talk;
    public void inittalk_frame(){
        Princess_talk = loadImage("Midgard/p_talk.png");
        npc_talk = loadImage("Midgard/npc_talk.png");
    }

    //初始化石头图片-------------------------------------------------------------
    Image gift;
    Image giftname;
    public void initstone(){
        gift = loadImage("Midgard/giftbackground.png");
        //giftname = loadImage("Midgard/giftname.png");
    }

    //画石头照片---------------------------------------------------------------------
    public void drawstone(){
        //drawImage(giftbackground,331, 110, 700, 500);
        //drawImage(giftname,470, 430, 400, 66);
        drawImage(gift, 350, 150, 266 * 2,148 * 2);
        changeColor(black);
        drawText( 524, 270, "Yggdrasil Stone", "Gabriola", 35);
        drawText(566, 394, "Press Space", "Gabriola",25);
        drawGif("Midgard/gleamyRock.gif", 550,270,125,100);
    }



    AudioClip music;
    @Override
    public void init(){
        music = loadAudio("Audio/Evan Call - Fern's Birthday.wav");

        //背景图
        background = loadImage("Midgard/background_human2.png");

        //init公主
        frames_up = new Image[3];
        frames_down = new Image[3];
        frames_left = new Image[3];
        frames_right = new Image[3];
        sheet = loadImage("spritesheet_princess.png");
        for (int i = 0; i < 3; i++) {
            frames_up[i] = subImage(sheet, 72 * i, 0, 72, 96);
            frames_right[i] = subImage(sheet, 72 * i, 96, 72, 96);
            frames_down[i]  = subImage(sheet, 72 * i, 192, 72, 96);
            frames_left[i]  = subImage(sheet, 72 * i, 288, 72, 96);
        }

        //init npc
        initnpc();

        //初始对话框
        inittalk_frame();

        //初始化石头
        initstone();


        pos.setLocation(552.24, 286.33);
    }

    public int getFrame(double d, int num_frames) {
        return (int) Math.floor(((animTime % d) / d) * num_frames);
    }

    int i = 1;
    @Override
    public void update(double dt) {

        if(i == 1){
            startAudioLoop(music);
            i--;
        }

        // Update Function
        animTime += dt;
        //让公主走路
        if(is_moving){
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
        }else {
            currentFrame = 0;
        }
    }

    @Override
    public void paintComponent() {
        if (!trig5) {
            clearBackground(1255, 700);
            drawImage(background, 0, 0, 1255, 700);
            if (is_up) {
                drawImage(frames_up[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
            } else if (is_down) {
                drawImage(frames_down[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
            } else if (is_left) {
                drawImage(frames_left[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
            } else if (is_right) {
                drawImage(frames_right[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
            }

            //画npc
            drawnpc();

            //画F
            drawReminder();

            //画对话1
            if(trig1){
                if(c1 == 1){
                    drawconversation1();
                }
            }

            //画石头
            if(trig2){
                drawstone();
            }

            //画对话2
            if(trig3){
                if(c2 == 1){
                    drawconversation2();
                }
            }

            if(trig4){
                pos.setLocation(pos.getX(), pos.getY() + 5);
                currentFrame = getFrame(0.3, 3);
                drawImage(frames_down[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);

                if(pos.getY() >= 600){
                    trig4 = false;
                    map = true;
                }
            }

            if(map){
                drawImage(loadImage("map/map1.jpg"), 0, 0, 1255, 700);
            }
        }


    }


    boolean is_moving = false;
    boolean is_up = false;
    boolean is_down = true;
    boolean is_left = false;
    boolean is_right = false;



    boolean trig1; //用于触发对话1
    boolean trig2; //用于显示石头
    boolean trig3; //用于第三段对话
    boolean trig4;
    boolean map;
    static boolean trig5;


    @Override
    public void keyPressed(KeyEvent e){
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
        } else if(e.getKeyCode() == KeyEvent.VK_F && distance(600, 250, pos.getX(), pos.getY()) < 75){
            if(c1 == 1){
                trig1 = true;
            }
        }else if(trig2 && e.getKeyCode() == KeyEvent.VK_SPACE){
            trig2 = false;
            trig3 = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        is_moving = false;
    }


    @Override
    public void mouseClicked(MouseEvent e){
        if(trig1){
            if ((e.getX() >= 0 && e.getX() <= 1225) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                if(c1 == 1){
                    c1--;
                    trig1 = false;
                    trig2 = true;
                }
            }
        }

        if(trig3){
            if ((e.getX() >= 0 && e.getX() <= 1225) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                if(c2 == 1){
                    c2--;
                    trig3 = false;
                    trig4 = true;
                }
            }
        }

        if(map){
            if((e.getX() >= 0 && e.getX() <= 1255) && (e.getY() >= 0 && e.getY() <= 700)){
                //System.out.println(3);
                map = false;
                trig5 = true;
                stopAudioLoop(music);
                createGame(new Alfheim());
                Close(this);

            }
        }

    }




}
