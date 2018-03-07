package com.pac.msm.component.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.pac.msm.component.domain.Metadata;

public interface MetadataRepository extends CassandraRepository<Metadata> {//CrudRepository<Metadata, Key> {

}
