#include <jni.h>

JNIEXPORT jlong JNICALL
Java_com_example_gbrighen_fibjni_MainActivity_fibJni(JNIEnv *env, jobject instance, jlong n) {

    if(n==0) return 1;
    if(n==1) return 1;

    return Java_com_example_gbrighen_fibjni_MainActivity_fibJni(env, instance, n-1) + Java_com_example_gbrighen_fibjni_MainActivity_fibJni(env, instance, n-2);
}

