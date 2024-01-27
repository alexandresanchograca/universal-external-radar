package com.radar.game.models;

import com.radar.game.GameRadar;
import com.radar.game.models.actors.Player;

import java.util.List;

public class Radar3DApp implements RadarModel {

    private GameRadar game;
    private TCPServer tcpServer;

    public Radar3DApp(GameRadar game, TCPServer tcpServer){
        this.tcpServer = tcpServer;
        this.game = game;
    }

    @Override
    public Player getLocalPlayer() {
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Override
    public float[][] getPlayersScreenPos(float radarScale, float screenCenterX, float screenCenterY) {
        return new float[0][];
    }

}
