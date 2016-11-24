package io.github.scaliermaelstrom.cardriver.shaders;

import io.github.scaliermaelstrom.cardriver.math.Vector2;
import io.github.scaliermaelstrom.cardriver.math.Vector3;
import io.github.scaliermaelstrom.cardriver.rendering.Rectangle;
import io.github.scaliermaelstrom.cardriver.rendering.Texture;
import io.github.scaliermaelstrom.cardriver.utils.FileUtil;

import static android.opengl.GLES20.*;

public class RectangleShader extends Shader {

    private int vertexAttrib;
    private int texCoordAttrib;

    public RectangleShader() {
        super(FileUtil.readAsString("rectangle.vert"), FileUtil.readAsString("rectangle.frag"));
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

    public void bindModel() {
        super.bind();

        glEnableVertexAttribArray(vertexAttrib);
        glVertexAttribPointer(vertexAttrib, 3, GL_FLOAT, false, 0, Rectangle.vertexBuffer);

        glEnableVertexAttribArray(texCoordAttrib);
        glVertexAttribPointer(texCoordAttrib, 2, GL_FLOAT, false, 0, Rectangle.textureCoordBuffer);
    }

    public void bind(Texture texture, float[] model, float[] view, float[] projection, Vector2 size, Vector3 position, float angle) {
        texture.bind();
        glUniform1i(getUniformLocation("textureSampler"), 0);

        glUniform1f(getUniformLocation("angle"), (float) Math.toRadians(angle));
        glUniform3f(getUniformLocation("position"), position.x, position.y, position.z);
        glUniform2f(getUniformLocation("size"), size.x, size.y);
        glUniformMatrix4fv(getUniformLocation("model"), 1, false, model, 0);
        glUniformMatrix4fv(getUniformLocation("view"), 1, false, view, 0);
        glUniformMatrix4fv(getUniformLocation("projection"), 1, false, projection, 0);
    }
}
