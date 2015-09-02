package com.example.mitchell.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.security.Provider;
import java.util.Random;

/**
 * Created by Mitchell on 3/27/2015.
 */


public class GameMode2 extends SurfaceView implements SurfaceHolder.Callback
{
    public static int gameMode = 3;

    int change = 10;
    boolean check = false;
    public static int x = 3;
    public static int y = 4;
    public static int level = 0;
    public static int randX = 1;
    public static int randY = 1;
    public static int colorRandR = 126;
    public static int colorRandG = 45;
    public static int colorRandB = 224;

    public static int colorRandR2 = 166;
    public static int colorRandG2 = 105;
    public static int colorRandB2 = 164;

    public static int high_score;

    String timer = "30";

    // Huge color text block inc
    int reds[] = {199,50, 83, 199,
                  214,43,
                  50,207,52,
                  65,24,
                  48,196,176,
                  116,168,125,
                  171,39,
                  43,39,168,
                  83,173};

    int greens[] = {109,80,194,214,
                 214,69,
                 128,43,70,
                 199,31,
                 92,62,98,
                 186,117,71,
                 34,29,
                 168,198,65,
                 99,117,12};

    int blues[] = {79,148,205, 34,
                7,69,
                45,128,25,
                152,115,
                80,79,98,
                108,176,39,
                34,29,
                138,39,168,
                39,48,160};

    int temp = 0;



    // Color and difficulty variables
    int r, g, b;
    int difficulty = 40;

    int centerX, centerY, radius;
    public static float xPos = 0;
    public static float yPos = 0;

    Paint white = new Paint();
    Paint black = new Paint();
    Paint black2 = new Paint();

    public static boolean repeater = true;

    CountDownTimer countdown = new CountDownTimer(30000, 1000) {
        public void onTick(long millisUntilFinished) {
            timer = ("" + ((millisUntilFinished / 1000)));
            //here you can have your logic to set text to editted
        }

        public void onFinish() {
            gameMode = 3;
            timer = "30";
        }

    };


