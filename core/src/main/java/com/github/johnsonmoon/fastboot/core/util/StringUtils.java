package com.github.johnsonmoon.fastboot.core.util;

/**
 * Created by johnsonmoon at 2018/5/11 13:50.
 */
public class StringUtils {
	/**
	 * Check string array contains null or empty string.
	 *
	 * @param check string array to be checked
	 * @return true/false
	 */
	public static boolean containsEmpty(String... check) {
		if (check == null) {
			return true;
		}
		for (String s : check) {
			if (s == null || s.isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
