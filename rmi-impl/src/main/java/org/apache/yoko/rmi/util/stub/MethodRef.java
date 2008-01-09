/**
*
* Licensed to the Apache Software Foundation (ASF) under one or more
*  contributor license agreements.  See the NOTICE file distributed with
*  this work for additional information regarding copyright ownership.
*  The ASF licenses this file to You under the Apache License, Version 2.0
*  (the "License"); you may not use this file except in compliance with
*  the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/ 

package org.apache.yoko.rmi.util.stub;

import java.lang.reflect.Method;

/**
 * Describes the interface of a method to be generated by the stub utility
 */
public class MethodRef {
    String name;

    Class declaringClass;

    Class[] parameterTypes;

    Class returnType;

    Class[] exceptionTypes;

    int modifiers;

    Method method;

    String signature;

    public MethodRef() {

    }

    public MethodRef(Method method) {
        this.method = method;

        name = method.getName();
        setDeclaringClass(method.getDeclaringClass());
        setParameterTypes(method.getParameterTypes());
        setReturnType(method.getReturnType());
        setExceptionTypes(method.getExceptionTypes());
        setModifiers(method.getModifiers());
    }

    public Method getMethod() {
        return method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDeclaringClass(Class declaringClass) {
        this.declaringClass = declaringClass;
    }

    public Class getDeclaringClass() {
        return declaringClass;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setExceptionTypes(Class[] exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }

    public Class[] getExceptionTypes() {
        return exceptionTypes;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public int getModifiers() {
        return modifiers;
    }

    public String getSignature() {
        if (signature != null)
            return signature;

        StringBuffer sb = new StringBuffer();

        sb.append('(');

        for (int i = 0; i < parameterTypes.length; i++)
            sb.append(getSignature(parameterTypes[i]));

        sb.append(')');

        sb.append(getSignature(returnType));

        signature = sb.toString();
        return signature;
    }

    static String getSignature(Class clz) {
        if (clz.isPrimitive()) {
            if (clz == Integer.TYPE) {
                return "I";
            } else if (clz == Boolean.TYPE) {
                return "Z";
            } else if (clz == Byte.TYPE) {
                return "B";
            } else if (clz == Short.TYPE) {
                return "S";
            } else if (clz == Long.TYPE) {
                return "J";
            } else if (clz == Double.TYPE) {
                return "D";
            } else if (clz == Float.TYPE) {
                return "F";
            } else if (clz == Void.TYPE) {
                return "V";
            } else if (clz == Character.TYPE) {
                return "C";
            } else {
                throw new InternalError("cannot handle " + clz);
            }
        } else if (clz.isArray()) {
            return "[" + getSignature(clz.getComponentType());
        } else {
            return "L" + clz.getName().replace('.', '/') + ";";
        }
    }
}
