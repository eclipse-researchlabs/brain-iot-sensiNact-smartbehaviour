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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusRequestDefinition;
import org.eclipse.sensinact.gateway.core.method.Parameter;
import org.eclipse.sensinact.gateway.core.security.Authentication;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundMediator;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundRecipient;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundRequestWrapper;

/**
 * Extended {@link NorthboundRequestWrapper} dedicated to EventBus request wrapper
 */
public class EventBusAccessRequest implements NorthboundRequestWrapper {
    private NorthboundMediator mediator;
    private EventBusRequestDefinition request;

    public EventBusAccessRequest(NorthboundMediator mediator, EventBusRequestDefinition request) {
        this.request = request;
        this.mediator = mediator;
    }

    @Override
    public NorthboundMediator getMediator() {
        return this.mediator;
    }

    /**
     * Return the name of the invoked access method
     * 
     * @return the invoked access method name
     */
    public String getMethod() {
    	return this.request.getMethod();
    }
    
    @Override
    public String getRequestURI() {
        return request.getPath();
    }

    @Override
    public String getRequestId() {    	
        return null;
    }

    @Override
    public String getRequestIdProperty() {    	
    	return null;
    }

    @Override
    public Map<String, List<String>> getQueryMap() {
    		return Collections.<String, List<String>>emptyMap();
    }

    @Override
    public String getContent() {
    	return request.getContent();
    }

    @Override
    public Authentication<?> getAuthentication() {        
        return request.getAuthentication();
    }
    
    @Override
    public NorthboundRecipient createRecipient(List<Parameter> parameters) {
        NorthboundRecipient recipient = new EventBusRecipient(mediator, request.getEventBusNotificationEventType());
        return recipient;
    }
}
