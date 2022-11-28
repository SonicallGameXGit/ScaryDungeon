#version 330

in vec2 texcoordFrag;
in float fogginess;

uniform vec4 color;
uniform vec2 textureOffset;
uniform vec2 textureScale;

uniform sampler2D textureSampler;

out vec4 fragColor;

void main() {
    vec4 texture = texture2D(textureSampler, (texcoordFrag + textureOffset) * textureScale);
    fragColor = vec4(color * texture);
    fragColor.rgb = mix(fragColor.rgb, vec3(0.0), pow(fogginess, 2.0));
}