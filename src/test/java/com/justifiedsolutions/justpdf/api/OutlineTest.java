/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import com.justifiedsolutions.justpdf.api.content.Phrase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OutlineTest {

    @Test
    public void add() {
        Outline outline = new Outline();
        Phrase content = new Phrase();
        Outline.Entry entry = outline.createEntry(content);

        assertEquals(1, outline.getEntries().size());
        assertEquals(entry, outline.getEntries().get(0));
    }

    @Test
    public void entryGetters() {
        Outline outline = new Outline();
        Phrase content = new Phrase();
        Outline.Entry entry = outline.createEntry(content);

        assertEquals(content.getOutlineId(), entry.getOutlineId());
    }

    @Test
    public void entryCreate() {
        Outline outline = new Outline();
        Phrase content = new Phrase();
        Outline.Entry e1 = outline.createEntry(content);
        Outline.Entry e2 = e1.createEntry(content);

        assertEquals(1, e1.getEntries().size());
        assertEquals(e2, e1.getEntries().get(0));
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Outline outline = new Outline();
        Phrase content = new Phrase();
        Outline.Entry e1 = outline.createEntry(content);

        assertTrue(e1.equals(e1));
        assertFalse(e1.equals(null));
        assertFalse(e1.equals(Boolean.TRUE));

        Outline.Entry e2 = outline.createEntry(content);
        assertTrue(e1.equals(e2));
        assertEquals(e1.hashCode(), e2.hashCode());

        Phrase content2 = new Phrase();
        Outline.Entry e3 = outline.createEntry(content2);
        assertFalse(e1.equals(e3));

        e2.createEntry(content2);
        assertFalse(e1.equals(e2));
    }
}