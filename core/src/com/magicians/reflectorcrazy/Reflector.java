package com.magicians.reflectorcrazy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    private int score = 0;

    //shape Renderer
   // private ShapeRenderer shapeRenderer;
    private Rectangle[] rectLeft;
    private Rectangle[] rectRight;
    private Circle circleBall;

    // ball variables
    private float ballVelocity = 900;
    private float ballX, ballY;

    //pillar variables
    private float pillarVelocity = 3;
    private Random  randomNum;
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

    // Textures
    private Texture ball;
    private Texture pillarLeft;
    private Texture pillarRight;
  //  private Texture background;

    //Font
    private BitmapFont scoreFont;

    //Button
    private TextButton buttonRestart;
    private Skin skinRestart;
    private Stage gameOverStage;

	
	@Override
	public void create () {

		batch = new SpriteBatch();

		viewportHeight = Gdx.graphics.getHeight();
		viewportWidth = Gdx.graphics.getWidth();
        //set camera dimension to screen dimension
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
        touchCoord = new Vector3();

        //Texture variables
        ball = new Texture(Gdx.files.internal("badlogic.jpg"));
        pillarLeft = new Texture(Gdx.files.internal("pillarLeft.png"));
        pillarRight = new Texture(Gdx.files.internal("pillarRight.png"));
     //   background = new Texture(Gdx.files.internal(""));

        //ball variables
        ballX = Gdx.graphics.getWidth()/2 - ball.getWidth()/2;
        ballY = Gdx.graphics.getHeight()/3 - ball.getHeight()/2;

        //pillar variables
        pillarOffset = new float[pillarNum];
        pillarY = new float[pillarNum];
        pillarDist = Gdx.graphics.getHeight() / 4;
        maxPillarOffset = Gdx.graphics.getWidth()/2 + gap/2 - 1.5f*ball.getWidth();
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


        for(int i=0; i<pillarNum; i++){

            //ensure gap in rows next to each other is not too far away from each other
            int previous = i-1;
            if(i == 0){
                previous = pillarNum-1;
            }
            do{
                pillarOffset[i] = (randomNum.nextFloat()-0.5f)* maxPillarOffset * 2;
            }while(i>0 && abs(pillarOffset[i]-pillarOffset[previous])>maxConsecGapDist &&
                    abs(pillarOffset[i]-pillarOffset[previous])<minConsecGapDist);

            //first pillar start from just off screen to the top
            pillarY[i] = Gdx.graphics.getHeight() + i * pillarDist;

            //shapeRenderer shapes
            rectLeft[i] = new Rectangle();
            rectRight[i] = new Rectangle();
        }

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     //   batch.setProjectionMatrix(camera.combined);
		batch.begin();
        /**
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        **/

        if(gameState==0 && Gdx.input.justTouched()){
            //start game
            gameState = 1;
        }else if(gameState == 1){

            //Track ball movement
            if(Gdx.input.isTouched()){

                touchCoord.set(Gdx.input.getX(), Gdx.input.getY(),0);
               // camera.unproject(touchCoord);
              //  float touchX = touchCoord.x;

                if(Gdx.input.getX() < ballX+ball.getWidth()/2){
                    //ball goes left if user touches to the left of ball
                    if(ballX > 0){ //ensure ball does not go off screen
                        ballX -= ballVelocity;
                    }
                }else {
                    //ball goes right
                    if(ballX + ball.getWidth() < Gdx.graphics.getWidth()){
                        ballX += ballVelocity;
                    }
                }
            }

            //Scoring system
            if(pillarY[pillarScoring] < Gdx.graphics.getHeight()/3){
                score++;
                if(pillarScoring < pillarNum -1)
                    pillarScoring++;
                else
                    pillarScoring = 0;
            }

            for(int i=0; i<pillarNum; i++){
                pillarY[i] -= pillarVelocity;

                if(pillarY[i] <= -Gdx.graphics.getHeight()*percScreenWidth){
                    pillarY[i] = pillarNum * pillarDist;
                    //set offset to new random offset
                    int previous = i-1;
                    if(i == 0){
                         previous = pillarNum-1;
                    }
                    do{
                        pillarOffset[i] = (randomNum.nextFloat()-0.5f)* maxPillarOffset * 2;
                    }while(abs(pillarOffset[i]-pillarOffset[previous])>maxConsecGapDist &&
                            abs(pillarOffset[i]-pillarOffset[previous])<minConsecGapDist);
                }

                float pillarLeftLen = Gdx.graphics.getWidth()/2 - gap/2 + pillarOffset[i];
                float pillarRightLen = Gdx.graphics.getWidth()/2 - gap/2 - pillarOffset[i];

                batch.draw(pillarLeft, 0, pillarY[i],pillarLeftLen,
                        Gdx.graphics.getHeight()*percScreenWidth);
                batch.draw(pillarRight, Gdx.graphics.getWidth()/2+gap/2+pillarOffset[i],
                        pillarY[i], pillarRightLen,
                        Gdx.graphics.getHeight()*percScreenWidth);
                rectLeft[i] = new Rectangle(0,pillarY[i], pillarLeftLen,
                        Gdx.graphics.getHeight()*percScreenWidth);
                rectRight[i] = new Rectangle(Gdx.graphics.getWidth()/2+gap/2+pillarOffset[i],
                        pillarY[i], pillarRightLen, Gdx.graphics.getHeight()*percScreenWidth);
                /**
                shapeRenderer.rect(0,pillarY[i], pillarLeftLen,
                        (float)(Gdx.graphics.getHeight()*0.05));
                shapeRenderer.rect(Gdx.graphics.getWidth()/2+gap/2+pillarOffset[i],
                        pillarY[i], pillarRightLen, (float)(Gdx.graphics.getHeight()*0.05));
                 **/

                if(Intersector.overlaps(circleBall,rectLeft[i]) ||
                        Intersector.overlaps(circleBall,rectRight[i])){
                    Gdx.app.log("Collision","Yes");
                    gameState = 2;
                }

            }

            batch.draw(ball, ballX, ballY);
            circleBall.set(ballX+ball.getWidth()/2, ballY+ball.getHeight()/2, ball.getHeight()/2);
           // shapeRenderer.circle(circleBall.x, circleBall.y, circleBall.radius);
            scoreFont.draw(batch, String.valueOf(score), 100, 200);

            ballVelocity += 0.0005;
            pillarVelocity += 0.0005;

        }else{ //game over
            //display score and highest score
            //gameOverStage.act(Math.min(Gdx.graphics.getDeltaTime(),1/30f));
            //gameOverStage.draw();
            //Table.drawDebug(gameOverStage);



            //reset score etc.
            score = 0;
            pillarVelocity = 3;
            ballVelocity = 3;
            gameState = 0;
            pillarScoring = 0;
        }
        //GameManager.render(batch, touchCount);
		batch.end();
      //  shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
       // shapeRenderer.dispose();
        pillarLeft.dispose();
        pillarRight.dispose();
        ball.dispose();
        gameOverStage.dispose();
        skinRestart.dispose();

      //  background.dispose();

	}
}
