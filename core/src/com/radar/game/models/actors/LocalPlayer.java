package com.radar.game.models.actors;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LocalPlayer extends Player{

    protected double camera_location_x;
    protected double camera_location_y;
    protected double camera_location_z;

    public LocalPlayer(byte[] playerData){
        serializeMe(playerData);
    }

    @Override
    protected void serializeMe(byte[] playerData) {
        ByteBuffer bb = ByteBuffer.wrap(playerData);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        super.draw = bb.getLong();
        this.camera_location_x = bb.getDouble();
        this.camera_location_y = bb.getDouble();
        this.camera_location_z = bb.getDouble();
        super.location_x = bb.getDouble();
        super.location_y = bb.getDouble();
        super.location_z = bb.getDouble();
        super.rotation_x = bb.getDouble();
        super.rotation_y = bb.getDouble();
        super.rotation_z = bb.getDouble();
        super.local_player = bb.getLong();
    }

    public double getCamera_location_x() {
        return camera_location_x;
    }

    public void setCamera_location_x(double camera_location_x) {
        this.camera_location_x = camera_location_x;
    }

    public double getCamera_location_y() {
        return camera_location_y;
    }

    public void setCamera_location_y(double camera_location_y) {
        this.camera_location_y = camera_location_y;
    }

    public double getCamera_location_z() {
        return camera_location_z;
    }

    public void setCamera_location_z(double camera_location_z) {
        this.camera_location_z = camera_location_z;
    }
}
