package org.sgx.sc.game.scene;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;
import org.sgx.sc.engine.Transform;
import org.sgx.sc.engine.io.texture.Texture;
import org.sgx.sc.game.math.Collider;
import org.sgx.sc.game.scene.io.TextureSetup;

public abstract class BlockTypes {
    public static Block BRICK() {
        return new Block(
            new Transform(
                    new Vector3d(),
                    new Vector3d(),
                    new Vector3d(1.0)),
            new Material(
                    new TextureSetup(
                            new Texture(
                                    "assets/textures/brick.png",
                                    Texture.NEAREST),
                            new Vector2d(),
                            new Vector2d(1.0),
                            TextureSetup.ANIMATED_TEXTURE_DIR_DOWN),
                    new Vector4d(1.0)), Collider.SOLID_TYPE);
    }
    public static Block SKULL() {
        return new Block(
                new Transform(
                        new Vector3d(),
                        new Vector3d(),
                        new Vector3d(1.0)),
                new Material(
                        new TextureSetup(
                                new Texture(
                                        "assets/textures/animated/end_skull.png",
                                        Texture.NEAREST),
                                new Vector2d(),
                                new Vector2d(1.0),
                                TextureSetup.ANIMATED_TEXTURE_DIR_DOWN),
                        new Vector4d(0.75)));
    }
    public static Block LADDER_NONE() {
        return new Block(
                new Transform(
                        new Vector3d(),
                        new Vector3d(),
                        new Vector3d(1.0)),
                new Material(
                        new TextureSetup(
                                new Texture(
                                        "assets/textures/ladder.png",
                                        Texture.NEAREST),
                                new Vector2d(),
                                new Vector2d(1.0),
                                TextureSetup.ANIMATED_TEXTURE_DIR_DOWN),
                        new Vector4d(1.0)), Collider.LADDER_TYPE);
    }
    public static Block LADDER_LEFT() {
        return new Block(
                new Transform(
                        new Vector3d(),
                        new Vector3d(),
                        new Vector3d(1.0)),
                new Material(
                        new TextureSetup(
                                new Texture(
                                        "assets/textures/ladder_connected_left.png",
                                        Texture.NEAREST),
                                new Vector2d(),
                                new Vector2d(1.0),
                                TextureSetup.ANIMATED_TEXTURE_DIR_DOWN),
                        new Vector4d(1.0)), Collider.LADDER_TYPE);
    }
    public static Block LADDER_RIGHT() {
        return new Block(
                new Transform(
                        new Vector3d(),
                        new Vector3d(),
                        new Vector3d(1.0)),
                new Material(
                        new TextureSetup(
                                new Texture(
                                        "assets/textures/ladder_connected_right.png",
                                        Texture.NEAREST),
                                new Vector2d(),
                                new Vector2d(1.0),
                                TextureSetup.ANIMATED_TEXTURE_DIR_DOWN),
                        new Vector4d(1.0)), Collider.LADDER_TYPE);
    }
    public static Block LADDER_BOTH() {
        return new Block(
                new Transform(
                        new Vector3d(),
                        new Vector3d(),
                        new Vector3d(1.0)),
                new Material(
                        new TextureSetup(
                                new Texture(
                                        "assets/textures/ladder_connected_both.png",
                                        Texture.NEAREST),
                                new Vector2d(),
                                new Vector2d(1.0),
                                TextureSetup.ANIMATED_TEXTURE_DIR_DOWN),
                        new Vector4d(1.0)), Collider.LADDER_TYPE);
    }
}