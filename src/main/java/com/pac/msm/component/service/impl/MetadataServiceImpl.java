package com.pac.msm.component.service.impl;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.lib.core.util.TextUtils;
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
	public ResponseEntity<Metadata> getMetadata(RequestContext requestContext, Key key) throws PacException {
		if(EnumUtils.isValidEnum(Type.class, key.getType())) {
			MapId primaryKey = BasicMapId.id("dbid", key.getDbid()).with("type", key.getType()).with("code", key.getCode());
			Metadata findOne = metadataRepository.findOne(primaryKey);
			return ResponseEntity.status(HttpStatus.OK).body(findOne);
		}
		com.pac.msm.component.domain.Error error = new com.pac.msm.component.domain.Error("INVALID_TYPE",123,"Type is invalid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Metadata) error);
	}
	
	@Override
	public List<Metadata> searchByName(RequestContext requestContext, String type, String name) throws PacException {
		SearchQuery searchQuery;
		if(TextUtils.isNotNullNotEmpty(type)) {
			searchQuery = new NativeSearchQueryBuilder()
			  .withFields("type", type)
			  .withFilter(QueryBuilders.matchPhrasePrefixQuery("name", name))
			  .build();
		} else {
			searchQuery = new NativeSearchQueryBuilder()
			  .withFilter(QueryBuilders.matchPhrasePrefixQuery("name", name))
			  .build();
		}
		
		Page<Metadata> search = elasticSearchMetadataRespository.search(searchQuery);
		return search.getContent();
	}

	@Override
	public void insertMetadata(RequestContext requestContext,
			Metadata metadata) throws PacException {
		metadata.getKey().setType(Type.EVENT.toString());
		metadataRepository.insert(metadata);
		elasticSearchMetadataRespository.save(metadata);
	}

}
