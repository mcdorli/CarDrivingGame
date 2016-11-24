package io.github.scaliermaelstrom.cardriver.shaders;

import android.util.Log;

import java.util.HashMap;

import static android.opengl.GLES20.*;

public abstract class Shader {

    protected int program;
    private HashMap<String, Integer> uniforms = new HashMap<>();

    public Shader(String vertexSource, String fragmentSource) {
        int vertex = createShader(vertexSource, GL_VERTEX_SHADER);
        int fragment = createShader(fragmentSource, GL_FRAGMENT_SHADER);
        program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        bindAttributes();
        glLinkProgram(program);
        int[] status = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, status, 0);
        if (status[0] == 0) {
            Log.d("CarDriver", glGetProgramInfoLog(program));
            cleanup();
        }

        glDeleteShader(vertex);
        glDeleteShader(fragment);
        getAttribLocations();
    }

    protected int createShader(String source, int type) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        int[] status = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, status, 0);
        if (status[0] == 0) {
            Log.d("CarDriver", glGetShaderInfoLog(shader));
            return -1;
        }
        return shader;
    }

    public int getUniformLocation(String name) {
        if (!uniforms.containsKey(name))
            uniforms.put(name, glGetUniformLocation(program, name));

        return uniforms.get(name);
    }

    protected abstract void bindAttributes();

    protected abstract void getAttribLocations();

    public void bind() {
        glUseProgram(program);
    }

    public void cleanup() {
        glDeleteProgram(program);
    }

}
