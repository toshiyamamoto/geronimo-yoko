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

package test.obv.TestOBVColoPackage;

//
// IDL:TestOBVColo/UV:1.0
//
/***/

final public class UV implements org.omg.CORBA.portable.IDLEntity
{
    java.lang.Object _ob_v_;
    boolean _ob_i_;
    int _ob_d_;

    static boolean
    _OB_check(int d0, int d1)
    {
        return d0 == d1;
    }

    public
    UV()
    {
        _ob_i_ = false;
    }

    public boolean
    discriminator()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        return _ob_d_ == 0 ? false : true;
    }

    public String
    str()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, 1))
            throw new org.omg.CORBA.BAD_OPERATION();

        return (String)_ob_v_;
    }

    public void
    str(String val)
    {
        _ob_i_ = true;
        _ob_d_ = 1;
        _ob_v_ = val;
    }

    public test.obv.TestValue
    val()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, 0))
            throw new org.omg.CORBA.BAD_OPERATION();

        return (test.obv.TestValue)_ob_v_;
    }

    public void
    val(test.obv.TestValue val)
    {
        _ob_i_ = true;
        _ob_d_ = 0;
        _ob_v_ = val;
    }
}
