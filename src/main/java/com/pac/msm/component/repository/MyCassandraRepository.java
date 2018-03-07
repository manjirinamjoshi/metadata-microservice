package com.pac.msm.component.repository;

import org.springframework.cassandra.core.WriteOptions;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyCassandraRepository<T> extends CassandraRepository<T> {

	/**
	 * Customized save method.
	 */
	T save(T entity, WriteOptions options);
}
