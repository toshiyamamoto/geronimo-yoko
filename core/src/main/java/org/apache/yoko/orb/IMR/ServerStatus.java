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

package org.apache.yoko.orb.IMR;

//
// IDL:orb.yoko.apache.org/IMR/ServerStatus:1.0
//
/**
 *
 * This enumeration contains the various states of a server.
 *
 **/

public class ServerStatus implements org.omg.CORBA.portable.IDLEntity
{
    private static ServerStatus [] values_ = new ServerStatus[5];
    private int value_;

    public final static int _FORKED = 0;
    public final static ServerStatus FORKED = new ServerStatus(_FORKED);
    public final static int _STARTING = 1;
    public final static ServerStatus STARTING = new ServerStatus(_STARTING);
    public final static int _RUNNING = 2;
    public final static ServerStatus RUNNING = new ServerStatus(_RUNNING);
    public final static int _STOPPING = 3;
    public final static ServerStatus STOPPING = new ServerStatus(_STOPPING);
    public final static int _STOPPED = 4;
    public final static ServerStatus STOPPED = new ServerStatus(_STOPPED);

    protected
    ServerStatus(int value)
    {
        values_[value] = this;
        value_ = value;
    }

    public int
    value()
    {
        return value_;
    }

    public static ServerStatus
    from_int(int value)
    {
        if(value < values_.length)
            return values_[value];
        else
            throw new org.omg.CORBA.BAD_PARAM("Value (" + value  + ") out of range", 25, org.omg.CORBA.CompletionStatus.COMPLETED_NO);
    }

    private java.lang.Object
    readResolve()
        throws java.io.ObjectStreamException
    {
        return from_int(value());
    }
}
