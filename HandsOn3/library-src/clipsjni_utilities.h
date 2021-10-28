#include "net_sf_clipsrules_jni_Environment.h"
#include "clips.h"

#ifndef _Included_clipsjni_utilities
#define _Included_clipsjni_utilities

void       ConvertPrimitiveValueToDataObject(Environment *,jobject,UDFValue *);
jobject    ConvertDataObject(JNIEnv *,jobject,Environment *,CLIPSValue *);
jobject    ConvertSingleFieldValue(JNIEnv *,jobject,Environment *,int,void *);
void      *JLongToPointer(jlong);
jlong      PointerToJLong(void *);

#endif