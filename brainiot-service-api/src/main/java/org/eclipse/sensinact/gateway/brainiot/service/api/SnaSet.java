package org.eclipse.sensinact.gateway.brainiot.service.api;

public class SnaSet extends SnaEvent implements EventBusRequestEvent {
	// fields must be public to respect DTO
	// https://osgi.org/specification/osgi.core/7.0.0/framework.dto.html
	public String provider;
	public String service;
	public String resource;	
	
	// TODO Since DTO must only use a limited selection of field types
	// we need to discuss how to handle this
	// Is it interresting to create an event per type? SnaStringSet, SnaIntSet,...
	public String value;
	public String type;	
	public long timestamp;
}
