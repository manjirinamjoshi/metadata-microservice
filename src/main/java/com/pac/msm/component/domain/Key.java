package com.pac.msm.component.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class Key implements Serializable {
	
	@PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String dbid;
	
	@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private String type;
	
	@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private String subtype;
	
	@PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private String id;

	public String getDbid() {
		return dbid;
	}

	public void setDbid(String dbid) {
		this.dbid = dbid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
        .append(this.dbid.hashCode())
        .append(this.type.hashCode())
        .append(this.subtype.hashCode())
        .append(this.id.hashCode())
        .toHashCode();
	}

	
}
