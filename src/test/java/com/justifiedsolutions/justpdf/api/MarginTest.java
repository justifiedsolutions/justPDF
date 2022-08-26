/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MarginTest {

    private final Margin margin = new Margin(72, 72, 72, 72);

    @Test
    void getDimensions() {
        assertEquals(72, margin.getTop());
        assertEquals(72, margin.getBottom());
        assertEquals(72, margin.getLeft());
        assertEquals(72, margin.getRight());
    }

    @Test
    void testEquals() {
        assertEquals(margin, margin);
        assertEquals(margin.hashCode(), margin.hashCode());

        assertNotEquals(margin, null);
        assertNotEquals(margin, Boolean.TRUE);

        Margin m1 = new Margin(72, 72, 72, 72);
        assertEquals(margin, m1);
        assertEquals(margin.hashCode(), m1.hashCode());

        Margin m2 = new Margin(0, 72, 72, 72);
        assertNotEquals(margin, m2);
        Margin m3 = new Margin(72, 0, 72, 72);
        assertNotEquals(margin, m3);
        Margin m4 = new Margin(72, 72, 0, 72);
        assertNotEquals(margin, m4);
        Margin m5 = new Margin(72, 72, 72, 0);
        assertNotEquals(margin, m5);
    }
}