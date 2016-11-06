attribute vec3 vertex;
attribute vec2 texCoord;

varying vec2 o_texCoord;

uniform vec3 position;
uniform vec2 size;
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(void) {
    gl_Position = projection * ((view * vec4(position, 1) + vec4(vertex, 1)) * vec4(size, 1, 1));
    o_texCoord = texCoord;
}