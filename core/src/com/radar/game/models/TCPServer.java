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

        ByteBuffer bb = ByteBuffer.wrap(packetData);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        //First in the packet is our local_player
        byte[] localPlayerData = new byte[LOCAL_PLAYER_SIZE];
        bb.get(localPlayerData, 0, localPlayerData.length);
        players.add( new LocalPlayer( localPlayerData ) );

        for (int i = 0; i < PLAYER_MAX_COUNT; i++) {
            byte[] playerData = new byte[PLAYER_SIZE];
            bb.get(playerData, 0, playerData.length);

            Player player = new Player(playerData);

            if(player.isLocalPlayer() || player.toDraw()){
                continue;
            }

            players.add( player );
        }
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
}
