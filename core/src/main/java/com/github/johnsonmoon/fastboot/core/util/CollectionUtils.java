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

	/**
	 * Convert type collection into type array.
	 *
	 * @param tCollection type collection
	 * @param <T>         type parameter
	 * @return type array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> tCollection) {
		if (tCollection == null || tCollection.isEmpty()) {
			return null;
		}
		return (T[]) tCollection.toArray();
	}
}
