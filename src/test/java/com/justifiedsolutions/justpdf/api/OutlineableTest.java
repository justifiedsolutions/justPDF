/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OutlineableTest {

    @Test
    public void setOutlineText() {
        String text = "fubar";
        Outlineable outlineable = new Outlineable();
        outlineable.setOutlineText(text);
        assertEquals(text, outlineable.getOutlineText());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Outlineable o1 = new Outlineable();
        Outlineable o2 = new Outlineable(o1);

        assertTrue(o1.equals(o1));
        assertFalse(o1.equals(null));
        assertFalse(o1.equals(Boolean.TRUE));

        assertTrue(o1.equals(o2));
        assertEquals(o1.hashCode(), o2.hashCode());
    }
}