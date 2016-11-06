package io.github.scaliermaelstrom.cardriver.rendering;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.scaliermaelstrom.cardriver.R;
import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;

public class Renderer implements GLSurfaceView.Renderer {
    Texture texture;
    Camera camera;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Rectangle.initialize();

        glClearColor(0, 0, 0, 1);
        glEnable(GL_DEPTH_TEST);

        texture = new Texture(R.drawable.test);
        camera = new Camera(new Vector3(0, 0, 0), 0, new Vector2(1, 1));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        camera.adjust(new Vector2(width, height));
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        Rectangle.draw(texture, new Vector3(0, 0, -0.5f), new Vector2(10, 10), camera, Rectangle.DrawType.PERSPECTIVE);
    }

}
