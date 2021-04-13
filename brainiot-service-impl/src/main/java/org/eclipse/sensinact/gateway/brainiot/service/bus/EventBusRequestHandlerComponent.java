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

import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusRequestEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusResponseEvent;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.DefaultEventBusResponseDefinition;
import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.EventBusRequestDefinition;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundMediator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(immediate = true, service = EventBusRequestHandlerComponent.class)
public class EventBusRequestHandlerComponent {

	private static final Logger LOG = LoggerFactory.getLogger(EventBusRequestHandlerComponent.class);
	private BundleContext bundleContext;
	private NorthboundMediator mediator;

	@Activate
	public void activate(ComponentContext context) {
		LOG.info("EventBusRequestHandlerComponent ACTIVATED");
		this.bundleContext = context.getBundleContext();
		this.mediator = new NorthboundMediator(bundleContext);
	}

	@Deactivate
	public void deactivate() {
		LOG.info("EventBusRequestHandlerComponent DEACTIVATED");
		this.mediator = null;
	}
	
	public EventBusResponseEvent handle(EventBusRequestEvent event) {

		String eventBusRequestEventType = event.getClass().getName();
		System.out.println(eventBusRequestEventType);
		try {
			EventBusTranslator translator = null;
			Collection<ServiceReference<EventBusTranslator>> refs = this.bundleContext.getServiceReferences(
				EventBusTranslator.class, String.format("(event=%s)", eventBusRequestEventType));
			
			for(Iterator<ServiceReference<EventBusTranslator>> it = refs.iterator();it.hasNext();) {
				try {
					ServiceReference<EventBusTranslator> ref = it.next();
					translator = this.bundleContext.getService(ref);
					if(translator!=null)
							break;
				}catch(NullPointerException | IllegalStateException e ) {
					continue;
				}
			}
			if(translator != null && translator.handle(event.getClass())) {
				EventBusRequestDefinition def = translator.translate(event);
				DefaultEventBusResponseDefinition resp = new DefaultEventBusResponseDefinition();
				EventBusAccess access = new EventBusAccess(new EventBusAccessRequest(this.mediator,def), resp);
				access.proceed();
				return translator.translate(resp);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return null;
	}
}
