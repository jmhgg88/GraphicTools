package com.android.graphictools;

/**
 * Created by hp on 2018/6/9.
 */

public class GraphicToolsJni {
    static {
        System.loadLibrary("GraphicTools_jni");
    }
    public native boolean Init();
    public native boolean Operate(int opt);
    public native void Destory();
}
