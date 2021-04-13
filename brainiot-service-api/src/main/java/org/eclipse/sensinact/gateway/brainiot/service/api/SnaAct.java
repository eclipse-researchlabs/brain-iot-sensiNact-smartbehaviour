package org.eclipse.sensinact.gateway.brainiot.service.api;

import java.util.List;

public class SnaAct extends SnaEvent implements EventBusRequestEvent {
	// fields must be public to respect DTO
	// https://osgi.org/specification/osgi.core/7.0.0/framework.dto.html
	public String provider;
	public String service;
	public String resource;	
	
	public List<SnaRequestParameter> parameters;
}
