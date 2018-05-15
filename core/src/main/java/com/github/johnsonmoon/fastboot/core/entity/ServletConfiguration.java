package com.github.johnsonmoon.fastboot.core.entity;

import java.util.Map;

/**
 * Create by johnsonmoon at 2018/5/13 10:38.
 */
public class ServletConfiguration {
	private String path;
	private String name;
	private String className;
	private Map<String, String> initParameters;

	public ServletConfiguration() {
	}

	public ServletConfiguration(String path, String name, String className, Map<String, String> initParameters) {
		this.path = path;
		this.name = name;
		this.className = className;
		this.initParameters = initParameters;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClass(Class<?> clazz) {
		this.className = clazz.getName();
	}

	public Map<String, String> getInitParameters() {
		return initParameters;
	}

	public void setInitParameters(Map<String, String> initParameters) {
		this.initParameters = initParameters;
	}

	@Override
	public String toString() {
		return "ServletConfiguration{" +
				"path='" + path + '\'' +
				", name='" + name + '\'' +
				", className='" + className + '\'' +
				", initParameters=" + initParameters +
				'}';
	}
}
