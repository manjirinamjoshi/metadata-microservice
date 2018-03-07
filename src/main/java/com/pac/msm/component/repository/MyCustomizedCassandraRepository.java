package com.pac.msm.component.repository;

import org.springframework.cassandra.core.WriteOptions;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.SimpleCassandraRepository;
import org.springframework.stereotype.Component;

/**
 * Custom repository base class
 * 
 * @param <T>
 */
//@Component
public class MyCustomizedCassandraRepository<T> extends SimpleCassandraRepository<T, MapId>
		implements MyCassandraRepository<T> {

	public MyCustomizedCassandraRepository(CassandraEntityInformation<T, MapId> metadata,
			CassandraOperations operations) {
		super(metadata, operations);
	}

	public T save(T entity, WriteOptions options) {

		System.out.println(String.format("Called MyCustomizedCassandraRepository.save(%s, %s)", entity, options));
		return entity;
	}
}
