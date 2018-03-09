package com.pac.msm.component.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.msm.component.domain.Key;
import com.pac.msm.component.domain.Metadata;

@JsonRpcService("/msm/metadata")
public interface MetadataService {
	
	public ResponseEntity<Metadata> getMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "key") Key key) throws PacException;

	public void insertMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "metadata") Metadata metadata) throws PacException;
	
	public List<Metadata> searchByName(@JsonRpcParam(value = "requestContext") RequestContext requestContext, 
			@JsonRpcParam(value = "type") String type, @JsonRpcParam(value = "name") String name) throws PacException;

}
