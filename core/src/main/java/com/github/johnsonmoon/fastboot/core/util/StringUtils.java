package com.github.johnsonmoon.fastboot.core.util;

import java.util.List;

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

	/**
	 * Combine string list into a single string with comma.
	 *
	 * @param strings string list
	 * @param symbol  combine symbol
	 * @return combined single string
	 */
	public static String combineWithSymbol(List<String> strings, String symbol) {
		if (strings == null || strings.isEmpty()
				|| symbol == null || symbol.isEmpty()) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (String s : strings) {
			stringBuilder.append(s);
			stringBuilder.append(symbol);
		}
		if (stringBuilder.length() == 0) {
			return "";
		}
		return stringBuilder.substring(0, stringBuilder.length() - symbol.length());
	}
}
