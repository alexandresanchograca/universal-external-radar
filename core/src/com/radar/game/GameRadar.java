package com.radar.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.radar.game.models.*;
import com.radar.game.views.MenuView;
import com.radar.game.views.OptionsView;
import com.radar.game.views.Radar2DView;
import com.radar.game.views.Radar3DView;

import java.util.HashMap;
import java.util.Map;

public class GameRadar extends Game {
    private TCPServer tcpServer;
    private OptionsApp optionsApp;
    private  Radar2DApp radar2DApp;
    private  Radar3DApp radar3DApp;
    private Map<Component, Screen> viewsMap;


    public enum Component {
        MENU, RADAR_2D, RADAR_3D, OPTIONS;
    }

    @Override
    public void create() {
        //Initializing our TCP server and running it on a new thread
        tcpServer = new TCPServer();
        Thread tcpSrv = new Thread(tcpServer);
        tcpSrv.start();

        //Creating our models
        this.optionsApp = new OptionsApp();
        this.radar2DApp = new Radar2DApp(this, tcpServer);
        this.radar3DApp = new Radar3DApp(this, tcpServer);

        //Creating the views
        this.viewsMap = createViewMap();

        //Our first screen will be the menu
        this.setScreen( viewsMap.get(Component.MENU) );
    }

    private Map<Component, Screen> createViewMap(){
        Map<Component, Screen> viewsMap = new HashMap<>();
        viewsMap.put(Component.MENU, new MenuView(this) );
        viewsMap.put(Component.RADAR_2D, new Radar2DView(this, radar2DApp ));
        viewsMap.put(Component.RADAR_3D, new Radar3DView(this, radar3DApp ));
        viewsMap.put(Component.OPTIONS, new OptionsView(this));
        return viewsMap;
    }


    public OptionsApp getOptions(){
        return optionsApp;
    }

    public void changeView(Component option){
        setScreen( viewsMap.get(option) );
    }

    @Override
    public void dispose() {
        tcpServer.closeServer();
    }
}
