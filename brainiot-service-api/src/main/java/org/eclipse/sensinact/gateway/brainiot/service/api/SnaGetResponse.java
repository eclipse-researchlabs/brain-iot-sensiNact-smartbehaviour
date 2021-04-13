package org.eclipse.sensinact.gateway.brainiot.service.api;

public class SnaGetResponse extends SnaEvent implements EventBusResponseEvent{
	// fields must be public to respect DTO
	// https://osgi.org/specification/osgi.core/7.0.0/framework.dto.html
	public String provider;
	public String service;
	public String resource;	
	
	public long timestamp;	
	public String value;	
	public String type;	
}
