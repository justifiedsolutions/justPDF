/*
 * Copyright 2022 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageBreakTest {

    @Test
    public void testConstructor() {
        PageBreak pb = new PageBreak();
        assertNotNull(pb);
        assertTrue(pb instanceof Content);
    }
}