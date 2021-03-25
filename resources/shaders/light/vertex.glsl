#version 460 core

// input values
layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;

// output values
out vec4 passColor;

// model, view, projection matrices
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

// main runnable method
void main() {
    // set the position of this vertex
    gl_Position = projection * view * model * vec4(position, 1.0);

    // pass the color
    passColor = color;
}
