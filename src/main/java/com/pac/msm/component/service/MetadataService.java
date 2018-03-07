package com.pac.msm.component.service;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.msm.component.domain.Key;
import com.pac.msm.component.domain.Metadata;

@JsonRpcService("/msm/metadata")
public interface MetadataService {
	/**
     * 
     * @return - Event metadata
     */
	@JsonRpcMethod("getEventMetadata")
	public Metadata getEventMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "key") Key key) throws PacException;

	public void insertEventMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "metadata") Metadata metadata) throws PacException;

}
