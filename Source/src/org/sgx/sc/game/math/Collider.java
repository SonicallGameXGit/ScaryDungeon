package org.sgx.sc.game.math;

import org.joml.Vector2d;

public class Collider {
    public static final short SOLID_TYPE = 0;
    public static final short LADDER_TYPE = 1;
    public static final short LAVA_TYPE = 2;
    public static final short ORB_TYPE = 3;

    private short type;

    public Vector2d position;
    public Vector2d scale;

    public Collider(Vector2d position, Vector2d scale, short type) {
        this.position = position;
        this.scale = scale;
        this.type = type;
    }

    public void setType(short type) { this.type = type; }

    public static boolean getCollision(Collider a, Collider b) {
        return a.position.x() <= b.position.x() + b.scale.x() &&
                a.position.x() + a.scale.x() >= b.position.x() &&
                a.position.y() <= b.position.y() + b.scale.y() &&
                a.position.y() + a.scale.y() >= b.position.y();
    }

    public short getType() { return type; }
}