package com.pac.msm.component.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.pac.msm.component.domain.EventMetadata;

public interface EventMetadataRepository extends CassandraRepository<EventMetadata> {

}
