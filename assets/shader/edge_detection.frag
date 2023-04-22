#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 u_textureSize;

void main()
{
    float offset = 1.0 / u_textureSize.y;
    vec4 color = texture2D(u_texture, v_texCoords);
    if (color.a > 0.5)
        gl_FragColor = color;
    else {
        float alpha = texture2D(u_texture, vec2(v_texCoords.x + offset, v_texCoords.y)).a +
        texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - offset)).a +
        texture2D(u_texture, vec2(v_texCoords.x - offset, v_texCoords.y)).a +
        texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + offset)).a;

        vec4 final_color = mix(color, vec4(0.0, 0.0, 0.0, 0.8), clamp(alpha, 0.0, 1.0));
        gl_FragColor = vec4(final_color.rgb, clamp(abs(alpha) + color.a, 0.0, 1.0) );
    }
}