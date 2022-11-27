package org.sgx.sc.engine.io;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.sgx.sc.engine.time.Time;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long window;

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