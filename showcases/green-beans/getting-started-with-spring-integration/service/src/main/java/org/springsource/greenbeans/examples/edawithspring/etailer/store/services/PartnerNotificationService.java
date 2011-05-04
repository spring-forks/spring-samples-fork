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
package org.springsource.greenbeans.examples.edawithspring.etailer.store.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.Payload;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Purchase;

/**
 * this service is responsible for publishing notifications to our partner services, including shipping.
 * Since the entire integration with the partner solution lives on the other end of a Spring Integration
 * {@link org.springframework.integration.MessageChannel}, we provide our service this interface
 * to insulate it from the specific Spring Integration API.
 *
 * @author Josh Long
 *
 */
public interface PartnerNotificationService {

    /**
     *
     * @param purchase the purchase to send as the payload of the trigger {@link org.springframework.integration.Message}
     */
    @Gateway(requestChannel = "partnerNotifications")
    void publishNotification( @Payload Purchase purchase ) ;
}
