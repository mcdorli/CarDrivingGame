package io.github.scaliermaelstrom.cardriver.shaders;

import io.github.scaliermaelstrom.cardriver.rendering.Camera;
import io.github.scaliermaelstrom.cardriver.rendering.Terrain;
import io.github.scaliermaelstrom.cardriver.utils.FileUtil;

import static android.opengl.GLES20.*;

public class TerrainShader extends Shader {

    private int vertexAttrib;
    private int texCoordAttrib;

    public TerrainShader() {
        super(FileUtil.readAsString("terrain.vert"), FileUtil.readAsString("terrain.frag"));
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

    public void draw(Terrain terrain, Camera camera) {
        super.bind();

        // ROAD

        glEnableVertexAttribArray(vertexAttrib);
        glEnableVertexAttribArray(texCoordAttrib);

        glVertexAttribPointer(vertexAttrib, 3, GL_FLOAT, false, 0, terrain.meshBuffer);
        glVertexAttribPointer(texCoordAttrib, 2, GL_FLOAT, false, 0, terrain.meshTextureBuffer);

        terrain.road.bind(0);
        glUniform1i(getUniformLocation("texture"), 0);

        glUniformMatrix4fv(getUniformLocation("model"), 1, false, terrain.getModelMatrix(), 0);
        glUniformMatrix4fv(getUniformLocation("view"), 1, false, camera.getViewMatrix(), 0);
        glUniformMatrix4fv(getUniformLocation("projection"), 1, false, camera.perspectiveM, 0);

        glDrawArrays(GL_TRIANGLE_STRIP, 0, terrain.vertexCount);

        // GRASS

        glVertexAttribPointer(vertexAttrib, 3, GL_FLOAT, false, 0, terrain.grassBuffer);
        glVertexAttribPointer(texCoordAttrib, 2, GL_FLOAT, false, 0, terrain.grassTextureBuffer);

        terrain.grass.bind(0);
        glUniform1i(getUniformLocation("texture"), 0);

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        // HOUSES

        glVertexAttribPointer(vertexAttrib, 3, GL_FLOAT, false, 0, terrain.houseBuffer);
        glVertexAttribPointer(texCoordAttrib, 2, GL_FLOAT, false, 0, terrain.houseTextureBuffer);

        terrain.houses.bind(0);
        glUniform1i(getUniformLocation("texture"), 0);

        glDrawArrays(GL_TRIANGLE_STRIP, 0, terrain.houseVertexCount);

    }
}
