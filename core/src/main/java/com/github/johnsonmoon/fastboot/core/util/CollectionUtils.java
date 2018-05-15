package com.github.johnsonmoon.fastboot.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by johnsonmoon at 2018/5/11 13:50.
 */
public class CollectionUtils {
	/**
	 * Check whether given object (extends java.util.Collection) is null or empty.
	 *
	 * @param collection collection object
	 * @return true/false
	 */
	public static boolean isCollectionEmpty(Collection<?> collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * Check whether given object (extends java.util.Map) is null or empty.
	 *
	 * @param map map object
	 * @return true/false
	 */
	public static boolean isMapEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
}
