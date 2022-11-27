package org.sgx.sc.game.scene.io;

import org.joml.Vector2d;
import org.sgx.sc.engine.io.texture.Texture;
import org.sgx.sc.engine.time.Time;

public class TextureSetup {
    public static final boolean ANIMATION_PLAY_FORWARDS = true;
    public static final boolean ANIMATION_PLAY_BACKWARDS = false;
    public static final boolean ANIMATED_TEXTURE_DIR_RIGHT = true;
    public static final boolean ANIMATED_TEXTURE_DIR_DOWN = false;

    public Texture texture;
    public Vector2d offset;
    public Vector2d scale;

    private int textureState;
    private double time;

    public boolean textureDirection;

    public TextureSetup(Texture texture, Vector2d offset, Vector2d scale, boolean textureDirection) {
        this.texture = texture;
        this.offset = offset;
        this.scale = scale;
        this.textureDirection = textureDirection;

        textureState = 0;
        time = 0.0;
    }

    public TextureSetup setTexture(Texture texture) {
        this.texture = texture;

        return new TextureSetup(texture, offset, scale, textureDirection);
    }
    public TextureSetup setOffset(Vector2d offset) {
        this.offset = offset;

        return new TextureSetup(texture, offset, scale, textureDirection);
    }
    public TextureSetup setScale(Vector2d scale) {
        this.scale = scale;

        return new TextureSetup(texture, offset, scale, textureDirection);
    }

    public void playTextureAnimation(Time time, boolean playDirection, double speed) {
        this.time += time.getDelta();

        if(this.time >= 1.0 / speed) {
            double aspect;

            if(textureDirection) {
                aspect = texture.getResolution().x / texture.getResolution().y;
            } else {
                aspect = texture.getResolution().y / texture.getResolution().x;
            }
            textureState = textureState >= aspect - 1.0 ? 0 : textureState + 1;
            offset.y = textureState * (playDirection ? 1 : -1);
            scale.y = 1.0 / aspect;

            this.time = 0.0;
        }
    }
    public void setState(int state) {
        if(textureDirection) {
            double aspect = texture.getResolution().x / texture.getResolution().y;
            textureState = textureState >= aspect ? 0 : textureState + 1;
            offset.x = textureState;
            scale.x = 1.0 / aspect;
        } else {
            double aspect = texture.getResolution().y / texture.getResolution().x;
            textureState = textureState >= aspect ? 0 : textureState + 1;
            offset.y = textureState;
            scale.y = 1.0 / aspect;
        }
    }

    public double getTextureState() { return textureState; }
}