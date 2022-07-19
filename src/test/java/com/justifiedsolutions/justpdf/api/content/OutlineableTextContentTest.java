/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OutlineableTextContentTest {

    private OutlineableTextContent content;

    @Test
    public void setFont() {
        assertNull(content.getFont());
        PDFFont font = new PDFFont();
        content.setFont(font);
        assertEquals(font, content.getFont());
    }

    @Test
    public void setHyphenate() {
        assertTrue(content.isHyphenate());
        content.setHyphenate(false);
        assertFalse(content.isHyphenate());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        assertTrue(content.equals(content));
        assertFalse(content.equals(null));
        assertFalse(content.equals(Boolean.TRUE));

        OutlineableTextContent otc1 = new OutlineableTextContent(content);
        assertTrue(content.equals(otc1));
        assertEquals(content.hashCode(), otc1.hashCode());

        OutlineableTextContent otc2 = new OutlineableTextContent(content);
        otc2.setOutlineText("foo");
        assertFalse(content.equals(otc2));

        OutlineableTextContent otc3 = new OutlineableTextContent(content);
        otc3.setFont(new PDFFont());
        assertFalse(content.equals(otc3));

        OutlineableTextContent otc4 = new OutlineableTextContent(content);
        otc4.setHyphenate(false);
        assertFalse(content.equals(otc4));

    }

    @BeforeEach
    public void setUp() {
        content = new OutlineableTextContent();
    }
}