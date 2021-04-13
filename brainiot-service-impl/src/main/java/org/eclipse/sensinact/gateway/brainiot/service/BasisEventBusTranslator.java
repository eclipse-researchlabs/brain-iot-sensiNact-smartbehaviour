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

import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusNotificationEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusRequestEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusResponseEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaAct;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaActResponse;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaGet;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaGetResponse;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaSet;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaSetResponse;
import org.eclipse.sensinact.gateway.brainiot.service.bus.EventBusTranslator;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusNotificationDefinition;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusRequestDefinition;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusResponseDefinition;
import org.eclipse.sensinact.gateway.util.UriUtils;
import org.osgi.service.component.annotations.Component;

@Component(immediate=true, 
	service = EventBusTranslator.class, 
	property = {
		"event=fr.cea.brain.iot.sensinact.api.sensinact.SnaGet",
		"event=fr.cea.brain.iot.sensinact.api.sensinact.SnaSet",
		"event=fr.cea.brain.iot.sensinact.api.sensinact.SnaAct"}
)
public class BasisEventBusTranslator implements EventBusTranslator {

	public BasisEventBusTranslator() {
	}

	@Override
	public boolean handle(Class<? extends EventBusEvent> clazz) {
		if(SnaGet.class == clazz)
			return true;
		if(SnaSet.class == clazz)
			return true;
		if(SnaAct.class == clazz)
			return true;
		return false;
	}

	@Override
	public EventBusRequestDefinition translate(EventBusRequestEvent event) {
		if(event instanceof SnaGet)
			return new DefaultEventBusRequestDefinition((SnaGet) event);
		if(event instanceof SnaSet)
			return new DefaultEventBusRequestDefinition((SnaSet) event);
		if(event instanceof SnaAct)
			return new DefaultEventBusRequestDefinition((SnaAct) event);
		return null;
	}

	@Override
	public EventBusResponseEvent translate(EventBusResponseDefinition definition) {
		String[] pathElements = UriUtils.getUriElements(definition.getPath());
		switch(definition.getType()) {
			case "GET":
				SnaGetResponse getResponse = new SnaGetResponse();
				getResponse.provider=pathElements[1];
				getResponse.service=pathElements[2];
				getResponse.resource=pathElements[3];
				getResponse.timestamp=definition.getTimestamp();
				getResponse.value=definition.getContent();
				return getResponse;
			case "SET":
				SnaSetResponse setResponse = new SnaSetResponse();
				setResponse.provider=pathElements[1];
				setResponse.service=pathElements[2];
				setResponse.resource=pathElements[3];
				setResponse.timestamp=definition.getTimestamp();
				setResponse.value=definition.getContent();
				return setResponse;
			case "ACT":
				SnaActResponse actResponse = new SnaActResponse();
				actResponse.provider=pathElements[1];
				actResponse.service=pathElements[2];
				actResponse.resource=pathElements[3];
				actResponse.status= definition.getStatus();
				actResponse.timestamp= definition.getTimestamp();
				actResponse.value=definition.getContent();
				return actResponse;
		}
		return null;
	}

	@Override
	public EventBusNotificationEvent translate(EventBusNotificationDefinition definition) {
		return null;
	}

}
