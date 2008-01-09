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

package org.apache.yoko.orb.OCI.IIOP;

//
// IDL:orb.yoko.apache.org/OCI/IIOP/TransportInfo:1.0
//
/**
 *
 * Information on an IIOP OCI Transport object.
 *
 * @see Transport
 * @see TransportInfo
 *
 **/

public interface TransportInfoOperations extends org.apache.yoko.orb.OCI.TransportInfoOperations
{
    //
    // IDL:orb.yoko.apache.org/OCI/IIOP/TransportInfo/addr:1.0
    //
    /** The local IP address. */

    String
    addr();

    //
    // IDL:orb.yoko.apache.org/OCI/IIOP/TransportInfo/port:1.0
    //
    /** The local port. */

    short
    port();

    //
    // IDL:orb.yoko.apache.org/OCI/IIOP/TransportInfo/remote_addr:1.0
    //
    /** The remote IP address. */

    String
    remote_addr();

    //
    // IDL:orb.yoko.apache.org/OCI/IIOP/TransportInfo/remote_port:1.0
    //
    /** The remote port. */

    short
    remote_port();
}
