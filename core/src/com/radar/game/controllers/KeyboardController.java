package com.radar.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class KeyboardController implements InputProcessor {
    private boolean escKey = false;

    @Override
    public boolean keyDown(int keycode) {
        if(Input.Keys.ESCAPE == keycode){
            return (escKey = true);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(Input.Keys.ESCAPE == keycode){
            return !(escKey = false);
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean isEscKey() {
        return escKey;
    }
}
