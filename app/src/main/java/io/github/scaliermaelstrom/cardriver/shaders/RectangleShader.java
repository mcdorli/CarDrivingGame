package io.github.scaliermaelstrom.cardriver.shaders;

import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;
import io.github.scaliermaelstrom.cardriver.rendering.Rectangle;
import io.github.scaliermaelstrom.cardriver.rendering.Texture;
import io.github.scaliermaelstrom.cardriver.utils.FileUtil;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glBindAttribLocation;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform2f;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;

public class RectangleShader extends Shader {

    private int vertexAttrib;
    private int texCoordAttrib;

    public RectangleShader() {
        super(FileUtil.readAsString("rectangle.vert"), FileUtil.readAsString("rectangle.frag"));

        super.bind();

        glEnableVertexAttribArray(vertexAttrib);
        glVertexAttribPointer(vertexAttrib, 3, GL_FLOAT, false, 0, Rectangle.vertexBuffer);

        glEnableVertexAttribArray(texCoordAttrib);
        glVertexAttribPointer(texCoordAttrib, 2, GL_FLOAT, false, 0, Rectangle.textureCoordBuffer);
    }

    @Override
    protected void bindAttributes() {
        glBindAttribLocation(program, 0, "vertex");
        glBindAttribLocation(program, 1, "texCoord");
    }

    @Override
    protected void getAttribLocations() {
        vertexAttrib = glGetAttribLocation(program, "vertex");
        texCoordAttrib = glGetAttribLocation(program, "texCoord");
    }

    public void bind(Texture texture, float[] model, float[] view, float[] projection, Vector2 size, Vector3 position) {
        texture.bind();
        glUniform1i(getUniformLocation("textureSampler"), 0);

        glUniform3f(getUniformLocation("position"), position.x, position.y, position.z);
        glUniform2f(getUniformLocation("size"), size.x, size.y);
        glUniformMatrix4fv(getUniformLocation("model"), 1, false, model, 0);
        glUniformMatrix4fv(getUniformLocation("view"), 1, false, view, 0);
        glUniformMatrix4fv(getUniformLocation("projection"), 1, false, projection, 0);
    }
}
