package com.magicians.reflectorcrazy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.magicians.reflectorcrazy.Reflector;
import com.sun.org.apache.xml.internal.dtm.ref.dom2dtm.DOM2DTM;

/**
 * Created by Jen on 2016-12-30.
 */

public class OverScreen implements Screen {

    private Reflector app;
    private GameScreen gameScreen;
    private Stage stage;
    private Skin skin;
    private ShapeRenderer shapeRenderer;
    private TextButton buttonPlay, buttonQuit, buttonMainMenu;
    private BitmapFont fontButton, fontScore;
    public int finalScore = 0;

    private Label label;
    private Table table;

    public OverScreen(Reflector app){
        this.app = app;
        this.stage = new Stage(new StretchViewport(app.viewportWidth, app.viewportHeight,
                app.camera));
        this.shapeRenderer = new ShapeRenderer();
        this.gameScreen = new GameScreen(this.app);
        this.fontButton = new BitmapFont();
        this.fontScore = new BitmapFont();


        this.table = new Table();
        this.table.setFillParent(true);

    }

    @Override
    public void show() {

        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        System.out.println("Over screen!");
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("UI/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", fontButton );
        this.skin.load(Gdx.files.internal("UI/uiskin.json"));


        initButton();
        fontScore.getData().setScale(8,8);

        label = new Label("Score: " +String.valueOf(finalScore),
                new Label.LabelStyle(fontScore, Color.BLACK));
        label.setSize(app.viewportWidth/2,app.viewportHeight/10);
        label.setPosition(app.viewportWidth/2, app.viewportHeight/6);
        System.out.println(finalScore);

        table.top().padTop(app.viewportHeight/6);
        table.add(label);//.padTop(app.viewportHeight/6);//.padLeft(app.viewportWidth/4).padTop(app.viewportHeight/4);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private void initButton(){
        fontButton.getData().setScale(7.5f,7.5f);

        buttonPlay = new TextButton("Restart", skin, "default");
        buttonPlay.setPosition(app.viewportWidth/4, app.viewportHeight*3/5);
        buttonPlay.setSize(app.viewportWidth/2, app.viewportHeight/10);
        buttonPlay.addListener(new ClickListener() {

            @Override

            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                table.clear();
                app.setScreen(app.gameScreen);

            }
        });
        stage.addActor(buttonPlay);

        buttonQuit = new TextButton("Quit", skin, "default");
        buttonQuit.setPosition(app.viewportWidth/4, app.viewportHeight/4.5f);
        buttonQuit.setSize(app.viewportWidth/2, app.viewportHeight/10);
        buttonQuit.addListener(new ClickListener() {

            @Override

            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);

            }
        });
        stage.addActor(buttonQuit);

        buttonMainMenu = new TextButton("Main\nScreen", skin, "default");
        buttonMainMenu.setPosition(app.viewportWidth/4, app.viewportHeight*2/5);
        buttonMainMenu.setSize(app.viewportWidth/2, app.viewportHeight/7);
        buttonMainMenu.addListener(new ClickListener() {

            @Override

            public void clicked(InputEvent event, float x, float y) {

                app.setScreen(app.startScreen);

            }
        });
        stage.addActor(buttonMainMenu);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        /**
        app.batch.begin();
        System.out.println(app.score);
        fontButton.draw(app.batch, String.valueOf(finalScore), 100, 200);
        app.batch.end();
         **/

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
        stage.dispose();
        skin.dispose();
        fontButton.dispose();


    }
}
