package org.sgx.sc.game.scene;

import org.joml.Vector4d;
import org.sgx.sc.game.scene.io.TextureSetup;

public class Material {
    public TextureSetup textureSetup;
    public Vector4d color;

    public Material(TextureSetup textureSetup, Vector4d color) {
        this.textureSetup = textureSetup;
        this.color = color;
    }

    public Material setTextureSetup(TextureSetup textureSetup) {
        this.textureSetup = textureSetup;

        return new Material(textureSetup, color);
    }
}