package io.github.scaliermaelstrom.cardriver;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.scaliermaelstrom.cardriver.rendering.Renderer;

public class MainActivity extends AppCompatActivity {

    GLSurfaceView glSurfaceView;

    public static Context context;
    public static AssetManager assetManager;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        MainActivity.assetManager = getAssets();
        MainActivity.mainActivity = this;

        glSurfaceView = (GLSurfaceView) findViewById(R.id.canvas);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new Renderer());
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
