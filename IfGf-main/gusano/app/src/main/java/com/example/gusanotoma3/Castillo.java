package com.example.gusanotoma3;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


public class Castillo {

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
    private short drawOrder[] = { 0,1,2,0,2,3,4,5,3,5,6,3,7,8,9,7,9,10,11,12,13,11,14,13};

    private FloatBuffer vertexbuffer;
    static final int CORDS_PER_VERTEX = 3;
    static float cordenadastriangulo[] = {
            -1.0f, 0.4f, 0.0f,//0
            -1.0f, -1.0f, 0.0f,//1
            1.0f, -1.0f, 0.0f,//2
            1.0f, 0.4f, 0.0f,//3
            1.0f, 0.9f, 0.0f,//4
            0.5f, 0.9f, 0.0f,//5
            0.5f, 0.4f, 0.0f,//6
            0.2f, 0.4f, 0.0f,//7
            0.2f, 0.9f, 0.0f,//8
            -0.3f, 0.9f, 0.0f,//9
            -0.3f, 0.4f, 0.0f,//10
            -0.5f, 0.4f, 0.0f,//11
            -0.5f, 0.9f, 0.0f,//12
            -1.0f, 0.9f, 0.0f,//13
            -1.0f, 0.4f, 0.0f,//14
    };

    float Color[] = {0.0f, 1.0f, 0.0f, 1.0f};

    private final int mProgram;

    private ShortBuffer drawListBuffer;

    public Castillo() {
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        
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

        //GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,drawOrder.length, vertexCont);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(PositionHandle);
    }
}
