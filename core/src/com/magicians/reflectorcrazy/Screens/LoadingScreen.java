package com.magicians.reflectorcrazy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.magicians.reflectorcrazy.Reflector;

import static com.badlogic.gdx.graphics.Color.*;

/**
 * Created by Jen on 2016-12-30.
 */

public class LoadingScreen implements Screen {

    private final Reflector app;
    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(Reflector app) {
        this.app= app;
        this.shapeRenderer = new ShapeRenderer();
    }

    private void queueAssets(){
        app.assets.load("Img/badlogic.jpg", Texture.class);
        app.assets.load("Img/pillarLeft.png", Texture.class);
        app.assets.load("Img/pillarRight.png", Texture.class);
        app.assets.load("UI/uiskin.atlas", TextureAtlas.class);
        app.assets.load("Img/sunset.jpg", Texture.class);
      //  app.assets.load();
      //  app.assets.load();

    }

    @Override
    public void show() {
        System.out.println("Loading Screen");

        this.progress = 0f;

        queueAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(app.viewportWidth/4, app.camera.viewportHeight/2, app.viewportWidth/2, app.viewportHeight/12 );
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(app.viewportWidth/4+3, app.camera.viewportHeight/2 + 3, (app.viewportWidth/2)*progress -3, app.viewportHeight/12 - 6);
        shapeRenderer.end();

    }

    private void update(float delta){

        progress = MathUtils.lerp(progress, 1, 0.1f);
        System.out.println(progress);

        if(progress >= 0.99f && app.assets.update()){
            //app.setScreen(app.gameScreen);
            app.setScreen(app.startScreen);
        }
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
        shapeRenderer.dispose();
    }
}
