package com.magicians.reflectorcrazy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import com.magicians.reflectorcrazy.InputHandler.InputHandler;
import java.util.Random;
import static java.lang.Math.abs;
import com.magicians.reflectorcrazy.GameManager.GameManager;
import com.magicians.reflectorcrazy.Screen1;
import com.badlogic.gdx.Game;
import com.magicians.reflectorcrazy.Screens.GameScreen;
import com.magicians.reflectorcrazy.Screens.LoadingScreen;
import com.magicians.reflectorcrazy.Screens.OverScreen;


public class Reflector extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;
    //viewport height and width
    public float viewportWidth, viewportHeight;
    public GameScreen gameScreen;
    public OverScreen overScreen;
    public LoadingScreen loadingScreen;
    public AssetManager assets;


    public BitmapFont font;


    @Override
    public void create() {

        batch = new SpriteBatch();

        viewportHeight = Gdx.graphics.getHeight();
        viewportWidth = Gdx.graphics.getWidth();
        //set camera dimension to screen dimension
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        gameScreen = new GameScreen(this);
        overScreen = new OverScreen(this);
        loadingScreen = new LoadingScreen(this);
        assets = new AssetManager();

        this.setScreen(loadingScreen);

        //touchCoord = new Vector3();


        font= new BitmapFont();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //   batch.setProjectionMatrix(camera.combined);

        super.render();




    }

    @Override
    public void dispose() {
        batch.dispose();
        loadingScreen.dispose();
        gameScreen.dispose();
        //overScreen.dispose();
        font.dispose();
        assets.dispose();

        // shapeRenderer.dispose();


        //  background.dispose();

    }
}