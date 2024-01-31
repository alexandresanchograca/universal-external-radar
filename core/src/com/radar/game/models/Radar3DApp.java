package com.radar.game.models;

import com.badlogic.gdx.math.Vector3;
import com.radar.game.GameRadar;
import com.radar.game.models.actors.LocalPlayer;
import com.radar.game.models.actors.Player;

import java.util.List;

import static com.radar.game.AppSettings.PLAYER_HEIGHT;

public class Radar3DApp implements RadarModel {

    private GameRadar game;
    private TCPServer tcpServer;

    public Radar3DApp(GameRadar game, TCPServer tcpServer){
        this.tcpServer = tcpServer;
        this.game = game;
    }

    @Override
    public LocalPlayer getLocalPlayer() {
        return (LocalPlayer)tcpServer.getPlayers().get(0);
    }

    @Override
    public List<Player> getPlayers() {
       return tcpServer.getPlayers();
    }


    public float[][] getPlayersScreenPos(float screenWidth, float screenHeight) {
        List<Player> players = getPlayers();
        LocalPlayer localPlayer = (LocalPlayer)players.removeFirst();

        float[][] screenPosArr = new float[players.size()][4];

        for(int i = 0; i < screenPosArr.length; i++){
            WorldToScreen(localPlayer, players.get(i), screenPosArr[i], PLAYER_HEIGHT, screenWidth, screenHeight);
        }

        return screenPosArr;
    }

    private boolean WorldToScreen(LocalPlayer localPlayer, Player player, float[] outScreenValue, float playerHeight, float screenWidth, float screenHeight){

        Vector3[] matrix = getMatrix(localPlayer);

        Vector3 vDelta = new Vector3(
                (float)(player.getLocation_x() - localPlayer.getCamera_location_x()),
                (float)(player.getLocation_y() - localPlayer.getCamera_location_y()),
                (float)( (player.getLocation_z() - playerHeight / 2) - localPlayer.getCamera_location_z())
        );

        Vector3 vDeltaHead = new Vector3(
                (float)(player.getLocation_x() - localPlayer.getCamera_location_x()),
                (float)(player.getLocation_y() - localPlayer.getCamera_location_y()),
                (float)((player.getLocation_z() + playerHeight / 2 )- localPlayer.getCamera_location_z())
        );


        Vector3 vTransform = new Vector3( vDelta.dot(matrix[1]), vDelta.dot(matrix[2]), vDelta.dot(matrix[0]) );
        Vector3 vTransformHead = new Vector3( vDeltaHead.dot(matrix[1]), vDeltaHead.dot(matrix[2]), vDeltaHead.dot(matrix[0]) );


        if (vTransform.z < 1.f){
            vTransform.z = 1.f;
        }

        if (vTransformHead.z < 1.f){
            vTransformHead.z = 1.f;
        }

        double cameraFovAngle = Math.tan( 90 * Math.PI / 360.0 ); //Depends on your settings
        float screenCenterX = screenWidth / 2.0f;
        float screenCenterY = screenHeight / 2.0f;

        outScreenValue[0] = (float)(screenCenterX + vTransform.x * (screenCenterX / cameraFovAngle ) / vTransform.z);
        outScreenValue[1] = (float)(screenCenterY - vTransform.y * (screenCenterX / cameraFovAngle ) / vTransform.z);
        outScreenValue[2] = (float)(screenCenterX + vTransformHead.x * (screenCenterX / cameraFovAngle ) / vTransformHead.z);
        outScreenValue[3] = (float)(screenCenterY - vTransformHead.y * (screenCenterX / cameraFovAngle ) / vTransformHead.z);

        if(outScreenValue[0] > screenWidth || outScreenValue[0] < 0){
            return false;
        }

        if(outScreenValue[1] > screenHeight || outScreenValue[1] < 0){
            return false;
        }

        return true;
    }

    private Vector3[] getMatrix(Player localPlayer){
        Vector3[] vMatrix = new Vector3[3];
        vMatrix[0] = new Vector3(0,0,0);
        vMatrix[1] = new Vector3(0,0,0);
        vMatrix[2] = new Vector3(0,0,0);

        double radPitch = Math.toRadians( localPlayer.getRotation_x());
        double radYaw = Math.toRadians( localPlayer.getRotation_y() );
        double radRoll = Math.toRadians( localPlayer.getRotation_z() );

        float SP = (float)Math.sin(radPitch);
        float CP = (float)Math.cos(radPitch);
        float SY = (float)Math.sin(radYaw);
        float CY = (float)Math.cos(radYaw);
        float SR = (float)Math.sin(radRoll);
        float CR = (float)Math.cos(radRoll);

        vMatrix[0].x = CP * CY;
        vMatrix[0].y = CP * SY;
        vMatrix[0].z = SP;

        vMatrix[1].x = SR * SP * CY - CR * SY;
        vMatrix[1].y = SR * SP * SY + CR * CY;
        vMatrix[1].z =  -SR * CP;

        vMatrix[2].x = -(CR * SP * CY + SR * SY);
        vMatrix[2].y = CY * SR - CR * SP * SY;
        vMatrix[2].z = CR * CP;

        return vMatrix;
    }

}
