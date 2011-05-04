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

package org.springsource.greenbeans.examples.edawithspring.shipping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MessageEndpoint;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.server.endpoint.mapping.PayloadRootQNameEndpointMapping;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import org.springframework.ws.transport.http.WebServiceMessageReceiverHandlerAdapter;
import org.springframework.ws.transport.http.WsdlDefinitionHandlerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springsource.greenbeans.examples.edawithspring.etailer.common.CommonConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShippingServiceConfiguration extends CommonConfiguration {
  private String locationUrl = "shipping";
  private String purchaseProcessingRequestPort = "purchase-processing-request";

  //the ws:inbound-gateway is in fact a reference to this base Spring WS object
  @Value("#{wsInboundGateway}")
  private MessageEndpoint marshallingWebServiceInboundGateway;

  @Bean
  public XsdSchema schema() {
    SimpleXsdSchema xsdSchema = new SimpleXsdSchema();
    xsdSchema.setXsd(partnerSchemaResource);
    return xsdSchema;
  }

  /**
   * http://localhost:8080/ws/tickets.wsdl
   */
  @Bean
  public DefaultWsdl11Definition shipping() throws Throwable {
    DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
    defaultWsdl11Definition.setSchema(schema());
    defaultWsdl11Definition.setPortTypeName(purchaseProcessingRequestPort);
    defaultWsdl11Definition.setLocationUri("/" + locationUrl);
    defaultWsdl11Definition.setTargetNamespace(NAMESPACE);

    return defaultWsdl11Definition;
  }

  @Bean
  public PayloadRootQNameEndpointMapping payloadRootQNameEndpointMapping() {

    String fqn = String.format("{%s}%s", NAMESPACE, purchaseProcessingRequestPort);

    Map<String, MessageEndpoint> endpoints = new HashMap<String, MessageEndpoint>();
    endpoints.put(fqn, this.marshallingWebServiceInboundGateway);

    PayloadRootQNameEndpointMapping payloadRootQNameEndpointMapping = new PayloadRootQNameEndpointMapping();
    payloadRootQNameEndpointMapping.setEndpointMap(endpoints);
    payloadRootQNameEndpointMapping.setInterceptors(new EndpointInterceptor[]{new PayloadLoggingInterceptor()});
    return payloadRootQNameEndpointMapping;
  }

  @Bean
  public WsdlDefinitionHandlerAdapter wsdlDefinitionHandlerAdapter() {
    WsdlDefinitionHandlerAdapter wsdlHandler = new WsdlDefinitionHandlerAdapter();
    wsdlHandler.setTransformLocations(true);
    return wsdlHandler;
  }

  @Bean
  public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
    SimpleUrlHandlerMapping simpleHandlerMapping = new SimpleUrlHandlerMapping();
    simpleHandlerMapping.setDefaultHandler(soapMessageDispatcher());
    Properties urlMap = new Properties();
    urlMap.setProperty("*.wsdl", this.locationUrl);
    simpleHandlerMapping.setMappings(urlMap);
    return simpleHandlerMapping;
  }

  @Bean
  public SaajSoapMessageFactory factory() {
    return new SaajSoapMessageFactory();
  }

  @Bean
  public WebServiceMessageReceiverHandlerAdapter webServiceMessageReceiverHAndlerAdapter() {
    WebServiceMessageReceiverHandlerAdapter wsHandler = new WebServiceMessageReceiverHandlerAdapter();
    wsHandler.setMessageFactory(factory());
    return wsHandler;
  }

  @Bean
  public SoapMessageDispatcher soapMessageDispatcher() {
    return new SoapMessageDispatcher();
  }
}
