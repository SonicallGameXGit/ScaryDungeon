package org.sgx.sc.game.io;

import org.joml.*;
import org.sgx.sc.engine.Camera;
import org.sgx.sc.engine.Transform;
import org.sgx.sc.engine.io.shader.Shader;

import java.lang.Math;

public class SceneShader extends Shader {
    private int colorId, projectId, viewId, transformId, textureOffsetId, textureScaleId;

    public SceneShader() {
        super("assets/shaders/test.vs", "assets/shaders/test.fs");
    }

    @Override
    protected void loadVariables() {
        createAttribute(0, "position");
        createAttribute(1, "texcoord");
        colorId = createVariable("color");
        projectId = createVariable("project");
        viewId = createVariable("view");
        transformId = createVariable("transform");
        textureOffsetId = createVariable("textureOffset");
        textureScaleId = createVariable("textureScale");

        setVariable(createVariable("textureSampler"), 0);
    }

    public void setColor(Vector4d color) { setVariable(colorId, color); }
    public void setProject(double fieldOfView, Vector2d windowScale, double near, double far) {
        double frustum = far - near;
        double aspect = windowScale.x / windowScale.y;
        double height = (1.0 / Math.tan(Math.toRadians(fieldOfView / 2.0))) * aspect;

        Matrix4f matrix = new Matrix4f();
        matrix.m00((float) (height / aspect));
        matrix.m11((float) height);
        matrix.m22((float) -((far + near) / frustum));
        matrix.m23(-1.0f);
        matrix.m32((float) -((2.0 * near * far) / frustum));
        matrix.m33(0.0f);

        setVariable(projectId, matrix);
    }
    public void setView(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        matrix.rotate((float) -Math.toRadians(camera.rotation.x), new Vector3f(1.0f, 0.0f, 0.0f));
        matrix.rotate((float) -Math.toRadians(camera.rotation.y), new Vector3f(0.0f, 1.0f, 0.0f));
        matrix.rotate((float) -Math.toRadians(camera.rotation.z), new Vector3f(0.0f, 0.0f, 1.0f));
        matrix.translate(new Vector3f((float) -camera.position.x, (float) -camera.position.y, (float) -camera.position.z));

        setVariable(viewId, matrix);
    }
    public void setTransform(Transform transform) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(new Vector3f((float) transform.position.x, (float) transform.position.y, (float) transform.position.z));
        matrix.rotate((float) Math.toRadians(transform.rotation.x), new Vector3f(1.0f, 0.0f, 0.0f), matrix);
        matrix.rotate((float) Math.toRadians(transform.rotation.y), new Vector3f(0.0f, 1.0f, 0.0f), matrix);
        matrix.rotate((float) Math.toRadians(transform.rotation.z), new Vector3f(0.0f, 0.0f, 1.0f), matrix);
        matrix.scale(new Vector3f((float) transform.scale.x, (float) transform.scale.y, (float) transform.scale.z));

        setVariable(transformId, matrix);
    }
    public void setTexture(Vector2d offset, Vector2d scale) {
        setVariable(textureOffsetId, offset);
        setVariable(textureScaleId, scale);
    }
}