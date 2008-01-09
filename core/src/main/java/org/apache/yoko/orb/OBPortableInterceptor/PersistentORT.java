/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
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

package org.apache.yoko.orb.OBPortableInterceptor;

//
// IDL:orb.yoko.apache.org/OBPortableInterceptor/PersistentORT:1.0
//
/***/

public abstract class PersistentORT implements org.omg.CORBA.portable.StreamableValue,
                                               ObjectReferenceTemplate
{
    //
    // IDL:orb.yoko.apache.org/OBPortableInterceptor/PersistentORT/the_server_id:1.0
    //
    /***/

    protected String the_server_id;

    //
    // IDL:orb.yoko.apache.org/OBPortableInterceptor/PersistentORT/the_orb_id:1.0
    //
    /***/

    protected String the_orb_id;

    //
    // IDL:orb.yoko.apache.org/OBPortableInterceptor/PersistentORT/the_adapter_name:1.0
    //
    /***/

    protected String[] the_adapter_name;

    //
    // IDL:orb.yoko.apache.org/OBPortableInterceptor/PersistentORT/the_ior_template:1.0
    //
    /***/

    protected org.omg.IOP.IOR the_ior_template;

    private static String[] _OB_truncatableIds_ =
    {
        PersistentORTHelper.id()
    };

    public String[]
    _truncatable_ids()
    {
        return _OB_truncatableIds_;
    }

    public void
    _read(org.omg.CORBA.portable.InputStream in)
    {
        the_server_id = org.omg.PortableInterceptor.ServerIdHelper.read(in);
        the_orb_id = org.omg.PortableInterceptor.ORBIdHelper.read(in);
        the_adapter_name = org.omg.PortableInterceptor.AdapterNameHelper.read(in);
        the_ior_template = org.omg.IOP.IORHelper.read(in);
    }

    public void
    _write(org.omg.CORBA.portable.OutputStream out)
    {
        org.omg.PortableInterceptor.ServerIdHelper.write(out, the_server_id);
        org.omg.PortableInterceptor.ORBIdHelper.write(out, the_orb_id);
        org.omg.PortableInterceptor.AdapterNameHelper.write(out, the_adapter_name);
        org.omg.IOP.IORHelper.write(out, the_ior_template);
    }

    public org.omg.CORBA.TypeCode
    _type()
    {
        return PersistentORTHelper.type();
    }
}
