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

package org.omg.PortableServer.POAPackage;

//
// IDL:omg.org/PortableServer/POA/NoServant:1.0
//
final public class NoServantHolder implements org.omg.CORBA.portable.Streamable
{
    public NoServant value;

    public
    NoServantHolder()
    {
    }

    public
    NoServantHolder(NoServant initial)
    {
        value = initial;
    }

    public void
    _read(org.omg.CORBA.portable.InputStream in)
    {
        value = NoServantHelper.read(in);
    }

    public void
    _write(org.omg.CORBA.portable.OutputStream out)
    {
        NoServantHelper.write(out, value);
    }

    public org.omg.CORBA.TypeCode
    _type()
    {
        return NoServantHelper.type();
    }
}
