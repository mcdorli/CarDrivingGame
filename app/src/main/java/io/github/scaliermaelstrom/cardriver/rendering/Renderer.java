package io.github.scaliermaelstrom.cardriver.rendering;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.scaliermaelstrom.cardriver.Car;
import io.github.scaliermaelstrom.cardriver.Input.Tilt;
import io.github.scaliermaelstrom.cardriver.Input.Touch;
import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;
import io.github.scaliermaelstrom.cardriver.utils.Collision;

import static android.opengl.GLES20.*;

public class Renderer implements GLSurfaceView.Renderer {
    Camera camera;

    Car car;

    private Tilt tilt;
    private Touch touch;

    private Terrain terrain;

    public Renderer(Tilt tilt, Touch touch) {
        this.tilt = tilt;
        this.touch = touch;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Rectangle.initialize();

        glClearColor(0.4f, 0.8f, 0.92f, 1);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        terrain = new Terrain("map.json");

        camera = new Camera(new Vector3(0, 2f, 0), 0, new Vector2(1, 1));
        car = new Car(camera, terrain);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        camera.adjust(new Vector2(width, height));
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        terrain.draw(camera);
        Rectangle.bind();

        for (Vector2 pos : terrain.trees) {
            Rectangle.draw(terrain.tree, new Vector3(pos.x / 2, 5f, pos.y / 2), new Vector2(20, 20), camera, Rectangle.DrawType.SCENE);
        }

        Vector2 carPos = new Vector2(car.position.x, car.position.z);
        for (Vector2 pos : terrain.spikes) {
            if (Collision.AABB(carPos, 150, 150, pos)) {
                Rectangle.draw(terrain.spike, new Vector3(pos.x / 2, 1.25f, pos.y / 2), new Vector2(5, 5), camera, Rectangle.DrawType.SCENE);
            }
        }

        car.update(tilt.getRotation(), touch.getPositions(), terrain);
        car.draw();
    }

}
