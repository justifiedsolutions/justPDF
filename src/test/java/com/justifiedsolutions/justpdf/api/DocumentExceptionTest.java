/*
 * Copyright 2022 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DocumentExceptionTest {

    @Test
    void testNoArgConstructor() {
        DocumentException ex = new DocumentException();
        assertNotNull(ex);
    }

    @Test
    void testStringConstructor() {
        var msg = "exception message";
        DocumentException ex = new DocumentException(msg);
        assertNotNull(ex);
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testStringThrowableConstructor() {
        var msg = "exception message";
        var thw = new RuntimeException();
        DocumentException ex = new DocumentException(msg, thw);
        assertNotNull(ex);
        assertEquals(msg, ex.getMessage());
        assertEquals(thw, ex.getCause());
    }
}