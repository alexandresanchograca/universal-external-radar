package com.radar.game;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Player{

    public Player(byte[] playerData){
        serializeMe(playerData);
    }

    public Player(){
    }

    private double location_x;
    private double location_y;
    private double location_z;
    private double rotation_x;
    private double rotation_y;
    private double rotation_z;
    private long local_player;


    private void serializeMe(byte[] playerData){
        ByteBuffer bb = ByteBuffer.wrap(playerData);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        this.location_x = bb.getDouble();
        this.location_y = bb.getDouble();
        this.location_z = bb.getDouble();
        this.rotation_x = bb.getDouble();
        this.rotation_y = bb.getDouble();
        this.rotation_z = bb.getDouble();
        this.local_player = bb.getInt();
    }


    public double getLocation_x() {
        return location_x;
    }

    public double getLocation_y() {
        return location_y;
    }

    public double getLocation_z() {
        return location_z;
    }

    public double getRotation_x() {
        return rotation_x;
    }

    public double getRotation_y() {
        return rotation_y;
    }

    public double getRotation_z() {
        return rotation_z;
    }

    public boolean isLocalPlayer() {
        return local_player == 1;
    }

    public void setLocation_x(double location_x) {
        this.location_x = location_x;
    }

    public void setLocation_y(double location_y) {
        this.location_y = location_y;
    }

    public void setLocation_z(double location_z) {
        this.location_z = location_z;
    }

    public void setRotation_x(double rotation_x) {
        this.rotation_x = rotation_x;
    }

    public void setRotation_y(double rotation_y) {
        this.rotation_y = rotation_y;
    }

    public void setRotation_z(double rotation_z) {
        this.rotation_z = rotation_z;
    }

    public void setLocal_player(long local_player) {
        this.local_player = local_player;
    }

    @Override
    public String toString() {
        return "Player{" +
                "location_x=" + location_x +
                ", location_y=" + location_y +
                ", location_z=" + location_z +
                ", rotation_x=" + rotation_x +
                ", rotation_y=" + rotation_y +
                ", rotation_z=" + rotation_z +
                ", local_player=" + local_player +
                '}';
    }
}


