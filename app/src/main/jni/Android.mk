LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := fib-jni
LOCAL_SRC_FILES := fib-jni.c

include $(BUILD_SHARED_LIBRARY)
