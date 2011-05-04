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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CommonConfiguration {

  public static Resource partnerSchemaResource = new ClassPathResource("/partner-schema.xsd");

  public static String NAMESPACE = "http://greenbeans.springsource.org/integration/shipping/schemas";

  @Bean
  public Marshaller marshaller() {
    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    jaxb2Marshaller.setClassesToBeBound(PurchaseProcessingRequest.class, PurchaseLineItem.class, PurchaseProcessingResponse.class);
    jaxb2Marshaller.setSchema(partnerSchemaResource);
    return jaxb2Marshaller;
  }
}
