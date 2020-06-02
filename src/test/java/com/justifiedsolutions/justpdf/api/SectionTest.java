/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SectionTest {

    private static final String SECTION_NUMBER = "1";
    private final Paragraph title = new Paragraph("title");
    private Section section;

    @BeforeEach
    public void setUp() {
        section = new Section(SECTION_NUMBER, title);
    }

    @Test
    public void constructor() {
        assertEquals(SECTION_NUMBER, section.getSectionNumber());
        assertEquals(title, section.getTitle());
    }

    @Test
    public void setStartsNewPage() {
        section.setStartsNewPage(true);
        assertTrue(section.isStartsNewPage());
        section.setStartsNewPage(false);
        assertFalse(section.isStartsNewPage());
    }

    @Test
    public void setDisplaySectionNumber() {
        section.setDisplaySectionNumber(true);
        assertTrue(section.isDisplaySectionNumber());
        section.setDisplaySectionNumber(false);
        assertFalse(section.isDisplaySectionNumber());
    }

    @Test
    public void getDisplayTextHideSection() {
        section.setDisplaySectionNumber(false);
        assertEquals(section.getTitle(), section.getDisplayTitle());
    }

    @Test
    public void addContent() {
        Phrase phrase = new Phrase("test");
        section.addContent(phrase);
        assertEquals(phrase, section.getContent().get(0));
    }

    @Test
    public void addSection() {
        Paragraph t2 = new Paragraph("t2");
        Section s1 = section.addSection(t2);
        assertEquals("1.1", s1.getSectionNumber());
        assertEquals(t2, s1.getTitle());
        assertEquals(s1, section.getSections().get(0));
    }

    @Test
    public void testEqualsHashCode() {
        assertEquals(section, section);
        assertEquals(section.hashCode(), section.hashCode());

        assertNotEquals(section, null);
        assertNotEquals(section, Boolean.TRUE);

        Section s1 = new Section(SECTION_NUMBER, title);
        assertEquals(section, s1);
        assertEquals(section.hashCode(), s1.hashCode());

        Section s2 = new Section("2", title);
        assertNotEquals(section, s2);

        Section s3 = new Section(SECTION_NUMBER, new Paragraph("foo"));
        assertNotEquals(section, s3);

        Section s4 = new Section(SECTION_NUMBER, title);
        s4.setStartsNewPage(!section.isStartsNewPage());
        assertNotEquals(section, s4);

        Section s5 = new Section(SECTION_NUMBER, title);
        s5.setDisplaySectionNumber(!section.isDisplaySectionNumber());
        assertNotEquals(section, s5);

        Section s6 = new Section(SECTION_NUMBER, title);
        s6.addContent(title);
        assertNotEquals(section, s6);

        Section s7 = new Section(SECTION_NUMBER, title);
        s7.addSection(title);
        assertNotEquals(section, s7);
    }
}