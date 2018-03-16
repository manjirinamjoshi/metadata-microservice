package com.pac.msm.component.service.impl;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
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
	public ResponseEntity<Metadata> saveMetadataKey(
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
	public ResponseEntity<Metadata> saveMetadataKeys(
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
			RequestContext requestContext, String keyType, int limit) throws PacException {
		return metadataRepository.findMetadataKeysByType(keyType, limit);
	}

}
