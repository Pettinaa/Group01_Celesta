package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class SvartalfheimUnder extends GameEngine {
//    public static void main(String[] args) {
//        createGame(new SvartalfheimUnder());
//    }
    Point2D pos = new Point2D.Double();
    Image princessSheet;
    Image[] frames_up;
    Image[] frames_down;
    Image[] frames_left;
    Image[] frames_right;

    Image dwarf;
    Image[] framesDwarfLeft;
    Image[] framesDwarfRight;
    boolean dwarfLeft;
    int currentDwarfFrame;
    double dwarfPositionX = 200;
    boolean movingRight = true;
    double animTime;
    boolean dwarfStop = false;

    Image[] dialogueImages = new Image[5];
    int currentDialogueIndex = -1;

    boolean dialogueFinished = false;

    boolean showBlackScreen = false;
    Image narrationImage;
    int currentTextIndex = -1;
    String[] texts = {
            "The princess asked the dwarves to help her reach the demon king's castle,",
            "However, the cunning dwarves, unsatisfied, plotted a betrayal,",
            "The princess trusted the dwarves' instructions without any doubt and set off on the road,",
            "The road looked ominous, with eerie sounds faintly heard,",
            "The princess suddenly realized she had been betrayed,",
            "Her vision blurred, and her consciousness began to fade...",
            "(Press space to continue)"

    };

    boolean allTextsShown = false;

    public void loadDialogueImages() {
        dialogueImages[0] = loadImage("Images/Svartalfheim/dwarfTalkUnder1.png");
        dialogueImages[1] = loadImage("Images/Svartalfheim/dwarfTalkUnder2.png");
        dialogueImages[2] = loadImage("Images/Svartalfheim/dwarfTalkUnder3.png");
        dialogueImages[3] = loadImage("Images/Svartalfheim/princessTalkUnder1.png");
        dialogueImages[4] = loadImage("Images/Svartalfheim/dwarfTalkUnder4.png");
    }

    public void loadNarrationImage() {
        narrationImage = loadImage("Images/Svartalfheim/darkBG.png");
    }

    public void drawDialogue() {
        if (currentDialogueIndex >= 0 && currentDialogueIndex < dialogueImages.length) {
            drawImage(dialogueImages[currentDialogueIndex], 0, 530, 1255, 200);
        }
    }

    public void drawNarration() {
        drawImage(narrationImage, 0, 0, 1255, 700);
        String fontName = "Trebuchet MS";
        if (currentTextIndex >= 0) {
            changeColor(Color.white);
            for (int i = 0; i <= currentTextIndex; i++) {
                drawText(50, 190 + i * 60, texts[i], fontName, 26);
            }
        }
    }

    public void initDwarf() {
        dwarf = loadImage("Images/Svartalfheim/dwarf.png");

        framesDwarfLeft = new Image[3];
        framesDwarfRight = new Image[3];

        for (int i = 0; i < 3; i++) {
            framesDwarfRight[i] = subImage(dwarf, 72 * i, 96, 72, 96);
            framesDwarfLeft[i] = subImage(dwarf, 72 * i, 288, 72, 96);
        }
    }

    public void drawDwarf() {
        if (dwarfLeft) {
            drawImage(framesDwarfLeft[currentDwarfFrame], dwarfPositionX + 200, 300, 72 * 1.5, 96 * 1.5);
        } else {
            drawImage(framesDwarfRight[currentDwarfFrame], dwarfPositionX + 200, 300, 72 * 1.5, 96 * 1.5);
        }
    }

    public void initPrincess() {
        frames_up = new Image[3];
        frames_down = new Image[3];
        frames_left = new Image[3];
        frames_right = new Image[3];

        princessSheet = loadImage("Images/Svartalfheim/spritesheet_princess.png");

        for (int i = 0; i < 3; i++) {
            frames_up[i] = subImage(princessSheet, 72 * i, 0, 72, 96);
            frames_right[i] = subImage(princessSheet, 72 * i, 96, 72, 96);
            frames_down[i] = subImage(princessSheet, 72 * i, 192, 72, 96);
            frames_left[i] = subImage(princessSheet, 72 * i, 288, 72, 96);
        }

        pos.setLocation(627.5, 350);
    }

    public void drawPrincess() {
        if (is_up) {
            drawImage(frames_up[currentFrame], pos.getX(), pos.getY(), 57.6 * 1.7, 76.8 * 2);
        } else if (is_down) {
            drawImage(frames_down[currentFrame], pos.getX(), pos.getY(), 57.6 * 1.7, 76.8 * 2);
        } else if (is_left) {
            drawImage(frames_left[currentFrame], pos.getX(), pos.getY(), 57.6 * 1.7, 76.8 * 2);
        } else if (is_right) {
            drawImage(frames_right[currentFrame], pos.getX(), pos.getY(), 57.6 * 1.7, 76.8 * 2);
        }
    }

    boolean is_moving = false;
    boolean is_up = false;
    boolean is_down = true;
    boolean is_left = false;
    boolean is_right = false;

    int currentFrame;

    public void updatePrincess(double dt) {
        if (is_moving) {
            if (is_up) {
                pos.setLocation(pos.getX(), pos.getY() - 5);
            } else if (is_down) {
                pos.setLocation(pos.getX(), pos.getY() + 5);
            } else if (is_left) {
                pos.setLocation(pos.getX() - 5, pos.getY());
            } else if (is_right) {
                pos.setLocation(pos.getX() + 5, pos.getY());
            }

            if (pos.getX() <= 0) {
                pos.setLocation(0, pos.getY());
            }
            if (pos.getX() >= 1197.4) {
                pos.setLocation(1197.4, pos.getY());
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
    }
    Image map;
    public void drawMap(){
        map = loadImage("map/map3.jpg");
        drawImage(map, 0, 0, 1255, 700);
    }

    @Override
    public void update(double dt) {
        animTime += dt;

        if (!dwarfStop) {
            if (movingRight) {
                dwarfPositionX += 1;
                if (dwarfPositionX >= 270) {
                    movingRight = false;
                    dwarfLeft = true;
                }
            } else {
                dwarfPositionX -= 1;
                if (dwarfPositionX <= 200) {
                    movingRight = true;
                    dwarfLeft = false;
                }
            }
            currentDwarfFrame = getFrame(0.3, 3);
        }

        updatePrincess(dt);

        checkMission();

        // 更新旁白文字显示
        if (showBlackScreen && !allTextsShown) {
//            if (animTime >= 2 && currentTextIndex < texts.length - 1) {
//                currentTextIndex++;
//                animTime = 0;
//                if (currentTextIndex == texts.length - 1) {
//                    allTextsShown = true;
//                }
//                mFrame.repaint();
//            }
            if (animTime >= 3 && currentTextIndex < texts.length - 1) {
                currentTextIndex++;
                animTime = 0;
                if (currentTextIndex == texts.length - 1) {
                    allTextsShown = true;
                }
                mFrame.repaint();
            }

        }
    }

    public int getFrame(double d, int num_frames) {
        return (int) Math.floor(((animTime % d) / d) * num_frames);
    }

    boolean checkMission = false;

    public void checkMission() {
        if (distance(dwarfPositionX + 200, 300, pos.getX(), pos.getY()) < 75) {
            checkMission = true;
        } else {
            checkMission = false;
        }
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    Image board;

    public void drawGrace() {
        board = loadImage("Images/Svartalfheim/board.png");
        drawImage(board, 370, 200, 500, 300);
        String gracePath = "Images/Svartalfheim/crown.gif";
        drawGif(gracePath, 550, 320, 150, 150);
    }

    AudioClip bgm;
    AudioClip bgm2;
    @Override
    public void init() {
        initDwarf();
        initPrincess();
        loadDialogueImages();
        loadNarrationImage();
        bgm = loadAudio("Audio/Orca.wav");
        startAudioLoop(bgm);
        bgm2 = loadAudio("Audio/MistyMountains.wav");

    }

    @Override
    public void paintComponent() {
        if (showBlackScreen) {
            changeColor(Color.black);
            drawSolidRectangle(0, 0, 1255, 700);
            drawNarration();
        }
        else {
            Image bg = loadImage("Images/Svartalfheim/bg.png");
            drawImage(bg, 0, 0, 1255, 700);

            drawDwarf();
            drawPrincess();

            if (checkMission) {
                changeColor(Color.white);
                drawText(dwarfPositionX + 230, 330, "Press F", "Arial", 20);
            }

            drawDialogue();

            if (dialogueFinished) {
                drawGrace();
            }
        }
        if(drawMap){
            drawMap();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        is_moving = false;
    }

    boolean drawMap = false;
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
        } else if (e.getKeyCode() == KeyEvent.VK_F && distance(dwarfPositionX + 300, 350, pos.getX(), pos.getY()) < 75) {
            currentDialogueIndex = 0;
            dwarfStop = true;
            dwarfLeft = false;
            currentDwarfFrame = 0;
        }

//        if (showBlackScreen && allTextsShown && e.getX() >= 0 && e.getX() <= 1255 && e.getY() >= 0 && e.getY() <= 700) {
//            // 用户点击屏幕创建一个新的类
//            createGame(new GoldMiner());
//        }
        if (showBlackScreen && allTextsShown && e.getKeyCode() == KeyEvent.VK_SPACE) {
            stopAudioLoop(bgm2);
            // 用户点击屏幕创建一个新的类
            drawMap = true;

        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getX() >= 380 && e.getX() <= 380 + 500 && e.getY() >= 190 && e.getY() <= 190 + 300) && dialogueFinished) {
            stopAudioLoop(bgm);
            startAudioLoop(bgm2);
            showBlackScreen = true;
            currentTextIndex = -1; // 重置文字索引
            allTextsShown = false; // 重置文字显示状态
            animTime = 0; // 重置时间
            mFrame.repaint();
            return;
        }

        if (e.getY() >= 530 && e.getY() <= 730) {
            if (currentDialogueIndex >= 0 && currentDialogueIndex < dialogueImages.length - 1) {
                currentDialogueIndex++;
            } else {
                currentDialogueIndex = -1;
                dialogueFinished = true;
            }
        }

        if(drawMap && e.getX() >= 0 && e.getX() <= 1255 && e.getY() >= 0 && e.getY() <= 700){
            //stopAudioLoop(bgm2);
            createGame(new FlyingPrincess());
            Close(this);
        }

    }
}
