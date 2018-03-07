package com.pac.msm.component.service.impl;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.msm.component.domain.Key;
import com.pac.msm.component.domain.Metadata;
import com.pac.msm.component.domain.Type;
import com.pac.msm.component.repository.MetadataRepository;
import com.pac.msm.component.service.MetadataService;

@Service
@AutoJsonRpcServiceImpl
public class MetadataServiceImpl implements MetadataService {
	
	private final MetadataRepository metadataRepository;
	private final ElasticsearchRepository elasticSearchMetadataRespository;
	
	public MetadataServiceImpl(MetadataRepository metadataRepository, ElasticsearchRepository elasticSearchMetadataRespository) {
        this.metadataRepository = metadataRepository;
        this.elasticSearchMetadataRespository = elasticSearchMetadataRespository;
    }
	
	@Override
	public Metadata getEventMetadata(RequestContext requestContext, Key key) throws PacException {
		MapId primaryKey = BasicMapId.id("dbid", key.getDbid()).with("type", Type.EVENT.toString()).with("code", key.getCode());
		return (Metadata) metadataRepository.findOne(primaryKey);
	}

	@Override
	public void insertEventMetadata(RequestContext requestContext,
			Metadata metadata) throws PacException {
		metadata.getKey().setType(Type.EVENT.toString());
		metadataRepository.save(metadata);
		elasticSearchMetadataRespository.save(metadata);
	}

}
