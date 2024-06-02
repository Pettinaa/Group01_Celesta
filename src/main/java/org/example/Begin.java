package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Begin extends GameEngine {

    public static void main(String[] args) {
        createGame(new Begin());
    }

    double animTime;
    double time;

    int i = 0;
    AudioClip music;

    Image start;
    boolean gameStart;
    Image background1;
    Image[] background;
    Image[] background2;
    Image startgame;
    Image text;
    int backgroundFrame = 0;
    boolean backgroundActive = true;
    Image map;

    int backgroundFrame1 = 0;
    boolean backgroundActive1 = true;
    boolean over = false;
    boolean over1 = false;


    public void init(){
        start = loadImage("Begin/start.png");
        time = 0;

        music = loadAudio("Begin/OneLastAdventure.wav");

        background1 = loadImage("Begin/background1.jpg");
        startgame = loadImage("Begin/startgame.png");
        text = loadImage("Begin/text.png");

        startAudioLoop(music);
        background = new Image[8];
        background[0] = loadImage("Begin/background6.jpg");
        background[1] = loadImage("Begin/background7.jpg");
        background[2] = loadImage("Begin/background8.jpg");
        background[3] = loadImage("Begin/background9.jpg");
        background[4] = loadImage("Begin/background10.jpg");
        background[5] = loadImage("Begin/background11.jpg");
        background[6] = loadImage("Begin/background12.jpg");
        background[7] = loadImage("Begin/background13.jpg");
        map = loadImage("map/m0.jpg");

        background2 = new Image[8];
        background2[0] = loadImage("Begin/background13.jpg");
        background2[1] = loadImage("Begin/background12.jpg");
        background2[2] = loadImage("Begin/background11.jpg");
        background2[3] = loadImage("Begin/background10.jpg");
        background2[4] = loadImage("Begin/background9.jpg");
        background2[5] = loadImage("Begin/background8.jpg");
        background2[6] = loadImage("Begin/background7.jpg");
        background2[7] = loadImage("Begin/background6.jpg");


    }


    @Override
    public void update(double dt) {
        animTime = animTime + dt;

        if(gameStart){
            time = time + dt;
        }

        if(time > 26){
            backgroundFrame = getFrame(0.8, 8);
        }

        if(time >= 44){
            backgroundFrame1 = getFrame(0.8, 8);

        }
    }


    @Override
    public void paintComponent() {
        if(!gameStart){
            drawImage(start, 0,0,1255,700);
            drawImage(startgame, 780,160,320,170);
        }else{


            drawImage(background1, 0,0,1255,700);

            changeColor(white);
            if(time >= 1 && time <= 26){
                drawText(80,140,"There is a kingdom called Nordria. ","Gabriola",40);

                if(time >= 4){
                    drawText(80, 200,"On this day, Celesta, the kingdom's only princess, celebrates her 15th birthday, ", "Gabriola",40);

                    if(time >= 7){
                        drawText(80, 260,"and the king tells Celesta secrets about the kingdom. ", "Gabriola",40);

                        if(time >= 10){
                            drawText(80, 320,"Since ancient times, Mordalin, the demon king who was transformed from human grievances,", "Gabriola",40);

                            if(time >= 13){
                                drawText(80, 380,"has come down to earth every once in a while to bring calamity to the kingdom.", "Gabriola",40);

                                if(time >= 16){
                                    drawText(80, 440,"Fifty years ago, the kingdom's soothsayer left a prophecy: ", "Gabriola",40);

                                    if(time >= 19){
                                        drawText(80, 500,"Fifty years from today, the only princess of the royal family will go to re-seal the demon king.\" ", "Gabriola",40);

                                        if(time >= 22){
                                            drawText(80, 560,"This princess is Celesta.", "Gabriola",40);
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(time >= 26){
                if(backgroundActive){
                    drawImage(background[backgroundFrame],0,0,1255,700);
                    if(backgroundFrame >= 6){
                        backgroundActive = false;
                    }
                }else {
                    drawImage(background[7],0,0,1255,700);
                }
            }

          if(time >= 28 && time <= 44){
              drawImage(text,50,570,1150, 120);
              changeColor(black);
              if(time >= 30 && time < 35){
                  drawText(290, 640, "The king himself handed Celesta the Staff of the Gods", "Gabriola",40);
              }

              if(time >= 35 && time < 40){
                  drawText(290, 640,"\"My dear Celesta, please keep going north.\" ","Gabriola",40);
              }

              if(time >= 40 && time < 45){
                  drawText(90,620, "\"Wait, father, this is a bit sudden, I don't even know how to use this staff or the seal","Gabriola",40);
                  drawText(90,670, "and what's going on with the demon king?\" ","Gabriola",40);
              }

              if(time >= 45 && time < 50){
                  drawText(90,620,"My dear Celesta, you will understand everything on your journey, and the day you return,","Gabriola",40 );
                  drawText(90,670,"all the people of the kingdom will be cheering for you.\"","Gabriola",40);
              }

              if(time >= 50){
                  drawText(90, 620,"And with that,","Gabriola",40 );
                  drawText(90, 670,"Ceresta set out on her journey with the Staff, her mind filled with doubt and unease.","Gabriola",40 );
              }
          }

          if(time > 51 && time < 54){
              if(backgroundActive1){
                  drawImage(background2[backgroundFrame1],0,0,1255,700);
                  if(backgroundFrame1 >= 6){
                      backgroundActive1 = false;
                  }
              }else {
                  drawImage(background2[7],0,0,1255,700);
              }
          }

          if(time >= 54){
              drawImage(map, 0, 0, 1255, 700);
          }
        }

        if(over){
            stopAudioLoop(music);
        }
    }

    public int getFrame(double d, int num_frames) {
        return (int) Math.floor(((animTime % d) / d) * num_frames);
    }

    public void mouseClicked(MouseEvent e){
        if((e.getX() >= 780 && e.getX() <= 1100) && (e.getY() >= 160 && e.getY() <= 330)){
            gameStart = true;
        }

        if(time >= 54)
        if((e.getX() >= 0 && e.getX() <= 1255) && (e.getY() >= 0 && e.getY() <= 700)){
            createGame(new Midgard());
            stopAudioLoop(music);
            over = true;
            Close(this);
        }
    }
}
