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
package org.apache.yoko;

import static test.fvd.Marshalling.VERSION1;
import static test.fvd.Marshalling.VERSION2;
import test.fvd.MissingFieldsClient;
import test.fvd.MissingFieldsServer;

public class FVDTest extends AbstractOrbTestBase {
    public void testMissingFields() throws Exception {
        if (true) return;
        final String refFile = "missingfields.ref";
        setWaitForFile(refFile);
        runServerClientTest(MissingFieldsServer.class, MissingFieldsClient.class, refFile, VERSION1.name(), VERSION2.name());
    }
}
