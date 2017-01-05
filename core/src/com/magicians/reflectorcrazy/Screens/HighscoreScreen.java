package com.magicians.reflectorcrazy.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.magicians.reflectorcrazy.HighscoreData.SaveFile;
import com.magicians.reflectorcrazy.Reflector;
import com.magicians.reflectorcrazy.ScoreData;

import static com.badlogic.gdx.utils.compression.CRC.Table;

/**
 * Created by Jen on 2017-01-04.
 */

public class HighscoreScreen implements Screen {

    private Reflector app;
    private ScoreData sd;
    private SaveFile file;
    private Label label;
    private com.badlogic.gdx.scenes.scene2d.ui.Table table;
    private BitmapFont font;
    private Stage stage;

    HighscoreScreen(Reflector app){
        this.app = app;
        sd = new ScoreData();
        file = new SaveFile(sd);
        table = new Table();
        font = new BitmapFont();
        stage = new Stage();
        table.top().padTop(50);
    }

    @Override
    public void show() {
        if(SaveFile.load()){
            for(int i=0; i<sd.MAXINDEX; i++){

                int score = sd.highScores[i];
                label = new Label(String.valueOf(i+1)+String.valueOf(score),
                        new Label.LabelStyle(font, Color.BLACK));
                label.setSize(app.viewportWidth/2,app.viewportHeight/10);
                label.setPosition(app.viewportWidth/2, app.viewportHeight/6);
                table.add(label).expandY();
            }

            stage.addActor(table);

        }
    }

    @Override
    public void render(float delta) {
        stage.draw();
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
