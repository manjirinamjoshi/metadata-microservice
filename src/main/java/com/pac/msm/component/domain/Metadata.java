package com.pac.msm.component.domain;

import java.util.Map;

import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Table(value = "codes")
@Document(indexName="jam.cds", type="cds", createIndex=false)
public class Metadata {
	
	@PrimaryKey
	private Key key;
	
	private Map<String, String> name;
	
	@Field(type = FieldType.Object, ignoreFields = {"parameters"})
	private Map<String, String> parameters;
	
	@org.springframework.data.annotation.Transient
	@GeoPointField
	private GeoPoint location;
	
	@CassandraType(type = com.datastax.driver.core.DataType.Name.UDT, userTypeName = "name")
	private Name z_lastupdatedusername;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
	}

	public Name getZ_lastupdatedusername() {
		return z_lastupdatedusername;
	}

	public void setZ_lastupdatedusername(
			Name z_lastupdatedusername) {
		this.z_lastupdatedusername = z_lastupdatedusername;
	}

	
	
}
