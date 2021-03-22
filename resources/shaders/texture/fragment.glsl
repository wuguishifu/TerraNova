#version 330 core

// input vectors
in vec2 passTextureCoord;
in vec3 passFragPos;
in vec4 passNormal;

// the lighting values for shading
uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightLevel;

// the position of the camera
uniform vec3 viewPos;

// the tex uniform
uniform sampler2D tex;

// the output color
out vec4 outColor;

void main() {

    // get the rgb components of the texture
    vec4 textureColor = texture(tex, passTextureCoord);
    vec3 color = vec3(1.0, 1.0, 1.0);

    // get the alpha value of the texture
    float alpha = textureColor.w;

    // get the xyz components of the normal vector
    vec3 normal = vec3(passNormal);

    // calculate the ambient lighting
    vec3 ambientLight = lightLevel * lightColor; // create the ambient light level

    // calculate diffusion lighting
    vec3 lightDir = normalize(lightPos - passFragPos); // find the direction of the light
    float diff = max(dot(normal, lightDir), 0.0);
    vec3 diffuseLight = diff * lightColor;

    // calculate specular lighting
    float specularStrength = 1;
    vec3 viewDir = normalize(viewPos - passFragPos);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * lightColor;

    // combine the ambient, diffusion, and specular lighting into the final fragment color
    vec3 colorResult = (ambientLight + diffuseLight + specular) * color; // combine the light components

    // pass the color
    outColor = vec4(colorResult, alpha);
}