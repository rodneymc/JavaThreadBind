#include <jni.h>
#include <string.h>
#include "mybind.h"

// This include generated in the build directory at compile time
#include "bld/com_daftdroid_droidlib_bind_ClientBind.h"


static JavaVM* jvm;
static jclass bindSetterClass;
static jmethodID getterMethod;

int thread_bind_addr_getter(char * buf)
{
       JNIEnv * env;
        (*jvm)->GetEnv(jvm, (void **)&env, JNI_VERSION_1_6);
	const char *strReturn;
	jobject rv = (*env)->CallStaticObjectMethod(env, bindSetterClass, getterMethod);

	if (rv == NULL)
		return 0;

	strReturn = (*env)->GetStringUTFChars(env, rv, 0);
	strcpy(buf, strReturn);
	(*env)->ReleaseStringUTFChars(env, rv, strReturn);
	return 1;
}

JNIEXPORT void JNICALL Java_com_daftdroid_droidlib_bind_ClientBind_initialiseBindSetter
  (JNIEnv *, jclass)
{

	(*env)->GetJavaVM(env, &jvm);
	bindSetterClass = (*env)->NewGlobalRef(env, class);
	getterMethod = (*env)->GetStaticMethodID(env, class, "getThreadBindVal", "()Ljava/lang/String;");
	set_thread_bind_addr_getter(thread_bind_addr_getter);
}

