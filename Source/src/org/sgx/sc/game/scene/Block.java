package org.sgx.sc.game.scene;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;
import org.sgx.sc.engine.Transform;
import org.sgx.sc.engine.graphics.Mesh;
import org.sgx.sc.game.io.SceneShader;
import org.sgx.sc.game.math.Collider;

public class Block {
    public static final Mesh mesh = new Mesh(new int[] { 0, 1, 3, 2, 3, 1 }, new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f }, new float[] { 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f });

    public Transform transform;
    private Transform latestTransform;
    public Material material;
    public Collider collider;

    public Block(Transform transform, Material material, Collider collider) {
        this.transform = transform;
        this.material = material;
        this.collider = collider;

        latestTransform = new Transform(transform);
    }
    public Block(Transform transform, Material material) {
        this.transform = transform;
        this.material = material;
        this.collider = new Collider(new Vector2d(transform.position.x(), transform.position.y()), new Vector2d(transform.scale.x(), transform.scale.y()), Collider.SOLID_TYPE);

        latestTransform = new Transform(transform);
    }
    public Block(Transform transform, Material material, short colliderType) {
        this.transform = transform;
        this.material = material;
        this.collider = new Collider(new Vector2d(transform.position.x(), transform.position.y()), new Vector2d(transform.scale.x(), transform.scale.y()), colliderType);

        latestTransform = new Transform(transform);
    }

    public Block setPosition(Vector3d position) { return new Block(transform.setPosition(position), material, new Collider(new Vector2d(position.x(), position.y()), collider.scale, collider.getType())); }
    public Block setMaterial(Material material) { return new Block(transform, material, collider); }

    public void saveTransform(Transform transform) {
        latestTransform = new Transform(transform);
    }
    public void render(SceneShader shader) {
        shader.setTransform(transform);
        shader.setTexture(material.textureSetup.offset, material.textureSetup.scale);
        shader.setColor(material.color);

        mesh.render(material.textureSetup.texture);
    }
    public void clear() {
        material.textureSetup.texture.clear();
    }

    public Transform getLatestTransformSave() { return new Transform(latestTransform); }
}