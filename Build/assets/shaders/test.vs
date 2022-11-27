#version 330
#define MAX_FOG_DISTANCE 5.0

in vec3 position;
in vec2 texcoord;

uniform mat4 project;
uniform mat4 view;
uniform mat4 transform;

out vec2 texcoordFrag;
out float fogginess;

void main() {
    vec4 transpose = transform * vec4(position, 1.0);
    gl_Position = project * view * transpose;
    texcoordFrag = texcoord;
    fogginess = transpose.z / MAX_FOG_DISTANCE;
}
