package com.radar.game.models;

import com.radar.game.GameRadar;
import com.radar.game.models.actors.Player;

import java.util.List;

public class Radar2DApp implements RadarModel {
    private GameRadar game;
    private TCPServer tcpServer;
    public Radar2DApp(GameRadar game, TCPServer tcpServer){
        this.tcpServer = tcpServer;
        this.game = game;
    }

    @Override
    public Player getLocalPlayer() {
        return tcpServer.getPlayers().get(0); //First is always the local player
    }

    @Override
    public List<Player> getPlayers() {
        return tcpServer.getPlayers();
    }

    @Override
    public float[][] getPlayersScreenPos(float radarScale, float screenCenterX, float screenCenterY) {
        List<Player> players = getPlayers();
        Player localPlayer = players.removeFirst();

        float[][] screenPosArr = new float[players.size()][2];

        for(int i = 0; i < screenPosArr.length; i++){
            worldToScreen(localPlayer, players.get(i), screenPosArr[i], radarScale, screenCenterX, screenCenterY);
        }

        return screenPosArr;
    }

    //Pass by reference for better performance
    private boolean worldToScreen(Player localPlayer, Player player, float[] outScreenValue, float radarScale, float screenCenterX, float screenCenterY){
        //Calculating position
        double relPosX = (localPlayer.getLocation_x() - player.getLocation_x()) / radarScale; //Converting to meters
        double relPosY = (localPlayer.getLocation_y() - player.getLocation_y()) / radarScale;
        relPosY *= -1;

        double rotPointX = screenCenterX + relPosX;
        double rotPointY = screenCenterY + relPosY;

        double radAngle = Math.toRadians(localPlayer.getRotation_y() - 90);

        //Our angle in sin and cos
        double sinAngle = Math.sin(radAngle);
        double cosAngle = Math.cos(radAngle);

        double locX = cosAngle * (rotPointX - screenCenterX) - sinAngle * (rotPointY - screenCenterY);
        double locY = sinAngle * (rotPointX - screenCenterX) + cosAngle * (rotPointY - screenCenterY);

        float finalX = (float)(locX + screenCenterX);
        float finalY = (float)(locY + screenCenterY);

        outScreenValue[0] = finalX;
        outScreenValue[1] = finalY;

        return true;
    }
}
