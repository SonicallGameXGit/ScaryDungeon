package org.sgx.sc.engine.io;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

import java.nio.DoubleBuffer;

public class Mouse {
    public static final int
            BUTTON_LEFT   = 0,
            BUTTON_RIGHT  = 1,
            BUTTON_MIDDLE = 2,
            BUTTON_4      = 3,
            BUTTON_5      = 4,
            BUTTON_6      = 5,
            BUTTON_7      = 6,
            BUTTON_8      = 7;

    private final long window;

    public Mouse(Window window) { this.window = window.getCore(); }

    public boolean getPress(int button) { return GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS; }

    public Vector2d getAbsolutePosition() {
        //DoubleBuffer buffer = GLFW.glfwGetCursorPos(window, position.x(), position.y());
        return null;
    }
}