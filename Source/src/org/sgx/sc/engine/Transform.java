package org.sgx.sc.engine;

import org.joml.Vector3d;

public class Transform {
    public Vector3d position;
    public Vector3d rotation;
    public Vector3d scale;
    public Vector3d anchor;

    public Transform(Vector3d position, Vector3d rotation, Vector3d scale, Vector3d anchor) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.anchor = anchor;
    }
    public Transform(Vector3d position, Vector3d rotation, Vector3d scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.anchor = new Vector3d();
    }
    public Transform(Transform transform) {
        this.position = new Vector3d(transform.position);
        this.rotation = new Vector3d(transform.rotation);
        this.scale = new Vector3d(transform.scale);
        this.anchor = new Vector3d(transform.anchor);
    }

    public Transform setPosition(Vector3d position) { return new Transform(position, rotation, scale); }

    public Vector3d getPosition() { return new Vector3d(position); }
    public Vector3d getRotation() { return new Vector3d(rotation); }
    public Vector3d getScale() { return new Vector3d(scale); };
}