/*
* Copyright 2006-2007 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.springsource.greenbeans.examples.edawithspring.etailer.common;

import org.apache.commons.lang.SystemUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class MarshallingTests {

    private File file  ;

    @Before
    public void start() throws Exception{
        file = new File(SystemUtils.getJavaIoTmpDir(), "output.xml");
    }

    @After
    public void after(){
        if(this.file.exists())
            this.file.delete();
    }

    @Test
    public void testMarshallingOurObjectGraphToXml() throws Exception {
        PurchaseProcessingRequest request = new PurchaseProcessingRequest();
        buildPurchaseLineItemObject(request);
        FileOutputStream fileOutputStream = new FileOutputStream(this.file);
        StreamResult streamResult = new StreamResult(fileOutputStream);
        jaxb2Marshaller().marshal(request, streamResult);
    }

    private Marshaller jaxb2Marshaller() throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(PurchaseProcessingRequest.class, PurchaseLineItem.class);
        jaxb2Marshaller.setSchema(new ClassPathResource("/partner-schema.xsd"));
        jaxb2Marshaller.afterPropertiesSet();
        jaxb2Marshaller.setBeanClassLoader(Thread.currentThread().getContextClassLoader());
        return jaxb2Marshaller;
    }

    private PurchaseLineItem buildPurchaseLineItemObject(PurchaseProcessingRequest request){
        PurchaseLineItem lineItem;

        lineItem = new PurchaseLineItem();
        lineItem.setFulfillmentDate(new Date());
        lineItem.setProductId(232);
        lineItem.setId(24345);
        request.addLineItem(lineItem);

        lineItem = new PurchaseLineItem();
        lineItem.setFulfillmentDate(new Date());
        lineItem.setProductId(22);
        lineItem.setId(256645);
        request.addLineItem(lineItem);
        return lineItem;
    }
}
