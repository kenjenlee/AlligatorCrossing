package com.magicians.reflectorcrazy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.magicians.reflectorcrazy.HighscoreData.SaveFile;
import com.magicians.reflectorcrazy.Screens.GameScreen;
import com.magicians.reflectorcrazy.Screens.HighscoreScreen;
import com.magicians.reflectorcrazy.Screens.LoadingScreen;
import com.magicians.reflectorcrazy.Screens.OverScreen;
import com.magicians.reflectorcrazy.Screens.StartScreen;


public class Reflector extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;
    //viewport height and width
    public float viewportWidth, viewportHeight;
    public GameScreen gameScreen;
    public OverScreen overScreen;
    public LoadingScreen loadingScreen;
    public StartScreen startScreen;
    public HighscoreScreen highscoreScreen;
    public SaveFile saveFile;
    public AssetManager assets;

    public int score = 0;
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
        startScreen = new StartScreen(this);
        highscoreScreen = new HighscoreScreen(this);
        saveFile = new SaveFile();
        saveFile.save();
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