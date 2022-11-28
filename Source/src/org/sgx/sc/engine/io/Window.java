package org.sgx.sc.engine.io;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.sgx.sc.engine.time.Time;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Locale;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long window;

    private int latestX;
    private int latestY;
    private int latestWidth;
    private int latestHeight;

    public Window(double width, double height, String title, boolean resizable, boolean fullscreen) {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

        if(fullscreen) {
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            window = glfwCreateWindow(videoMode.width(), videoMode.height(), title, glfwGetPrimaryMonitor(), NULL);
        } else {
            window = glfwCreateWindow((int) width, (int) height, title, NULL, NULL);
        }
        if(window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true); });
        glfwSetWindowSizeCallback(window, (window, newWidth, newHeight) -> { GL11.glViewport(0, 0, newWidth, newHeight); });

        Vector2d screenSize = getScale();

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        int centeredX = (videoMode.width() - (int) screenSize.x) / 2;
        int centeredY = (videoMode.height() - (int) screenSize.y) / 2;

        latestX = centeredX;
        latestY = centeredY;
        latestWidth = (int) width;
        latestHeight = (int) height;

        glfwSetWindowPos(window, centeredX, centeredY);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glViewport(0, 0, (int) screenSize.x, (int) screenSize.y);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }
    public void close() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public void changeBackground(Vector3d backgroundColor) { GL11.glClearColor((float) backgroundColor.x, (float) backgroundColor.y, (float) backgroundColor.z, 1.0f); }
    public void setTitle(String title) { glfwSetWindowTitle(window, title); }
    public void setFullscreen(boolean fullscreen) {
        int refreshRate = 0;
        if(fullscreen) {
            long monitor = glfwGetPrimaryMonitor();
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            refreshRate = videoMode.refreshRate();

            glfwSetWindowMonitor(window, monitor, 0, 0, videoMode.width(), videoMode.height(), refreshRate);
        } else {
            glfwSetWindowMonitor(window, NULL, latestX, latestY, latestWidth, latestHeight, refreshRate);
        }
    }
    public void setIcon(String location) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer imageWidth = stack.mallocInt(1);
            IntBuffer imageHeight = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load(location, imageWidth, imageHeight, comp, 4);
            if(image == null) throw new IOException(location + " not found");

            GLFWImage icon = GLFWImage.malloc();
            GLFWImage.Buffer buffer = GLFWImage.malloc(1);
            icon.set(imageWidth.get(), imageHeight.get(), image);
            buffer.put(0, icon);
            glfwSetWindowIcon(window, buffer);
        } catch (IOException exception) { throw new RuntimeException(exception); }
    }

    public boolean getRunning() { return !glfwWindowShouldClose(window); }

    public long getCore() { return window; }
    public Vector2d getScale() {
        IntBuffer width;
        IntBuffer height;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            width = stack.mallocInt(1);
            height = stack.mallocInt(1);

            glfwGetWindowSize(window, width, height);
        }

        return new Vector2d(width.get(), height.get());
    }
}