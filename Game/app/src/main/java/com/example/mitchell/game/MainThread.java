package com.example.mitchell.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Mitchell on 3/27/2015.
 */
public class MainThread extends Thread {

    private int FPS = 60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameMode2 gameMode2;
    private boolean running;
    public static Canvas canvas;


    public MainThread(SurfaceHolder surfaceHolder, GameMode2 gameMode2)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameMode2 = gameMode2;

    }

    @Override
    public void run()
    {
        long startTime;
        long timeMills;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;




        while(running) {


          // int randX = gamePanel.randNum(0, x);
          // int randY = gamePanel.randNum(0, y);
            this.gameMode2.draw(canvas);

           startTime = System.nanoTime();
           canvas = null;
           try{
               canvas = this.surfaceHolder.lockCanvas();
               synchronized (surfaceHolder) {
                   if (gameMode2.gameMode == 1) {
                       this.gameMode2.drawMenu(canvas);
                   }
                   if (gameMode2.gameMode == 2) {
                       this.gameMode2.update();
                       this.gameMode2.drawGrid(canvas, gameMode2.x, gameMode2.y, gameMode2.randX, gameMode2.randY);
                   }
                   if (gameMode2.gameMode == 3){
                       this.gameMode2.drawEndMenu(canvas);
                   }

                   //this.gamePanel.nextLevel(canvas, gamePanel.x, gamePanel.y);

               }
           }catch(Exception e){}
           finally{
             if(canvas!=null) {

                 try{
                    surfaceHolder.unlockCanvasAndPost(canvas);
                 }catch(Exception e){e.printStackTrace();}

             }

           }



           timeMills = (System.nanoTime()-startTime)/1000000;
           waitTime = targetTime - timeMills;

           try{
               this.sleep(waitTime);
           }catch(Exception e){}

           totalTime += System.nanoTime() - startTime;
           frameCount++;

           if(frameCount == FPS)
           {
               averageFPS = 1000/((totalTime/frameCount)/1000000);
               frameCount = 0;
               totalTime = 0;
               //System.out.println(averageFPS);
           }
       }

    }
    public void setRunning(boolean b){
        running=b;
    }


}
