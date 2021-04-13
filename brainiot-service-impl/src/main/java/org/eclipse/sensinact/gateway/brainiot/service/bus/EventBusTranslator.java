/*
 * Copyright (c) 2017-2020 CEA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    CEA - initial API and implementation
 */
package org.eclipse.sensinact.gateway.brainiot.service.bus;

import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusNotificationEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusRequestEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusResponseEvent;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusNotificationDefinition;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusRequestDefinition;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusResponseDefinition;


/**
 *  Provides translations feature from {@link EventBusRequestEvent} request to {@link EventBusRequestDefinition} and
 *  from {@link EventBusResponseDefinition} to {@link EventBusResponseEvent} response
 */
public interface EventBusTranslator {
	
	/**
	 * Returns true if the extended {@link EventBusEvent} passed as parameter is handled by 
	 * this EventBusTranslator  
	 * 
	 * @param clazz the extended {@link EventBusEvent} type to check whether it is handled by this 
	 * EventBusTranslator or not
	 * 
	 * @return 
	 * <ul>
	 * 		<li>true if the specified extended {@link EventBusEvent} is handled by this EventBusTranslator</li>
	 * 		<li>false otherwise</li>
	 * </ul>
	 */
	boolean handle(Class<? extends EventBusEvent> clazz);

	/**
	 * Translates the extended {@link EventBusRequestEvent} request passed as parameter into an {@link EventBusRequestDefinition}
	 * 
	 * @param eventType the {@link EventBusRequestEvent} request to be translated
	 * 
	 * @return the {@link EventBusRequestDefinition} resulting from the specified {@link EventBusRequestEvent} request translation
	 */
	 EventBusRequestDefinition translate(EventBusRequestEvent event);

	/**
	 * Translates the {@link EventBusResponsetDefinition} passed as parameter into an extended {@link EventBusResponseEvent} response
	 * 
	 * @param definition the {@link EventBusResponseDefinition} to be translated
	 * 
	 * @return the {@link EventBusResponseEvent} response resulting on the specified {@link EventBusResponseDefinition} translation
	 */
	 EventBusResponseEvent translate(EventBusResponseDefinition definition);
	 
	/**
	 * Translates the {@link EventBusNotificationDefinition} passed as parameter into an extended {@link EventBusNotificationEvent} response
	 * 
	 * @param definition the {@link EventBusNotificationDefinition} to be translated
	 * 
	 * @return the {@link EventBusNotificationEvent} response resulting on the specified {@link EventBusNotificationDefinition} translation
	 */
	 EventBusNotificationEvent translate(EventBusNotificationDefinition definition);
	 
}
