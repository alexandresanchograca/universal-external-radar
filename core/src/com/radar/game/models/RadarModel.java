package com.radar.game.models;

import com.radar.game.models.actors.Player;

import java.util.List;

public interface RadarModel {
    Player getLocalPlayer();
    List<Player> getPlayers();
    float[][] getPlayersScreenPos(float radarScale, float screenCenterX, float screenCenterY);
}
