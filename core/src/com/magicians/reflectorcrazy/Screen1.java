package com.magicians.reflectorcrazy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * Created by karan on 2016-12-27.
 */

public class Screen1 implements Screen{

    private final Reflector app;
    private Stage stage;

    public Screen1 (final Reflector app){
        this.app=app;
        this.stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        //this.app.setScreen;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //render code goes here

        update(delta);

        stage.draw();

        //app.batch.begin();
        //app.font.draw(app.batch, "Splashscreen!", 120, 120);
        //app.batch.end();
    }

    public void update(float delta){
        stage.act(delta);
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

    }
}
