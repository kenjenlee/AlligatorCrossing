package com.magicians.reflectorcrazy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import com.badlogic.gdx.math.Vector3;
//import com.magicians.reflectorcrazy.InputHandler.InputHandler;

import java.util.Random;
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
    private float velocity = 3;
   // private Random  randomNum;

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
        pillarLeft = new Texture(Gdx.files.internal(""));
        pillarRight = new Texture(Gdx.files.internal(""));
        background = new Texture(Gdx.files.internal(""));

        touchCoord = new Vector3();
        ballX = Gdx.graphics.getWidth()/2 - ball.getWidth()/2;
        ballY = Gdx.graphics.getHeight()/2 - ball.getHeight()/2;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();

        if(gameState==0 && Gdx.input.justTouched()){
            gameState = 1;
        }else if(gameState == 1){
            if(Gdx.input.justTouched()){

                touchCoord.set(Gdx.input.getX(), Gdx.input.getY(),0);
                camera.unproject(touchCoord);

                float touchX = touchCoord.x;

                if(touchX > Gdx.graphics.getWidth()/2){
                    //ball goes left
                    if(ballX > 0){ //ensure ball does not go off screen
                        ballX -= velocity * Gdx.graphics.getRawDeltaTime();
                    }

                }else{
                    //ball goes right
                    if(ballX < Gdx.graphics.getWidth()){
                        ballX += velocity * Gdx.graphics.getRawDeltaTime();
                    }
                }

                time += Gdx.graphics.getRawDeltaTime();
                if(time%7 == 0){
                    //Increase speed every 7 seconds
                    velocity += 2;
                }

            }
        }else{
            score = 0;
        }
        //GameManager.render(batch, touchCount);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
