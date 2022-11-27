package org.sgx.sc.game.math;

import org.joml.Vector2d;

public class Collider {

    public Vector2d position;
    public Vector2d scale;

    private boolean active;

    public Collider(Vector2d position, Vector2d scale) {
        this.position = position;
        this.scale = scale;
    }

    public static boolean getCollision(Collider a, Collider b) {
        return a.position.x() <= b.position.x() + b.scale.x() &&
                a.position.x() + a.scale.x() >= b.position.x() &&
                a.position.y() <= b.position.y() + b.scale.y() &&
                a.position.y() + a.scale.y() >= b.position.y();
    }
}