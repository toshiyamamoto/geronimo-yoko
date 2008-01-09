/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/

package org.apache.yoko.tools.processors.idl;

import org.apache.schemas.yoko.bindings.corba.Alias;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.yoko.wsdl.CorbaTypeImpl;

public class TypedefDeferredAction extends DeferredActionBase {

    protected Alias alias;    
    
    public TypedefDeferredAction(Alias aliasType, Scope scope) {                           
        super(scope);
        alias = aliasType;                
    }                   
    
    public void doDeferredAction(XmlSchemaType stype, CorbaTypeImpl ctype) {  
        if (alias != null) {
            alias.setBasetype(ctype.getQName());            
            alias.setType(stype.getQName());
        }        
    }
       
}



