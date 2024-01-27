package com.radar.game.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.radar.game.AppSettings;
import com.radar.game.GameRadar;
import com.radar.game.controllers.KeyboardController;
import com.radar.game.models.RadarModel;

public class Radar2DView implements Screen {
    final private int screenCenterX = 800 / 2 - 64 / 2;
    final private int screenCenterY = 480 / 2 - 64 / 2;
    private float boxSize = 24.0f;
    private OrthographicCamera camera;
    private ShapeRenderer shape;
    private final GameRadar game;
    private RadarModel model;
    private float radarScale;
    private KeyboardController controller;


    public Radar2DView(GameRadar parent, RadarModel model){
        this.game = parent;
        this.model = model;
        this.shape = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.controller = new KeyboardController();
    }

    @Override
    public void show() {
        this.camera.setToOrtho(false, 800, 480);
        this.radarScale = game.getOptions().getRadarScale();
        this.boxSize = game.getOptions().getDotSize();
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color.
        //r,g,b,a from 0 -> 1
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        //Represent our local player
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.identity();
        shape.translate(screenCenterX, screenCenterY, 0);
        shape.rotate(0, 0, 1, 0);
        shape.setColor(0, 1, 0, 1);
        shape.triangle(0f+13f, 0f+26f, 0, 0f, 0f+26,0f);
        shape.setColor(0, 0, 0, 0);
        shape.triangle(0f+13f, 0f+9f, 0, 0f, 0f+26,0f);
        shape.end();

        //Drawing players on screen
        float[][] playersScreenPos = model.getPlayersScreenPos(radarScale, screenCenterX, screenCenterY);
        for(int i = 0; i < playersScreenPos.length; i++){
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.identity();
            shape.translate(playersScreenPos[i][0], playersScreenPos[i][1], 0);
            shape.setColor(Color.RED);
            shape.ellipse(0, 0, boxSize,boxSize);
            shape.end();
        }

        if(controller.isEscKey()){
            controller.keyUp(Input.Keys.ESCAPE);
            game.changeView(GameRadar.Component.MENU);
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
        shape.dispose();
    }
}
