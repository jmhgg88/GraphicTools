LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional

#LOCAL_USE_AAPT2 := true

LOCAL_PRIVATE_PLATFORM_APIS := true
LOCAL_STATIC_JAVA_LIBRARIES := android-support-v4 \
    android-support-constraint-layout \
    android-support-constraint-layout-solver

LOCAL_STATIC_JAVA_AAR_LIBRARIES := android-support-constraint-layout
LOCAL_AAPT_FLAGS := \
    --auto-add-overlay \
    --extra-packages android.support.constraint

LOCAL_SRC_FILES := $(call all-java-files-under, java)
LOCAL_PACKAGE_NAME := GraphicTools
LOCAL_CERTIFICATE := platform
#WITH_DEXPREOPT := false
#LOCAL_JNI_SHARED_LIBRARIES :=
include $(BUILD_PACKAGE)
include $(CLEAR_VARS)
#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := sjinstruction:libs
#include $(BUILD_MULTI_PREBUILT)
include $(call all-makefiles-under,$(LOCAL_PATH))