package io.github.scaliermaelstrom.cardriver.rendering;

import android.opengl.Matrix;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import io.github.scaliermaelstrom.cardriver.R;
import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.shaders.TerrainShader;
import io.github.scaliermaelstrom.cardriver.utils.BufferUtil;
import io.github.scaliermaelstrom.cardriver.utils.FileUtil;

public class Terrain {

    public final float SIZE = 500;
    private final float HOUSE_HEIGHT = 100;

    public float speedOnRoad;
    public float accelerationOnRoad;
    public float breakSpeed;
    public float slowingSpeed;

    public ArrayList<Vector2> mesh = new ArrayList<>();
    public ArrayList<Vector2> trees = new ArrayList<>();
    public ArrayList<Vector2> spikes = new ArrayList<>();
    public Vector2 startPos;
    public float angle;
    public FloatBuffer meshBuffer;
    public FloatBuffer grassBuffer;
    public FloatBuffer houseBuffer;
    public FloatBuffer meshTextureBuffer;
    public FloatBuffer grassTextureBuffer;
    public FloatBuffer houseTextureBuffer;
    public Texture grass;
    public Texture road;
    public Texture houses;
    public Texture tree;
    public Texture spike;
    public int vertexCount;
    public int houseVertexCount;
    private String type;
    private TerrainShader shader;

    public Terrain(String name) {
        try {
            JSONObject object = new JSONObject(FileUtil.readAsString(name));
            JSONArray meshArray = object.getJSONArray("mesh");
            for (int i = 0; i < meshArray.length(); i++) {
                JSONObject position = meshArray.getJSONObject(i);
                mesh.add(new Vector2(
                        (float) position.getDouble("x") * SIZE,
                        (float) position.getDouble("y") * SIZE
                ));
            }

            JSONArray treeArray = object.getJSONArray("trees");
            for (int i = 0; i < treeArray.length(); i++) {
                JSONObject position = treeArray.getJSONObject(i);
                Vector2 tree = new Vector2(
                        (float) position.getDouble("x") * SIZE,
                        (float) position.getDouble("y") * SIZE
                );
                trees.add(tree);
            }

            JSONArray spikeArray = object.getJSONArray("spikes");
            for (int i = 0; i < spikeArray.length(); i++) {
                JSONObject position = spikeArray.getJSONObject(i);
                spikes.add(new Vector2(
                        (float) position.getDouble("x") * SIZE,
                        (float) position.getDouble("y") * SIZE
                ));
            }

            type = object.getString("type");
            JSONObject start = object.getJSONObject("start");
            startPos = new Vector2(
                    (float) start.getDouble("x") * SIZE,
                    (float) start.getDouble("y") * SIZE
            );
            angle = (float) start.getDouble("angle");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        float[] meshData = new float[mesh.size() * 3];
        float[] textureCoords = new float[mesh.size() * 2];
        int counter = 0;

        for (Vector2 pos : mesh) {
            meshData[counter * 3] = pos.x;
            meshData[counter * 3 + 1] = 0;
            meshData[counter * 3 + 2] = pos.y;

            textureCoords[counter * 2] = pos.x / 10;
            textureCoords[counter * 2 + 1] = pos.y / 10;

            counter++;
        }
        vertexCount = counter;

        meshBuffer = BufferUtil.createFromData(meshData);
        grassBuffer = BufferUtil.createFromData(new float[]{
                -0.5f * SIZE, -0.05f, 0.5f * SIZE,
                -0.5f * SIZE, -0.05f, -0.5f * SIZE,
                0.5f * SIZE, -0.05f, 0.5f * SIZE,
                0.5f * SIZE, -0.05f, -0.5f * SIZE
        });
        houseBuffer = BufferUtil.createFromData(new float[]{
                -SIZE / 2, -0.5f, -SIZE / 2,
                -SIZE / 2, HOUSE_HEIGHT - 0.5f, -SIZE / 2,

                -SIZE / 2, -0.5f, SIZE / 2,
                -SIZE / 2, HOUSE_HEIGHT - 0.5f, SIZE / 2,

                SIZE / 2, -0.5f, SIZE / 2,
                SIZE / 2, HOUSE_HEIGHT - 0.5f, SIZE / 2,

                SIZE / 2, -0.5f, -SIZE / 2,
                SIZE / 2, HOUSE_HEIGHT - 0.5f, -SIZE / 2,

                -SIZE / 2, -0.5f, -SIZE / 2,
                -SIZE / 2, HOUSE_HEIGHT - 0.5f, -SIZE / 2,
        });

        houseVertexCount = 10;

        meshTextureBuffer = BufferUtil.createFromData(textureCoords);
        grassTextureBuffer = BufferUtil.createFromData(new float[]{
                0, 0,
                0, 2,
                2, 0,
                2, 2
        });

        float houseRepeat = SIZE / HOUSE_HEIGHT;

        houseTextureBuffer = BufferUtil.createFromData(new float[]{
                0, 1,
                0, 0,

                houseRepeat, 1,
                houseRepeat, 0,

                houseRepeat * 2, 1,
                houseRepeat * 2, 0,

                houseRepeat * 3, 1,
                houseRepeat * 3, 0,

                houseRepeat * 4, 1,
                houseRepeat * 4, 0,
        });


        grass = new Texture(R.drawable.grass);
        road = new Texture(R.drawable.road);
        houses = new Texture(R.drawable.houses);
        tree = new Texture(R.drawable.tree);
        spike = new Texture(R.drawable.spikes);

        shader = new TerrainShader();

        switch (type) {
            case "savanna":
            case "normal":
                speedOnRoad = 0.75f;
                accelerationOnRoad = 0.03f;
                breakSpeed = 0.9f;
                slowingSpeed = 0.99f;
                break;
            case "ice":
                speedOnRoad = 0.8f;
                accelerationOnRoad = 0.02f;
                breakSpeed = 0.95f;
                slowingSpeed = 0.995f;
                break;
        }
    }

    public void draw(Camera camera) {
        shader.draw(this, camera);
    }

    public float[] getModelMatrix() {
        float[] model = new float[16];
        Matrix.setIdentityM(model, 0);
        return model;
    }

}
