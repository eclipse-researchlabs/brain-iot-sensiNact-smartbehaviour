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
package org.eclipse.sensinact.gateway.brainiot.service;

import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusNotificationEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaAct;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaGet;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaSet;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusRequestDefinition;
import org.eclipse.sensinact.gateway.core.DataResource;
import org.eclipse.sensinact.gateway.core.security.Authentication;
import org.eclipse.sensinact.gateway.util.UriUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DefaultEventBusRequestDefinition implements EventBusRequestDefinition {

	private String method;
	private String path;
	private String content;

	/**
	 * Constructor
	 * 
	 * @param event the {@link SnaGet} event to build the description of
	 */
	public DefaultEventBusRequestDefinition(SnaGet event) {
		this.method = "GET";
		this.path = UriUtils.getUri(new String[] {"sensinact", event.provider, event.service, event.resource, "GET"});
		JSONArray arr = new JSONArray();
		arr.put(new JSONObject().put("name","attributeName").put("type","string").put("value",DataResource.VALUE));
		this.content = arr.toString();
	}
	

	/**
	 * Constructor
	 * 
	 * @param event the {@link SnaSet} event to build the description of
	 */
	public DefaultEventBusRequestDefinition(SnaSet event) {
		this.method = "POST";
		this.path = UriUtils.getUri(new String[] {"sensinact", event.provider, event.service, event.resource, "SET"});
		JSONArray arr = new JSONArray();
		arr.put(new JSONObject().put("name","attributeName").put("type","string").put("value", DataResource.VALUE));
		arr.put(new JSONObject().put("name","value").put("type",event.type).put("value", event.value));
		this.content = arr.toString();
	}	


	/**
	 * Constructor
	 * 
	 * @param event the {@link SnaAct} event to build the description of
	 */
	public DefaultEventBusRequestDefinition(SnaAct event) {
		this.method = "POST";
		this.path = UriUtils.getUri(new String[] {"sensinact", event.provider, event.service, event.resource, "ACT"});
		final JSONArray arr = new JSONArray();
		if(event.parameters!=null && event.parameters.size()>0) 
			event.parameters.stream().forEach(p -> arr.put(new JSONObject().put("name",p.name).put("type",p.type).put("value", p.value)));
		this.content = arr.toString();
	}	
	
	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public Authentication<?> getAuthentication() {
		return null;
	}

	@Override
	public Class<? extends EventBusNotificationEvent> getEventBusNotificationEventType() {
		return null;
	}

}
