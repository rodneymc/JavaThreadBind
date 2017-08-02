/*	
*
(c) 2017 rodney@daftdroid.com

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/

#include <jni.h>
#include <string.h>
#include "mybind.h"


static JavaVM* jvm;
static jclass bindSetterClass;
static jmethodID getterMethod;

static int thread_bind_addr_getter(char * buf)
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
  (JNIEnv * env, jclass class)
{

	(*env)->GetJavaVM(env, &jvm);
	bindSetterClass = (*env)->NewGlobalRef(env, class);
	getterMethod = (*env)->GetStaticMethodID(env, class, "getThreadBindVal", "()Ljava/lang/String;");
	set_thread_bind_addr_getter(thread_bind_addr_getter);
}

