/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.yoko.bindings.corba.types;

import javax.xml.namespace.QName;

import org.apache.yoko.bindings.corba.CorbaTypeMap;
import org.apache.yoko.wsdl.CorbaConstants;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

import junit.framework.TestCase;

public class CorbaAnyHandlerTest extends TestCase {

    private ORB orb;
    
    public CorbaAnyHandlerTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(CorbaAnyHandlerTest.class);
    }
    
    protected void setUp() throws Exception {
        super.setUp();

        java.util.Properties props = System.getProperties();
        props.put("org.omg.CORBA.ORBClass", "org.apache.yoko.orb.CORBA.ORB");
        props.put("org.omg.CORBA.ORBSingletonClass", "org.apache.yoko.orb.CORBA.ORBSingleton");
        props.put("yoko.orb.id", "Yoko-Server-Binding");
        orb = ORB.init(new String[0], props);
    }
    
    protected void tearDown() throws Exception {
        if (orb != null) {
            try {
                orb.destroy();
            } catch (Exception ex) {
                // Do nothing.  Throw an Exception?
            }
        } 
    }

    public void testCorbaAnyHandler() {

        Any a = orb.create_any();
        a.insert_string("TestMessage");

        QName anyName = new QName("AnyHandlerName");
        QName anyIdlType = CorbaConstants.NT_CORBA_ANY;
        TypeCode anyTC = orb.get_primitive_tc(TCKind.from_int(TCKind._tk_any));
        CorbaTypeMap tm = new CorbaTypeMap(CorbaConstants.NU_WSDL_CORBA);
        
        CorbaAnyHandler anyHandler = new CorbaAnyHandler(anyName, anyIdlType, anyTC, null);
        anyHandler.setTypeMap(tm);

        // Test the get/set value methods
        anyHandler.setValue(a);
        Any resultAny = anyHandler.getValue();

        assertNotNull(resultAny);

        try {
            String value = resultAny.extract_string();
            assertTrue(value.equals("TestMessage"));
        } catch (Exception ex) {
            assertTrue(false);
        }

        // Test get/set CorbaTypeMap methods
        CorbaTypeMap resultTM = anyHandler.getTypeMap();
        assertTrue(resultTM.getTargetNamespace().equals(CorbaConstants.NU_WSDL_CORBA));
    }
}
