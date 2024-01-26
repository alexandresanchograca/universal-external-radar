package com.radar.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameRadarMain extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ShapeRenderer shape;
	private Texture arrowImage;
	private Rectangle arrow;
	final private int screenCenterX = 800 / 2 - 64 / 2;
	final private int screenCenterY = 480 / 2 - 64 / 2;
	final private float boxSize = 26.0f;

	/* Used to load all assets */
	@Override
	public void create() {
		shape = new ShapeRenderer();
		arrowImage = new Texture(Gdx.files.internal("arrow.png"));
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the arrow
		arrow = new Rectangle();
		arrow.x = screenCenterX;
		arrow.y = screenCenterY;
		arrow.width = 26;
		arrow.height = 26;
	}

	@Override
	public void render() {
		// clear the screen with a dark blue color.
		//r,g,b,a from 0 -> 1
		//ScreenUtils.clear(0, 0, 0.2f, 1);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.RED);
		shape.ellipse(screenCenterX + 50, screenCenterY, boxSize,boxSize);
		shape.end();

		batch.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.BLUE);
		shape.triangle(screenCenterX + 13, screenCenterY + 26,
				screenCenterX, screenCenterY,
				screenCenterX + 26, screenCenterY);
		shape.end();
	}

	@Override
	public void dispose() {
		//Executing cleanup when client exits
		batch.dispose();
	}
}
