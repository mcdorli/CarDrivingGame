package io.github.scaliermaelstrom.cardriver.utils;

import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;

public class Collision {

    public static boolean isPointInTriangle(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 position) {
        return (triangleHelper(p1, p2, p3, position) ||
                triangleHelper(p1, p3, p2, position) ||
                triangleHelper(p2, p1, p3, position) ||
                triangleHelper(p2, p3, p1, position) ||
                triangleHelper(p3, p1, p2, position) ||
                triangleHelper(p3, p2, p1, position));

    }

    private static boolean triangleHelper(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 position) {
        Vector2 v0 = new Vector2(p2.x - p1.x, p2.y - p1.y);
        Vector2 v1 = new Vector2(p3.x - p1.x, p3.y - p1.y);
        Vector2 v2 = new Vector2(position.x - p1.x, position.y - p1.y);

        float dot00 = v0.dot(v0);
        float dot01 = v0.dot(v1);
        float dot02 = v0.dot(v2);
        float dot11 = v1.dot(v1);
        float dot12 = v1.dot(v2);

        float iD = 1 / (dot00 * dot11 * dot12);
        float u = (dot11 * dot02 - dot01 * dot12) * iD;
        float v = (dot00 * dot12 - dot01 * dot02) * iD;

        return (u >= 0) && (v >= 0) && (u + v < 1);
    }

    public static boolean AABB(Vector2 center, float width, float height, Vector2 position) {
        float minX = center.x - width / 2;
        float minY = center.y - height / 2;
        float maxX = center.x + width / 2;
        float maxY = center.y + height / 2;
        return (position.x >= minX && position.y >= minY && position.x <= maxX && position.y <= maxY);
    }

    public static boolean AABBTriangle(Vector2 p1, Vector2 p2, Vector2 p3, Vector3 position) {
        float minX = Math.min(Math.min(p1.x, p2.x), p3.x);
        float minY = Math.min(Math.min(p1.y, p2.y), p3.y);
        float maxX = Math.max(Math.max(p1.x, p2.x), p3.x);
        float maxY = Math.max(Math.max(p1.y, p2.y), p3.y);

        return (position.x >= minX && position.x <= maxX && position.z >= minY && position.z <= maxY);
    }

    public static boolean cylinderCollision(Vector2 p1, float r1, Vector2 p2, float r2) {
        float dist = (float) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
        return dist < (r1 + r2);
    }

}
