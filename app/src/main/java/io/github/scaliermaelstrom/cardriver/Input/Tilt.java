package io.github.scaliermaelstrom.cardriver.Input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.WindowManager;

import io.github.scaliermaelstrom.cardriver.MainActivity;
import io.github.scaliermaelstrom.cardriver.math.Vector3;

public class Tilt implements SensorEventListener {

    private final float MAX_ROTATION = 2;
    private float[] gravity;
    private float[] geomagnetic;
    private Vector3 start;
    private Vector3 angle;

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                gravity = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                geomagnetic = event.values.clone();
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Vector3 getRotation() {
        float[] R = new float[9];
        float[] I = new float[9];

        if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {

            float[] orientation = new float[3];
            SensorManager.getOrientation(R, orientation);

            if (start == null) {
                start = new Vector3(
                        (float) Math.toDegrees(orientation[0]),
                        (float) Math.toDegrees(orientation[1]),
                        (float) Math.toDegrees(orientation[2])
                );
                angle = new Vector3(
                        0,
                        0,
                        0
                );
            }

            angle.x += Math.max(Math.min(Math.toDegrees(orientation[0]) - angle.x, MAX_ROTATION), -MAX_ROTATION);
            angle.y += Math.max(Math.min(Math.toDegrees(orientation[1]) - angle.y, MAX_ROTATION), -MAX_ROTATION);
            angle.z += Math.max(Math.min(Math.toDegrees(orientation[2]) - angle.z, MAX_ROTATION), -MAX_ROTATION);

            angle.x %= 360;
            angle.y %= 360;
            angle.z %= 360;

            Display display = ((WindowManager) MainActivity.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int rotation = display.getRotation();

            if (rotation == 1) {
                return new Vector3(angle.x, -angle.y, angle.z);
            }

            return angle;
        }
        return new Vector3(-10, -10, -10);
    }
}
