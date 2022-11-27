package org.sgx.sc.game.scene;

import org.joml.Vector3d;
import org.sgx.sc.game.scene.io.TextureSetup;

public class Material {
    public TextureSetup textureSetup;
    public Vector3d color;

    public Material(TextureSetup textureSetup, Vector3d color) {
        this.textureSetup = textureSetup;
        this.color = color;
    }

    public Material setTextureSetup(TextureSetup textureSetup) { return new Material(textureSetup, color); }
}