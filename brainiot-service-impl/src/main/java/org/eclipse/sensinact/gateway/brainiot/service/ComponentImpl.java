package org.eclipse.sensinact.gateway.brainiot.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JSONRequired;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ComponentImpl.class)
@JaxrsResource
@JSONRequired
@Designate(ocd = ComponentImpl.Config.class)
public class ComponentImpl {

	private static final Logger LOG = LoggerFactory.getLogger(ComponentImpl.class);

	private volatile Config configuration;
		
	@ObjectClassDefinition(name = "Gateway Configuration", description = "Defines the gateway infos (only port, for now)")
	public @interface Config {
		@AttributeDefinition(name = "Port", description = "Gateway port")
		int gateway_port() default 8080;
	}
	
	@Activate
	@Modified
	protected void activate(final Config config) {
		LOG.info("Default Component ACTIVATED or MODIFIED. Gateway port=" + config.gateway_port());
		this.configuration = config;
	}

	@GET
	@Path("/config")
	public String getConfiguration() {
		LOG.info("Configuration requested");
		return "<h1>Config</h1><p>port :" + configuration.gateway_port()+"</p>";
	}
		
	@GET
	@Path("hello")
	public String hello() {
		LOG.info("HELLO ENDPOINT");
		return "hello world";
	}

	@GET
	@Path("json")
	@Produces(APPLICATION_JSON)
	public Element json() {
		LOG.info("JSON ENDPOINT");
		return new Element(1, "hello world");
	}
}
