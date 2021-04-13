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

import java.io.IOException;
import java.util.List;

import org.eclipse.sensinact.gateway.brainiot.service.bus.definition.DefaultEventBusResponseDefinition;
import org.eclipse.sensinact.gateway.core.method.AccessMethodResponse;
import org.eclipse.sensinact.gateway.core.method.legacy.DescribeResponse;
import org.eclipse.sensinact.gateway.core.security.Authentication;
import org.eclipse.sensinact.gateway.core.security.AuthenticationToken;
import org.eclipse.sensinact.gateway.core.security.InvalidCredentialException;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundAccess;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundEndpoint;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundMediator;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundRequest;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundRequestBuilder;
import org.eclipse.sensinact.gateway.nthbnd.endpoint.format.JSONResponseFormat;
import org.json.JSONObject;

/**
 * Extended {@link NorthboundAccess} dedicated to EventBus access request processing
 *
 * @author <a href="mailto:cmunilla@cmssi.fr">Christophe Munilla</a>
 */
public class EventBusAccess extends NorthboundAccess<EventBusAccessRequest> {
	
    protected NorthboundEndpoint endpoint;
	private DefaultEventBusResponseDefinition response;

    /**
     * Constructor
     *
     * @param request the {@link EventBusAccessRequest} that will be treated by the EventBusAccess to be instantiated
     * @param resp the {@link DefaultEventBusResponseDefinition} that will be used by the EventBusAccess to be 
     * instantiated to store the response data 
     * 
     * @throws IOException
     * @throws InvalidCredentialException
     */
    public EventBusAccess(EventBusAccessRequest request, DefaultEventBusResponseDefinition resp) throws IOException, InvalidCredentialException {
        super(request);
        this.response = resp;
        this.response.setPath(request.getRequestURI());
        
        Authentication<?> authentication = request.getAuthentication();
        if (authentication == null) {
            this.endpoint = request.getMediator().getNorthboundEndpoints().getEndpoint();
            resp.setToken(this.endpoint.getSessionToken());
        } else if (AuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            this.endpoint = request.getMediator().getNorthboundEndpoints().getEndpoint((AuthenticationToken) authentication);
        } else {
            throw new InvalidCredentialException("Authentication token was expected");
        }
    }

    /*
     * (non-javadoc)
     * 
     * @see org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundAccess#
     * respond(org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundMediator, org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundRequestBuilder)
     */
    @Override
    protected boolean respond(NorthboundMediator mediator, NorthboundRequestBuilder builder) throws IOException {

        NorthboundRequest nthbndRequest = builder.build();
        if (nthbndRequest == null) {
            this.sendError(500, "Internal server error");
            return false;
        }
        AccessMethodResponse<?> cap = this.endpoint.execute(nthbndRequest);
    	
    	String resultStr = null;
        List<String> rawList = super.request.getQueryMap().get("rawDescribe");

        if (rawList != null && (rawList.contains("true") 
        		|| rawList.contains("True") 
        		|| rawList.contains("yes") 
        		|| rawList.contains("Yes")) 
        	&& DescribeResponse.class.isAssignableFrom(cap.getClass())) {
            resultStr = ((DescribeResponse<?>) cap).getJSON(true);
        } else {
            resultStr = cap.getJSON();
        }
        JSONObject result = new JSONResponseFormat(mediator).format(resultStr);
        if (result == null) {
            this.sendError(500, "Internal server error");
            return false;
        }
        this.response.setType(builder.getMethod());
        this.response.setStatus(result.getInt("statusCode"));
        JSONObject response = result.optJSONObject("response");
        this.response.setContent(response==null?null:response.toString());
        return true;
    }

    /*
     * (non-javadoc)
     * 
     * @see org.eclipse.sensinact.gateway.nthbnd.endpoint.NorthboundAccess#sendError(int, java.lang.String)
     */
    @Override
    protected void sendError(int i, String message) throws IOException {
    	this.response.setStatus(i);
    	this.response.setContent(message);
    }
}
