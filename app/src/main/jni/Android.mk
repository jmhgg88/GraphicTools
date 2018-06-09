LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := libGraphicTools_jni
LOCAL_LDLIBS += -llog
LOCAL_SRC_FILES := com_android_graphictools_GraphicToolsJni.cpp

include $(BUILD_SHARED_LIBRARY)
