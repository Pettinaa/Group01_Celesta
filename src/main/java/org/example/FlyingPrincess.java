package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class FlyingPrincess extends GameEngine{
//    public static void main(String[] args) {
//        createGame(new FlyingPrincess());
//    }

    /*princess*/
    private int princessX, princessY;
    private int princessVelocityY;
    private int GRAVITY = 1;
    private int JUMP_STRENGTH = -7;
    Image sheet;
    Image[] frames_right;
    int flyingFrame;
    double flyTime;

    boolean flyActive;
    Image fireEnd;
    AudioClip bgm;
    AudioClip fireDeath;
    public void initPrincess(){
        bgm = loadAudio("Audio/Rondeau des fleurs et des rapieres.WAV");
        sheet = loadImage("FlyingPrincess/flyPrincess.png");
        fireP = loadImage("FlyingPrincess/black.png");
        frames_right = new Image[3];
        princessX = 500;
        if (failNumber == 1){
            princessY  = height()/2;
        }else {
            princessY += 200;
        }
        princessVelocityY = 0;
        nextWord = false;
        for (int i = 0; i < 3; i++) {
            frames_right[i] = subImage(sheet, 72 * i, 96, 72, 96);
        }
        fireEnd = subImage(fireP,0,96,72,96);
    }
    public void drawPrincess(){
        if (!fireCollision && !success){
            drawImage(frames_right[flyingFrame],princessX,princessY,57.6, 76.8);
            if(flyingFrame == 2){
                flyActive = false;
            }
        }else if (!fireCollision){
            drawImage(frames_right[(int) i],princessX,princessY,57.6, 76.8);
        }else if (fireCollision && !success){
            drawImage(fireEnd,princessX,princessY,57.6,76.8  );
            gameOver = true;
        }
    }

    double i;
    int failNumber;
    public void updatePrincess(double dt){
        princessVelocityY += GRAVITY;
        princessY += princessVelocityY;
        if (flyActive){
            princessVelocityY = JUMP_STRENGTH;
            flyTime += dt;
            if (flyTime >= 0.5){
                flyActive = false;
            }
            if (fireCollision ){
                gameOver = true;
            }
            flyingFrame = (int)(flyTime*6);

            if (flyingFrame >= 3){
                flyingFrame = 0;
            }
            if (success){
                i += 0.2;
                if (i >= 3){
                    i = 0;
                }
                princessX += 10;
                GRAVITY = 0;
                princessVelocityY = 0;
                princessY -= 1;
                JUMP_STRENGTH = 0;
                if (princessX < 1410 &&princessX >= 1400){
                    nextWord = true;
                }else {
                    nextWord = false;
                }
            }
        }else {
            flyTime = 0;
            flyingFrame = 0;
        }
    }
    /*spike*/
    double spikeTX[],spikeTY[];
    double spikeDX[],spikeDY[];
    double spikeTW[],spikeTH[];
    double spikeDW[],spikeDH[];
    int maxNumber;
    Image spikeImage;
    Image fireImage;
    Image[] fireImages;
    int minSpikeGapT ;
    int minSpikeGapD ;
    int fire[];
    public void initSpike(){
        maxNumber = 10;
        spikeTX = new double[maxNumber];
        spikeTY = new double[maxNumber];
        spikeDX = new double[maxNumber];
        spikeDY = new double[maxNumber];
        spikeTW = new double[maxNumber];
        spikeTH = new double[maxNumber];
        spikeDW = new double[maxNumber];
        spikeDH = new double[maxNumber];

        spikeImage = loadImage("FlyingPrincess/spike_trap.png");
        fireImage = loadImage("FlyingPrincess/fire2.png");

        fireImages = new Image[40];
        for(int i = 0; i < 5; i++) {
            fireImages[i] = subImage(fireImage, i*10, 0, 10, 24);
        }
        fire = new int[maxNumber];
        for (int i=0; i<maxNumber; i++){
            fire[i] = 0;
        }

        int originalWidth = spikeImage.getWidth(null);
        int originalHeight = spikeImage.getHeight(null);
        minSpikeGapT = 80;
        minSpikeGapD = 80;

        if (failNumber == 1){
            for (int i=0;  i<maxNumber; i++){
                spikeDH[i] = (rand(3) + 1) * 110;
                double originalRatio = (double)originalWidth / originalHeight;
                spikeDW[i] = (spikeDH[i] * originalRatio)/3;
                spikeDY[i] = height() - spikeDH[i] +200;
                if (i == 0) {
                    spikeDX[i] = rand(10);
                } else {
                    spikeDX[i] = spikeDX[i-1] + spikeDW[i-1] + minSpikeGapD;
                }
            }
        }else {
            for (int i=0;  i<maxNumber; i++){
                spikeDH[i] = (rand(3) + 1) * 110;
                double originalRatio = (double)originalWidth / originalHeight;
                spikeDW[i] = (spikeDH[i] * originalRatio)/3;
                spikeDY[i] = height() - spikeDH[i] ;
                if (i == 0) {
                    spikeDX[i] = rand(10);
                } else {
                    spikeDX[i] = spikeDX[i-1] + spikeDW[i-1] + minSpikeGapD;
                }
            }
        }


        for(int i=0; i<maxNumber; i++){
            spikeTH[i] = (rand(3) + 1) * 80;
            double originalRatio = (double)originalWidth / originalHeight;
            spikeTW[i] = spikeTH[i] * originalRatio;
            spikeTY[i] = 0;
            if (i == 0) {
                spikeTX[i] = rand(10);
            } else {
                spikeTX[i] = spikeTX[i-1] + spikeTW[i-1] + minSpikeGapT;
            }
        }
    }

    public void updateSpike(){
        for (int i = 0; i < maxNumber; i++) {
            spikeDX[i] -= 4;
            if (spikeDX[i] < -12) {
                spikeDX[i] = rand(10) +  1255;
                spikeDY[i] = height() - spikeDH[i];
            }
            fire[i] ++;
            if (fire[i] == 5){
                fire[i] = 0;
            }
        }
        for (int i = 0; i < maxNumber; i++) {
            spikeTX[i] -= 4;
            if (spikeTX[i] < -12) {
                spikeTX[i] = rand(10) +  1255;
                spikeTY[i] = 0;
            }
        }
    }
    public void drawSpike(){
        for (int i = 0; i < maxNumber; i++) {
            drawImage(fireImages[fire[i]], spikeDX[i], spikeDY[i],spikeDW[i],spikeDH[i] );
        }
        for (int i = 0; i < maxNumber; i++) {
            drawImage(spikeImage, spikeTX[i], spikeTY[i],spikeTW[i],spikeTH[i] );
        }

    }
    /**/
    boolean spikeCollision;
    double explosionTime;
    int explosionFrame;
    public void checkCollisionWithSpikes() {
        Rectangle princessBounds = new Rectangle(princessX, princessY,18, 67 );
        for (int i = 0; i < maxNumber; i++) {
            Rectangle spikeBounds = new Rectangle((int) spikeTX[i], (int) spikeTY[i], (int) spikeTW[i]-40, (int) spikeTH[i]-20);
            if (princessBounds.intersects(spikeBounds)) {
                //System.out.println("jjjjjj");
                spikeCollision = true;
                playAudio(bloodV);
                //System.out.println(spikeCollision);
            }
        }
    }
    boolean  fireCollision;
    Image fireP;
    public void checkCollisionWithFire() {
        Rectangle princessBounds = new Rectangle(princessX, princessY,28, 37 );
        for (int i = 0; i < maxNumber; i++) {
            Rectangle spikeBounds = new Rectangle((int) spikeDX[i], (int) spikeDY[i], (int) spikeDW[i]/2, (int) spikeDH[i]/2);
            if (princessBounds.intersects(spikeBounds)) {
                fireCollision = true;
                playAudio(fireDeath);
            }
        }
    }
    Image bloodSheet;
    Image[] bloodFrames;
    public void initBlood(){
        bloodSheet = loadImage("FlyingPrincess/blood.png");
        bloodFrames = new Image[6];
        for (int i = 0; i < 3; i++) {
            bloodFrames[i] = subImage(bloodSheet, 641 * i, 0, 641, 800);
        }
        for (int i = 3; i < 6; i++) {
            bloodFrames[i] = subImage(bloodSheet, 641 * (i-3), 800, 641, 800);
        }
    }
    public void drawBlood(){
        if (spikeCollision) {
            drawImage(bloodFrames[explosionFrame], princessX-10, princessY - 50, 90, 90);
        }
    }

   public void updateBlood(double dt){
       checkCollisionWithSpikes();
       if(spikeCollision) {
           explosionTime += dt;
           //System.out.println();

          // System.out.println(explosionFrame);
           if (explosionTime >= 0.5) {
               explosionTime = 0.0;
               explosionFrame = 0;
               //System.out.println(explosionFrame);
               spikeCollision = false;
           }
           if (explosionFrame == 5){
               spikeCollision = false;
               gameOver = true;
           }
           explosionFrame = (int) (explosionTime*12 );
       }
   }
    /*game*/
    boolean gameOver;
    Image backgroundImage;
    Image failureImage;
    boolean begin;

   AudioClip lightingV;
   AudioClip bloodV;

    public void init(){
        failNumber++;
        //setupWindow(1255, 700);
        fireDeath = loadAudio("FlyingPrincess/blood.wav");
        backgroundImage = loadImage("FlyingPrincess/background.jpg");
        blackImage = loadImage("FlyingPrincess/black_screen.jpg");
        information = loadImage("FlyingPrincess/misson.png");
        lighting = loadImage("FlyingPrincess/lighting.png");
        lightingV = loadAudio("FlyingPrincess/light.wav");
        bloodV = loadAudio("FlyingPrincess/blood.wav");
        failureImage = loadImage("FlyingPrincess/failure.png");

        backgroundX = 0;
        backgroundY = 0;
        GRAVITY = 0;
        JUMP_STRENGTH = -7;
        princessVelocityY = 0;
        begin = false;
        begin2 = false;
        beginGame = false;
        fireCollision = false;
        success = false;
        gameOver = false;//System.out.println(gameOver);
        showStartScreen = true;
        initPrincess();
        initSpike();
        initBlood();
    }
    double textTimer = 0;
    double textTimer2 = 0;
    int currentLine = 0;
    boolean textFinished = false;
    boolean start = true;
    boolean nextWord;
    int y = 1;
    @Override
    public void update(double dt) {
        //System.out.println(gameOver);
        if (startText) {
            textTimer += dt;
            if (textTimer >= 1) {
                textTimer = 0;
                currentLine++;
                if (currentLine >= 3) {
                    textFinished = true;
                    //System.out.println(startText);
                }
            }
            if (textFinished) {
                textTimer2 += dt;//System.out.println(start);
                //System.out.println(textTimer2);
                if (textTimer2 >= 3) {
                    if(y == 1){
                        //startAudioLoop(bgm);
                        y--;
                    }

                    start = false;
                }
            }
        }
        if (begin){
            GRAVITY = 1;
        }
        if (!gameOver) {
            if (!spikeCollision && begin){
                if (backgroundX >= -1580) {
                    backgroundX -= 4;
                } else {
                    success = true;
                    flyActive = true;
                }
                updatePrincess(dt);
                updateSpike();
            }
            // checkCollisionWithSpikes();
            //System.out.println(spikeCollision);
            if (!success){
                updateBlood(dt);
                checkCollisionWithFire();
            }
        }
        if (princessY > height() || princessY < 0){
            gameOver = true;
        }
        if(nextWord){
            stopAudioLoop(bgm);
            createGame(new Helheim());
            Close(this);
        }
    }

    private double backgroundX;
    private int backgroundY;
    boolean success;
    Image information;

    boolean showStartScreen;
    boolean begin2;
    @Override
    public void paintComponent() {
        clearBackground(1255, 700);
        if (showStartScreen) {
            drawStartScreen();
        }else if (startText && start){
            drawImage(blackImage, backgroundX, backgroundY, 2880, 700);
            drawStartText();
        }
       else if (!gameOver && !success) {
            drawImage(backgroundImage, backgroundX, backgroundY, 2880, 700);
            drawPrincess();
            drawSpike();
            drawBlood();
            if (!begin2){
                drawImage(information,331, 100, 620, 400);
            }
            if (!begin && begin2 && !beginGame){
                changeColor(white);
                drawText(331,400,"Press space to start");
            }
        }else if (!gameOver && success){
            clearBackground(1255, 700);
            drawImage(backgroundImage, backgroundX, backgroundY, 2880, 700);
            drawPrincess();
        }else if (gameOver){
            drawImage(backgroundImage, backgroundX, backgroundY, 2880, 700);
            drawPrincess();
            drawSpike();
            drawBlood();
            drawImage(failureImage,331, -30, 621, 668);
            changeColor(white);
            drawText(530, 380, "Unfortunately","Elephant", 35);
            drawText(550, 430, "your playing failed.","Elephant", 20);
            drawText(450, 480, "Please press space and try again~","Elephant", 25);
        }
    }
    boolean beginGame = false;
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (begin2 && !beginGame) {
                begin = true;
                beginGame = true;
            }else if (gameOver){
                init();
            }else {
                flyActive = true;
            }

        }
    }

    public void mouseClicked(MouseEvent e){
        if ((e.getX() >= 331 && e.getX() <= 1031) &&
                (e.getY() >= 100 && e.getY() <= 600) && !begin){
            begin2 = true;
        }
    }
    Image blackImage;
    Image lighting;
    int count = 0;
    boolean startText;
    private void drawStartScreen() {
        if (System.currentTimeMillis() % 1000 < 500 && count < 20) {
            drawImage(blackImage, backgroundX, backgroundY, 2880, 700);
        } else {
            count++;
            double x;
            if(count <= 10){
               x = 0;
            }else {
                x = 1000;
            }
            drawImage(backgroundImage, backgroundX-x, backgroundY, 2880, 700);
            playAudio(lightingV);
            drawImage(lighting,150+x/5,0,300 + x/10,400+ x/6);
            if (count >= 20) {
                showStartScreen = false;
                startText = true;
            }
        }
    }

    private void drawStartText() {
        changeColor(white);
        int lineHeight = 60;
        int startY = 200;
        String[] lines = {"It seems that due to some unknown error, the teleportation array malfunctioned.",
                "You have arrived at a strange place, where the ground is burning with flames and nails are scattered around.",
                "Just then, the blessing of the elves emitted a glow.",
                "Please use these wings to escape from this place as soon as possible."};
        for (int i = 0; i < currentLine && i < lines.length; i++) {
            drawBoldText(100, startY + i * lineHeight, lines[i],20);
        }
    }
}
