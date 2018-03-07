package com.pac.msm.component.service;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.msm.component.domain.EventMetadata;

@JsonRpcService("/eventmetadata")
public interface EventService {
	/**
     * 
     * @return - Event metadata
     */
	@JsonRpcMethod("getEventMetadata")
	public EventMetadata getEventMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "key") String key) throws PacException;

	public void insertEventMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "eventMetadata") EventMetadata eventMetadata) throws PacException;
	
	public int multiplier(@JsonRpcParam(value = "a") int a, @JsonRpcParam(value = "b") int b);

}
