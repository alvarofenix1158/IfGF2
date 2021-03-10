package com.example.gusanotoma3;

import android.content.Context;
import  android.opengl.GLSurfaceView;

public class MyGLSurFaceViewe extends GLSurfaceView{

    private final  MyGLRenderer renderer;

    public MyGLSurFaceViewe(Context context) {
        super(context);


        setEGLContextClientVersion(2);

        renderer= new MyGLRenderer();

        setRenderer(renderer);

    }

}
