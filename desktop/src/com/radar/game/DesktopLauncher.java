package com.radar.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(120);
		config.setTitle("Universal Game Radar");
		config.setWindowedMode(800, 480);
		config.useVsync(true);
		new Lwjgl3Application(new GameRadar(), config);
	}
}
