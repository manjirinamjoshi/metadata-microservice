package com.pac.msm.component.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.msm.component.domain.Metadata;

@JsonRpcService("/msm/metadatakeys")
public interface MetadataKeysService {
	
	public ResponseEntity<Metadata> upsertMetadataKey(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "metadataKey") Metadata metadataKey) throws PacException;
	
	public ResponseEntity<Metadata> upsertMetadataKeys(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "keyType") String keyType,
			@JsonRpcParam(value = "metadataKeys") List<Metadata> metadataKeys) throws PacException;
	
	public List<Metadata> getMetadataKeys(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "keyType") String keyType, 
			@JsonRpcParam(value = "limit") int limit) throws PacException;

}
