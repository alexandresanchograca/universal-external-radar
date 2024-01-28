package com.radar.game.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.radar.game.GameRadar;
import com.radar.game.controllers.KeyboardController;
import com.radar.game.models.Radar3DApp;
import com.radar.game.models.RadarModel;

public class Radar3DView implements Screen {
    final private int screenWidth = 800;
    final private int screenHeight = 480;
    final private int screenCenterX = screenWidth / 2 - 64 / 2;
    final private int screenCenterY = screenHeight / 2 - 64 / 2;
    private OrthographicCamera camera;
    private ShapeRenderer shape;
    private final GameRadar game;
    private Radar3DApp model;
    private KeyboardController controller;

    public Radar3DView(GameRadar game, Radar3DApp model){
        this.game = game;
        this.model = model;
        this.shape = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.controller = new KeyboardController();
    }
    @Override
    public void show() {
        this.camera.setToOrtho(false, screenWidth, screenHeight);
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        //Represent our local player
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.identity();
        shape.translate(screenCenterX, screenCenterY, 0);
        shape.rotate(0, 0, 1, 0);
        shape.setColor(0, 1, 0, 1);
        shape.ellipse(0, 0, 12, 12);
        shape.end();

        float[][] playersScreenPos = model.getPlayersScreenPos(screenWidth - 32, screenHeight - 32);
        for(int i = 0; i < playersScreenPos.length; i++){

            //Correcting Y axis because libgdx has it inverted
            float corrYAxis = screenHeight - playersScreenPos[i][1];
            float corrYAxisHead = screenHeight - playersScreenPos[i][3];

            float rectHeight = (corrYAxis - corrYAxisHead) * 2;
            float rectWidth = rectHeight * 0.60f;

            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.identity();
            shape.translate(playersScreenPos[i][0] - rectWidth / 2, corrYAxis, 0);
            shape.setColor(Color.RED);
            shape.rect(0, 0, rectWidth,rectHeight);
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
