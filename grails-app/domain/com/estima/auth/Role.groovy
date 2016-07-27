package com.estima.auth

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='name')
@ToString(includes='name', includeNames=true, includePackage=false)
class Role implements Serializable {

	private static final long serialVersionUID = 1

	String name

	Role(String name) {
		this()
		this.name = name
	}

	static constraints = {
		name blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
