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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusNotificationEvent;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.DefaultEventBusNotificationDefinition;
import org.eclipse.sensinact.gateway.common.execution.Executable;
import org.eclipse.sensinact.gateway.core.message.SnaMessage;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundMediator;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundRecipient;
import org.eclipse.sensinact.gateway.util.JSONUtils;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.EventBus;


/**
 * {@link NorthboundRecipient} dedicated to subscribe access method using the EventBus
 */
public class EventBusRecipient extends NorthboundRecipient {

	private Class<? extends EventBusNotificationEvent> eventBusNotificationEventType;

	/**
     * Constructor
     *
     * @param mediator the {@link NorthboundMediator} allowing the EventBusRecipient to be instantiated to interact 
     * with the OSGi host environment
     * @param eventType {@link EventBusNotificationEvent} to be used to transmit notifications
     */
    public EventBusRecipient(NorthboundMediator mediator, Class<? extends EventBusNotificationEvent> eventType) {
        super(mediator);
        this.eventBusNotificationEventType = eventType;
    }

    /**
     * @inheritDoc
     * @see org.eclipse.sensinact.gateway.core.message.Recipient#callback(java.lang.String, org.eclipse.sensinact.gateway.core.message.SnaMessage[])
     */
    public void callback(String callbackId, SnaMessage[] messages) {
        int index = 0;
        int length = messages == null ? 0 : messages.length;

        StringBuilder builder = new StringBuilder();
        builder.append(JSONUtils.OPEN_BRACE);
        builder.append("\"callbackId\" : \"");
        builder.append(callbackId);
        builder.append("\",\"messages\" :");
        builder.append(JSONUtils.OPEN_BRACKET);
        for (; index < length; index++) {
            builder.append(index == 0 ? "" : ",");
            builder.append(messages[index].getJSON());
        }
        builder.append(JSONUtils.CLOSE_BRACKET);
        builder.append(JSONUtils.CLOSE_BRACE);
        
        if(this.eventBusNotificationEventType == null) {
        	super.mediator.error("Unable to send notification - No EventBusNotificationEvent type defined : \n%s", builder.toString());
        	return;
        }
        try {
	        EventBusTranslator translator = null;
			Collection<ServiceReference<EventBusTranslator>> refs = super.mediator.getContext().getServiceReferences(
				EventBusTranslator.class, String.format("(event=%s)", eventBusNotificationEventType.getName()));
			for(Iterator<ServiceReference<EventBusTranslator>> it = refs.iterator();it.hasNext();) {
				try {
					ServiceReference<EventBusTranslator> ref = it.next();
					translator = super.mediator.getContext().getService(ref);
					if(translator!=null)
							break;
				} catch(NullPointerException | IllegalStateException e ) {
					continue;
				}
			}
			if(translator == null || !translator.handle(eventBusNotificationEventType)) {
				super.mediator.error("Unable to send notification - No appropriate translator found : \n%s", builder.toString());
	        	return;
			}
			DefaultEventBusNotificationDefinition resp = new DefaultEventBusNotificationDefinition();
			final EventBusNotificationEvent event = translator.translate(resp);
			if(event instanceof BrainIoTEvent) {
				super.mediator.callService(EventBus.class, new Executable<EventBus,Void>(){
					@Override
					public Void execute(EventBus bus) throws Exception {
						bus.deliver((BrainIoTEvent)event);
						return null;
					}
					
				});
			}
        } catch(InvalidSyntaxException e) {
        	super.mediator.error(e.getMessage(),e);
			super.mediator.error("Unable to send notification - No appropriate translator found : \n%s", builder.toString());
        }
    }
}
