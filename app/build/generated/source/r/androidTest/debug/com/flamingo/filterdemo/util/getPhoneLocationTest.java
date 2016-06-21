package com.flamingo.filterdemo.util;

import junit.framework.TestCase;

/**
 * Created by Dontouch on 16/6/20.
 */
public class getPhoneLocationTest extends TestCase {

    public void testCalcPhoneCity() throws Exception {

        assertEquals("广东",getPhoneLocation.calcPhoneCity("13189075235"));

    }
}