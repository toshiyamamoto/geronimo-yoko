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

package ORBTest;

//
// IDL:ORBTest/Intf:1.0
//
public class IntfPOATie extends IntfPOA
{
    private IntfOperations _ob_delegate_;
    private org.omg.PortableServer.POA _ob_poa_;

    public
    IntfPOATie(IntfOperations delegate)
    {
        _ob_delegate_ = delegate;
    }

    public
    IntfPOATie(IntfOperations delegate, org.omg.PortableServer.POA poa)
    {
        _ob_delegate_ = delegate;
        _ob_poa_ = poa;
    }

    public IntfOperations
    _delegate()
    {
        return _ob_delegate_;
    }

    public void
    _delegate(IntfOperations delegate)
    {
        _ob_delegate_ = delegate;
    }

    public org.omg.PortableServer.POA
    _default_POA()
    {
        if(_ob_poa_ != null)
            return _ob_poa_;
        else
            return super._default_POA();
    }

    //
    // IDL:ORBTest/Intf/get_ORB_type:1.0
    //
    public ORBType
    get_ORB_type()
    {
        return _ob_delegate_.get_ORB_type();
    }

    //
    // IDL:ORBTest/Intf/deactivate:1.0
    //
    public void
    deactivate()
    {
        _ob_delegate_.deactivate();
    }

    //
    // IDL:ORBTest/Intf/concurrent_request_execution:1.0
    //
    public boolean
    concurrent_request_execution()
    {
        return _ob_delegate_.concurrent_request_execution();
    }

    //
    // IDL:ORBTest/Intf/get_test_case_list:1.0
    //
    public TestCase[]
    get_test_case_list()
    {
        return _ob_delegate_.get_test_case_list();
    }
}
