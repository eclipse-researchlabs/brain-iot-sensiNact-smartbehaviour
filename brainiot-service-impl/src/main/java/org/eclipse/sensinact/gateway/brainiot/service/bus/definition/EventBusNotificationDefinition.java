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
 * sensiNact API notification to be translated into an event
 */
public interface EventBusNotificationDefinition {
	
	/**
	 * Returns the String path of callback resource path
	 * 
	 * @return the String path of the callback
	 */
	String getPath();

	/**
	 * Returns the String type of the callback notification
	 * 
	 * @return the String type of the callback
	 */
	String getType();

	/**
	 * Returns the String content of the callback notification
	 * 
	 * @return the  String content of the callback 
	 */
	String getContent();
	
	/**
	 * Returns the String callback notification identifier
	 * 
	 * @return the String identifier of the callback 
	 */
	String getCallbackId();	

}
