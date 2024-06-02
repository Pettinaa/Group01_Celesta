package org.example;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class Midgard extends GameEngine {

//    public static void main(String[] args) {
//        createGame(new Midgard());
////        Midgard midgard = new Midgard();
////        createGame(midgard);
//
////        while(!end){
////            System.out.println(" ");
////        }
////
////        System.out.println(1);
////        if(end){
////            Close(midgard);
////            //createGame(new Midgard_End());
////        }
//    }

    Image sheet;
    Image background;
    Image background2;
    Image[] frames_up;
    Image[] frames_down;
    Image[] frames_left;
    Image[] frames_right;
    Image NPCWizard;
    Image sheet_cat;
    Image cat[];
    int currentFrame;
    double animTime;
    Point2D pos = new Point2D.Double();
    //Plants

    double[] treePositionX;
    double[] treePositionY;



    // Initialise the Game

    AudioClip music;
    @Override
    public void init() {
        //setupWindow(1255, 700);
        music = loadAudio("Audio/Evan Call - Fern's Birthday.wav");

        frames_up = new Image[3];
        frames_down = new Image[3];
        frames_left = new Image[3];
        frames_right = new Image[3];
        sheet = loadImage("spritesheet_princess.png");
        background = loadImage("Midgard/background_human.png");
        background2 = loadImage("Midgard/background_human2.png");
        for (int i = 0; i < 3; i++) {
            frames_up[i] = subImage(sheet, 72 * i, 0, 72, 96);
            frames_right[i] = subImage(sheet, 72 * i, 96, 72, 96);
            frames_down[i] = subImage(sheet, 72 * i, 192, 72, 96);
            frames_left[i] = subImage(sheet, 72 * i, 288, 72, 96);
        }
        initNPC();
        initPlant();
        initCat();

        pos.setLocation(627.5, 350);
    }

    int y = 1;
    @Override
    public void update(double dt) {
        if(y == 1){
            startAudioLoop(music);
            y--;
        }

        // Update Function
        animTime += dt;

        //让公主走路
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

        //让鸡走路
        if(chickenLeft){
            chickenPositionX += 1;
        }else {
            chickenPositionX -= 1;
        }
        if (chickenPositionX > 900) {
            chickenLeft = false;
        }else if (chickenPositionX < 800){
            chickenLeft = true;
        }
        currentChickenFrame = getFrame(0.3, 3);


        // Detect Collision between Alien and Asteroid
        //不让公主与老头撞
        if (distance(600, 250, pos.getX(), pos.getY()) < 60) {

            double angle = Math.atan2(pos.getY() - 250, pos.getX() - 600);

            // Move the princess away from the collision point
            double newX = 600 + 60 * Math.cos(angle);
            double newY = 250 + 60 * Math.sin(angle);
            pos.setLocation(newX, newY);
        }

        //不让和树桩撞
        if (distance(270, 450, pos.getX(), pos.getY()) < 60) {

            double angle = Math.atan2(pos.getY() - 450, pos.getX() - 270);

            // Move the princess away from the collision point
            double newX = 270 + 60 * Math.cos(angle);
            double newY = 450 + 60 * Math.sin(angle);
            pos.setLocation(newX, newY);
        }
        //不让和树撞
        for (int i = 0; i < 4; i++){
            if (distance(treePositionX[i]+30, treePositionY[i]+30, pos.getX(), pos.getY()) < 50) {
                double angle = Math.atan2(pos.getY() - (treePositionY[i]+30), pos.getX() - (treePositionX[i]+30));
                double newX = treePositionX[i]+30 + 50 * Math.cos(angle);
                double newY = treePositionY[i]+30 + 50 * Math.sin(angle);
                pos.setLocation(newX, newY);
            }
        }

        //两个长方形空气墙
        judgeBoundary();
    }

    // Calculates the
    public int getFrame(double d, int num_frames) {
        return (int) Math.floor(((animTime % d) / d) * num_frames);
    }

    // This gets called any time the Operating System

    public void judgeBoundary(){
        int leftX1 = 270;
        int rightX1 = 700;
        int topY1 = 150;
        int bottomY1 = 450;

        int leftX2 = 700;
        int rightX2 = 900;
        int topY2 = 350;
        int bottomY2 = 550;
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

    static boolean end;
    int i = 1;

    @Override
    public void paintComponent() {
        // Clear the background to black
        //changeBackgroundColor(Color.white);
        clearBackground(1255, 700);

        if(!plots3){
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
            drawCat();
            drawNPC();
            drawPlant();
        }else{
            //clearBackground(1255, 700);
            //changeBackgroundColor(black);
            end = true;
            if(i == 1){
                stopAudioLoop(music);
                createGame(new Midgard_End());
                Close(this);
//                createGame(new Midgard_End());
                i--;
            }

//            System.out.println("房子送礼");
//            drawImage(background2, 0, 0, 1255, 700);
//            if (is_up) {
//                drawImage(frames_up[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//            } else if (is_down) {
//                drawImage(frames_down[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//            } else if (is_left) {
//                drawImage(frames_left[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//            } else if (is_right) {
//                drawImage(frames_right[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//            }
//            drawCat();
//            drawNPC();
            //drawPlant();
        }


//        if (is_up) {
//            drawImage(frames_up[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//        } else if (is_down) {
//            drawImage(frames_down[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//        } else if (is_left) {
//            drawImage(frames_left[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//        } else if (is_right) {
//            drawImage(frames_right[currentFrame], pos.getX(), pos.getY(), 57.6, 76.8);
//        }

//        drawCat();
//        drawNPC();
//        drawPlant();

        drawReminder();

        changeColor(black);
        //触发剧情1的对话
        if(trig1){
            drawCommunication_1();
        }

        //剧情1结束，画出来任务
        if(plots1){
            drawMisiion();
        }

        //剧情1对话已经结束并且还没有完成找猫的任务，触发这段
        if(trig3){
            drawRepeat();
        }

        //人找到小猫了，小猫叫的剧情
        if(catp && trig4){
            drawCat_p();
        }

        //剧情2开始了
        if(trig2){
            drawCommunication_2();
        }

        //剧情2结束了
        if(plots3){
            //System.out.println("任务完成");
        }

    }




    //NPC-Event
    Image Wizard_talk;
    Image Princess_talk;
    Image Cat_talk;
    String[] conversation1;
    String[] cat_p;
    String[] conversation2;

    //cat
    public void initNPC() {
        NPCWizard = loadImage("Midgard/Wizard.png");
        Princess_talk = loadImage("Princess_talk.png");
        Wizard_talk = loadImage("Midgard/Wizard_talk.png");
        Cat_talk = loadImage("Midgard/cat_talk.png");
        conversation1 = new String[]{"Ahem, my poor girl......", "Hi, is something happening? Maybe I can help you.",
                "My girl, Shadow, what a cute kitten, has been lost for 3 whole hours!\nOh my God, who knows where she is now,has she been bullied, oooh oooh oooh ......",
                "Please don't worry, I'll look around the neighborhood."};
        cat_p = new String[]{"Meow~", "Aha, found you, naughty little one, now please come home with me."};
        conversation2 = new String[]{"Oh, my poor girl, you must have suffered these last few hours.\nMy girl, you've really done me a favor and I should thank you.",
                "It's been a pleasure. I'll see you then.", "Oh, wait, my girl, your staff, my God, you're actually Princess Celesta.\nHow did you leave the castle to come here.",
                "Seriously, I don't know why I'm here,but the Seer says I have to go seal Mordalin, the Demon Lord.",
                "So now I must introduce myself to you, my name is Edgar Holden, that's right, it's that famous\nHolden witch family, our family has always had this rumor about the origin of the world.",
                "A long, long time ago, when the universe was just born, there was nothing in the whole universe,\nsuddenly one day, I don't know what the reason is for the emergence of two equal elements in the universe, ",
                "one is the element of ice, one is the element of fire, the two major elements are incompatible with\neach other, and each of them occupied half of the whole universe, and in the process of crashing into",
                "each other, the 12 rivers were formed. One day, these 12 rivers converged in the same place, and the\nfusion of the elements formed the first life of the universe, which is the goddess Freyja that human",
                "beings believe in. with passage of time, the elves, human beings, dwarves, the underworld, and other\nraces were born, and the Mordalin is the demon that is transformed by the human's resentment.",
                "Demons descended upon the human realm, and the people were left to fend for themselves. Thanks to our\ngreat goddess, who was willing to share her divine power with humans, the demons have been sealed",
                "ever since. My dear Princess, you are the one who obtained the Goddess' divine power. My great\nPrincess, then you will save our country.",
                "oh...... I see.", "Dear Your Royal Highness, as a thank you for helping me retrieve this little one as well as\nfor making your journey smoother, please come back home with me, I have items to present to you."
        };
        //conversation2 = new String[]{}
    }

    public void drawNPC() {
        drawImage(NPCWizard, 600, 250, 57.6, 76.8);
    }

    public void drawReminder() {
        if (distance(600, 250, pos.getX(), pos.getY()) < 75) {
            changeColor(new Color(255, 255, 255, 255));
            drawText(540, 295, "Press F  ", "Arial", 20);

        }
    }



    //Plants
    Image humanTree;
    Image humanBranches;
    Image humanStone;
    Image humanBird;
    Image humanChickenSheet;
    Image humanFarm;
    Image humanStump;
    Image[] framesChickenLeft;
    Image[] framesChickenRight;
    boolean chickenLeft;
    int currentChickenFrame;
    double chickenPositionX = 770;

    public void initPlant(){
        humanTree = loadImage("Midgard/HumanTree.png");
        humanBranches = loadImage("Midgard/HumanBranches.png");
        humanStone = loadImage("Midgard/HumanStone.png");
        humanBird = loadImage("Midgard/HumanBird.png");
        humanChickenSheet = loadImage("Midgard/HumanChickenSheet.png");
        humanFarm = loadImage("Midgard/HumanLand.png");
        humanStump = loadImage("Midgard/HumanStump.png");


        treePositionX = new double[4];
        treePositionY = new double[4];

        framesChickenLeft = new Image[3];
        framesChickenRight = new Image[3] ;
        for (int i = 0; i < 3; i++) {
            framesChickenRight[i] = subImage(humanChickenSheet,32*i,96,32,32);
            framesChickenLeft[i] = subImage(humanChickenSheet,32*i,32,32,32);
        }

    }

    public void  drawPlant(){
        treePositionX[0] = 300; treePositionY[0] = 200;
        treePositionX[1] = 300; treePositionY[1] = 300;
        treePositionX[2] = 370; treePositionY[2] = 335;
        treePositionX[3] = 430; treePositionY[3] = 420;

        for (int i = 0; i < 4; i++){
            drawImage(humanTree,treePositionX[i],treePositionY[i],100,100);
        }

        drawImage(humanBranches,300,370,51,51);

        drawImage(humanStone,950,350,41,41);
        drawImage(humanBird,970,342,20,20);
        drawImage(humanFarm,740,250,250,130);
        //drawImage(humanSeeds,730,260,250,130);
        drawImage(humanStump,230,300,55,41);

        drawImage(humanStump,180,430,55,41);
        drawImage(humanStump,270,450,55,41);
        if(chickenLeft){
            drawImage(framesChickenLeft[currentChickenFrame],  chickenPositionX, 220,32,32);
        }else{
            drawImage(framesChickenRight[currentChickenFrame],  chickenPositionX, 220,32,32);
        }
    }

    int conversationIndex = 0;

    //剧情1对话
    public void drawCommunication_1() {


        int wrap = -1;
        if (conversationIndex < conversation1.length) {
            String sentence = conversation1[conversationIndex];
            if(conversationIndex % 2 != 0){
                drawImage(Princess_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(600, 650, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(600, 650, firstPart, "Arial", 20);
                    drawText(600, 680, secondPart, "Arial", 20);
                }

            }else{
                drawImage(Wizard_talk, 0, 550, 1255, 200);

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

        if(conversationIndex < conversation1.length - 1){
            if(conversationIndex % 2 != 0){
                drawText(30, 690, "next sentence", "Arial", 12);
            }else{
                drawText(1130, 690, "next sentence", "Arial", 12);
            }

        }else{
            if(conversationIndex % 2 != 0){
                drawText(30, 690, "Close", "Arial", 12);
            }else{
                drawText(1130, 690, "Close", "Arial", 12);
            }

        }

    }

    //剧情1对话已经结束并且还没有完成找猫的任务，触发这段
    public  void drawRepeat(){
        drawImage(Wizard_talk, 0, 550, 1255, 200);
        drawText(200, 650,"Ahem, my poor girl......", "Arial", 20);
        drawText(1130, 690, "Close", "Arial", 12);
        clickFlag = false;
    }

    //剧情2对话
    int communicate2Index = 0;
    public void drawCommunication_2(){
        int wrap = -1;
        if (communicate2Index < conversation2.length) {
            String sentence = conversation2[communicate2Index];
            if(communicate2Index % 2 != 0 && communicate2Index!= 5 && communicate2Index!= 7 && communicate2Index!= 9){
                drawImage(Princess_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(200, 650, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(200, 650, firstPart, "Arial", 20);
                    drawText(200, 680, secondPart, "Arial", 20);
                }

            }else{
                drawImage(Wizard_talk, 0, 550, 1255, 200);

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

        if(communicate2Index < conversation2.length - 1){
            if(communicate2Index % 2 != 0 && communicate2Index!= 5 && communicate2Index != 7 && communicate2Index != 9){
                drawText(30, 690, "next sentence", "Arial", 12);
            }else{
                drawText(1130, 690, "next sentence", "Arial", 12);
            }

        }else{
            if(communicate2Index % 2 != 0 && communicate2Index != 5 && communicate2Index != 7 && communicate2Index != 9){
                drawText(30, 690, "Close", "Arial", 12);
            }else{
                drawText(1130, 690, "Close", "Arial", 12);
            }

        }
    }

    //和小猫的对话
    int catIndex = 0;
    public void  drawCat_p(){

        int wrap = -1;
        if (catIndex < cat_p.length) {
            String sentence = cat_p[catIndex];
            if(catIndex % 2 != 0){
                drawImage(Princess_talk, 0, 550, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(400, 660, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(600, 650, firstPart, "Arial", 20);
                    drawText(600, 680, secondPart, "Arial", 20);
                }

            }else{
                drawImage(Cat_talk, 0, 560, 1255, 200);

                wrap = sentence.indexOf("\n");
                if(wrap == -1){
                    drawText(200, 660, sentence, "Arial", 20);
                }else{
                    String firstPart = sentence.substring(0, wrap);
                    String secondPart = sentence.substring(wrap + 1);
                    drawText(200, 650, firstPart, "Arial", 20);
                    drawText(200, 680, secondPart, "Arial", 20);
                }
            }

            clickFlag = false;
        }

        if(catIndex < cat_p.length - 1){
            if(catIndex % 2 != 0){
                drawText(30, 690, "next sentence", "Arial", 12);
            }else{
                drawText(1130, 690, "next sentence", "Arial", 12);
            }

        }else{
            if(catIndex % 2 != 0){
                drawText(30, 690, "Close", "Arial", 12);
            }else{
                drawText(1130, 690, "Close", "Arial", 12);
            }

        }
    }

    boolean complete = false;
    //画任务框
    Image mission;
    public void drawMisiion(){
        mission = loadImage("Midgard/mission.png");
        drawImage(mission, 331, 100, 700, 500);
        //drawSolidCircle(521, 540, 40);

    }

    double catX, catY;
    Image catFront[];
    Image catBack[];
    public void initCat(){
        cat = new Image[12];
        catFront = new Image[2];
        catBack = new Image[2];
        catX = 0;
        catY = 0;
        sheet_cat = loadImage("Midgard/spritesheet_cat.png");
        for(int iy = 0; iy < 3; iy++){
            for(int ix = 0; ix < 4; ix++){
                cat[iy*4 + ix] = subImage(sheet_cat, ix*16, iy*16, 16, 16);
            }
        }
        catFront[0] = loadImage("Midgard/cat_front_right.png");
        catFront[1] = loadImage("Midgard/cat_front_left.png");
        catBack[0] = loadImage("Midgard/cat_back_right.png");
        catBack[1] = loadImage("Midgard/cat_back_left.png");
    }

    boolean catp = false;

    //画和猫的对话框
    public void drawCat(){
        if(distance(300, 370, pos.getX(), pos.getY()) < 60 && trig4){
            //找到猫啦
            catp = true;
        }
        if(complete && !plots2){

            if(is_right){
                drawImage((cat[currentFrame]), pos.getX() + 59.6, pos.getY() + 40, -40, 40);
            }else if(is_left){
                drawImage((cat[currentFrame]), pos.getX() + 28.8, pos.getY() + 40, 40, 40);
            }else if(is_down){
                drawImage(catFront[currentFrame%2], pos.getX() + 28.8, pos.getY() + 40, 40, 40);
            }else if(is_up){
                drawImage(catBack[currentFrame%2], pos.getX() + 28.8, pos.getY() + 40, 40, 40);
            }

       }else if(plots2){
            drawImage(cat[0], 600, 300, 50, 50);
        }
        else{
            drawImage(cat[0], 300, 370, 50, 50);
       }
    }

    boolean is_moving = false;
    boolean is_up = false;
    boolean is_down = true;
    boolean is_left = false;
    boolean is_right = false;
    static int c1 = 0;
    int c2 = 0;
    int c3 = 0;
    boolean trig1 = false;
    boolean plots1 = false;
    boolean plots2 = false;
    boolean trig2 = false;
    boolean plots3 = false;
    boolean trig3 = false;
    boolean trig4 = false;//得剧情1结束才能触发这段对话。而且没有complete
    @Override
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
        } else if (e.getKeyCode() == KeyEvent.VK_F && distance(600, 250, pos.getX(), pos.getY()) < 75) {
            //conversationIndex=0说明剧情1还没有开始，让剧情true
            if(conversationIndex == 0){
                trig1 = true;
            }
            //找到猫了，可以跟老头进行第二次对话了,plots2用于归还后小猫的位置，plots2用于第二段对话剧情
            if(complete){
                trig2 = true;
                plots2 = true;
            }
            //剧情1对话已经结束并且还没有完成找猫的任务，触发这段
            if(conversationIndex != 0 && !complete){
                trig3 = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        is_moving = false;
    }

    boolean clickFlag = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        //触发剧情1
        if (trig1) {
            if(conversationIndex % 2 != 0){
                if ((e.getX() >= 0 && e.getX() <= 1225) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (conversationIndex < conversation1.length) {
                        conversationIndex++;
                    }
                    if(conversationIndex == conversation1.length){
                        plots1 = true;
                        trig1 = false;
                        trig4 = true;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }else{
                if ((e.getX() >= 0 && e.getX() <= 1255) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (conversationIndex < conversation1.length) {
                        conversationIndex++;
                    }
                    if(conversationIndex == conversation1.length){
                        plots1 = true;
                        trig1 = false;
                        trig4 = true;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }
        }

        //剧情1结束后是任务框，点击任务框的返回后表示剧情1结束
        if(plots1){
            if ((e.getX() >= 481 && e.getX() <= 561) &&
                    (e.getY() >= 500 && e.getY() <= 580)){
                plots1 = false;

            }
        }

        //触发小猫对话，catp是距离判定找到猫了，complete（找到猫了，且猫的对话结束）是true,
        if(catp && trig4){
            if(catIndex % 2 != 0){
                if ((e.getX() >= 0 && e.getX() <= 1225) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (catIndex <cat_p.length) {
                        catIndex++;
                    }
                    if(catIndex == cat_p.length){
                        catp = false;
                        complete = true;
                        trig4 = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }else{
                if ((e.getX() >= 0 && e.getX() <= 1255) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (catIndex < cat_p.length) {
                        catIndex++;
                    }
                    if(catIndex == cat_p.length){
                        catp = false;
                        complete = true;
                        trig4 = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }
        }

        //触发老头第2次对话，plots3=true，猫归还老头
        if (trig2) {
            if(communicate2Index % 2 != 0 && communicate2Index != 5 && communicate2Index != 7 && communicate2Index != 9){
                if ((e.getX() >= 0 && e.getX() <= 1225) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (communicate2Index < conversation2.length) {
                        communicate2Index++;
                    }
                    if(communicate2Index == conversation2.length){
                        plots3 = true;
                        trig2 = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }else{
                if ((e.getX() >= 0 && e.getX() <= 1255) &&
                        (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                    if (communicate2Index < conversation2.length) {
                        communicate2Index++;
                    }
                    if(communicate2Index == conversation2.length){
                        plots3 = true;
                        trig2 = false;
                    }
                    clickFlag = true; // 执行点击事件后将标志置为true
                }
            }
        }

        //剧情1对话已经结束并且还没有完成找猫的任务，触发这段
        if(trig3){
            if ((e.getX() >= 0 && e.getX() <= 1225) &&
                    (e.getY() >= 550 && e.getY() <= 700) && !clickFlag) {
                trig3 = false;
            }
            clickFlag = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}