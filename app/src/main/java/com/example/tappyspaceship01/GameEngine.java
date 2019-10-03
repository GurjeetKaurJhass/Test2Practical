package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="DINO-RAINBOWS";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;

    Player player;
    Item item1;
    Item item2;

    int lives=0;
    int score=0;

    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    // represent the TOP LEFT CORNER OF THE GRAPHIC

    // ----------------------------
    // ## GAME STATS
    // ----------------------------


    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();


        this.player = new Player(getContext(), 1500,500 );
        this.item1=new Item(getContext(),250,40);
        this.item2=new Item(getContext(),500,40);


    }



    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    public void updatePositions() {


//        this.item2.setxPosition(this.item2.getxPosition() + 25);
        if(item1.getxPosition()<=this.screenWidth) {
            this.item1.setxPosition(this.item1.getxPosition() + 25);
        }
        else
        {
            this.item1.setxPosition(250);


        }


        if(item2.getxPosition()<=this.screenWidth) {
            this.item2.setxPosition(this.item2.getxPosition() + 25);
        }
        else
        {
            this.item2.setxPosition(500);

        }



//        if(item2.getxPosition()<=this.screenWidth) {
//
//        }


        if (this.fingerAction == "Tophalf") {
            // if mousedown, then move player up
            // Make the UP movement > than down movement - this will
            // make it look like the player is moving up alot


            player.setyPosition(200);
            player.setxPosition(1500);
           // player.updateHitbox();
        }
        else if (this.fingerAction == "Bottomhalf") {
            // if mouseup, then move player down
            player.setyPosition(400);
            player.setxPosition(1500);
           // player.updateHitbox(1500);
        }




    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------


            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            canvas.drawBitmap(player.getImage(), player.getxPosition(), player.getyPosition(), paintbrush);
            canvas.drawBitmap(item1.getImage(),item1.getxPosition(),item1.getyPosition(),paintbrush);
            canvas.drawBitmap(item2.getImage2(),item2.getxPosition(),item2.getyPosition(),paintbrush);
            canvas.drawBitmap(item1.getImage(),item1.getxPosition(),item1.getyPosition()+380,paintbrush);
            canvas.drawBitmap(item2.getImage2(),item2.getxPosition(),item2.getyPosition()+380,paintbrush);
            paintbrush.setColor(Color.GREEN);


            canvas.drawRect(25,300,1200,220,paintbrush);
            //canvas.drawRect(25,500,1200,400,paintbrush);
            canvas.drawRect(25,700,1200,600,paintbrush);
            canvas.drawRect(25,1100,1200,1000,paintbrush);
//            canvas.drawRect(25,250,1200,220,paintbrush);

            paintbrush.setColor(Color.BLACK);
            paintbrush.setTextSize(60);
            canvas.drawText("Lives remaining: " + lives,
                    1100,
                    800,
                    paintbrush
            );

            paintbrush.setTextSize(60);
            canvas.drawText("Lives remaining: " + score,
                    500,
                    800,
                    paintbrush
            );




            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN)
        {

                fingerAction = "Tophalf";
            } else if (userAction == MotionEvent.ACTION_UP)
            {
                fingerAction="Bottomhalf";
            }



            return true;
        }
    }
