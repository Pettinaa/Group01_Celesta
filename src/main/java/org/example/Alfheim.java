package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import static java.lang.System.exit;
import static java.lang.System.load;

public class Alfheim extends GameEngine{


//    public static void main(String[] args) {
////        Alfheim alfheim = new Alfheim();
////        System.out.println(alfheim);
////        createGame(alfheim);
////
////        while(!trig4){
////            System.out.println(" ");
////        }
////
////        //System.out.println(1);
////        if(trig4){
////            //System.out.println(trig4);
////            //alfheim.setclose(true);
////            //createGame(alfheim);
////            Close(alfheim);
////            System.out.println(alfheim);
////            //System.exit();
////            createGame(new SvartalfheimUpper());
//////            System.out.println(1);
////        }
//
//        createGame(new Alfheim());
//
//    }

    Image background;
    Image bad_background;
    Image sheet;
    Image[] frames_up;
    Image[] frames_down;
    Image[] frames_left;
    Image[] frames_right;
    int currentFrame;
    double animTime;
    Point2D pos = new Point2D.Double();


    //初始化npc--------------------------------------------------------------
    Image npc_sprite;
    Image npc_little_sprite;
    Image npc_sprite_princess;
    Image npc_princess_sprite;
    Image npc_sprite2;
    Image npc_little_sprite2;
    public void initnpc(){
        npc_sprite = loadImage("Alfheim/sprite.png");
        npc_little_sprite = subImage(npc_sprite, 0, 192, 72, 96);
        npc_sprite_princess = loadImage("Alfheim/sprite_princess.png");
        npc_princess_sprite = subImage(npc_sprite_princess, 0, 192, 72, 96);
        npc_sprite2 = loadImage("Alfheim/little_sprite.png");
        npc_little_sprite2 = subImage(npc_sprite2, 0, 192, 72, 96);
    }

    //画npc----------------------------------------------------------------
    public void drawnpc(){
        drawImage(npc_little_sprite, 280, 380);
    }

    //画npc精灵女王----------------------------------------------------------
    public void drawnpc_2(){
        drawImage(npc_little_sprite2, 600, 130);
        drawImage(npc_princess_sprite, 680, 100);
    }

    //画Press F-------------------------------------------------------------
    public void drawReminder(){
        if(trig3){
            if (distance(600, 130, pos.getX(), pos.getY()) < 75  || distance(680, 100, pos.getX(), pos.getY()) < 75) {
                changeColor(new Color(255, 255, 255, 255));
                drawText(620, 120, "Press F  ", "Arial", 20);
            }
        }else{
            if (distance(280, 380, pos.getX(), pos.getY()) < 75) {
                changeColor(new Color(255, 255, 255, 255));
                drawText(280, 360, "Press F  ", "Arial", 20);
            }
        }

    }

    //找空气墙位置------------------------------------------------------------
    public void boundry(){
        changeColor(red);

        //圈着npc的长方形
        drawLine(230, 340, 680, 340);//上
        drawLine(230, 520, 680, 520);//下
        drawLine(230, 340, 230, 520);//左
        drawLine(680, 340, 680, 520);//右

        //下边的长方形
        changeColor(black);
        drawLine(430, 520, 820, 520);//上
        drawLine(430, 520, 430, 690);//左
        drawLine(430,690,820,690);//下
        drawLine(820, 520, 820, 690);//右;

    }

    //设置空气墙--------------------------------------------------------------
    public void judgeBoundary(){
        int leftX1 = 230;
        int rightX1 = 680;
        int topY1 = 520;
        int bottomY1 = 690;

        int leftX2 = 430;
        int rightX2 = 820;
        int topY2 = 520;
        int bottomY2 = 690;

        double distance1 = Math.sqrt(300 * 300 + 430 * 430);
        double distance2 = Math.sqrt(200 * 200 + 430 * 430);

        if (pos.getX() < leftX1 && pos.getY() <= bottomY1) {
            pos.setLocation(leftX1, pos.getY());
        } else if (pos.getX() >= rightX1 && pos.getY() <= topY2 && Math.sqrt(Math.pow(pos.getX() - leftX1, 2) + Math.pow(pos.getY() - topY1, 2)) < distance2) {
            pos.setLocation(rightX1, pos.getY());
        }

        if (pos.getY() < topY1 && pos.getX() <= rightX1) {
            pos.setLocation(pos.getX(), topY1);
        }
        else if (pos.getY() > bottomY1 && pos.getX() <= leftX2 && Math.sqrt(Math.pow(pos.getX() - leftX1, 2) + Math.pow(pos.getY() - topY1, 2)) < distance1 ) {
            pos.setLocation(pos.getX(), bottomY1);
        }

        if (pos.getX() < leftX2 && pos.getY() > bottomY1 &&  Math.sqrt(Math.pow(pos.getX() - leftX1, 2) + Math.pow(pos.getY() - topY1, 2)) >= distance1) {
            pos.setLocation(leftX2, pos.getY());
        } else if (pos.getX() > rightX2 && pos.getY() >= topY2) {
            pos.setLocation(rightX2, pos.getY());
        }

        if (pos.getX() > rightX1 && pos.getY() < topY2 && Math.sqrt(Math.pow(pos.getX() - leftX1, 2) + Math.pow(pos.getY() - topY1, 2)) >= distance2) {
            pos.setLocation(pos.getX(), topY2);
        } else if (pos.getY() > bottomY2 && pos.getX() >= leftX2) {
            pos.setLocation(pos.getX(), bottomY2);
        }
    }


