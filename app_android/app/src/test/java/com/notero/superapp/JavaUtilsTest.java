package com.notero.superapp;

import org.junit.Assert;
import org.junit.Test;

public class JavaUtilsTest {
    @Test
    public void add_returnsCorrectSum() {
        int result = JavaUtils.add(2, 3);
        Assert.assertEquals(5, result);
    }
}
