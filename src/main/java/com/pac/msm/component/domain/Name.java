package com.pac.msm.component.domain;

import org.springframework.data.cassandra.mapping.UserDefinedType;

//@UserDefinedType("name") 
public class Name {
	private String first;
	private String last;
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	
}
