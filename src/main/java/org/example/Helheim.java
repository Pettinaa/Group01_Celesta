package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Helheim extends GameEngine{
//       public static void main(String[] args) {
//       createGame(new Helheim());
//   }
    Image sheet;
    Image background;
    Image black;
    Image map;
    Image[] frames_up;
    Image[] frames_down;
    Image[] frames_left;
    Image[] frames_right;
    AudioClip backgroundAudio;
    Image plotImage1;
    Image plotImage2;
    Image plotImage3;
    Image plotImage4;
    Image teleportationSheet;
    Image[] teleportation;

    int currentFrame;
    double animTime;
    int gameTime = 0;
    Point2D pos = new Point2D.Double();
    @Override
    public void init() {
       // setupWindow(1255, 700);
        if (gameTime == 0){
            beginTime = 0;
        }else {
            beginTime = 28;
        }

        lastTime = 0;
        failureTime = 0;
        gameOver = false;

        frames_up = new Image[3];
        frames_down = new Image[3];
        frames_left = new Image[3];
        frames_right = new Image[3];
        sheet = loadImage("spritesheet_princess.png");
        background = loadImage("Helheim/Heheim.png");
        black = loadImage("FlyingPrincess/black_screen.jpg");
        princessTalk = loadImage("Princess_talk.png");
        plotImage1 = loadImage("Helheim/plot1.png");
        plotImage2 = loadImage("Helheim/plot2.png");
        plotImage3 = loadImage("Helheim/plot3.png");
        plotImage4 = loadImage("Helheim/plot4.png");
        map = loadImage("Helheim/map.jpg");
        backgroundAudio = loadAudio("Helheim/HelBackground.wav");
        for (int i = 0; i < 3; i++) {
            frames_up[i] = subImage(sheet, 72 * i, 0, 72, 96);
            frames_right[i] = subImage(sheet, 72 * i, 96, 72, 96);
            frames_down[i] = subImage(sheet, 72 * i, 192, 72, 96);
            frames_left[i] = subImage(sheet, 72 * i, 288, 72, 96);
        }
        pos.setLocation(627.5, 550);

        teleportationSheet = loadImage("Helheim/tel.png");
        teleportation = new Image[6];
        for (int i = 0; i < 6; i++) {
           teleportation[i] = subImage(teleportationSheet, 128 * i, 0, 128, 128);
        }
        initNPC();
        initMission();
        initPlot();
    }
    boolean is_moving = false;
    boolean is_up = true;
    boolean is_down = false;
    boolean is_left = false;
    boolean is_right = false;
    /*start*/
    double beginTime;
    Image princessTalk;
    double lastTime;
    double failureTime;
    boolean gameOver;
    double telTime;

    @Override
    public void update(double dt) {
        // Update Function
        animTime += dt;
        beginTime += dt;
        telTime += dt;

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
        if (beginTime >= 15.0 && beginTime < 16.7){
            movingHel = true;
        }else {
            movingHel = false;
        }
        if (movingHel){
            helFrame = getFrame(0.3,3);
            helY += 5;
        }
        if (beginTime > 28.0 && beginTime < 29.0){
            missionActive = true;
        }
        if (distance(helX, helY, pos.getX(), pos.getY()) < 60) {
            double angle = Math.atan2(pos.getY() - helY, pos.getX() - helX);

            // Move the princess away from the collision point
            double newX = helX + 60 * Math.cos(angle);
            double newY = helY + 60 * Math.sin(angle);
            pos.setLocation(newX, newY);
        }

        teleportationFrame = getFrame(0.3,6);
        //plot
        if ( plotACount == 6) {
            plot1A = false;
            plot1B = false;
            plot2 = true;
        }
        if (plotBCount == 2 || plot2BCount == 2 || plot3BCount == 2 ){
            gameOver = true;
        }
        if (plot2A || plot2B || plot2BCount >0 || plot2ACount > 0){
            plot2 = false;
        }

        if ( plot2ACount == 3) {
            plot2A = false;
            plot2B = false;
            plot3 = true;
        }if (plot3A || plot3B || plot3BCount >0 || plot3ACount > 0){
            plot3 = false;
        }

        if ( plot3ACount == 2 ) {
            plot3A = false;
            plot3B = false;
            plot4 = true;
        }
        if (plot4Count > 0){
            plot4 = false;
        }
        if (!plot4 && plot3ACount > 0){
            lastTime += dt;
        }
        if (lastTime > 4.0 && distance(pos.getX(),pos.getY(),780,320) < 100){
            teleportationActive = true;
            //System.out.println(teleportationActive);
        }


    }

    public int getFrame(double d, int num_frames) {
        return (int) Math.floor(((animTime % d) / d) * num_frames);
    }


    public void paintComponent() {
        // Clear the background to black
        //changeBackgroundColor(Color.white);
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

        judgeBoundary();
            //start
        if (beginTime >= 0.5 && beginTime <= 3.0){
            startAudioLoop(backgroundAudio);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(200, 650,"(A beautiful and serene palace)", "Arial", 20);
        }else if (beginTime > 3.0 && beginTime <= 6.0){
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(200, 650,"Where is this?", "Arial", 20);
        }else if (beginTime > 6.0 && beginTime <= 15.0){
            drawImage(framesDownHel[0],helX,helY,57.6, 76.8);
            if (beginTime > 7.0 && beginTime <= 11.0){
                drawImage(helTalk, 0, 550, 1255, 200);
                drawText(200, 650,"This is Helheim, the eternal realm of the underworld, a forgotten domain by the living.", "Arial", 20);
            }
            if (beginTime > 11.0){
                drawImage(princessTalk, 0, 550, 1255, 200);
                drawText(200, 650,"!!??", "Arial", 30);
            }
        }else if (beginTime > 15.0){
            drawImage(framesDownHel[helFrame],helX,helY,57.6, 76.8);
            if (beginTime > 17.0 && beginTime <= 19.0){
                drawImage(helTalk, 0, 550, 1255, 200);
                drawText(200, 650,"The realm of the dead does not welcome the living.", "Arial", 20);
            }if (beginTime > 19.0 && beginTime <= 24.0){
                drawImage(helTalk, 0, 550, 1255, 200);
                drawText(200, 650,"But you are the only human to have safely entered here in nearly five centuries. ", "Arial", 20);
                drawText(200,680,"In light of that, I have no intention of troubling you.",20);
            }if (beginTime > 24.0 && beginTime <= 27.0){
                drawImage(helTalk, 0, 550, 1255, 200);
                drawText(200, 650,"Step onto the teleportation array there, and you will be able to return to Midgard.", "Arial", 20);
            }if (beginTime > 27.0 && beginTime <= 28.0){
                drawImage(princessTalk, 0, 550, 1255, 200);
                drawText(200, 650,"Wait a moment... ", "Arial", 20);
            }
        }
        if (beginTime > 28){
            drawTeleportation();
        }
        if (missionActive) drawImage(missionImage,331, 100, 620, 400);
        if (distance(helX, helY, pos.getX(), pos.getY()) < 150 && beginTime > 28 && !missionActive) {
            changeColor(white);
            drawText(helX,helY,"Press F",20);
        }

        //plot1
        if (plot1){

            drawImage(plotImage1,331, 100, 620, 400);
            //changeColor(black);
            //drawText(331, 250,"Facing the cold ghost, it seems a good idea to choose a topic that can't go wrong and start a conversation.",20);
        }
        if (plot1A && plotACount == 0){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"I am Princess Celesta of Nordria. Madam, may I know your name?", "Arial", 20);
        }
        if (plot1A && plotACount == 1){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"(Melodious and eerie atmosphere)", "Arial", 20);
            drawText(200,680,"In shadows deep where souls do sleep,I reign o'er realms where darkness seeps~","Arial", 20);
        }
        if (plot1A && plotACount == 2){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            drawText(1030, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Through endless night, my kingdom's keep,\n" +
                    "Where silence reigns, and secrets creep.", "Arial", 20);
            drawText(200,680,"Where silence reigns, and secrets creep~ With icy gaze and heart of stone, I sit upon my spectral throne.\n ","Arial", 20);
        }
        if (plot1A && plotACount == 3){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Madam?", "Arial", 20);
        }
        if (plot1A && plotACount == 4){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            drawText(1030, 690, "next sentence", "Arial", 12);
            drawText(200, 650, "(She looks at you)", "Arial", 20);
            drawText(200, 680,"I am Hel, the ruler of the realm of the underworld, the Dark Queen. In the eternal void, I guard the domain of finality.", "Arial", 20);
        }
        if (plot1A && plotACount == 5){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            //drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650, "(The land where the living are forbidden to enter...)", "Arial", 20);
        }
        if (plot1B && plotBCount == 0){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Madam,Can you tell me your identity?", "Arial", 20);
        }
        if (plot1B && plotBCount == 1){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            //drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"It's none of your concern", "Arial", 20);
        }

        //System.out.println(plotACount);
        ///plot2
        if (plot2){
            //System.out.println(plot2);
            drawImage(plotImage2,331, 100, 620, 400);
        }
        //System.out.println(plot2ACount);
        if (plot2A && plot2ACount == 0){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Did you say earlier that I am the only human to have entered Helheim in nearly five hundred years?", "Arial", 20);
        }
        if (plot2A && plot2ACount == 1){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            drawText(1030, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Yes, You are the only living soul to set foot in Helheim in nearly five hundred years. It is truly an extraordinary journey.", "Arial", 20);
        }
        if (plot2A && plot2ACount == 2){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650," Yes, Your Majesty. It has been a journey filled with challenges and miracles.", "Arial", 20);
        }
        if (plot2B && plot2BCount == 0){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Many people are dying outside now. Can you tell me how to save them?", "Arial", 20);
        }
        if (plot2B && plot2BCount == 1){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            //drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"It's none of my concern", "Arial", 20);
        }
        //plot3
        System.out.println(plot3A);
        if (plot3){
            drawImage(plotImage3,331, 100, 620, 400);
        }
        if (plot3A && plot3ACount == 0){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"From the outset, I journeyed alongside dwarves, elves, and humans. ", "Arial", 20);
            drawText(200, 680,"We traversed vast forests, crossed treacherous mountains, admired the splendor of the land . ", "Arial", 20);
        }if (plot3A && plot3ACount == 1){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            //drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Your inner resolve and fearlessness are admirable qualities. However, I am curious about the endpoint of your journey. Where do you intend to go?", "Arial", 20);
        }
        if (plot3B && plot3BCount == 0){
            changeColor(Color.BLACK);
            drawImage(princessTalk, 0, 550, 1255, 200);
            drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"Perhaps you'd like to hear a story from my childhood. ", "Arial", 20);
            //drawText(200, 680,"We traversed vast forests, crossed treacherous mountains, admired the splendor of the land . ", "Arial", 20);
        }if (plot3B && plot3BCount == 1){
            changeColor(Color.BLACK);
            drawImage(helTalk, 0, 550, 1255, 200);
            //drawText(30, 690, "next sentence", "Arial", 12);
            drawText(200, 650,"It's none of my concern", "Arial", 20);
        }

        if (plot4){
            drawImage(plotImage4,331, 100, 620, 400);
        }
        if (!plot4 && plot3ACount > 0){
            if (lastTime < 4.0 && lastTime > 0){
                changeColor(Color.BLACK);
                drawImage(helTalk, 0, 550, 1255, 200);
                //drawText(30, 690, "next sentence", "Arial", 12);
                drawText(200, 650,"The Dark Citadel... Your goal is ambitious. Nevertheless, I look forward to witnessing your journey's end, Princess.", "Arial", 20);
            }
            if (lastTime > 4.0 ){
                changeColor(white);
                drawText(280,200,"Hel has activated the portal to the underworld. ");
                drawText(280,250,"Now, let's head to the portal.");
            }
        }
        if (gameOver){
            gameTime++;
            failureTime++;
            if (failureTime < 65.0){
                drawImage(black,0, 0, 1255, 700);
                drawText(200, height()/2,"Hel doesn't seem interested in your topic, communication failed.", "Arial", 20);
            } else {
               init();
            }
        }
        if (teleportationActive){
            drawImage(map,0, 0, 1255, 700);
        }

    }
    public void judgeBoundary(){
        int leftX1 = 320;
        int rightX1 = 860;
        int topY1 = 370;
        int bottomY1 = 470;

        int leftX2 = 420;
        int rightX2 = 800;
        int topY2 = 470;
        double distance1 = Math.sqrt((leftX2-leftX1)*(leftX2-leftX1) + (topY2-topY1)*(topY2-topY1));
        double distance2 = Math.sqrt((bottomY1+100-topY1) * (bottomY1+100-topY1) + (rightX1 - rightX2)*  (rightX1 - rightX2));

        if (pos.getX() <= leftX1 && pos.getY() <= bottomY1) {
            pos.setLocation(leftX1, pos.getY());
        }
        if (pos.getX() >= rightX1 && pos.getY() <= bottomY1 + 100) {
            pos.setLocation(rightX1, pos.getY());
        }
        if (pos.getY() <= topY1) {
            pos.setLocation(pos.getX(), topY1);
        }
        if (pos.getX() <= leftX2 && pos.getY() >= bottomY1 && Math.sqrt(Math.pow(pos.getX() - leftX1, 2) + Math.pow(pos.getY() - topY1, 2)) < distance1) {
            pos.setLocation(pos.getX(), bottomY1);
        }
        if (pos.getX() <= leftX2 && pos.getY() >= bottomY1 && Math.sqrt(Math.pow(pos.getX() - leftX1, 2) + Math.pow(pos.getY() - topY1, 2)) > distance1) {
            pos.setLocation(leftX2,pos.getY());
        }
        if (pos.getX() <= leftX2 && pos.getY() >= bottomY1 && Math.sqrt(Math.pow(pos.getX() - leftX1, 2) + Math.pow(pos.getY() - topY1, 2)) > distance1) {
            pos.setLocation(leftX2,pos.getY());
        }
        if (pos.getX() >= rightX2 && pos.getY() >= bottomY1+100 && Math.sqrt(Math.pow(pos.getX() - rightX1, 2) + Math.pow(pos.getY() - topY1, 2)) <= distance2) {
            pos.setLocation(pos.getX(),bottomY1 + 100);
        }
        if (pos.getX() >= rightX2 && pos.getY() >= bottomY1+100 && Math.sqrt(Math.pow(pos.getX() - rightX1, 2) + Math.pow(pos.getY() - topY1, 2)) > distance2) {
            pos.setLocation(rightX2,pos.getY());
        }
    }
    /**/
    int teleportationFrame;
    boolean teleportationActive;
    public void drawTeleportation(){
        drawImage(teleportation[teleportationFrame],700,320,200,200);
    }
    /*npc*/
    boolean movingHel;
    int helFrame;
    Image helSheet;
    Image[] framesUpHel;
    Image[] framesDownHel;
    Image[] framesLeftHel;
    Image[] framesRightHel;
    double helX;
    double helY;
    Image helTalk;
    public  void initNPC(){
        movingHel = false;
        helFrame = 0;
        helX = 600;
        helY = 320;
        framesUpHel = new Image[3];
        framesDownHel = new Image[3];
        framesLeftHel = new Image[3];
        framesRightHel = new Image[3];
        helSheet = loadImage("Helheim/hel.png");
        helTalk = loadImage("Helheim/helTalk.png");
        for (int i = 0; i < 3; i++) {
            framesUpHel[i] = subImage(helSheet, 72 * i, 0, 72, 96);
            framesRightHel[i] = subImage(helSheet, 72 * i, 96, 72, 96);
            framesDownHel[i] = subImage(helSheet, 72 * i, 192, 72, 96);
            framesLeftHel[i] = subImage(helSheet, 72 * i, 288, 72, 96);
        }
    }
    /*mission*/
    boolean missionActive;
    Image missionImage;
    private void initMission(){
        missionActive = false;
        missionImage = loadImage("Helheim/mission.png");
    }
    /*plot*/
    /*plot1*/
    int plotACount;

    int plotBCount;
    boolean plot1A;
    boolean plot1B;
    boolean plot1;
    /*plot2*/
    int plot2ACount;

    int plot2BCount;
    boolean plot2A;
    boolean plot2B;
    boolean plot2;
    /*plot3*/
    int plot3ACount;

    int plot3BCount;
    boolean plot3A;
    boolean plot3B;
    boolean plot3;
    boolean plot4;
    int plot4Count;
    private void initPlot(){
        plot1 = false;
        plot1A = false;
        plot1B = false;
        plotACount = 0;
        plotBCount = 0;

        plot2 = false;
        plot2A = false;
        plot2B = false;
        plot2ACount = 0;
        plot2BCount = 0;

        plot3 = false;
        plot3A = false;
        plot3B = false;
        plot3ACount = 0;
        plot3BCount = 0;

        plot4 = false;

        number = 0;
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
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            is_moving = true;
            is_left = false;
            is_up = false;
            is_down = false;
            is_right = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_F && beginTime > 28 && !missionActive){
            plot1 = true;
        }
    }
    int number;

    @Override
    public void keyReleased(KeyEvent e) {
        is_moving = false;
    }
    public void mouseClicked(MouseEvent e){
        if ((e.getX() >= 331 && e.getX() <= 1031) &&
                (e.getY() >= 100 && e.getY() <= 600) && missionActive){
             missionActive = false;
        }
        if ((e.getX() >= 0 && e.getX() <= 1255) &&
                (e.getY() >= 0 && e.getY() <= 700) && teleportationActive && number == 0){
            createGame(new Devildom());
            Close(this);
            number --;
        }
        if (plot1) {
            if ((e.getX() >= 500 && e.getX() <= 631) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                plot1A = true; plotACount = 0;
                plot1 = false;
                //System.out.println(plot1A);
                //System.out.println(plot1B);
            }
            if ((e.getX() >= 600 && e.getX() <= 831) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                plot1B = true;plotBCount = 0;
                plot1 = false;
                //System.out.println(plot1A);
                //System.out.println(plot1B);
            }
        }
        if (plot1A){
            if ((e.getX() >= 0 && e.getX() <= 1255) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                plotACount++;
            }
        }
        if (plot1B){
            if ((e.getX() >= 0 && e.getX() <= 1255) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                plotBCount++;
            }
        }
        if (plot2) {
            if ((e.getX() >= 500 && e.getX() <= 631) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                plot2A = true; plot2ACount = 0;
                plot2 = false;
                //System.out.println(plot2A);
                //System.out.println(plot2);
            }
            if ((e.getX() >= 600 && e.getX() <= 831) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                plot2B = true;plot2BCount = 0;
                plot2 = false;
            }
        }
        if (plot2A){
            if ((e.getX() >= 0 && e.getX() <= 1255) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                plot2ACount++;
            }
        }
        if (plot2B){
            if ((e.getX() >= 0 && e.getX() <= 1255) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                plot2BCount++;
            }
        }

        if (plot3) {
            if ((e.getX() >= 500 && e.getX() <= 631) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                plot3A = true; plot3ACount = 0;
                plot3 = false;
                //System.out.println(plot1A);
                //System.out.println(plot1B);
            }
            if ((e.getX() >= 600 && e.getX() <= 831) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                plot3B = true; plot3BCount = 0;
                plot3 = false;
                //System.out.println(plot1A);
                //System.out.println(plot1B);
            }
        }
        if (plot3A){
            if ((e.getX() >= 0 && e.getX() <= 1255) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                plot3ACount++;
            }
        }
        if (plot3B){
            if ((e.getX() >= 0 && e.getX() <= 1255) &&
                    (e.getY() >= 550 && e.getY() <= 700)){
                plot3BCount++;
            }
        }
        if (plot4) {
            if ((e.getX() >= 500 && e.getX() <= 631) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                //plot3A = true; plot3ACount = 0;
                plot4 = false;plot4Count++;
                //System.out.println(plot1A);
                //System.out.println(plot1B);
            }
            if ((e.getX() >= 600 && e.getX() <= 831) &&
                    (e.getY() >= 200 && e.getY() <= 600) ){
                //plot3B = true; plot3BCount = 0;
                plot4 = false;plot4Count++;
            }
        }
    }

}
