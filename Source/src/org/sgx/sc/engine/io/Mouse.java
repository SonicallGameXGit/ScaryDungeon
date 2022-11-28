package org.sgx.sc.engine.io;

import org.lwjgl.glfw.GLFW;

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

    public void grab(boolean grabbed) {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, grabbed ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }
}