    public static int randNum(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private MainThread thread;
    public GameMode2(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
        public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try {
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }
        }

    @Override
        public void surfaceCreated(SurfaceHolder holder){

        newColor();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

         int action = event.getAction();
         if (action==MotionEvent.ACTION_DOWN) {
             xPos = event.getX();
             yPos = event.getY();
             //System.out.println(centerX);
             //System.out.println(centerY);
             // Main Menu
             if (gameMode == 1) {
                 if (xPos < (getWidth()/3 + getWidth()/6 - getWidth()/100) && xPos > (getWidth()/3 - (getWidth()/6 - getWidth()/100))) {

                     if (yPos < (getHeight()/3 + getWidth()/6 - getWidth()/100) && yPos > (getHeight()/3 - (getWidth()/6 - getWidth()/100))) {
                           gameMode = 2;
                     }
                 }
             }

            // Game
             if (gameMode == 2) {
                 if (xPos < (centerX + radius) && xPos > (centerX - radius)) {

                     if (yPos < (centerY + radius) && yPos > (centerY - radius)) {
                         if (level == 0) {
                             countdown.start();

                         }

                         nextLevel(MainThread.canvas, x, y);
                         //System.out.println(level);
                     }
                 }

             }

             // Overhead
             if (gameMode == 3) {
                 if (xPos < (getWidth()/3 + getWidth()/6 - getWidth()/100) && xPos > (getWidth()/3 - (getWidth()/6 - getWidth()/100))) {

                     if (yPos < (3*getHeight()/5 + getWidth()/6 - getWidth()/100) && yPos > (3*getHeight()/5 - (getWidth()/6 - getWidth()/100))) {
                         gameMode = 2;
                         level = -1;
                         difficulty = 40;
                         nextLevel(MainThread.canvas, 2, 3);
                     }
                 }
             }
         }
        return true;
    }


    public void update(){

        if(level <=3){
            x = 2;
            y = 3;
        }
        if(level > 3&& level<=7){
            x = 3;
            y = 4;
        }
        if(level > 7 && level<=11){
            x = 4;
            y = 5;
        }
        if(level > 11 && level<=16){
            x = 5;
            y = 7;
        }
        if(level > 16 && level<=21){
            x = 4;
            y = 5;
        }
        if(level > 21) {
            x = 3;
            y = 4;
        }
    }

    @Override
    public void draw(Canvas canvas) {

        black.setARGB(255, 0, 0, 0);
        black.setTextSize(150);

        black2.setARGB(255, 0, 0, 0);
        black2.setTextSize(125);

        //white.setShader(new LinearGradient(0, 0, 0, getHeight(), Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));;
        white.setARGB(255,255,255,255);

    }

    // GameMode 1 (menu)

    public void drawMenu(Canvas canvas) {
        level = -1;
        difficulty = 40;
        nextLevel(MainThread.canvas, 2, 3);

        Paint menuBackground = new Paint();
        menuBackground.setARGB(255, 136, 0, 21);

        Paint menuText = new Paint();
        menuText.setARGB(255, 237, 28, 36);

        Paint simMenuText = new Paint();
        simMenuText.setARGB(255, 200, 0, 50);

        canvas.drawPaint(white);

        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 2; j++) {
                canvas.drawCircle(i*getWidth()/3, (j-1)*getWidth()/3+getHeight()/3, getWidth()/6 - getWidth()/100, simMenuText);
            }
        }
        canvas.drawCircle(getWidth() / 3, getHeight() / 3, getWidth() / 6 - getWidth() / 100, menuText);

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        menuText.setTextAlign(Paint.Align.CENTER);
        menuText.setTextSize(getWidth() / 10);
        menuText.setTypeface(tf);
        canvas.drawText("Shade Sleuth", getWidth() / 2, 3 * getHeight() / 4, menuText);
        menuText.setTextSize(getWidth() / 14);
        //canvas.drawText("Stop playing our shit game", getWidth()/2, 17*getHeight()/20, menuText);

    }

    // GameMode 2 (game)

    public void nextLevel(Canvas canvas, int x, int y) {

         level++;
        // difficulty = 40*(1-((level/100)*10));
        if (level < 28) {
            difficulty = 40 -level;
        }
        else {
            difficulty = 12;
        }

         randX = randNum(0, x - 1);
         randY = randNum(0, y - 1);
         temp = randNum(0, reds.length - 1);
         newColor();
    }

    public void drawGrid(Canvas canvas, int x, int y, int randX, int randY) {

        canvas.drawPaint(white);

        int ii, jj;
        radius = ((getWidth()-(5*(x+1)))/x) / 2;
        int totalHeight = y * radius * 2;
        int correction = (getHeight() - totalHeight - (5*(y+1))) / 2;

        Paint paint = new Paint();

        Paint menuText = new Paint();
        menuText.setARGB(255, 237, 28, 36);
        menuText.setTextAlign(Paint.Align.CENTER);

        menuText.setTextSize(getWidth()/8);
        black2.setTextSize(getWidth()/12);

        Paint randColor = new Paint();

        paint.setARGB(255, colorRandR ,colorRandG , colorRandB );
        randColor.setARGB(255, colorRandR2, colorRandG2 , colorRandB2);

        for (ii = 0; ii < y; ii++) {

            for (jj = 0; jj < x; jj++)
            {
                if (ii == randY && jj == randX)
                {
                    canvas.drawCircle((radius * 2 * jj) + radius + (5*jj) + 5, (radius * 2 * ii) + radius + correction + (5*ii) + 5,  radius, randColor);
                    centerX = (radius * 2 * jj) + radius + (5*jj) + 5;
                    centerY = (radius * 2 * ii) + radius + correction + (5*ii) + 5;
                }
                else {
                    canvas.drawCircle((radius * 2 * jj) + radius + (5*jj) + 5, (radius * 2 * ii) + radius + correction + (5*ii) + 5, radius, paint);
                }

            }
        }
        if (level == 0) {
            menuText.setTextSize(getWidth()/12);
            canvas.drawText("Touch to Start", getWidth()/2, menuText.getTextSize(), menuText);
        }
        else {
            menuText.setTextSize(getWidth()/8);
            canvas.drawText(timer, getWidth()/2, menuText.getTextSize(), menuText);
        }
        canvas.drawText(Integer.toString(level), getWidth()/2, getHeight() - menuText.getTextSize()/2, menuText);
        //canvas.drawText("Stage: ", getWidth()/20, getHeight() - black2.getTextSize()/2, black2);


    }

    public void newColor(){



        colorRandR = reds[temp];

        colorRandG = greens[temp];

        colorRandB = blues[temp];

        colorRandR2 = colorRandR + difficulty;

        colorRandG2 = colorRandG + difficulty;

        colorRandB2 = colorRandB + difficulty;

    }


    // GameMode 3 (end menu)

    public void drawEndMenu(Canvas canvas) {

        int temp = level;
        if (level > high_score)
        {
            high_score = level;
        }

        Paint menuBackground = new Paint();
        menuBackground.setARGB(255, 255, 255, 255);

        Paint menuText = new Paint();
        menuText.setARGB(255, 237, 28, 36);
        menuText.setTextAlign(Paint.Align.CENTER);

        Paint simMenuText = new Paint();
        simMenuText.setARGB(255, 200, 0, 50);

        Paint whiteText = new Paint();
        whiteText.setARGB(255,255,255,255);


        canvas.drawPaint(white);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        canvas.drawCircle(getWidth()/3, 3*getHeight()/5, getWidth()/6 - getWidth()/100, menuText);
        canvas.drawCircle(2*getWidth()/3, 3*getHeight()/5, getWidth()/6 - getWidth()/100, simMenuText);
        canvas.drawCircle(getWidth()/3, 4*getHeight()/5 + getWidth()/100, getWidth()/6 - getWidth()/100, simMenuText);
        canvas.drawCircle(2*getWidth()/3, 4*getHeight()/5 + getWidth()/100, getWidth()/6 - getWidth()/100, simMenuText);

        menuText.setTextSize(getWidth() / 5);
        menuText.setTypeface(tf);
        canvas.drawText("Score: " + Integer.toString(temp), getWidth() / 2, getHeight() / 5, menuText);

        menuText.setTextSize(getWidth() / 8);
        canvas.drawText("High Score: " + Integer.toString(high_score), getWidth() / 2, 4 * getHeight() / 10, menuText);

        whiteText.setTextSize(getWidth() / 12);
        whiteText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Play", getWidth()/3 , (23*getHeight()/40),whiteText);
        canvas.drawText("Again", getWidth()/3 , (25*getHeight()/40),whiteText);




    }

}
