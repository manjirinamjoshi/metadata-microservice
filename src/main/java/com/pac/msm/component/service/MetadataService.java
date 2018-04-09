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
	
	public List<Metadata> getAllMetadataByDbId(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "dbid") String dbid, 
			@JsonRpcParam(value = "type") String type) throws PacException;

	public ResponseEntity<Metadata> saveMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "metadata") Metadata metadata) throws PacException;
	
	public List<Metadata> searchByName(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "dbid") String dbid,
			@JsonRpcParam(value = "type") String type,
			@JsonRpcParam(value = "name") String name) throws PacException;
	
	public ResponseEntity<Metadata> deleteMetadata(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "key") Key key) throws PacException;
	
	public ResponseEntity<Metadata> saveMetadataAndLinkPerformers(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "metadata") Metadata metadata,
			@JsonRpcParam(value = "performers") List<String> performers) throws PacException;
	
	public ResponseEntity<Metadata> linkPerformers(
			@JsonRpcParam(value = "requestContext") RequestContext requestContext,
			@JsonRpcParam(value = "dbid") String dbid,
			@JsonRpcParam(value = "id") String id,
			@JsonRpcParam(value = "performers") List<String> performers) throws PacException;
}
