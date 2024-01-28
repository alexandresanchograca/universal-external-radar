package com.radar.game.models;

import com.radar.game.models.actors.LocalPlayer;
import com.radar.game.models.actors.Player;

import java.util.List;

public interface RadarModel {
    LocalPlayer getLocalPlayer();
    List<Player> getPlayers();
}
