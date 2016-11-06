varying vec2 o_texCoord;

uniform sampler2D textureSampler;

void main(void) {
    gl_FragColor = texture2D(textureSampler, o_texCoord);
}