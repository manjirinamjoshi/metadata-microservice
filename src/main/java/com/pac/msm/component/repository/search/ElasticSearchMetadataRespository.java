package com.pac.msm.component.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.pac.msm.component.domain.Metadata;

public interface ElasticSearchMetadataRespository extends ElasticsearchRepository<Metadata, String>{

}
