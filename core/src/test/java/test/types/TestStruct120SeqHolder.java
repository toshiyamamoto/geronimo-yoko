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

package test.types;

//
// IDL:TestStruct120Seq:1.0
//
final public class TestStruct120SeqHolder implements org.omg.CORBA.portable.Streamable
{
    public TestStruct1[] value;

    public
    TestStruct120SeqHolder()
    {
    }

    public
    TestStruct120SeqHolder(TestStruct1[] initial)
    {
        value = initial;
    }

    public void
    _read(org.omg.CORBA.portable.InputStream in)
    {
        value = TestStruct120SeqHelper.read(in);
    }

    public void
    _write(org.omg.CORBA.portable.OutputStream out)
    {
        TestStruct120SeqHelper.write(out, value);
    }

    public org.omg.CORBA.TypeCode
    _type()
    {
        return TestStruct120SeqHelper.type();
    }
}
