package io.github.scaliermaelstrom.cardriver.rendering;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

import io.github.scaliermaelstrom.cardriver.MainActivity;

import static android.opengl.GLES20.*;

public class Texture {

    private int texture;

    public Texture(int resourceId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        InputStream is = MainActivity.context.getResources().openRawResource(resourceId);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        int[] temp = new int[1];
        glGenTextures(1, temp, 0);
        texture = temp[0];

        glBindTexture(GL_TEXTURE_2D, texture);


        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        bitmap.recycle();
    }

    public void bind() {
        bind(0);
    }

    public void bind(int index) {
        glActiveTexture(GL_TEXTURE0 + index);
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void cleanup() {
        glDeleteTextures(1, new int[]{texture}, 0);
    }

}
