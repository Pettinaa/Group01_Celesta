package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GoldMiner extends GameEngine {
//    public static void main(String[] args) {
//        createGame(new GoldMiner());
//
//    }
    Font customFont;

    AudioClip bgm;
    AudioClip cheers;

    // Line properties
    int startX;
    int startY;
    int currentX;
    int currentY;

    // State: 0 = swinging, 1 = extending, 2 = retracting
    int state;

    double lineLength;
    double maxLineLength = 500; // Maximum length the line can extend
    double angle = 0.5;
    double targetAngle;
    int direction = 1;

    // Gold properties
    int numGolds = 6; // 3 types * 2 each
    int[] goldX = new int[numGolds];
    int[] goldY = new int[numGolds];
    int goldWidth = 40;
    int goldHeight = 40;
    boolean[] goldGet = new boolean[numGolds];
    boolean[] goldCaptured = new boolean[numGolds];
    int[] goldTypes = new int[numGolds]; // 0: gold, 1: gold2, 2: gold3
    ImageIcon gold;
    Image gold2;
    Image gold3;

    // Gem properties
    int numGems = 4; // 1 of each type
    int[] gemX = new int[numGems];
    int[] gemY = new int[numGems];
    int gemWidth = 32;
    int gemHeight = 32;
    boolean[] gemGet = new boolean[numGems];
    boolean[] gemCaptured = new boolean[numGems];
    Image gems;
    Image gem1;
    Image gem2;
    Image gem3;
    Image gem4;

    //confetti
    double confettiTime;
    int confettiFrame;
    Image confettiSheet;
    Image[] confettiImages;

    boolean playConfetti; // 标志位，表示是否播放彩带特效
    double confettiTimer; // 计时器，用于控制彩带特效的播放时间
    int currentConfettiFrame; // 当前彩带特效的帧索引

    // Score properties
    int score = 0;

    Image hook;
    Random rand = new Random();

    public void initLine() {
        lineLength = 100;
        startX = 630;
        startY = 330;
        currentX = startX;
        currentY = startY;
    }

    public void initGoldsAndGems() {
        for (int i = 0; i < numGolds; i++) {
            goldX[i] = rand.nextInt(1200) + 50; // Random x position between 50 and 1250
            goldY[i] = rand.nextInt(300) + 400; // Random y position between 400 and 700
            goldTypes[i] = i / 2; // Assign type based on index (2 of each type)
        }

        for (int i = 0; i < numGems; i++) {
            gemX[i] = rand.nextInt(700) + 200; // Random x position between 50 and 1250
            gemY[i] = rand.nextInt(250) + 400; // Random y position between 400 and 650
        }
    }

    public void updateHook(double dt) {
        if (state == 0) { // Swinging state
            if (angle < 0.1) {
                direction = 1;
            } else if (angle > 0.9) {
                direction = -1;
            }
            angle = angle + direction * 0.005;
            currentX = (int) (startX + lineLength * Math.cos(angle * Math.PI));
            currentY = (int) (startY + lineLength * Math.sin(angle * Math.PI));
        } else if (state == 1) { // Extending state
            lineLength += 200 * dt;
            if (lineLength >= maxLineLength) {
                lineLength = maxLineLength;
                state = 2; // Switch to retracting state
            }
            currentX = (int) (startX + lineLength * Math.cos(targetAngle));
            currentY = (int) (startY + lineLength * Math.sin(targetAngle));

            // Check for collision with gold
            for (int i = 0; i < numGolds; i++) {
                if (!goldCaptured[i] && checkCollision(currentX, currentY, goldX[i], goldY[i], goldWidth, goldHeight)) {
                    goldGet[i] = true;
                    goldCaptured[i] = true; // Mark the gold as captured
                    score += 1; // Increment score by 1 for gold
                    state = 2; // Switch to retracting state immediately
                    break;
                }
            }

            // Check for collision with gems
            for (int i = 0; i < numGems; i++) {
                if (!gemCaptured[i] && checkCollision(currentX, currentY, gemX[i], gemY[i], gemWidth, gemHeight)) {
                    gemGet[i] = true;
                    gemCaptured[i] = true; // Mark the gem as captured
                    if (i == 3) {
                        score += 5; // Increment score by 5 for diamond
                    } else {
                        score += 3; // Increment score by 3 for other gems
                    }
                    state = 2; // Switch to retracting state immediately
                    break;
                }
            }
        } else if (state == 2) { // Retracting state
            lineLength -= 200 * dt;
            if (lineLength <= 100) {
                lineLength = 100;
                state = 0; // Switch to swinging state
                for (int i = 0; i < numGolds; i++) {
                    goldGet[i] = false; // Hide gold when hook is retracted
                }
                for (int i = 0; i < numGems; i++) {
                    gemGet[i] = false; // Hide gem when hook is retracted
                }
            }
            currentX = (int) (startX + lineLength * Math.cos(targetAngle));
            currentY = (int) (startY + lineLength * Math.sin(targetAngle));
        }
    }

    public void drawHook() {
        changeColor(Color.RED);
        drawLine(startX, startY, currentX, currentY, 3);
        hook = loadImage("Images/GoldMiner/hook.png");
        drawImage(hook, currentX - 35, currentY - 10, 72, 50);
    }
    String goldPath;
    String gold2Path;
    String gold3Path;

    public void drawGoldAndGems() {
        if (gold == null) {

            //gold = loadImage("Images/GoldMiner/gold0.gif");
            gold = new ImageIcon("Images/GoldMiner/gold0.gif");

            goldPath = "Images/GoldMiner/gold0.gif";
            gold2Path = "Images/GoldMiner/gold1.gif";
            gold3Path = "Images/GoldMiner/gold2.gif";

            gold2 = loadImage("Images/GoldMiner/gold1.gif");
            gold3 = loadImage("Images/GoldMiner/gold2.gif");

            gems = loadImage("Images/GoldMiner/gems.png");

            //将每种宝石从精灵图中提取出来
            gem1 = subImage(gems, 0, 0, 32, 32);
            gem2 = subImage(gems, 32, 0, 32, 32);
            gem3 = subImage(gems, 0, 32, 32, 32);
            gem4 = subImage(gems, 32, 32, 32, 32);
        }

        // Draw gold
        for (int i = 0; i < numGolds; i++) {
            if (goldGet[i]) {
                // Draw gold at hook's current position
                if (goldTypes[i] == 0) {
                    drawGif(goldPath, currentX - goldWidth / 2, currentY - goldHeight / 2 + 30, goldWidth, goldHeight);
                } else if (goldTypes[i] == 1) {
                    drawGif(gold2Path, currentX - goldWidth / 2, currentY - goldHeight / 2 + 30, goldWidth, goldHeight);
                } else {
                    drawGif(gold3Path, currentX - goldWidth / 2, currentY - goldHeight / 2 + 30, goldWidth, goldHeight);
                }
            } else if (!goldCaptured[i]) {
                // Draw gold at its original position if it has not been captured
                if (goldTypes[i] == 0) {
                    drawGif(goldPath, goldX[i], goldY[i], goldWidth, goldHeight);
                } else if (goldTypes[i] == 1) {
                    drawGif(gold2Path, goldX[i], goldY[i], goldWidth, goldHeight);
                } else {
                    drawGif(gold3Path, goldX[i], goldY[i], goldWidth, goldHeight);
                }
            }
        }

        // Draw gems
        for (int i = 0; i < numGems; i++) {
            if (gemGet[i]) {
                // Draw gem at hook's current position
                if (i == 0) {
                    drawImage(gem1, currentX - gemWidth / 2, currentY - gemHeight / 2 + 20, gemWidth, gemHeight);
                } else if (i == 1) {
                    drawImage(gem2, currentX - gemWidth / 2, currentY - gemHeight / 2 + 20, gemWidth, gemHeight);
                } else if (i == 2) {
                    drawImage(gem3, currentX - gemWidth / 2, currentY - gemHeight / 2 + 20, gemWidth, gemHeight);
                } else {
                    drawImage(gem4, currentX - gemWidth / 2, currentY - gemHeight / 2 + 20, gemWidth, gemHeight);
                }
            } else if (!gemCaptured[i]) {
                // Draw gem at its original position if it has not been captured
                if (i == 0) {
                    drawImage(gem1, gemX[i], gemY[i], gemWidth, gemHeight);
                } else if (i == 1) {
                    drawImage(gem2, gemX[i], gemY[i], gemWidth, gemHeight);
                } else if (i == 2) {
                    drawImage(gem3, gemX[i], gemY[i], gemWidth, gemHeight);
                } else {
                    drawImage(gem4, gemX[i], gemY[i], gemWidth, gemHeight);
                }
            }
        }
    }

    public boolean checkCollision(int x, int y, int rectX, int rectY, int rectWidth, int rectHeight) {
        return x >= rectX && x <= rectX + rectWidth && y >= rectY && y <= rectY + rectHeight;
    }

    // Game properties
    Image background;
    Image people;
    Image victory;
    String cheersPath;
    String cheersPath2;
    boolean showCongratulation; // Flag for showing congratulation text
    double congratulationTimer; // Timer for controlling congratulation text duration


    public void init() {
        //setupWindow(1255, 700);
        background = loadImage("Images/GoldMiner/bg.png");
        people = loadImage("Images/GoldMiner/people.png");
        bgm = loadAudio("Audio/GoldMiner/bgm.wav");
        cheers = loadAudio("Audio/GoldMiner/cheers.wav");

        //startAudioLoop(bgm, 90);
        startAudioLoop(bgm);

        initLine();
        initGoldsAndGems();

        customFont = new Font("Sans-serif", Font.PLAIN, 24); // 使用 Arial 字体，常规样式，大小 24

        cheersPath = "Images/GoldMiner/cheers4.gif";
        cheersPath2 = "Images/GoldMiner/cheers3.gif";

        //confetti
//        confettiSheet = loadImage("Images/GoldMiner/Confetti.png");
//        confettiImages = new Image[64];
//
//        Image[] temp = new Image[8];
//        int k = 0;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                temp[j] = subImage(confettiSheet, 512 * j, 512 * i, 512, 512);
//                confettiImages[k] = temp[j];
//                k++;
//            }
//        }

        confettiSheet = loadImage("Images/GoldMiner/Confetti.png");
        confettiImages = new Image[64];
        int frameWidth = confettiSheet.getWidth(null) / 8;
        int frameHeight = confettiSheet.getHeight(null) / 8;
        for (int i = 0; i < 64; i++) {
            int row = i / 8;
            int col = i % 8;
            confettiImages[i] = subImage(confettiSheet, col * frameWidth, row * frameHeight, frameWidth, frameHeight);
        }

//        if(playCheers){
//            playAudio(cheers);
//        }


    }

    public void drawCheers(){
        victory = loadImage("Images/GoldMiner/victory.png");
        drawGif(cheersPath, 200, -200,  1000, 900);
        drawGif(cheersPath2, 200, -200,  1000, 900);

        drawImage(victory, 550, 350,200, 170);
    }
    @Override
    public void update(double dt) {
        updateHook(dt);

//        if (score >= 10) {
//            playConfetti = true;
//        }
//
//        if (playConfetti) {
//            confettiTimer += dt;
//            currentConfettiFrame = (int) (confettiTimer * 20) % confettiImages.length; // Assume 20 frames per second
//            if (confettiTimer >= 1.5) { // Adjust confetti duration as needed
//                playConfetti = false;
//                confettiTimer = 0.0;
//                currentConfettiFrame = 0; // Reset frame index
//            }
//        }

        if(score >= 10){
            playCheers = true;
        }

//        if (score >= 10 && !playConfetti) {
//            playConfetti = true;
//            showCongratulation = true; // Set flag to show congratulation text
//            congratulationTimer = 0; // Reset the congratulation timer
//        }
//
//        // Update congratulation timer if the flag is set
//        if (showCongratulation) {
//            congratulationTimer += dt;
//            if (congratulationTimer >= 3) {
//                showCongratulation = false; // Hide congratulation text after 3 seconds
//            }
//        }

//        if (playConfetti) {
//            confettiTimer += dt;
//            currentConfettiFrame = (int) (confettiTimer * 20) % confettiImages.length; // Assume 20 frames per second
//            if (confettiTimer >= 1.5) { // Adjust confetti duration as needed
//                playConfetti = false;
//                confettiTimer = 0.0;
//                currentConfettiFrame = 0; // Reset frame index
//            }
//        }

    }


    int victory_audio = 1;

    @Override
    public void paintComponent() {
        clearBackground(1255, 700);
        drawImage(background, 0, 0, 1255, 700);
        drawImage(people, 580, 240, 100, 100);
        drawHook();
        drawGoldAndGems();
        drawScore();

        int confettiX = (1255 - confettiImages[0].getWidth(null)) / 2; // 游戏窗口宽度减去彩带特效宽度的一半
        int confettiY = (700 - confettiImages[0].getHeight(null)) / 2; // 游戏窗口高度减去彩带特效高度的一半
//        if (playConfetti) {
//            drawImage(confettiImages[currentConfettiFrame], 300, 200, 1000, 1000);
//        }

//        if (playConfetti) {
//            drawImage(confettiImages[currentConfettiFrame], confettiX, confettiY, confettiImages[0].getWidth(null), confettiImages[0].getHeight(null));
//        }

        if (showCongratulation) {
           // drawText("congratulation！", 600, 400, customFont, Color.RED);
            //drawImage() ;
        }

        if(playCheers){
            drawCheers();
        }
        if(playCheers && victory_audio == 1){
            playAudio(cheers);
            victory_audio--;
        }


    }
    Image scoreBook;

    public void drawScore() {
        scoreBook = loadImage("Images/GoldMiner/scoreBook.png");
        drawImage(scoreBook, 0, 0, 280, 150);
        changeColor(yellow);
        mFrame.setFont(customFont); // 设置自定义字体
        drawText(40, 80,"score: " + score );


    }
    boolean playCheers = false;
    static boolean nextMission = false;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && state == 0) {
            targetAngle = angle * Math.PI; // Save the current angle
            state = 1; // Switch to extending state
        }

        if((e.getX() >= 550 && e.getX() <= 550 + 200 && e.getY() >= 350 && e.getY() <= 350 + 170) && playCheers){
            //nextMission = true;
            stopAudioLoop(bgm);
            createGame(new SvartalfheimUnder());
            Close(this);

        }

        // Check if a gem is captured
        for (int i = 0; i < numGems; i++) {

            if (!gemCaptured[i] && checkCollision(currentX, currentY, gemX[i], gemY[i], gemWidth, gemHeight)) {
                gemGet[i] = true;
                gemCaptured[i] = true; // Mark the gem as captured
                if (i == 3) {
                    score += 5; // Increment score by 5 for diamond
                } else {
                    score += 3; // Increment score by 3 for other gems
                }
                //playConfetti = true; // Start confetti animation


                state = 2; // Switch to retracting state immediately
                break;
            }
        }
    }


}
