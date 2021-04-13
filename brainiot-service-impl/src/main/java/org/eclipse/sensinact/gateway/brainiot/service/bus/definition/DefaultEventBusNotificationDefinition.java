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

public class DefaultEventBusNotificationDefinition implements EventBusNotificationDefinition {

	private String path;
	private String content;
	private String callbackId;

	
	@Override
	public String getType() {
		return "CALLBACK_NOTIFICATION";
	}
	
	@Override
	public String getPath() {
		return this.path;
	}

	/**
	 * The String path of the resource sending the notification 
	 * 
	 * @param path the String path of the resource
	 */
	public  void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * Defines the JSON String formated notification content 
	 * 
	 * @param content the JSON String content of the notification
	 */
	public  void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getCallbackId() {
		return this.callbackId;
	}

	/**
	 * Defines the notification String identifier
	 * 
	 * @param callbackId the String identifier of the notification
	 */
	public  void setCallbackId(String callbackId) {
		this.callbackId = callbackId;
	}
}
