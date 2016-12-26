package com.magicians.reflectorcrazy.GameManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jen on 2016-12-25.
 */

public class GameManager {

    private int velocity = 3;


    public static void render(SpriteBatch batch,int touchCount){

        // First time touch, used to start game
        if(Gdx.input.isTouched() && touchCount==0){
            touchCount ++;
        }else if(touchCount != 0){

        }
    }
}
