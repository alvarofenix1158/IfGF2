package com.example.gusanotoma3;


import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Triangulo {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "gl_Position =  vPosition;" +
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexbuffer;
    static final int CORDS_PER_VERTEX = 3;
    static float cordenadastriangulo[] = {
            0.0f, 0.5f, 0.0f,// Arriba
            -0.5f, -0.5f, 0.0f,// Isquierda
            0.5f, -0.5f, 0.0f // Derecha
    };

    float Color[] = {0.0f, 1.0f, 0.0f, 1.0f};

    private final int mProgram;

    public Triangulo() {
        ByteBuffer bb = ByteBuffer.allocateDirect(cordenadastriangulo.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexbuffer = bb.asFloatBuffer();
        vertexbuffer.put(cordenadastriangulo);
        vertexbuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);// shaders Para cpmpilar
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // creamos un programama de open gl Vacio
        mProgram = GLES20.glCreateProgram();

        //Añadir 2 vertex shaders al programam con tuto y atributos y toda lacosa
        GLES20.glAttachShader(mProgram, vertexShader);

        GLES20.glAttachShader(mProgram, fragmentShader);

        /// Creamos ejecuable de opengl
        GLES20.glLinkProgram(mProgram);

    }

    private int PositionHandle;
    private int colorHandle;

    private  final int vertexCont= cordenadastriangulo.length / CORDS_PER_VERTEX;
    private final int vertexStride= CORDS_PER_VERTEX * 4;

    public void draw(){
        ///Añadir nustro Programa al entorno de opengl
        GLES20.glUseProgram(mProgram);

        // obtenber el identificador del miembro  vPotitiondel sombreador de vertices

        PositionHandle= GLES20.glGetAttribLocation(mProgram,"vPosition");

        GLES20.glEnableVertexAttribArray(PositionHandle);

        GLES20.glVertexAttribPointer(PositionHandle, CORDS_PER_VERTEX, GLES20.GL_FLOAT, false,vertexStride, vertexbuffer);
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, Color, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0, vertexCont);

        GLES20.glDisableVertexAttribArray(PositionHandle);
    }
}
