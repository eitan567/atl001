package com.xaviar.market.rest.item;

import grails.validation.Validateable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Validateable
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataHolder {

	public Map<String, String> map = new HashMap<String, String>();

	public boolean validate() {
		return true;
	}

	public Map<String, String> getMap() {
		return this.map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void put(String key, String value) {
		map.put(key, value);
	}

	public void add(HashMap<String, String> map) {
		Set<Map.Entry<String, String>> entries = map.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			map.put(entry.getKey(), entry.getValue());
		}
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public String get(String key) {
		return map.get(key);
	}
}