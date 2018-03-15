package com.pac.msm.component.service.impl;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.msm.component.domain.KeyType;
import com.pac.msm.component.domain.Metadata;
import com.pac.msm.component.repository.MetadataRepository;
import com.pac.msm.component.service.MetadataKeysService;

@Service
@AutoJsonRpcServiceImpl
public class MetadataKeysServiceImpl implements MetadataKeysService {

	public final static String SUBTYPE = "METADATA";
	
	private final MetadataRepository metadataRepository;
	private final ElasticsearchRepository elasticSearchMetadataRespository;
	
	public MetadataKeysServiceImpl(MetadataRepository metadataRepository, ElasticsearchRepository elasticSearchMetadataRespository) {
        this.metadataRepository = metadataRepository;
        this.elasticSearchMetadataRespository = elasticSearchMetadataRespository;
    }
	
	@Override
	public ResponseEntity<Metadata> upsertMetadataKey(
			RequestContext requestContext, Metadata metadataKey)
			throws PacException {
		if(EnumUtils.isValidEnum(KeyType.class, metadataKey.getKey().getType())) {
			metadataKey.getKey().setDbid("-1");
			metadataKey.getKey().setSubtype(SUBTYPE);
			metadataRepository.insert(metadataKey);
			elasticSearchMetadataRespository.save(metadataKey);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		com.pac.msm.component.domain.Error error = new com.pac.msm.component.domain.Error("INVALID_TYPE",123,"Type is invalid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Metadata) error);
	}

	@Override
	public ResponseEntity<Metadata> upsertMetadataKeys(
			RequestContext requestContext, String keyType, List<Metadata> metadataKeys)
			throws PacException {
		if(EnumUtils.isValidEnum(KeyType.class, keyType)) {
			if(metadataKeys!=null && !metadataKeys.isEmpty()) {
				for (Metadata metadataKey : metadataKeys) {
					metadataKey.getKey().setDbid("-1");
					metadataKey.getKey().setType(keyType);
					metadataKey.getKey().setSubtype(SUBTYPE);
					metadataRepository.insert(metadataKey);
					elasticSearchMetadataRespository.save(metadataKey);
				}
			}
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		com.pac.msm.component.domain.Error error = new com.pac.msm.component.domain.Error("INVALID_TYPE",123,"Type is invalid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Metadata) error);
	}

	@Override
	public List<Metadata> getMetadataKeys(
			RequestContext requestContext, String type) throws PacException {
		final NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery());

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.matchQuery("dbid", "-1"));
		boolQueryBuilder.must(QueryBuilders.matchQuery("key.type", type));
		boolQueryBuilder.must(QueryBuilders.matchQuery("key.subtype", SUBTYPE));
		nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
		NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
		Page<Metadata> search = elasticSearchMetadataRespository.search(searchQuery);
		return search.getContent();
	}

}
