precision mediump float;

varying vec2 o_texCoord;

uniform sampler2D texture;
uniform float isRoad;

void main(void) {
    vec4 color;

    color = texture2D(texture, o_texCoord);

    gl_FragColor = color;
}