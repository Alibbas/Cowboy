package com.ngdroidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import istanbul.gamelab.ngdroid.base.BaseCanvas;
import istanbul.gamelab.ngdroid.util.Log;
import istanbul.gamelab.ngdroid.util.Utils;


/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public class GameCanvas extends BaseCanvas {

    private Bitmap tileset, spritesheet;
    private Rect tilesource, tiledestination, spritesource, spritedestination;

    private int tilesrcw, tilesrch, tiledstw, tiledsth;
    private int tilesrcx, tilesrcy, tiledstx, tiledsty;

    private int spritesrcw, spritesrch, spritedstw, spritedsth;
    private int spritesrcx, spritesrcy, spritedstx, spritedsty;

    private int animationtypes = 4;
    private int framenum, animationtype, animationfirstframenum[], animationlastframenum[];

    private int spritevx, spritevy, spriteix, spriteiy;

    private  int touchdownx, touchdowny;

    private int yon;

    public GameCanvas(NgApp ngApp) {
        super(ngApp);
    }

    public void setup() {
        Log.i(TAG, "setup");
        tileset = Utils.loadImage(root, "tilea2.png");
        tilesource = new Rect();
        tiledestination = new Rect();

        spritesheet = Utils.loadImage(root, "cowboy.png");
        spritesource = new Rect();
        spritedestination = new Rect();

        tilesrcw = 64;
        tilesrch = 64;
        tilesrcx = 0;
        tilesrcy = 0;

        tiledstw = 128;
        tiledsth = 128;
        tiledstx = 0;
        tiledsty = 0;

        spritesrcw = 128;
        spritesrch = 128;
        spritesrcx = 0;
        spritesrcy = 0;

        spritedstw = 256;
        spritedsth = 256;
        spritedstx = 0;
        spritedsty = 0;

        framenum = 0;
        animationtype = 1;

        animationfirstframenum = new int[animationtypes];
        animationlastframenum = new int[animationtypes];

        animationfirstframenum[0] = 0;
        animationlastframenum[0] = 0;

        animationfirstframenum[1] = 1;
        animationlastframenum[1] = 8;

        animationfirstframenum[2] = 9;
        animationlastframenum[2] = 11;

        animationfirstframenum[3] = 12;
        animationlastframenum[3] = 13;

        spritevx = spritedstw / 8;
        spritevy = spritedsth / 8;

        spriteix = 1;
        spriteiy = 0;

        yon = 3;
    }

    public void update() {
        Log.i(TAG, "update");
        Log.i("debug", "animationtype:" + animationtype);
        framenum++;

        if(framenum > animationlastframenum[animationtype]){
            framenum = animationfirstframenum[animationtype];
        }

        spritedstx += spritevx * spriteix;
        spritedsty += spritevy * spriteiy;

        if(spritedstx >= getWidth() - spritedstw){
            spritedstx = getWidth() - spritedstw;
            animationtype = 0;
        } else if(spritedstx < 0){
            spritedstx = 0;
            animationtype = 0;
        } else if(spritedsty >= getHeight() - spritedsth){
            spritedsty = getHeight() - spritedsth;
            animationtype = 0;
        } else if(spritedsty < 0){
            spritedsty = 0;
            animationtype = 0;
        }

        spritesrcx = framenum * spritesrcw;
        spritesrcy = yon * spritesrch;
    }

    public void draw(Canvas canvas) {
        Log.i(TAG, "draw");

        for(int i = 0; i < getWidth(); i += tiledstw){
            for (int j = 0; j < getHeight(); j += tiledsth){
                tilesource.set(tilesrcx, tilesrcy, tilesrcx + tilesrcw, tilesrcy + tilesrch);

                tiledestination.set(i, j, i + tiledstw, j + tiledsth);

                canvas.drawBitmap(tileset, tilesource, tiledestination, null);
            }
        }
        spritesource.set(spritesrcx, spritesrcy, spritesrcx + spritesrcw, spritesrcy + spritesrch);
        spritedestination.set(spritedstx, spritedsty, spritedstx + spritedstw, spritedsty + spritedsth);
        canvas.drawBitmap(spritesheet, spritesource, spritedestination, null);
    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {

    }

    public boolean backPressed() {
        return true;
    }

    public void surfaceChanged(int width, int height) {

    }

    public void surfaceCreated() {

    }

    public void surfaceDestroyed() {

    }

    public void touchDown(int x, int y, int id) {
        touchdownx = x;
        touchdowny = y;
    }

    public void touchMove(int x, int y, int id) {
    }

    public void touchUp(int x, int y, int id) {
        int xfarki = x - touchdownx;
        int yfarki = y - touchdowny;
        animationtype = 1;
        if (Math.abs(xfarki) >= Math.abs(yfarki)){
            spriteix = 1;
            yon = 3;
            if (xfarki < 0){
                spriteix = -1;
                yon = 7;
            }
            spriteiy = 0;
        }
        else{
            spriteiy = 1;
            yon = 9;
            if (yfarki < 0){
                spriteiy = -1;
                yon = 5;
            }
            spriteix = 0;
        }
    }


    public void pause() {

    }


    public void resume() {

    }


    public void reloadTextures() {

    }


    public void showNotify() {
    }

    public void hideNotify() {
    }

}
