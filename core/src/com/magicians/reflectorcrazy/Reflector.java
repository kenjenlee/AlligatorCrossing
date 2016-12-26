package com.magicians.reflectorcrazy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Reflector extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	//viewport height and width
	private float viewportWidth, viewportHeight;

    private Texture ball;
    private Texture wallDefault;
    private Texture wallTouched;
    private Texture background;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		viewportHeight = Gdx.graphics.getHeight();
		viewportWidth = Gdx.graphics.getWidth();
        //set camera dimension to screen dimension
		camera = new OrthographicCamera(viewportWidth, viewportHeight);

        ball = new Texture(Gdx.files.internal(""));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
