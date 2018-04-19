package com.pac.msm.component.repository.search;

import java.io.Serializable;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import com.pac.msm.component.domain.Metadata;

public class CustomElasticsearchRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends ElasticsearchRepositoryFactoryBean<T, S, ID> {

    private ElasticsearchOperations operations;

    public CustomElasticsearchRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    public void setElasticsearchOperations(ElasticsearchOperations operations) {
        super.setElasticsearchOperations(operations);
        Assert.notNull(operations);
        //this only works if index already exists !
        //operations.putMapping(Metadata.class);
        this.operations = operations;
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new ElasticsearchRepositoryFactory(operations) {
            @Override
            protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
                //if (!Integer.class.isAssignableFrom(metadata.getIdType()) && !Long.class.isAssignableFrom(metadata.getIdType()) && !Double.class.isAssignableFrom(metadata.getIdType()) && metadata.getIdType() != String.class && metadata.getIdType() != UUID.class) {
                    return HashKeyedRepository.class;
                //}
                //return super.getRepositoryBaseClass(metadata);
            }
        };
    }
}
