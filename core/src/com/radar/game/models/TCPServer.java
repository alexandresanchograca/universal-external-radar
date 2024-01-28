package com.radar.game.models;

import com.radar.game.GameRadar;
import com.radar.game.models.actors.LocalPlayer;
import com.radar.game.models.actors.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.radar.game.AppSettings.*;

public class TCPServer implements Runnable{

    private boolean closeServer;
    private List<Player> players;
    private boolean clientConnected;

    public TCPServer(){
        players = new LinkedList<>();
        this.closeServer = false;
        this.clientConnected = false;
    }

    @Override
    public void run() {
        try {
            startTCP();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startTCP() throws IOException {

        while(!closeServer){
            ServerSocket srvSocket = new ServerSocket(8081);

            Socket clientConnection = null;
            try {
                srvSocket.setSoTimeout(5000);
                clientConnection = srvSocket.accept();
                clientConnected = true;
            }
            catch (SocketTimeoutException e){
                System.out.println("No Incoming connections...");
                clientConnected = false;
                srvSocket.close();
                continue;
            }

            OutputStream out = clientConnection.getOutputStream();
            InputStream in = clientConnection.getInputStream();

            byte[] packetData = new byte[PACKET_SIZE];
            int result = in.read(packetData, 0, packetData.length);

            if(result > 0) {
                players = dataToPlayers(packetData);
            }

            out.write(1);
            out.flush();
            srvSocket.close();
        }

    }

    private static List<Player> dataToPlayers(byte[] packetData) {
        List<Player> players = new LinkedList<>();

        final int chunk = PLAYER_SIZE - 8; //PlayerSize - drawLocal
        final int localChunk = LOCAL_PLAYER_SIZE - 8;

        StringBuilder str = new StringBuilder();
        ByteBuffer bb = ByteBuffer.wrap(packetData);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        //First in the packet is our local_player
        long drawLocal = bb.getLong();
        byte[] localPlayerData = new byte[localChunk];
        bb.get(localPlayerData, 0, localPlayerData.length);
        players.add( new LocalPlayer( localPlayerData ) );

        for (int i = 0; i < PLAYER_MAX_COUNT; i++) {
            long toDraw = bb.getLong();

            if(toDraw != 1){
                break;
            }

            byte[] playerData = new byte[chunk];
            bb.get(playerData, 0, playerData.length);

            Player player = new Player(playerData);

            if(player.isLocalPlayer()){
                continue;
            }

            players.add( player );
        }

        addMockData(players);

        return players;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public void closeServer(){
        closeServer = true;
    }

    public boolean isClientConnected(){
        return clientConnected;
    }

    public static void addMockData(List<Player> playerList){

        Player player = new Player();
        player.setLocal_player(0);
        player.setLocation_x(-400983.64896542736);
        player.setLocation_y(131330.49495841592);
        player.setLocation_z(-1739.4436520681136);

        playerList.add(player);
    }
}
