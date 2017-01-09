package com.magicians.reflectorcrazy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.magicians.reflectorcrazy.Reflector;
import com.magicians.reflectorcrazy.Screen1;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Jen on 2016-12-30.
 */

public class GameScreen implements Screen {

    private Reflector app;

    private int gameState = 0;
    //private Vector3 touchCood;


    //shape Renderer
    // private ShapeRenderer shapeRenderer;
    private Rectangle[] rectLeft;
    private Rectangle[] rectRight;
    private Circle circleBall;

    // ball variables
    private float ballVelocity;
    private float ballX, ballY;

    //pillar variables
    private float pillarVelocity = 10;
    private Random randomNum;
    private float[] pillarOffset;
    private float maxPillarOffset;
    private float[] pillarY;
    //pillarDist is distance between bottom left corner of
    //two consecutive pillars
    private float pillarDist;
    private int pillarNum = 7;
    //gap between pillars in same row
    private float gap;
    private int pillarScoring = 0;
    private int maxConsecGapDist = 700;
    private int minConsecGapDist = 200;
    private float percScreenWidth = 0.05f;

   // private Vector3 touchCoord;
    // Textures
    private Texture ball;
    private Texture pillarLeft;
    private Texture pillarRight;
    //  private Texture background;

    //Font
    public BitmapFont scoreFont;

    //Button
    private TextButton buttonRestart;
    private Skin skinRestart;
    //  private Stage gameOverStage;

    public GameScreen(Reflector app){
        this.app = app;

    }

    @Override
    public void show() {

        System.out.println("Game Screen");

        //Texture variables
        ball = app.assets.get("Img/badlogic.jpg", Texture.class);
        pillarLeft = app.assets.get("Img/pillarLeft.png", Texture.class);
        pillarRight = app.assets.get("Img/pillarRight.png", Texture.class);
        //   background = new Texture(Gdx.files.internal(""));

        //ball variables
        ballX = Gdx.graphics.getWidth() / 2 - ball.getWidth() / 2;
        ballY = Gdx.graphics.getHeight() / 3 - ball.getHeight() / 2;

        //pillar variables
        pillarOffset = new float[pillarNum];
        pillarY = new float[pillarNum];
        pillarDist = Gdx.graphics.getHeight() / 4;
        maxPillarOffset = Gdx.graphics.getWidth() / 2 + gap / 2 - 1.5f * ball.getWidth();
        gap = ball.getWidth() * 1.5f;
        randomNum = new Random();

        //shapeRenderer variables
        //shapeRenderer = new ShapeRenderer();
        rectLeft = new Rectangle[pillarNum];
        rectRight = new Rectangle[pillarNum];
        circleBall = new Circle();

        //Font
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.BLACK);
        scoreFont.getData().setScale(10);

        app.score = 0;
        ballVelocity = 10;

       // gameState = 0;
       // touchCood = new Vector3();
        //Button
        /**
         skinRestart = new Skin();
         buttonRestart = new TextButton("Restart", skinRestart);
         buttonRestart.setColor(Color.BLACK);
         buttonRestart.setPosition(200,200);
         gameOverStage = new Stage();
         gameOverStage.addActor(buttonRestart);
         gameOverStage.addActor(buttonRestart);
         **/


        for (int i = 0; i < pillarNum; i++) {

            //ensure gap in rows next to each other is not too far away from each other
            int previous = i - 1;
            if (i == 0) {
                previous = pillarNum - 1;
            }
            do {
                pillarOffset[i] = (randomNum.nextFloat() - 0.5f) * maxPillarOffset * 2;
            } while (i > 0 && abs(pillarOffset[i] - pillarOffset[previous]) > maxConsecGapDist &&
                    abs(pillarOffset[i] - pillarOffset[previous]) < minConsecGapDist);

            //first pillar start from just off screen to the top
            pillarY[i] = Gdx.graphics.getHeight() + i * pillarDist;

            //shapeRenderer shapes
            rectLeft[i] = new Rectangle();
            rectRight[i] = new Rectangle();
        }

