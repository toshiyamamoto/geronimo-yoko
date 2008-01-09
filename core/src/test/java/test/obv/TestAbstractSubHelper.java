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

package test.obv;

//
// IDL:TestAbstractSub:1.0
//
final public class TestAbstractSubHelper
{
    public static void
    insert(org.omg.CORBA.Any any, TestAbstractSub val)
    {
        any.insert_Object(val, type());
    }

    public static TestAbstractSub
    extract(org.omg.CORBA.Any any)
    {
        if(any.type().equivalent(type()))
            return narrow(any.extract_Object());

        throw new org.omg.CORBA.BAD_OPERATION();
    }

    private static org.omg.CORBA.TypeCode typeCode_;

    public static org.omg.CORBA.TypeCode
    type()
    {
        if(typeCode_ == null)
        {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            typeCode_ = orb.create_interface_tc(id(), "TestAbstractSub");
        }

        return typeCode_;
    }

    public static String
    id()
    {
        return "IDL:TestAbstractSub:1.0";
    }

    public static TestAbstractSub
    read(org.omg.CORBA.portable.InputStream in)
    {
        org.omg.CORBA.Object _ob_v = in.read_Object();

        try
        {
            return (TestAbstractSub)_ob_v;
        }
        catch(ClassCastException ex)
        {
        }

        org.omg.CORBA.portable.ObjectImpl _ob_impl;
        _ob_impl = (org.omg.CORBA.portable.ObjectImpl)_ob_v;
        _TestAbstractSubStub _ob_stub = new _TestAbstractSubStub();
        _ob_stub._set_delegate(_ob_impl._get_delegate());
        return _ob_stub;
    }

    public static void
    write(org.omg.CORBA.portable.OutputStream out, TestAbstractSub val)
    {
        out.write_Object(val);
    }

    public static TestAbstractSub
    narrow(java.lang.Object val)
    {
        if(val != null)
        {
            if(val instanceof org.omg.CORBA.Object)
                return narrow((org.omg.CORBA.Object)val);
            throw new org.omg.CORBA.BAD_PARAM();
        }

        return null;
    }

    public static TestAbstractSub
    unchecked_narrow(java.lang.Object val)
    {
        if(val != null)
        {
            if(val instanceof org.omg.CORBA.Object)
                return unchecked_narrow((org.omg.CORBA.Object)val);
            throw new org.omg.CORBA.BAD_PARAM();
        }

        return null;
    }

    public static TestAbstractSub
    narrow(org.omg.CORBA.Object val)
    {
        if(val != null)
        {
            try
            {
                return (TestAbstractSub)val;
            }
            catch(ClassCastException ex)
            {
            }

            if(val._is_a(id()))
            {
                org.omg.CORBA.portable.ObjectImpl _ob_impl;
                _TestAbstractSubStub _ob_stub = new _TestAbstractSubStub();
                _ob_impl = (org.omg.CORBA.portable.ObjectImpl)val;
                _ob_stub._set_delegate(_ob_impl._get_delegate());
                return _ob_stub;
            }

            throw new org.omg.CORBA.BAD_PARAM();
        }

        return null;
    }

    public static TestAbstractSub
    unchecked_narrow(org.omg.CORBA.Object val)
    {
        if(val != null)
        {
            try
            {
                return (TestAbstractSub)val;
            }
            catch(ClassCastException ex)
            {
            }

            org.omg.CORBA.portable.ObjectImpl _ob_impl;
            _TestAbstractSubStub _ob_stub = new _TestAbstractSubStub();
            _ob_impl = (org.omg.CORBA.portable.ObjectImpl)val;
            _ob_stub._set_delegate(_ob_impl._get_delegate());
            return _ob_stub;
        }

        return null;
    }
}
