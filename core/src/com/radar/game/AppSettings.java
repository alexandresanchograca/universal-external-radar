package com.radar.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class AppSettings {
    public final static FileHandle SKIN = Gdx.files.internal("skin/star-soldier-ui.json");
    public final static float PLAYER_HEIGHT = 100.0f;
    public final static int PACKET_SIZE = 6552;
    public final static int LOCAL_PLAYER_SIZE = 88;
    public final static int PLAYER_SIZE = 64;
    public final static int PLAYER_MAX_COUNT = 101;
}
