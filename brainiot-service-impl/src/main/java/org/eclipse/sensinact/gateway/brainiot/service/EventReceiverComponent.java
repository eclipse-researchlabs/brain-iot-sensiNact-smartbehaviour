package org.eclipse.sensinact.gateway.brainiot.service;

import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusRequestEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.EventBusResponseEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaAct;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaEvent;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaGet;
import org.eclipse.sensinact.gateway.brainiot.service.api.SnaSet;
import org.eclipse.sensinact.gateway.brainiot.service.bus.EventBusRequestHandlerComponent;
import org.eclipse.sensinact.gateway.common.bundle.Mediator;
import org.eclipse.sensinact.gateway.common.execution.Executable;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.EventBus;
import eu.brain.iot.eventing.api.SmartBehaviour;


@Component(immediate=true,service=SmartBehaviour.class)
@SmartBehaviourDefinition(consumed = {SnaGet.class, SnaSet.class, SnaAct.class}, filter="(timestamp=*)", name="sensiNact Requests Listener")
public class EventReceiverComponent implements SmartBehaviour<SnaEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(EventReceiverComponent.class);

	@Reference
	EventBusRequestHandlerComponent eventBusRequestHandlerComponent;

	private Mediator mediator;

	@Activate
	public void activate(ComponentContext componentContext) {
		LOG.info("EventReceiverComponent ACTIVATED");
		this.mediator = new Mediator(componentContext.getBundleContext());
	}

	@Override
	public void notify(SnaEvent event) {
		LOG.info("MesageEvent RECEIVED : {}", event);
		if(!(event instanceof EventBusRequestEvent))
			return;
		
		final EventBusResponseEvent resp = eventBusRequestHandlerComponent.handle((EventBusRequestEvent)event);
		if(resp instanceof BrainIoTEvent)
			this.mediator.callService(EventBus.class, new Executable<EventBus,Void>(){
				@Override
				public Void execute(EventBus eventBus) throws Exception {
					eventBus.deliver((BrainIoTEvent) resp);
					return null;
				}
			});
	}
}
