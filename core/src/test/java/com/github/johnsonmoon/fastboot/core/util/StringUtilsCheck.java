package com.github.johnsonmoon.fastboot.core.util;

import org.junit.Assert;
import org.junit.Test;

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
}
