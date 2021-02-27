#version 410

uniform vec3 u_colour1;			// colour as a 3D vector (r,g,b)
uniform vec3 u_colour2;			// colour as a 3D vector (r,g,b)
uniform vec2 u_screenSize;  	// screen dimensions in pixels

layout(location = 0) out vec4 o_colour;	// output to colour buffer (r,g,b,a)

void main() {
	float h = gl_FragCoord.y / u_screenSize.y;

    o_colour = vec4(mix(u_colour1, u_colour2, h), 1);
}
