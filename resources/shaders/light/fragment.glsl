#version 330 core

// input values
in vec4 passColor;

// the output color
out vec4 outColor;

// the lighting values for shading
uniform vec3 lightPos;
uniform float lightLevel;

// the light color
uniform vec3 lightColor;

// the position of the camera
uniform vec3 viewPos;

void main() {
    // output color
    outColor = passColor;
}
