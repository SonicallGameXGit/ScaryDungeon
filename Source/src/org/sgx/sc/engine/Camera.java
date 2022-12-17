package org.sgx.sc.engine;

import org.joml.Vector3d;

public class Camera {
    public Vector3d position;
    public Vector3d rotation;

    public Camera(Vector3d position, Vector3d rotation) {
        this.position = position;
        this.rotation = rotation;
    }
}