    //初始化公主与小精灵的对话内容--------------------------------------------------
    String conversation1[];
    public void initconversation1(){
        conversation1 = new String[]{"May I ask what happened to make you so sad and upset? It might be more comfortable to talk about it.",
        "Oh, recently the seal of the Demon Lord Mordalin has been loosened, and its magic has eaten into the poor\nanimals, ooh, they are so unfortunate to be the most affected by the Demon Lord's magic, I can't bear to listen",
                "to the animals' agonizing cries, but I can't do anything about it, and now I don't even have the leaves to give the\nanimals healing.",
        "I'm very sorry for this, the purpose of my trip was to reinforce the Demon King's seal, so to make up for my\nmistake, can I do something to help them?",
        "Do you play piano music? Maybe a piece of music would make the animals less miserable."};
    }

    //初始化公主与小精灵和精灵女王的对话内容------------------------------------------
    String conversation2[];
    public void initconversation2(){
        conversation2 = new String[]{"Thank you, thanks to you the animals look happy again.",
        "Little human girl, I am Solace Moonleaf, King of the Elven Kingdom, and from what Aelia has described to me,\nyou are traveling to seal Mordalin, the Demon King.",
        "It's an honor to meet you, King Moonleaf. Yes, I didn't realize the world would be affected before the Demon King\neven appeared, I'm very sorry.",
        "It's not just your responsibility to seal the Demon King, all the races should work on it. This is the unique wing\nfragment of the elf race, the human race will be able to gain the ability of elf wings by using it, I decided to gift it",
                "to you, I hope you can reinforce the seal as soon as possible, on behalf of the entire elf race, I would like to\nexpress my gratitude to you."};
    }


    //初始化对话框的图片--------------------------------------------------------
    Image Princess_talk;
    Image Sprite_talk;
    Image Sprite_princess_talk;
    public void inittalk_frame(){
        Princess_talk = loadImage("Princess_talk.png");
        Sprite_talk = loadImage("Alfheim/talk_sprite.png");
        Sprite_princess_talk = loadImage("Alfheim/princess_sprite_talk.png");
    }


