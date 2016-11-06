package io.github.scaliermaelstrom.cardriver.rendering;

import android.opengl.Matrix;

import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;

public class Camera {

    private Vector3 position;
    private float angle;

    public float[] perspectiveM = new float[16];
    public float[] orthographicM = new float[16];

    public Camera(Vector3 position, float angle, Vector2 windowSize) {
        this.position = position;
        this.angle = angle;

        float aspect = windowSize.x / windowSize.y;
        Matrix.frustumM(perspectiveM, 0, -aspect, aspect, -1, 1, 0.1f, 1000f);
        Matrix.orthoM(orthographicM, 0, -aspect, aspect, -1, 1, 0.1f, 1000f);
    }

    public void adjust(Vector2 windowSize) {
        float aspect = windowSize.x / windowSize.y;
        Matrix.frustumM(perspectiveM, 0, -aspect, aspect, -1, 1, 0.1f, 1000f);
        Matrix.orthoM(orthographicM, 0, -aspect, aspect, -1, 1, 0.1f, 1000f);
    }

    public float[] getViewMatrix() {
        float[] viewM = new float[16];

        Matrix.setIdentityM(viewM, 0);
        Matrix.translateM(viewM, 0, -position.x, -position.y, -position.z);
        Matrix.rotateM(viewM, 0, angle, 0, 1, 0);

        return viewM;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void move(Vector3 dAngle) {
        position = position.add(dAngle);
    }

    public void rotate(float dAngle) {
        angle += dAngle;
    }
}
