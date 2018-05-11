package com.github.johnsonmoon.fastboot.core.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by johnsonmoon at 2018/5/11 14:06.
 */
public class CollectionUtilsTest {
    @Test
    public void isEmptyTest() {
        Assert.assertTrue(CollectionUtils.isCollectionEmpty(null));
        Assert.assertTrue(CollectionUtils.isCollectionEmpty(new ArrayList<>()));
        Assert.assertTrue(CollectionUtils.isCollectionEmpty(new HashSet<>()));
        Assert.assertFalse(CollectionUtils.isCollectionEmpty(Collections.singletonList("abc")));
        Assert.assertTrue(CollectionUtils.isMapEmpty(null));
        Assert.assertTrue(CollectionUtils.isMapEmpty(new HashMap()));
        Assert.assertFalse(CollectionUtils.isMapEmpty(Collections.singletonMap("abc", "def")));
    }
}
