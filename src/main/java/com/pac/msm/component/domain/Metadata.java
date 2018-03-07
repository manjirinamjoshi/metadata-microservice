package com.pac.msm.component.domain;

import java.util.Map;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.elasticsearch.annotations.Document;

@Table(value = "metadata")
@Document(indexName="metadata_name", type="default", createIndex=false)
public class Metadata {
	
	@PrimaryKey
	private Key key;
	
	private String name;
	
	private Map<String, String> metadata;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	
}
