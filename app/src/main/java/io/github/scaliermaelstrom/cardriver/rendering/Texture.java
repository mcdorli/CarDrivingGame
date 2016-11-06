package io.github.scaliermaelstrom.cardriver.rendering;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import io.github.scaliermaelstrom.cardriver.MainActivity;

import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glTexParameteri;

public class Texture {

    private int texture;

    public Texture(int resourceId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Resources resources = MainActivity.context.getResources();

        Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

        int[] temp = new int[1];
        glGenTextures(1, temp, 0);
        texture = temp[0];

        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();
    }

    public void bind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void cleanup() {
        glDeleteTextures(1, new int[] {texture}, 0);
    }

}
