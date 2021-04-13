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

/**
 * sensiNact API call response to be translated into an {@link BrainIoTEvent}
 */
public interface EventBusResponseDefinition {
	
	/**
	 * Returns integer status code of the sensiNact API call response described by this EventBusResponseDefinition
	 * 
	 * @return the integer status code of the response 
	 */
	int getStatus();

	/**
	 * Returns the String path of the resource on which applied the request whose response is described by this
	 * EventBusResponseDefinition
	 * 
	 * @return the String path of the resource on which applied the request 
	 */
	String getPath();

	/**
	 * Returns the sensiNact API call String type of the response described by this EventBusResponseDefinition
	 * 
	 * @return the String response type 
	 */
	String getType();

	/**
	 * Returns the sensiNact API call JSON String formated response
	 * 
	 * @return the JSON String formated response 
	 */
	String getContent();

	/**
	 * Returns the String formated token identifying the requirer session
	 * 
	 * @return the String formated token 
	 */
	String getToken();

	/**
	 * Returns the long timestamp of the value of the resource on which applied the request whose response is described by this
	 * EventBusResponseDefinition
	 * 
	 * @return the long timestamp of the value of the resource on which applied the request
	 */
	long getTimestamp();
}
