attribute vec4 vertex;
attribute vec2 texCoord;

varying vec2 o_texCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(void) {
    gl_Position = projection * view * model * vertex;
    o_texCoord = texCoord;
}