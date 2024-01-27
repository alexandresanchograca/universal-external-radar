package com.radar.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.radar.game.AppSettings;
import com.radar.game.GameRadar;

public class OptionsView implements Screen {
    private final GameRadar game;
    private Stage stage;
    private Label dotSizeValue;
    private Label radarScaleValue;

    public OptionsView(GameRadar parent){
        this.game = parent;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        // A table will fill the screen
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(AppSettings.SKIN);
        Label titleLabel = new Label( "Options", skin );

        Label radarScaleLabel = new Label( "Radar Scale", skin );
        Slider radarScaleSlider = new Slider(0.1f, 100.0f, 0.5f, false, skin);
        radarScaleValue = new Label( String.valueOf( game.getOptions().getRadarScale() ), skin );


        Label dotSizeLabel = new Label( "Dot Size", skin );
        Slider dotSizeSlider = new Slider(0.1f, 100.0f, 0.5f, false, skin);
        dotSizeValue = new Label( String.valueOf( game.getOptions().getDotSize() ), skin );

        TextButton back = new TextButton("Back", skin);

        //Adding elements to our table
        table.add(titleLabel).colspan(3);
        table.row().width(250).pad(40, 2,40,5);
        table.add(radarScaleLabel).left();
        table.add(radarScaleSlider);
        table.add(radarScaleValue);
        table.row().width(250).pad(40, 2,40,5);
        table.add(dotSizeLabel).left();
        table.add(dotSizeSlider);
        table.add(dotSizeValue);
        table.row();
        table.add(back).colspan(3);

        //Add Buttons/Slider Functionality
        radarScaleSlider.setValue( game.getOptions().getRadarScale() );
        radarScaleSlider.addListener(event -> {
            game.getOptions().setRadarScale( radarScaleSlider.getValue() );
            return false;
        });

        dotSizeSlider.setValue( game.getOptions().getDotSize() );
        dotSizeSlider.addListener(event -> {
            game.getOptions().setDotSize( dotSizeSlider.getValue() );
            return false;
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeView( GameRadar.Component.MENU );
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        dotSizeValue.setText( String.valueOf( game.getOptions().getDotSize() ) );
        radarScaleValue.setText( String.valueOf( game.getOptions().getRadarScale() ) );

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
