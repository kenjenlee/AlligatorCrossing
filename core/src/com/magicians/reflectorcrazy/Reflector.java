package com.magicians.reflectorcrazy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
//import com.magicians.reflectorcrazy.InputHandler.InputHandler;

import java.util.Random;

import static java.lang.Math.abs;
//import com.magicians.reflectorcrazy.GameManager.GameManager;



public class Reflector extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	//viewport height and width

	private float viewportWidth, viewportHeight;
    private int gameState = 0;
    private Vector3 touchCoord;
    private float ballX, ballY;
    private int score = 0;
    private float time = 0;
    private float ballVelocity = 3;
    private float pillarVelocity = 3;
    private Random  randomNum;
    private float[] pillarOffset;
    private float maxPillarOffset;
    private float[] pillarY;
    //pillarDist is distance between bottom left corner of
    //two consecutive pillars
    private float pillarDist;
    private int pillarNum = 5;
    //gap between pillars in same row
    private float gap = 100;

    private Texture ball;
    private Texture pillarLeft;
    private Texture pillarRight;
    private Texture background;
	
	@Override
	public void create () {

		batch = new SpriteBatch();

		viewportHeight = Gdx.graphics.getHeight();
		viewportWidth = Gdx.graphics.getWidth();
        //set camera dimension to screen dimension
		camera = new OrthographicCamera(viewportWidth, viewportHeight);

        ball = new Texture(Gdx.files.internal("badlogic.jpg"));
        pillarLeft = new Texture(Gdx.files.internal("pillarLeft.png"));
        pillarRight = new Texture(Gdx.files.internal("pillarRight.png"));
        background = new Texture(Gdx.files.internal(""));

        touchCoord = new Vector3();
        ballX = Gdx.graphics.getWidth()/2 - ball.getWidth()/2;
        ballY = Gdx.graphics.getHeight()/2 - ball.getHeight()/2;
        randomNum = new Random();
        pillarOffset = new float[pillarNum];
        pillarY = new float[pillarNum];
        pillarDist = Gdx.graphics.getHeight() / 5;
        maxPillarOffset = Gdx.graphics.getWidth() - gap/2 - 100;

        for(int i=0; i<pillarNum; i++){

            //ensure gap in rows next to each other is not too far away from each other
            do{
                pillarOffset[i] = (randomNum.nextFloat()-0.5f)* maxPillarOffset * 2;
            }while(i>0 && abs(pillarOffset[i]-pillarOffset[i-1])>500);

            //first pillar start from just off screen to the top
            pillarY[i] = Gdx.graphics.getHeight() + i * pillarDist;
        }

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();

        if(gameState==0 && Gdx.input.justTouched()){
            //start game
            gameState = 1;
        }else if(gameState == 1){
            if(Gdx.input.justTouched()){

                touchCoord.set(Gdx.input.getX(), Gdx.input.getY(),0);
                camera.unproject(touchCoord);

                float touchX = touchCoord.x;

                if(touchX > Gdx.graphics.getWidth()/2){
                    //ball goes left
                    if(ballX > 0){ //ensure ball does not go off screen
                        ballX -= ballVelocity * Gdx.graphics.getRawDeltaTime();
                    }

                }else{
                    //ball goes right
                    if(ballX < Gdx.graphics.getWidth()){
                        ballX += ballVelocity * Gdx.graphics.getRawDeltaTime();
                    }
                }
            }

            for(int i=0; i<pillarNum; i++){
                pillarY[i] -= pillarVelocity * Gdx.graphics.getRawDeltaTime();

                if(pillarY[i] <= -pillarLeft.getHeight()){
                    pillarY[i] = pillarNum * pillarDist;
                    //set offset to new random offset
                    pillarOffset[i] = (randomNum.nextFloat() - 0.5f) * 2 * maxPillarOffset;
                }

                batch.draw(pillarLeft,
                        Gdx.graphics.getWidth()/2-gap/2+pillarOffset[i]-pillarLeft.getWidth(),
                        pillarY[i]);
                batch.draw(pillarRight, Gdx.graphics.getWidth()/2+gap/2+pillarOffset[i],
                        pillarY[i]);
            }

            batch.draw(ball, ballX, ballY);

            time += Gdx.graphics.getRawDeltaTime();
            if(time%7 == 0){
                //Increase speed every 7 seconds
                ballVelocity += 2;
                pillarVelocity += 2;
            }



        }else{ //game over
            //display score and highest score




            //reset score etc.
            score = 0;
            pillarVelocity = 3;
            ballVelocity = 3;
            gameState = 0;
        }
        //GameManager.render(batch, touchCount);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        pillarLeft.dispose();
        pillarRight.dispose();
        ball.dispose();
        background.dispose();
	}
}
