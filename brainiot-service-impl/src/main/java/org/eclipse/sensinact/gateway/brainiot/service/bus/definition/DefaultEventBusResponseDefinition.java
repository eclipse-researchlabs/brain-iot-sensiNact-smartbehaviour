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
 * Default and basis {@link EventBusResponseDefinition} implementation
 */
public class DefaultEventBusResponseDefinition implements EventBusResponseDefinition {

	private int status;
	private String path;
	private String content;
	private String type;
	private String token;
	private long timestamp;

	@Override
	public int getStatus() {
		return this.status;
	}
	
	/**
	 * Define  the response integer status code
	 * 
	 * @param status the status code of the response
	 */
	public  void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	/**
	 * Define  the String path of the targeted resource 
	 * 
	 * @param path the String path of the resource
	 */
	public  void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String getType() {
		return this.type;
	}

	/**
	 * Defines the String type of the initial request to which the response responds 
	 * 
	 * @param type the String type of the initial request 
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * Defines the JSON String formated response content 
	 * 
	 * @param content the JSON String content of the response
	 */
	public  void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getToken() {
		return this.token;
	}
	
	/**
	 * Defines the String identifier of the requirer session
	 * 
	 * @param token the String identifier of the sessions of the requirer
	 */
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Defines the long timestamp of the value of the resource on which applied the request whose response is described 
	 * by this EventBusResponseDefinition
	 * 
	 * @param timestamp the long timestamp of the value of the resource on which applied the request
	 */
	public void setTimstamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
