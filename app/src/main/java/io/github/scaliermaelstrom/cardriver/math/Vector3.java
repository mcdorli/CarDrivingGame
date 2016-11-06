package io.github.scaliermaelstrom.cardriver.math;

public class Vector3 {

    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 vec) {
        return new Vector3(
                x + vec.x,
                y + vec.y,
                z + vec.z
        );
    }
}
