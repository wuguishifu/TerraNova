#version 460 core

// input values
layout(location = 0) in vec3 position;
layout(location = 1) in vec2 textureCoord;
layout(location = 2) in vec3 normal;
layout(location = 3) in vec3 tangent;
layout(location = 4) in vec3 bitangent;

// output values
out mat3 TBN;
out vec3 passNormal;
out vec3 passTangent;
out vec3 passBitangent;
out vec3 passFragPos;
out vec2 passTextureCoord;

// the model, view, and projection matrices to render at
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

// the main runnable
void main() {
    // set the position of this vertex
    gl_Position = projection * view * model * vec4(position, 1.0);

    // set the fragment position of this vertex in relation to the model and pass it to the fragment shader
    passFragPos = vec3(model * vec4(position, 1.0));

    // pass the normal vector for the specified vertex to the fragment shader
    passNormal = normalize(vec3(model * vec4(normal, 0.0)));
    passTangent = normalize(vec3(model * vec4(tangent, 0.0)));
    passBitangent = normalize(vec3(model * vec4(bitangent, 0.0)));
    TBN = mat3(passTangent, passBitangent, passNormal);

    // pass the color
    passTextureCoord = textureCoord;
}
