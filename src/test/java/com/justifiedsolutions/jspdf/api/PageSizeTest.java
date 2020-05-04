/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageSizeTest {

    @Test
    public void width() {
        assertEquals(612, PageSize.LETTER.width());
        assertEquals(612, PageSize.LEGAL.width());
        assertEquals(2384, PageSize.A0.width());
        assertEquals(1684, PageSize.A1.width());
        assertEquals(1191, PageSize.A2.width());
        assertEquals(842, PageSize.A3.width());
        assertEquals(595, PageSize.A4.width());
        assertEquals(420, PageSize.A5.width());
        assertEquals(297, PageSize.A6.width());
        assertEquals(210, PageSize.A7.width());
        assertEquals(148, PageSize.A8.width());
    }

    @Test
    public void height() {
        assertEquals(792, PageSize.LETTER.height());
        assertEquals(1008, PageSize.LEGAL.height());
        assertEquals(3370, PageSize.A0.height());
        assertEquals(2384, PageSize.A1.height());
        assertEquals(1684, PageSize.A2.height());
        assertEquals(1191, PageSize.A3.height());
        assertEquals(842, PageSize.A4.height());
        assertEquals(595, PageSize.A5.height());
        assertEquals(420, PageSize.A6.height());
        assertEquals(297, PageSize.A7.height());
        assertEquals(210, PageSize.A8.height());
    }
}