        System.out.println("Game Screen");

    }

    @Override
    public void render(float delta) {

        app.batch.begin();
        /**
         shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
         shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
         **/
        if (gameState == 0 ) {
            if(Gdx.input.justTouched()){
                //start game
                gameState = 1;
                System.out.println("Touching!");
            }else{
                scoreFont.draw(app.batch, "Tap to start", app.viewportWidth/6, app.viewportHeight*2/3);
            }

        } //else if(gameState==0 ){


         else if (gameState == 1) {

            //Track ball movement
            if (Gdx.input.isTouched()) {

                // touchCood.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                // camera.unproject(touchCoord);
                //  float touchX = touchCoord.x;

                if (Gdx.input.getX() < ballX + ball.getWidth() / 2) {
                    //ball goes left if user touches to the left of ball

                    Gdx.app.log("ball velocity", String.valueOf(ballVelocity));
                    if (ballX > 0) { //ensure ball does not go off screen
                        ballX -= ballVelocity;
                    }
                } else {
                    //ball goes right
                    if (ballX + ball.getWidth() < Gdx.graphics.getWidth()) {
                        ballX += ballVelocity;
                    }
                }

            }

            //Scoring system
            if (pillarY[pillarScoring] < Gdx.graphics.getHeight() / 3) {
                app.score++;
                if (pillarScoring < pillarNum - 1)
                    pillarScoring++;
                else
                    pillarScoring = 0;
            }

            for (int i = 0; i < pillarNum; i++) {
                pillarY[i] -= pillarVelocity;

                if (pillarY[i] <= -Gdx.graphics.getHeight() * percScreenWidth) {
                    pillarY[i] = pillarNum * pillarDist;
                    //set offset to new random offset
                    int previous = i - 1;
                    if (i == 0) {
                        previous = pillarNum - 1;
                    }
                    do {
                        pillarOffset[i] = (randomNum.nextFloat() - 0.5f) * maxPillarOffset * 2;
                    } while (abs(pillarOffset[i] - pillarOffset[previous]) > maxConsecGapDist &&
                            abs(pillarOffset[i] - pillarOffset[previous]) < minConsecGapDist);
                }

                float pillarLeftLen = Gdx.graphics.getWidth() / 2 - gap / 2 + pillarOffset[i];
                float pillarRightLen = Gdx.graphics.getWidth() / 2 - gap / 2 - pillarOffset[i];

                app.batch.draw(pillarLeft, 0, pillarY[i], pillarLeftLen,
                        Gdx.graphics.getHeight() * percScreenWidth);
                app.batch.draw(pillarRight, Gdx.graphics.getWidth() / 2 + gap / 2 + pillarOffset[i],
                        pillarY[i], pillarRightLen,
                        Gdx.graphics.getHeight() * percScreenWidth);
                rectLeft[i] = new Rectangle(0, pillarY[i], pillarLeftLen,
                        Gdx.graphics.getHeight() * percScreenWidth);
                rectRight[i] = new Rectangle(Gdx.graphics.getWidth() / 2 + gap / 2 + pillarOffset[i],
                        pillarY[i], pillarRightLen, Gdx.graphics.getHeight() * percScreenWidth);
                /**
                 shapeRenderer.rect(0,pillarY[i], pillarLeftLen,
                 (float)(Gdx.graphics.getHeight()*0.05));
                 shapeRenderer.rect(Gdx.graphics.getWidth()/2+gap/2+pillarOffset[i],
                 pillarY[i], pillarRightLen, (float)(Gdx.graphics.getHeight()*0.05));
                 **/

                if (Intersector.overlaps(circleBall, rectLeft[i]) ||
                        Intersector.overlaps(circleBall, rectRight[i])) {
                    Gdx.app.log("Collision", "Yes");
                    gameState = 2;
                }

            }

            app.batch.draw(ball, ballX, ballY);
            circleBall.set(ballX + ball.getWidth() / 2, ballY + ball.getHeight() / 2, ball.getHeight() / 2);
            // shapeRenderer.circle(circleBall.x, circleBall.y, circleBall.radius);
            scoreFont.draw(app.batch, String.valueOf(app.score), 100, 200);

            ballVelocity += 0.0005;
            pillarVelocity += 0.0005;

        } else if(gameState == 2) { //game over
            System.out.println("I am in here!");

            app.overScreen.finalScore = app.score;
            //reset score etc.
            app.score = 0;
            pillarVelocity = 10;
            ballVelocity = 10;
            gameState = 0;
            pillarScoring = 0;
            app.setScreen(app.overScreen);
             //display score and highest score
             //gameOverStage.act(Math.min(Gdx.graphics.getDeltaTime(),1/30f));
             //gameOverStage.draw();
             //Table.drawDebug(gameOverStage);
        }

        app.batch.end();

        //  shapeRenderer.end();
    }

    private void over(){

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        pillarLeft.dispose();
        pillarRight.dispose();
        ball.dispose();
        //gameOverStage.dispose();
        skinRestart.dispose();
    }
}
