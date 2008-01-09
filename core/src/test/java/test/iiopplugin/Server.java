/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
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


/**
 * @version $Rev: 491396 $ $Date: 2006-12-30 22:06:13 -0800 (Sat, 30 Dec 2006) $
 */

package test.iiopplugin;

import java.util.Properties;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

public class Server {
    public static int run(ORB orb, String[] args)
            throws org.omg.CORBA.UserException {
        //
        // Resolve Root POA
        //
        POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

        //
        // Get a reference to the POA manager and activate it
        //
        POAManager manager = poa.the_POAManager();
        manager.activate();

        //
        // Create implementation object
        //
        Test_impl testImpl = new Test_impl(poa);
        Test test = testImpl._this(orb);

        //
        // Save reference. This must be done after POA manager
        // activation, otherwise there is a potential for a race
        // condition between the client sending a request and the
        // server not being ready yet.
        //
        String refFile = "Test.ref";
        try {
            String ref = orb.object_to_string(test);
            java.io.FileOutputStream file = new java.io.FileOutputStream(
                    refFile);
            java.io.PrintWriter out = new java.io.PrintWriter(file);
            out.println(ref);
            out.flush();
            file.close();
        } catch (java.io.IOException ex) {
            System.err.println("Can't write to `" + ex.getMessage() + "'");
            return 1;
        }

        //
        // Run implementation
        //
        orb.run();

        //
        // Delete file
        //
        new java.io.File(refFile).delete();

        return 0;
    }

    public static void main(String args[]) {
        java.util.Properties props = new Properties();
        props.putAll(System.getProperties());
        props.put("org.omg.CORBA.ORBClass", "org.apache.yoko.orb.CORBA.ORB");
        props.put("org.omg.CORBA.ORBSingletonClass",
                "org.apache.yoko.orb.CORBA.ORBSingleton");
        props.put("org.omg.PortableInterceptor.ORBInitializerClass.test.iiopplugin.TestORBInitializer", "");

        String[] arguments = new String[] { "-IIOPconnectionHelper", "test.iiopplugin.ServerPlugin", "-IIOPconnectionHelperArgs", ServerPlugin.SERVER_ARGS };

        int status = 0;
        ORB orb = null;

        try {
            orb = ORB.init(arguments, props);
            status = run(orb, arguments);
        } catch (Exception ex) {
            ex.printStackTrace();
            status = 1;
        }

        if (orb != null) {
            try {
                orb.destroy();
            } catch (Exception ex) {
                ex.printStackTrace();
                status = 1;
            }
        }

        System.exit(status);
    }
}
