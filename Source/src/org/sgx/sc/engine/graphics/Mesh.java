package org.sgx.sc.engine.graphics;

import org.lwjgl.opengl.*;
import org.sgx.sc.engine.io.texture.Texture;

public class Mesh {
    private final int[] elements;

    private final int VAOID;
    private final int EBOID;
    private final int VBOID;
    private final int TBOID;

    public Mesh(int[] elements, float[] vertices, float[] texcoords) {
        this.elements = elements;

        VAOID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VAOID);

        EBOID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBOID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, elements, GL15.GL_STATIC_DRAW);

        VBOID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        TBOID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, TBOID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texcoords, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

        GL30.glBindVertexArray(0);
    }

    public void load() {
        GL30.glBindVertexArray(VAOID);

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
    }
    public void render(Texture texture) {
        if(texture != null) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
        }
        GL11.glDrawElements(GL11.GL_TRIANGLES, elements.length, GL11.GL_UNSIGNED_INT, 0);
    }
    public void unload() {
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);

        GL30.glBindVertexArray(0);
    }
    public void clear() {
        unload();

        GL15.glDeleteBuffers(TBOID);
        GL15.glDeleteBuffers(VBOID);
        GL15.glDeleteBuffers(EBOID);
        GL30.glDeleteVertexArrays(VAOID);
    }
}