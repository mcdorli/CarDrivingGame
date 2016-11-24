package io.github.scaliermaelstrom.cardriver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

import io.github.scaliermaelstrom.cardriver.Input.Tilt;
import io.github.scaliermaelstrom.cardriver.Input.Touch;
import io.github.scaliermaelstrom.cardriver.rendering.Renderer;

public class MainActivity extends Activity {

    public static Context context;
    public static AssetManager assetManager;
    public static MainActivity mainActivity;
    GLSurfaceView glSurfaceView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        MainActivity.context = getApplicationContext();
        MainActivity.assetManager = getAssets();
        MainActivity.mainActivity = this;

        Tilt tilt = new Tilt();
        Touch touch = new Touch();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(tilt, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(tilt, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);

        glSurfaceView = (GLSurfaceView) findViewById(R.id.canvas);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new Renderer(tilt, touch));
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        glSurfaceView.setOnTouchListener(touch);
    }
}
