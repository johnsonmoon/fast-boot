package com.github.johnsonmoon.fastboot.core.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by johnsonmoon at 2018/5/11 13:53.
 */
public class StringUtilsCheck {
	@Test
	public void containsEmptyTest() {
		String[] strings = null;
		Assert.assertTrue(StringUtils.containsEmpty(strings));
		Assert.assertTrue(StringUtils.containsEmpty(""));
		Assert.assertFalse(StringUtils.containsEmpty("abc"));
		Assert.assertTrue(StringUtils.containsEmpty("abc", null));
		Assert.assertTrue(StringUtils.containsEmpty("abc", ""));
		Assert.assertFalse(StringUtils.containsEmpty("abc", "def"));
	}

	@Test
	public void combineStringWithSymbol() {
		System.out.println(StringUtils.combineWithSymbol(Arrays.asList("abc", "def", "ghi"), ","));
		System.out.println(StringUtils.combineWithSymbol(Arrays.asList("abc", "def", "ghi"), "||"));
	}
}
