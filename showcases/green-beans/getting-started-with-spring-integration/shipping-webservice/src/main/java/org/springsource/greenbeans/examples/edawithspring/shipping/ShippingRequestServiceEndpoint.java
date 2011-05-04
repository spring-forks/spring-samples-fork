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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Controller;
import org.springsource.greenbeans.examples.edawithspring.etailer.common.PurchaseProcessingRequest;
import org.springsource.greenbeans.examples.edawithspring.etailer.common.PurchaseProcessingResponse;

import java.util.Date;

@Controller
public class ShippingRequestServiceEndpoint {

	private Log log = LogFactory.getLog(getClass());

	@ServiceActivator
	public PurchaseProcessingResponse handleRequest(PurchaseProcessingRequest processingRequest) throws Exception {
		log.info("Received request for " + ToStringBuilder.reflectionToString(processingRequest));
		return new PurchaseProcessingResponse(processingRequest.getCustomerId(), processingRequest.getPurchaseId(), new Date());
	}

}
