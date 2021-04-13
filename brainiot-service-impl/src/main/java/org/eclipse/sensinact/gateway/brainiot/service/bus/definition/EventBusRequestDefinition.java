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
package org.eclipse.sensinact.gateway.brainiot.service.bus.definition;

import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusNotificationEvent;
import org.eclipse.sensinact.gateway.core.security.Authentication;

/**
 * sensiNact API call request built from an event
 */
public interface EventBusRequestDefinition {

	/**
	 * Returns the String access method name invoked by the request described by this
	 * EventBusRequestDefinition
	 * 
	 * @return the String access method name invoked by the request
	 */
	String getMethod();
	
	/**
	 * Returns the String path of the resource on which applied the request described by this
	 * EventBusRequestDefinition
	 * 
	 * @return the String path of the resource on which applied the request 
	 */
	String getPath();

	/**
	 * Returns the JSON formated String content of the sensiNact API call request described by this
	 * EventBusRequestDefinition
	 * 
	 * @return the JSON formated String request content  
	 */
	String getContent();

	/**
	 * Returns the {@link Authentication} allowing to identify/create the requirer session
	 * 
	 * @return the {@link Authentication} of the requirer session
	 */
	Authentication<?> getAuthentication();

	/**
	 * Returns the {@link EventBusNotificationEvent} type to be used, for subscription notifications
	 * 
	 * @return the {@link EventBusNotificationEvent} type to be used
	 */
	Class<? extends EventBusNotificationEvent> getEventBusNotificationEventType();
}
