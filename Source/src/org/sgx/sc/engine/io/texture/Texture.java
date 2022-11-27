package org.sgx.sc.engine.io.texture;

import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

public class Texture {
    public static int NEAREST = GL11.GL_NEAREST;
    public static int LINEAR = GL11.GL_LINEAR;

    private final int id;

    private final Vector2d resolution;

    public Texture(String location, int filter) {
        double[] data = loadTexture(location.split("\\.")[1], location, filter);
        id = (int) data[0];
        resolution = new Vector2d(data[1], data[2]);
    }

    private double[] loadTexture(String type, String location, int filter) {
        org.newdawn.slick.opengl.Texture texture = null;
        try {
            texture = TextureLoader.getTexture(type.toUpperCase(Locale.ENGLISH), new FileInputStream(location), filter);
        } catch(IOException exception) { exception.printStackTrace(); }

        if(texture != null) {
            return new double[] { texture.getTextureID(), texture.getTextureWidth(), texture.getTextureHeight() };
        }

        return new double[3];
    }

    public void clear() {
        GL11.glDeleteTextures(id);
    }

    public int getId() { return id; }

    public Vector2d getResolution() { return resolution; }
}