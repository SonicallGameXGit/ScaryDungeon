package org.sgx.sc.game.scene;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.sgx.sc.engine.Transform;
import org.sgx.sc.engine.io.texture.Texture;
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
                    new Vector3d(1.0)));
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
                        new Vector3d(1.0)));
    }
}