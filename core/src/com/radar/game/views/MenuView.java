package com.radar.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.radar.game.AppSettings;
import com.radar.game.GameRadar;

public class MenuView implements Screen {
    private final GameRadar game;
    private Stage stage;

    public MenuView(GameRadar parent){
        this.game = parent;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(AppSettings.SKIN);
        TextButton radar2d = new TextButton("2D Radar", skin);
        TextButton radar3d = new TextButton("3D Radar", skin);
        TextButton options = new TextButton("Options", skin);

        table.add(radar2d).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);

        table.add(radar3d).fillX().uniformX();
        table.row();

        table.add(options).fillX().uniformX();
        table.row();

        //Adding button listeners to change the views when pressed
        radar2d.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeView( GameRadar.Component.RADAR_2D );
            }
        });

        radar3d.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeView( GameRadar.Component.RADAR_3D );
            }
        });

        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeView( GameRadar.Component.OPTIONS );
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
}