    //画出公主与小精灵的对话框-----------------------------------------------------
    int conversation1_index = 0;
    public void drawcommunication_1(){
        changeColor(black);
        int wrap = -1;
        if (conversation1_index < conversation1.length) {
            String sentence = conversation1[conversation1_index];
            if(conversation1_index == 0 || conversation1_index == 3){
                drawImage(Princess_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(60, 650, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(60, 650, firstPart, "Arial", 20);
                    drawText(60, 680, secondPart, "Arial", 20);
                }

            }else{
                drawImage(Sprite_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(200, 650, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(200, 650, firstPart, "Arial", 20);
                    drawText(200, 680, secondPart, "Arial", 20);
                }
            }

            clickFlag = false;
        }

        if(conversation1_index < conversation1.length - 1){
            if(conversation1_index % 2 != 0){
                drawText(30, 690, "next sentence", "Arial", 12);
            }else{
                drawText(1130, 690, "next sentence", "Arial", 12);
            }

        }else{
            if(conversation1_index % 2 != 0){
                drawText(30, 690, "Close", "Arial", 12);
            }else{
                drawText(1130, 690, "Close", "Arial", 12);
            }

        }
    }

    //画公主与小精灵个精灵女王的对话框----------------------------------------------------
    int conversation2_index = 0;
    public void drawcommunication2(){
        changeColor(black);
        int wrap = -1;
        if (conversation2_index < conversation2.length) {
            String sentence = conversation2[conversation2_index];
            if(conversation2_index == 2){
                drawImage(Princess_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(60, 650, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(60, 650, firstPart, "Arial", 20);
                    drawText(60, 680, secondPart, "Arial", 20);
                }

            }else if(conversation2_index == 0){
                drawImage(Sprite_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(200, 650, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(200, 650, firstPart, "Arial", 20);
                    drawText(200, 680, secondPart, "Arial", 20);
                }
            }else {
                drawImage(Sprite_princess_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(200, 650, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(200, 650, firstPart, "Arial", 20);
                    drawText(200, 680, secondPart, "Arial", 20);
                }
            }

            clickFlag = false;
        }

        if(conversation2_index < conversation2.length - 1){
            if(conversation2_index == 2){
                drawText(30, 690, "next sentence", "Arial", 12);
            }else{
                drawText(1130, 690, "next sentence", "Arial", 12);
            }

        }else{
                drawText(1130, 690, "Close", "Arial", 12);
        }
    }

    //初始化任务框图片--------------------------------------------------------------
    Image mission;
    Image hint_mission;
    public void initmission(){
        mission = loadImage("Alfheim/mission.png");
        hint_mission = loadImage("Piano/hint.png");
    }

    //初始化音乐------------------------------------------------------------------------
    AudioClip backgroundmusic;
    AudioClip piano[];
    //对应音乐鼓点时间
    double[] beatTimes = {1.153282, 2.975489, 3.891926, 4.795562, 5.929640, 6.612434, 7.973755, 8.890192, 9.794895, 10.92790, 11.84327, 12.06731, 13.88525, 14.56485, 15.69786, 16.86821, 17.98095, 18.89419, 19.80102, 20.93404, 21.84087, 22.88107, 23.90419, 24.83130, 25.92910, 26.62470, 27.98068};

    public void initmusic() {
        backgroundmusic = loadAudio("Piano/Brain's_Base.WAV");
        piano = new AudioClip[6];
        piano[0] = loadAudio("Piano/m1.WAV");
        piano[1] = loadAudio("Piano/m2.WAV");
        piano[2] = loadAudio("Piano/m3.WAV");
        piano[3] = loadAudio("Piano/m4.WAV");
        piano[4] = loadAudio("Piano/m5.WAV");
        piano[5] = loadAudio("Piano/m6.WAV");
    }

    //初始化音符-----------------------------------------------------------------------------
    static int max_Phonetic_symbol; //最多音符数量
    double Phonetic_symbolX[], Phonetic_symbolY[]; //音符位置
    double Phonetic_symbolSX[], Phonetic_symbolSY[]; //音符开始位置
    double Phonetic_symbolVX[], Phonetic_symbolVY[]; //音符速度
    Image symble[];
    ImageIcon click;

    public void init_Phonetic_symbol() {
        //音符图片
        symble = new Image[6];
        symble[0] = loadImage("Piano/music1.png");
        symble[1] = loadImage("Piano/music2.png");
        symble[2] = loadImage("Piano/music3.png");
        symble[3] = loadImage("Piano/music4.png");
        symble[4] = loadImage("Piano/music5.png");
        symble[5] = loadImage("Piano/music6.png");

        //click = new ImageIcon("Piano/click.gif");

        //音符最大数等于鼓点的数量
        max_Phonetic_symbol = beatTimes.length;

        Phonetic_symbolX = new double[max_Phonetic_symbol];
        Phonetic_symbolY = new double[max_Phonetic_symbol];

        Phonetic_symbolSX = new double[max_Phonetic_symbol];
        Phonetic_symbolSY = new double[max_Phonetic_symbol];

        Phonetic_symbolVX = new double[max_Phonetic_symbol];
        Phonetic_symbolVY = new double[max_Phonetic_symbol];

        for (int i = 0; i < max_Phonetic_symbol; i++) {
            newSymbol(i);
        }
    }

    public void newSymbol(int i) {
        //目的地
        double ty = 550;
        //速度
        Phonetic_symbolVY[i] = 80;
        //计算出现哪个图片的号
        int index_image = i % 6;
        //音符初始位置在对应轨道的中间
        Phonetic_symbolSX[i] = index_image * 156 + 78;
        Phonetic_symbolSY[i] = ty - Phonetic_symbolVY[i] * beatTimes[i];
        //音符位置
        Phonetic_symbolX[i] = Phonetic_symbolSX[i];
        Phonetic_symbolY[i] = Phonetic_symbolSY[i];
    }


    //音符位置和对应分数的更新--------------------------------------------------------------
    int score = 0;
    int num = 0;//用于掉落的音符计数，从而判断音乐是否已经结束
    public void updatePhonetic_symbol(double dt) {
        for (int i = 0; i < max_Phonetic_symbol; i++) {
            //实现音符移动
            Phonetic_symbolY[i] += Phonetic_symbolVY[i] * dt;
            //边界判定
            int index_image = i % 6;
            if(index_image == 0){
                if(distance(Phonetic_symbolX[i], 700, Phonetic_symbolX[i], Phonetic_symbolY[i]) <= 5){
                    num++;
                    Phonetic_symbolY[i] = 10000;
                }
            }else if(index_image == 1){
                if(distance(Phonetic_symbolX[i], 770, Phonetic_symbolX[i], Phonetic_symbolY[i]) <= 5){
                    num++;
                    Phonetic_symbolY[i] = 10000;
                }
            }else if(index_image == 2){
                if(distance(Phonetic_symbolX[i], 840, Phonetic_symbolX[i], Phonetic_symbolY[i]) <= 5){
                    num++;
                    Phonetic_symbolY[i] = 10000;
                }
            }else if(index_image == 3){
                if(distance(Phonetic_symbolX[i], 910, Phonetic_symbolX[i], Phonetic_symbolY[i]) <= 5){
                    num++;
                    Phonetic_symbolY[i] = 10000;
                }
            }else if(index_image == 4) {
                if(distance(Phonetic_symbolX[i], 980, Phonetic_symbolX[i], Phonetic_symbolY[i]) <= 5){
                    num++;
                    Phonetic_symbolY[i] = 10000;
                }
            }else if(index_image == 5){
                if(distance(Phonetic_symbolX[i], 1050, Phonetic_symbolX[i], Phonetic_symbolY[i]) <= 5){
                    num++;
                    Phonetic_symbolY[i] = 10000;
                }
            }
        }

        //计算出现哪个图片的号
        for (int i = 0; i < max_Phonetic_symbol; i++) {
            int index_image = i % 6;
            //判断是否音符被弹中，弹中加分
            if (a && index_image == 0) {
                if (distance(78, 600, Phonetic_symbolX[i], Phonetic_symbolY[i]) < 100) {
                    Phonetic_symbolY[i] = 1000;
                    score += 4;
                    num++;
                }
            }
            if (s && index_image == 1) {
                if (distance(78 + 156, 600 + 69.5, Phonetic_symbolX[i], Phonetic_symbolY[i]) < 100) {
                    Phonetic_symbolY[i] = 1000;
                    if(score < 100){
                        score += 4;
                        num++;
                    }
                }
            }
            if (d && index_image == 2) {
                if (distance(78 + 156 * 2, 600 + 69.5 * 2, Phonetic_symbolX[i], Phonetic_symbolY[i]) < 100) {
                    Phonetic_symbolY[i] = 1000;
                    if(score < 100){
                        score += 4;
                        num++;
                    }
                }
            }
            if (j && index_image == 3) {
                if (distance(78 + 156 * 3, 600 + 69.5 * 3, Phonetic_symbolX[i], Phonetic_symbolY[i]) < 100) {
                    Phonetic_symbolY[i] = 1000;
                    if(score < 100){
                        score += 4;
                        num++;
                    }
                }
            }
            if (k && index_image == 4) {
                if (distance(78 + 156 * 4, 600 + 69.5 * 4, Phonetic_symbolX[i], Phonetic_symbolY[i]) < 100) {
                    Phonetic_symbolY[i] = 1000;
                    if(score < 100){
                        score += 4;
                        num++;
                    }
                }
            }
            if (l && index_image == 5) {
                if (distance(78 + 156 * 5, 600 + 69.5 * 5, Phonetic_symbolX[i], Phonetic_symbolY[i]) < 100) {
                    Phonetic_symbolY[i] = 10000;
                    if(score < 100){
                        score += 4;
                        num++;
                    }
                }
            }
        }

    }


    //画音符--------------------------------------------------------------------------------
    public void drawPhonetic_symbol(){
        for(int i = 0; i < max_Phonetic_symbol; i++){
            // Save Transform
            saveCurrentTransform();

            // Translate to Missile Position
            translate(Phonetic_symbolX[i], Phonetic_symbolY[i]);

            //计算出现哪个图片的号
            int index_image = i % 6;

            // Draw Phonetic symbol Image
            drawImage(symble[index_image], -46.25, -69.5 * index_image, 93, 139);

            // Reset Transform
            restoreLastTransform();
        }
    }

    //初始化钢琴游戏背景，并将其大卸八块---------------------------------------------------------------
    Image background_piano;
    Image Background8[];
    public void initpiano_background(){
        //初始化背景大卸八块
        background_piano = loadImage("Piano/background2.png");
        Background8 = new Image[8];
        for(int i = 0; i < 6; i++){
            Background8[i] = subImage(background_piano, i * 156, 0, 156, 600);
        }
        Background8[6] = subImage(background_piano, 0, 600, 936, 100);
        Background8[7] = subImage(background_piano, 936, 0, 316, 700);
    }

    //初始化钢琴小游戏胜利/失败特效-------------------------------------------------------------
    Image victory1, victory2, victory3, failed1;
    AudioClip victory_audio;
    AudioClip loss_audio;
    public void init_piano_result(){
        victory1 = loadImage("Piano/victory1.png");
        victory2 = loadImage("Piano/victory2.png");
        victory3 = loadImage("Piano/victory3.png");
        failed1 = loadImage("Piano/failed.png");
        victory_audio = loadAudio("Piano/Victory.wav");
        loss_audio = loadAudio("Piano/game_over_bad_chest.wav");
    }


    //画钢琴游戏成功画面------------------------------------------------------------------------
    int po_y = -600;
    int victory_audio_num = 1;
    public void drawvictory(){
        if(po_y < 0){
            drawImage(victory1, 300, 100 + po_y, 512, 508);
            drawImage(victory2, 510, 320 + po_y, 79, 79);
            drawImage(victory3, 490, 400 + po_y,122, 47);
            po_y += 5;
        }else{
            drawImage(victory1, 300, 100, 512, 508);
            drawImage(victory2, 510, 320, 79, 79);
            drawImage(victory3, 490, 400,122, 47);
        }

        if(victory_audio_num == 1){
            playAudio(victory_audio);
            victory_audio_num --;
        }
    }


    //画钢琴游戏失败画面-----------------------------------------------------------------------------------------------
    int po_y2 = -450;
    int loss_audio_num = 1;
    public void drawfailed(){
        if(po_y2 < 0){
            drawImage(failed1, 200, -30 + po_y2, 621,668);
            drawText(400, 380 + po_y2, "Unfortunately","Elephant", 35);
            drawText(420, 430 + po_y2, "your playing failed.","Elephant", 20);
            drawText(320, 480 + po_y2, "Please press space and try again~","Elephant", 25);
            po_y2 += 5;
        }else{
            drawImage(failed1, 200, -30, 621,668);
            drawText(400, 380, "Unfortunately","Elephant", 35);
            drawText(420, 430, "your playing failed.","Elephant", 20);
            drawText(320, 480, "Please press space and try again~","Elephant", 25);
        }

        if(loss_audio_num == 1){
            playAudio(loss_audio);
            loss_audio_num --;
        }
    }


    //初始化精灵女王送翅膀------------------------------------------------
    Image Swing[];
    Image gift;
    public void initSwing(){
        Swing = new Image[4];
        Swing[0] = loadImage("Alfheim/0.png");
        Swing[1] = loadImage("Alfheim/1.png");
        Swing[2] = loadImage("Alfheim/2.png");
        Swing[3] = loadImage("Alfheim/3.png");

        gift = loadImage("Alfheim/gift.png");

    }


    //更新翅膀------------------------------------------------------------
    Image swing;
    int swing_num = 0;
    double time;
    public void updateswing(double dt){
        time += dt;
        int i = swing_num % 4;
        if(time >= 0.15){
            swing = subImage(Swing[i], 0, 0, 102, 87);
            swing_num++;
            time = 0;
        }else{
            swing = subImage(Swing[i], 0, 0, 102, 87);
        }

    }

    //画翅膀--------------------------------------------------------------
    public void drawswing(){
        drawImage(gift, 350, 150, 266 * 2,148 * 2);
        changeColor(black);
        drawText( 524, 270, "Wing of Angel", "Gabriola", 35);
        drawText(566, 394, "Press Space", "Gabriola",25);
        drawImage(swing, 422, 230, 102 * 4, 87 * 2);
    }


    // 初始化地图
    Image map_map;
    Image light;
    public void initmap(){
        map_map = loadImage("map/map2.jpg");
        light = loadImage("map/light3.png");
    }

    //更新时间
    double time2 = 0;
    public void updatetime(double dt){
        time2 += dt;
        if(time2 >= 2){
            draw_map = true;
            time2 = 0;
        }else{
            draw_map = false;
        }
    }

    //画地图
    boolean draw_map;
    public void drawmap(){
        drawImage(map_map, 0, 0, 1255, 700);
        if(draw_map){
            drawImage(light, 500, 230, 106 * 2, 80);
        }
//        drawImage(light, 500, 230, 53 + time2 * 100, 53 + time2 * 10);
    }

    //初始化小动物们------------------------------------------------------------------------
    Image animal;
    Image dog[], cat[], chicken[], sheep[], cow[], horse[], tiger[], lion[];
    public void initanimal(){
        animal = loadImage("Alfheim/animal.png");
        dog = new Image[12];
        cat = new Image[12];
        chicken = new Image[12];
        sheep = new Image[12];
        cow = new Image[12];
        horse = new Image[12];
        tiger = new Image[12];
        lion = new Image[12];

        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 3; i++){
                dog[j * 3 + i] = subImage(animal, i * 24, j * 32, 24, 32);
                cat[j * 3 + i] = subImage(animal, i * 24 + 24 * 3, j * 32, 24, 32);
                chicken[j * 3 + i] = subImage(animal, i * 24 + 24 * 6, j * 32, 24, 32);
                sheep[j * 3 + i] = subImage(animal, i * 24 + 24 * 9, j * 32, 24, 32);
                cow[j * 3 + i] = subImage(animal, i * 24, j * 32 + 32 * 4, 24, 32);
                horse[j * 3 + i] = subImage(animal, i * 24 + 24 * 3, j * 32 + 32 * 4, 24, 32);
                tiger[j * 3 + i] = subImage(animal, i * 24 + 24 * 6, j * 32 + 32 * 4, 24, 32);
                lion[j * 3 + i] = subImage(animal, i * 24 + 24 * 9, j * 32 + 32 * 4, 24, 32);
            }

        }
    }

    //第一次画静止动物
    public void drawanimal1(){
        drawImage(dog[1], 230, 370, 24 * 2.5, 32 * 2.5);
        drawImage(cat[1], 250, 400, 24 * 2.5, 32 * 2.5);
        drawImage(chicken[1], 220, 400, 24 * 2.5, 32 * 2.5);
        drawImage(sheep[1], 195, 385, 24 * 2.5, 32 * 2.5);
        drawImage(cow[7], 150, 370, 24 * 2.5, 32 * 2.5);
        drawImage(horse[7], 155, 435, 24 * 2.5, 32 * 2.3);
        drawImage(tiger[4], 215, 340, 24 * 2.5, 32 * 2.5);
        drawImage(lion[7], 230, 460, 24 * 2.5, 32 * 2.5);
    }

    //第二次画会动的小动物
    public void drawanimal2(){
        if(left){
            drawImage(dog[index_animal + 3], px, py, 24 * 2.5, 32 * 2.5);
            drawImage(cat[index_animal + 3], px+ 60, py, 24 * 2.5, 32 * 2.5);
        }else if(right){
            drawImage(dog[index_animal + 6], px , py, 24 * 2.5, 32 * 2.5);
            drawImage(cat[index_animal + 6], px + 60, py, 24 * 2.5, 32 * 2.5);
        }else if(up){
            drawImage(dog[index_animal + 9], px, py , 24 * 2.5, 32 * 2.5);
            drawImage(cat[index_animal + 9], px + 60, py, 24 * 2.5, 32 * 2.5);
        }else if(down){
            drawImage(dog[index_animal], px, py, 24 * 2.5, 32 * 2.5);
            drawImage(cat[index_animal], px + 60, py, 24 * 2.5, 32 * 2.5);
        }



//        drawImage(chicken[1], 220, 400, 24 * 2.5, 32 * 2.5);
//        drawImage(sheep[1], 195, 385, 24 * 2.5, 32 * 2.5);
//        drawImage(cow[7], 150, 370, 24 * 2.5, 32 * 2.5);
//        drawImage(horse[7], 155, 435, 24 * 2.5, 32 * 2.3);
//        drawImage(tiger[4], 215, 340, 24 * 2.5, 32 * 2.5);
        drawImage(lion[1], 730, 130, 24 * 2.5, 32 * 2.5);
    }

    //更新会动的小动物的位置
    double px = 550;
    double py = 80;
    boolean left = true;
    boolean right;
    boolean up;
    boolean down;
    int time_animal;
    int index_animal;
    public void updateanimal(double dt){
        time_animal += dt*30;
        index_animal = time_animal % 3;
        if(left){
            px--;
            if(px <= 550){
                left = false;
                up = true;
            }
        }else if(right){
            px++;
            if(px >= 600){
                right = false;
                down = true;
            }
        }else if(up){
            py--;
            if(py <= 60){
                up = false;
                right = true;
            }
        }else if(down){
            py++;
            if(py >= 80){
                down = false;
                left = true;
            }
        }
    }


    //初始化背景音乐
    AudioClip music1, music2;
    public void initbackground_audio(){
        music1 = loadAudio("Alfheim/Evan Call - Life Is Worth Living.wav");
        music2 = loadAudio("Audio/HOYO-MiX-Cruising in the Balmy Breeze.wav");
    }


    Image storebook;
    @Override
    public void init(){
        //setupWindow(1255, 700);
        //背景图
        bad_background = loadImage("Alfheim/bad_background.png");
        background = loadImage("Alfheim/backgroud.png");

        //初始化背景音乐
        initbackground_audio();

        //init公主
        frames_up = new Image[3];
        frames_down = new Image[3];
        frames_left = new Image[3];
        frames_right = new Image[3];
        sheet = loadImage("spritesheet_princess.png");
        for (int i = 0; i < 3; i++) {
            frames_up[i] = subImage(sheet, 72 * i, 0, 72, 96);
            frames_right[i] = subImage(sheet, 72 * i, 96, 72, 96);
            frames_down[i] = subImage(sheet, 72 * i, 192, 72, 96);
            frames_left[i] = subImage(sheet, 72 * i, 288, 72, 96);
        }

        //init npc
        initnpc();

        //初始化公主与小精灵的对话内容
        initconversation1();
        initconversation2();

        //初始化对话框的图片
        inittalk_frame();

        //初始化任务框图片
        initmission();

        pos.setLocation(627.5, 350);

        //初始化钢琴游戏背景
        initpiano_background();

        //初始化音乐
        initmusic();

        //初始化音符
        init_Phonetic_symbol();

        //初始化钢琴游戏成功/失败特效
        init_piano_result();

        //初始化翅膀
        initSwing();

        //初始化地图
        initmap();

        //初始化动物
        initanimal();

        storebook = loadImage("Images/GoldMiner/scoreBook.png");

    }

    boolean mu = false;//用于钢琴小游戏判断假弹是否开始
    int music1_num = 1;
    int music2_num = 1;
    @Override
    public void update(double dt) {
        //不是钢琴小游戏的时候------------------------------------------------------------------------------------
        if (!trig2 && !trig2_end && !trig3 && !trig3_end && !map) {
            if(music1_num == 1){
                startAudioLoop(music1);
                music1_num--;
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

            //不让公主与小精灵撞
            if (distance(280, 380, pos.getX(), pos.getY()) < 65) {

                double angle = Math.atan2(pos.getY() - 380, pos.getX() - 280);
                // Move the princess away from the collision point
                double newX = 280 + 65 * Math.cos(angle);
                double newY = 380 + 65 * Math.sin(angle);
                pos.setLocation(newX, newY);
            }

            //判断空气墙位置，公主不让走
            //judgeBoundary();

        }else if(trig2 || trig2_end){//-----------------------------------------------------------------------
            //播放音乐+音符掉落
            if(!mu){
                if(note>=7){
                    playAudio(backgroundmusic);
                    mu = true;
                }
            }
            if(mu){
                //更新音符位置和分数
                updatePhonetic_symbol(dt);
            }
        }else if(trig3 || trig3_end){//-----------------------------------------------------------------------------------------

            if(music2_num == 1){
                startAudioLoop(music2);
                music2_num--;
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

            //不让公主与小精灵撞
            if (distance(600, 130, pos.getX(), pos.getY()) < 65) {
                double angle = Math.atan2(pos.getY() - 130, pos.getX() - 600);
                // Move the princess away from the collision point
                double newX = 600 + 65 * Math.cos(angle);
                double newY = 130 + 65 * Math.sin(angle);
                pos.setLocation(newX, newY);
            }
            if (distance(680, 100, pos.getX(), pos.getY()) < 65) {
                double angle = Math.atan2(pos.getY() - 100, pos.getX() - 680);
                // Move the princess away from the collision point
                double newX = 680 + 65 * Math.cos(angle);
                double newY = 100 + 65 * Math.sin(angle);
                pos.setLocation(newX, newY);
            }


            //更新动物位置
            updateanimal(dt);

            //更新翅膀
            updateswing(dt);
        }else if(map){//地图------------------------------------------------------------------
            updatetime(dt);
        }


    }

    public int getFrame(double d, int num_frames) {
        return (int) Math.floor(((animTime % d) / d) * num_frames);
    }

    @Override
    public void paintComponent() {
       if(trig2  || trig2_end){//钢琴小游戏--------------------------------------------------------------------------------
           stopAudioLoop(music1);
            clearBackground(1255, 700);
            for(int i = 0; i < 6; i++){
                drawImageWithAlpha(Background8[i], i * 156, 0, 156, 600, 0.7f);
            }
            drawImageWithAlpha(Background8[6], 0, 600, 936, 100, 1f);
            drawImageWithAlpha(Background8[7], 936, 0, 319, 700, 1f);

            //按下按键时改变对应键的颜色
            //按下按键时让对应的背景颜色变深
            if(a){
                clearBackground(0, 0, 154, 590);
                drawImageWithAlpha(Background8[0], 0, 0, 156, 600, 1f);
                drawImageWithAlpha(Background8[6], 0, 600, 936, 100, 1f);
                drawImageWithAlpha(Background8[7], 936, 0, 319, 700, 1f);
            }
            if(d){
                clearBackground(156 * 2, 0, 154, 590);
                drawImageWithAlpha(Background8[2], 2 * 156, 0, 156, 600, 1f);
                drawImageWithAlpha(Background8[6], 0, 600, 936, 100, 1f);
                drawImageWithAlpha(Background8[7], 936, 0, 319, 700, 1f);
            }
            if(j){
                clearBackground(156 * 3, 0, 154, 590);
                drawImageWithAlpha(Background8[3], 3 * 156, 0, 156, 600, 1f);
                drawImageWithAlpha(Background8[6], 0, 600, 936, 100, 1f);
                drawImageWithAlpha(Background8[7], 936, 0, 319, 700, 1f);
            }
            if(k){
                clearBackground(156 * 4, 0, 154, 590);
                drawImageWithAlpha(Background8[4], 4 * 156, 0, 156, 600, 1f);
                drawImageWithAlpha(Background8[6], 0, 600, 936, 100, 1f);
                drawImageWithAlpha(Background8[7], 936, 0, 319, 700, 1f);
            }
            if(l){
                clearBackground(156 * 5, 0, 154, 590);
                drawImageWithAlpha(Background8[5], 5 * 156, 0, 156, 600, 1f);
                drawImageWithAlpha(Background8[6], 0, 600, 936, 100, 1f);
                drawImageWithAlpha(Background8[7], 936, 0, 319, 700, 1f);
            }
            if(s){
                clearBackground(156, 0, 154, 590);
                drawImageWithAlpha(Background8[1], 156, 0, 156, 600, 1f);
                drawImageWithAlpha(Background8[6], 0, 600, 936, 100, 1f);
                drawImageWithAlpha(Background8[7], 936, 0, 319, 700, 1f);
            }

            //画线
            changeColor(black);
            for(int i = 1; i <= 6; i++){
                drawLine(i * 156, 0, i * 156, 700);
                if(i == 6){
                    drawLine(i * 156, 0, i * 156, 600, 3);
                }
            }
            drawLine(0, 600, 936, 600, 3);

            //写游戏规则
            changeColor(white);
            drawText(990, 50, "INSTRUCTION", "Curlz MT", 35);
            drawText(960, 90, "From left to right, each piano key", "Harrington", 18);
            drawText(960, 90 + 30, "corresponds to \"A\", \"S\", \"D\", \"J\",", "Harrington", 18);
            drawText(960, 90 + 30 * 2, "\"K\", \"L\" on the keyboard. When the", "Harrington", 18);
            drawText(960, 90 + 30 * 3, "phonetic symbol reaches the black", "Harrington", 18);
            drawText(960, 90 + 30 * 4, "part of the piano key, press the", "Harrington", 18);
            drawText(960, 90 + 30 * 5, "corresponding keyboard letter and", "Harrington", 18);
            drawText(960, 90 + 30 * 6, "play a healing sound for the forest", "Harrington", 18);
            drawText(960, 90 + 30 * 7, "animals.", "Harrington", 18);

            //画音符
            drawPhonetic_symbol();

            //画得分
           drawImage(storebook, 800 + 170, 300 + 50, 50 * 5, 28.15 * 5);

           changeColor(white);
           drawText(960 + 20 + 20 + 10, 90 + 30 * 10, "Music Completion", 20);
           drawText(1060 + 20, 90 + 30 * 10 + 50, score + "%", 20);

            //画提示框
           if(hint){
               drawImage(hint_mission, 131, 100, 700, 500);
           }

            //System.out.println(num);
            //判断钢琴弹奏结束与否
            if(num >= beatTimes.length-2){
                if(score >= 90){//游戏胜利
                     trig2 = false;
                     trig2_end = true;
                }else{//游戏失败
                    //初始化音乐
                    initmusic();
                    //初始化音符
                    init_Phonetic_symbol();
                    mu = false;//让音符先不下落
                    note = 0; //初始化真弹数量
                    num = 0; //初始化音符数量
                    score = 0; //初始化分数
                    failed = true;
                }
            }

            if(failed){
                drawfailed();
            }

           if(trig2_end){
               //画游戏胜利特效
               drawvictory();
           }

        }else if((trig3 || trig3_end) && !map && !trig4){//精灵女王送礼------------------------------------------------------------------------------------
            //clearBackground(1255, 700);
            //画背景，bad
            drawImage(background, 0, 0, 1255, 700);

            //画公主
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
           drawnpc_2();
            //画 Press F
           drawReminder();

           //画会动的小动物
           drawanimal2();

           //空气墙


           //画第二次对话
           if(trig3_communication){
               drawcommunication2();
           }

           //画翅膀
           if(trig3_end){
               drawswing();
           }


        }else if(!trig2 && !trig2_end && !trig3 && !trig3_end && !map){//公主初遇小精灵----------------------------------------------------------------------------------------------
            clearBackground(1255, 700);

            //画背景，bad
            drawImage(bad_background, 0, 0, 1255, 700);

            //画公主
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

            //画小动物
           drawanimal1();

           //画 Press F
           drawReminder();


            //手动找空气墙
            //boundry();

            //画公主与小精灵的第一次对话
            //trig1 为 true 的时候，才会执行
            if(trig1){
                drawcommunication_1();
            }

            //第一次对话结束后，画任务框
            if(trig1_end){
                drawImage(mission, 331, 100, 700, 500);
            }
        }else if(map){
           drawmap();
       }

        //实验
//        drawRectangle(355, 165, 615, 183);
    }


    boolean is_moving = false;
    boolean is_up = false;
    boolean is_down = true;
    boolean is_left = false;
    boolean is_right = false;

    boolean trig1;//用于小精灵与公主的第一次对话，true的时候证明对话开始
    boolean trig1_end; //用于小精灵与公主的第一次对话， true的时候证明对话结束了
    boolean trig2;//弹钢琴的游戏开始啦
    boolean hint; //提示框
    boolean hint_click; //提示框点击
    boolean failed;//钢琴游戏成功与否
    boolean trig2_end;//弹钢琴游戏结束了
    boolean trig3;//精灵女王送礼开始
    boolean trig3_communication;//送礼对话开始
    boolean trig3_end;//用于公主和小精灵、精灵女王的对话
    boolean map;
    static boolean trig4;


    //对应钢琴的按键
    boolean a;
    boolean s;
    boolean d;
    boolean j;
    boolean k;
    boolean l;
    int note = 0; //假弹之前真弹的数量


    @Override
    public void keyPressed(KeyEvent e){
        if (!trig2) {
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
            } else if(e.getKeyCode() == KeyEvent.VK_F && distance(280, 380, pos.getX(), pos.getY()) < 75 && !trig3){
                //公主与小精灵对话的内容的索引是0，说明这次对话还没开始
                if(conversation1_index == 0){
                    trig1 = true; //trig1 是 true，这次对话可以开始了
                }
            }else if(e.getKeyCode() == KeyEvent.VK_F && (distance(600, 130, pos.getX(), pos.getY()) < 75 || distance(680, 100, pos.getX(), pos.getY()) < 75) && trig3){
                trig3_communication = true;
            }
        }

        //钢琴游戏开始了---------------------------------------------------------------------------
        if((trig2 || trig2_end) && hint_click){
            if(e.getKeyCode() == KeyEvent.VK_A){
                a = true;
                note++;
                if(note < 8){
                    playAudio(piano[0]);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_S){
                s = true;
                note++;
                if(note < 8){
                    playAudio(piano[1]);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_D){
                d = true;
                note++;
                if(note < 8){
                    playAudio(piano[2]);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_J){
                j = true;
                note++;
                if(note < 8){
                    playAudio(piano[3]);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_K){
                k = true;
                note++;
                if(note < 8){
                    playAudio(piano[4]);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_L){
                l = true;
                note++;
                if(note < 8){
                    playAudio(piano[5]);
                }
            }

            //钢琴游戏失败了
            if(failed){
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    failed = false;
                }
            }
        }

        //送礼结束-------------------------------------------------------------------------------
        if(trig3_end){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                trig3_end = false;
                map = true;
                System.out.println(map);

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        is_moving = false;

        //钢琴游戏开始后
        if((trig2 || trig2_end) && hint_click){
            if(e.getKeyCode() == KeyEvent.VK_A){
                a = false;
            }else if(e.getKeyCode() == KeyEvent.VK_S){
                s = false;
            }else if(e.getKeyCode() == KeyEvent.VK_D){
                d = false;
            }else if(e.getKeyCode() == KeyEvent.VK_J){
                j = false;
            }else if(e.getKeyCode() == KeyEvent.VK_K){
                k = false;
            }else if(e.getKeyCode() == KeyEvent.VK_L){
                l = false;
            }
        }
    }


    boolean clickFlag = false;
    @Override
    public void mouseClicked(MouseEvent e){
        //公主与小精灵的对话正在进行中的时候点击鼠标-----------------------------------------------------------------
        if(trig1){
            if(conversation1_index % 2 != 0){
                if ((e.getX() >= 0 && e.getX() <= 1225) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (conversation1_index < conversation1.length) {
                        conversation1_index++;
                    }
                    if(conversation1_index == conversation1.length){
                        trig1_end = true;
                        trig1 = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }else{
                if ((e.getX() >= 0 && e.getX() <= 1255) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (conversation1_index < conversation1.length) {
                        conversation1_index++;
                    }
                    if(conversation1_index == conversation1.length){
                        trig1_end = true;
                        trig1 = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }
        }

        //第一段对话结束了，任务框出现，点击退出对话框---------------------------------------------------------------------
        if(trig1_end){
            if ((e.getX() >= 481 && e.getX() <= 561) &&
                    (e.getY() >= 500 && e.getY() <= 580)){
                trig1_end = false;
                trig2 = true;//接受完任务，弹钢琴游戏直接开始。
                hint = true;//提示框
            }
        }

        //提示框关闭
        if(hint){
            System.out.println(1);
            if((e.getX() >= 281 && e.getX() <= 361) &&
                    (e.getY() >= 500 && e.getY() <= 580)){
                hint = false;
                hint_click = true;
            }
        }

        //钢琴小游戏胜利了，点击徽章处，进入精灵女王送礼--------------------------------------------------------------------
        if(trig2_end){
            if((e.getX() >= 470 && e.getX() <= 710) &&
                    (e.getY() >= 300 && e.getY() <= 540)){
                trig2_end = false;
                trig3 = true; //进入精灵女王送礼环节
            }
        }

        //进入女王送礼------------------------------------------------------------------------------------------------
        if(trig3_communication){
            if(conversation2_index % 2 != 0){
                if ((e.getX() >= 0 && e.getX() <= 1225) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (conversation2_index < conversation2.length) {
                        conversation2_index++;
                    }
                    if(conversation2_index == conversation2.length){
                        trig3_end = true;
                        trig3_communication = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }else{
                if ((e.getX() >= 0 && e.getX() <= 1255) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (conversation2_index < conversation2.length) {
                        conversation2_index++;
                    }
                    if(conversation2_index == conversation2.length){
                        trig3_end = true;
                        trig3_communication = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }
        }

        //送礼结束
//        if(trig3_end){
//            if((e.getX() >= 350 && e.getX() <= 882) && (e.getY() >= 150 && e.getY() <= 446)){
//                trig3_end = false;
//                map = true;
//                System.out.println(map);
//
//            }
//        }

        //地图界面
        if(map){
            System.out.println(2);
            if((e.getX() >= 0 && e.getX() <= 1255) && (e.getY() >= 0 && e.getY() <= 1255)){
                //System.out.println(3);
                stopAudioLoop(music2);
                map = false;
                trig4 = true;
                createGame(new SvartalfheimUpper());
                Close(this);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
