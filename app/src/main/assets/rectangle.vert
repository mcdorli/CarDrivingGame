attribute vec4 vertex;
attribute vec2 texCoord;

varying vec2 o_texCoord;

uniform float angle;
uniform vec3 position;
uniform vec2 size;
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(void) {
    mat4 rot = mat4(
        cos(angle), -sin(angle), 0, 0,
        sin(angle), cos(angle), 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0
    );

    gl_Position = projection * (view * model * vec4(position, 1) + rot * (vertex * vec4(size, 1, 1)));
    o_texCoord = texCoord;
}