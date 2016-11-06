package io.github.scaliermaelstrom.cardriver.rendering;

import android.opengl.Matrix;

import java.nio.FloatBuffer;

import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;
import io.github.scaliermaelstrom.cardriver.shaders.RectangleShader;
import io.github.scaliermaelstrom.cardriver.utils.BufferUtil;

import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;

public class Rectangle {

    public enum DrawType {
        ORTHOGRAPHIC,
        PERSPECTIVE
    }

    private static float[] vertices = new float[] {
            -0.5f, 0.5f, 0,
            -0.5f, -0.5f, 0,
            0.5f, 0.5f, 0,
            0.5f, -0.5f, 0
    };
    private static float[] textureCoords = new float[] {
            0, 0,
            0, 1,
            1, 0,
            1, 1
    };

    private static RectangleShader shader;
    public static FloatBuffer vertexBuffer;
    public static FloatBuffer textureCoordBuffer;
    private static int vertexCount;
    public static void initialize() {
        vertexBuffer = BufferUtil.createFromData(vertices);
        textureCoordBuffer = BufferUtil.createFromData(textureCoords);

        vertexCount = vertices.length / 3;
        shader = new RectangleShader();
    }

    public static void draw(Texture texture, Vector3 position, Vector2 size, Camera camera, DrawType drawType) {
        float[] model = new float[16];
        Matrix.setIdentityM(model, 0);
        Matrix.translateM(model, 0, position.x, position.y, position.z);
        shader.bind(texture, model, camera.getViewMatrix(), drawType == DrawType.ORTHOGRAPHIC ? camera.orthographicM : camera.perspectiveM, size, position);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, vertexCount);
    }

    public static void cleanup() {
        shader.cleanup();
    }

}
