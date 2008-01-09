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
// IDL:TestValueInterface:1.0
//
/***/

public abstract class TestValueInterface implements org.omg.CORBA.portable.StreamableValue,
                                                    TestInterfaceOperations
{
    //
    // IDL:TestValueInterface/count:1.0
    //
    /***/

    public int count;

    //
    // IDL:TestValueInterface/value_op:1.0
    //
    /***/

    public abstract void
    value_op();

    private static String[] _OB_truncatableIds_ =
    {
        TestValueInterfaceHelper.id()
    };

    public String[]
    _truncatable_ids()
    {
        return _OB_truncatableIds_;
    }

    public void
    _read(org.omg.CORBA.portable.InputStream in)
    {
        count = in.read_long();
    }

    public void
    _write(org.omg.CORBA.portable.OutputStream out)
    {
        out.write_long(count);
    }

    public org.omg.CORBA.TypeCode
    _type()
    {
        return TestValueInterfaceHelper.type();
    }
}
