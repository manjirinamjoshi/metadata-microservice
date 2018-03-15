package com.pac.msm.component.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.pac.msm.component.domain.Metadata;

public interface MetadataRepository extends CassandraRepository<Metadata> {//CrudRepository<Metadata, Key> {
	
	@Query("SELECT * FROM codes WHERE dbid='-1' and type=?0 LIMIT ?1")
	List<Metadata> findMetadataKeysByType(final String type, Integer limit);

}
