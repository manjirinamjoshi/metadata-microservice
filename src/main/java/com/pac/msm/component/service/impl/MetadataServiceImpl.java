package com.pac.msm.component.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
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
import com.pac.lib.core.json.JsonUtil;
import com.pac.lib.core.util.TextUtils;
import com.pac.msm.component.domain.Constants;
import com.pac.msm.component.domain.Key;
import com.pac.msm.component.domain.Metadata;
import com.pac.msm.component.domain.Type;
import com.pac.msm.component.repository.MetadataRepository;
import com.pac.msm.component.service.MetadataService;

@Service
@AutoJsonRpcServiceImpl
public class MetadataServiceImpl implements MetadataService {
	
	public final static String SUBTYPE = "METADATA";
	
	private final MetadataRepository metadataRepository;
	private final ElasticsearchRepository elasticSearchMetadataRespository;
	
	public MetadataServiceImpl(MetadataRepository metadataRepository, ElasticsearchRepository elasticSearchMetadataRespository) {
        this.metadataRepository = metadataRepository;
        this.elasticSearchMetadataRespository = elasticSearchMetadataRespository;
    }
	
	@Override
	public ResponseEntity<Metadata> getMetadata(RequestContext requestContext, Key key) throws PacException {
		if(EnumUtils.isValidEnum(Type.class, key.getType())) {
			MapId primaryKey = BasicMapId.id("dbid", key.getDbid()).with("type", key.getType()).with("subtype", SUBTYPE).with("id", key.getId());
			Metadata findOne = metadataRepository.findOne(primaryKey);
			return ResponseEntity.status(HttpStatus.OK).body(findOne);
		}
		com.pac.msm.component.domain.Error error = new com.pac.msm.component.domain.Error("INVALID_TYPE",123,"Type is invalid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Metadata) error);
	}
	
	@Override
	public List<Metadata> searchByName(RequestContext requestContext, String type, String name) throws PacException {
		final NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery());

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.matchQuery("key.subtype", SUBTYPE));
		boolQueryBuilder.must(QueryBuilders.matchQuery("key.type", type));
		if(TextUtils.isNotNullNotEmpty(name)) {
			boolQueryBuilder.must(QueryBuilders.wildcardQuery("name.en_US", name.toLowerCase()));
		}
		nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
		NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
		Page<Metadata> search = elasticSearchMetadataRespository.search(searchQuery);
		return search.getContent();
	}
	
	@Override
	public ResponseEntity<Metadata> saveMetadata(RequestContext requestContext, Metadata metadata) throws PacException {
		if(EnumUtils.isValidEnum(Type.class, metadata.getKey().getType())) {
			metadata.getKey().setSubtype(SUBTYPE);
			metadataRepository.insert(metadata);
			elasticSearchMetadataRespository.save(metadata);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		com.pac.msm.component.domain.Error error = new com.pac.msm.component.domain.Error("INVALID_TYPE",123,"Type is invalid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Metadata) error);
	}
	
	@Override
	public ResponseEntity<Metadata> saveMetadataAndLinkPerformers(RequestContext requestContext, Metadata metadata, List<String> performers) throws PacException {
		if(EnumUtils.isValidEnum(Type.class, metadata.getKey().getType())) {
			metadata.getKey().setSubtype(SUBTYPE);
			if(performers!=null && !performers.isEmpty()) {
				Map<String, String> parameters = metadata.getParameters();
				if(parameters == null) {
					parameters = new HashMap<String, String>();
				}
				parameters.put(Constants.PERFORMERS_KEY, JsonUtil.getInstance().convertToJSON(performers));
				metadata.setParameters(parameters);
			}
			metadataRepository.insert(metadata);
			elasticSearchMetadataRespository.save(metadata);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		com.pac.msm.component.domain.Error error = new com.pac.msm.component.domain.Error("INVALID_TYPE",123,"Type is invalid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Metadata) error);
	}
	
	public ResponseEntity<Metadata> linkPerformers(RequestContext requestContext, String dbid, String id, List<String> performers) throws PacException {
		Key key = new Key();
		key.setDbid(dbid);
		key.setType(Type.EVENT_METADATA.toString());
		key.setId(id);
		ResponseEntity<Metadata> response = getMetadata(requestContext, key);
		if(response!=null && HttpStatus.OK.equals(response.getStatusCode())) {
			Metadata metadata = response.getBody();
			if(metadata!=null) {
				return saveMetadataAndLinkPerformers(requestContext, metadata, performers);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}
		return response;
	}

}
