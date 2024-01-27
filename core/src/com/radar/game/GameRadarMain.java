package com.radar.game;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.radar.game.models.TCPServer;
import com.radar.game.models.actors.Player;

public class GameRadarMain extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ShapeRenderer shape;
	private Texture arrowImage;
	private Rectangle arrow;
	final private int screenCenterX = 800 / 2 - 64 / 2;
	final private int screenCenterY = 480 / 2 - 64 / 2;
	final private float boxSize = 24.0f;
	private TCPServer tcpServer;
	private ExecutorService executorService;
	private float radarScale = 4;

	/* Used to load all assets */
	@Override
	public void create() {
		shape = new ShapeRenderer();
		arrowImage = new Texture(Gdx.files.internal("arrow.png"));
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the arrow
		arrow = new Rectangle();
		arrow.x = screenCenterX;
		arrow.y = screenCenterY;
		arrow.width = 26;
		arrow.height = 26;

		//Initializing our TCP server and running it on a new thread
		tcpServer = new TCPServer();
		Thread tcpSrv = new Thread(tcpServer);
		tcpSrv.start();
	}

	@Override
	public void render() {
		// clear the screen with a dark blue color.
		//r,g,b,a from 0 -> 1
		ScreenUtils.clear(0, 0, 0.2f, 1);

		// tell the camera to update its matrices.
		camera.update();

		List<Player> players = tcpServer.getPlayers();

		if(players.isEmpty()){
			return;
		}

		//First is always the local player
		Player localPlayer = tcpServer.getPlayers().get(0);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.identity();
		shape.translate(screenCenterX, screenCenterY, 0);
		shape.rotate(0, 0, 1, 0);
		shape.setColor(0, 1, 0, 1);
		shape.triangle(0f+13f, 0f+26f, 0, 0f, 0f+26,0f);
		shape.setColor(0, 0, 0, 0);
		shape.triangle(0f+13f, 0f+9f, 0, 0f, 0f+26,0f);
		shape.end();

		//Players Drawing Loop
		for(Player player : players){
			if(!player.isLocalPlayer()){
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

				shape.begin(ShapeRenderer.ShapeType.Filled);
				shape.identity();
				shape.translate(finalX, finalY, 0);
				shape.setColor(Color.RED);
				shape.ellipse(0, 0, boxSize,boxSize);
				shape.end();
			}
		}
	}

	@Override
	public void dispose() {
		//Executing cleanup when client exits
		tcpServer.closeServer();
		shape.dispose();
		batch.dispose();
	}
}
