package io.github.scaliermaelstrom.cardriver.math;

public class Vector2 {

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float dot(Vector2 v) {
        return v.x * x + v.y + y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

}
