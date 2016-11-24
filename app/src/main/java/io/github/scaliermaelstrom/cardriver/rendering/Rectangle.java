package io.github.scaliermaelstrom.cardriver.rendering;

import android.opengl.Matrix;

import java.nio.FloatBuffer;

import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;
import io.github.scaliermaelstrom.cardriver.shaders.RectangleShader;
import io.github.scaliermaelstrom.cardriver.utils.BufferUtil;

import static android.opengl.GLES20.*;

public class Rectangle {

    public static FloatBuffer vertexBuffer;
    public static FloatBuffer textureCoordBuffer;
    private static float[] vertices = new float[]{
            -0.5f, 0.5f, 0,
            -0.5f, -0.5f, 0,
            0.5f, 0.5f, 0,
            0.5f, -0.5f, 0
    };
    private static float[] textureCoords = new float[]{
            0, 0,
            0, 1,
            1, 0,
            1, 1
    };
    private static RectangleShader shader;
    private static float[] orthoView = new float[16];
    private static int vertexCount;

    public static void initialize() {
        vertexBuffer = BufferUtil.createFromData(vertices);
        textureCoordBuffer = BufferUtil.createFromData(textureCoords);

        vertexCount = vertices.length / 3;
        shader = new RectangleShader();

        Matrix.setIdentityM(orthoView, 0);
    }

    public static void bind() {
        shader.bindModel();
    }

    public static void draw(Texture texture, Vector3 position, Vector2 size, float angle, Camera camera, DrawType drawType) {
        float[] model = new float[16];
        Matrix.setIdentityM(model, 0);
        Matrix.translateM(model, 0, position.x, position.y, position.z);
        shader.bind(texture, model, drawType == DrawType.UI ? orthoView : camera.getViewMatrix(), drawType == DrawType.UI ? camera.orthographicM : camera.perspectiveM, size, position, angle);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, vertexCount);
    }

    public static void draw(Texture texture, Vector3 position, Vector2 size, Camera camera, DrawType drawType) {
        draw(texture, position, size, 0, camera, drawType);
    }

    public static void cleanup() {
        shader.cleanup();
    }

    public enum DrawType {
        UI,
        SCENE
    }

}
