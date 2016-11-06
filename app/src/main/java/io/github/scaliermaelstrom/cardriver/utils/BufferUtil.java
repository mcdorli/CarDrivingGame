package io.github.scaliermaelstrom.cardriver.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {

    public static FloatBuffer createFromData(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data);
        buffer.position(0);
        return buffer;
    }

    public static IntBuffer createFromData(int[] data) {
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data);
        buffer.position(0);
        return buffer;
    }

}
