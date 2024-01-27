package com.radar.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class OptionsApp {
    private static final String OPTIONS_NAME = "universal_radar_options";
    private static final String RADAR_SCALE = "radar_scale";
    private static final String DOT_SIZE = "dot_size";

    /* Used to create/read the config file */
    private Preferences getOptions(){
        //A settings xml file will be created with OPTIONS_NAME
        return Gdx.app.getPreferences(OPTIONS_NAME);
    }

    public void setRadarScale(float value){
        getOptions().putFloat(RADAR_SCALE, value);
        getOptions().flush(); //Write value
    }

    public float getRadarScale(){
        return getOptions().getFloat(RADAR_SCALE, 4.0f);
    }

    public void setDotSize(float value){
        getOptions().putFloat(DOT_SIZE, value);
        getOptions().flush();
    }

    public float getDotSize(){
        return getOptions().getFloat(DOT_SIZE, 24.0f);
    }
}
