precision mediump float;

varying vec2 o_texCoord;

uniform sampler2D textureSampler;

void main(void) {
    vec4 color = texture2D(textureSampler, o_texCoord);

    gl_FragColor = color;
}