package com.pac.msm.component.repository.search;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;

import com.pac.msm.component.domain.Key;
import com.pac.msm.component.domain.Metadata;

public class HashKeyedRepository<T, IKey extends Key> extends AbstractElasticsearchRepository<T, IKey> {

    public HashKeyedRepository() {
        super();
    }

    public HashKeyedRepository(ElasticsearchEntityInformation<T, IKey> metadata,
                                 ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
    }

    public HashKeyedRepository(ElasticsearchOperations elasticsearchOperations) {
        super(elasticsearchOperations);
    }

    @Override
    protected String stringIdRepresentation(IKey id) {
        return String.valueOf(id.hashCode());
    }

	@Override
	protected IKey extractIdFromBean(T entity) {
		Metadata indexedMetadata = (Metadata)entity;
		return (IKey) indexedMetadata.getKey();
	}
    
    
}