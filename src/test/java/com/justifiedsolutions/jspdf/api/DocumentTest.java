/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api;

import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.api.content.Paragraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {

    private Document document;

    @BeforeEach
    public void setup() {
        document = new Document(PageSize.LETTER, new Margin(10, 10, 10, 10));
    }

    @Test
    public void setMetadataAddAndRemove() {
        String expected = "text";
        Metadata metadata = Metadata.TITLE;
        document.setMetadata(metadata, expected);
        assertEquals(1, document.getMetadata().size());
        assertEquals(expected, document.getMetadata(Metadata.TITLE));
        document.setMetadata(metadata, null);
        assertEquals(0, document.getMetadata().size());
        assertNull(document.getMetadata(metadata));
    }

    @Test
    public void setMetadataNPE() {
        assertThrows(NullPointerException.class, () -> document.setMetadata(null, ""));
    }

    @Test
    public void getMetadataNPE() {
        assertThrows(NullPointerException.class, () -> document.getMetadata(null));
    }

    @Test
    public void addSectionAlreadyContent() throws DocumentException {
        document.add(new Chunk());
        assertEquals(1, document.getContent().size());

        Paragraph title = new Paragraph(new Chunk());
        assertThrows(DocumentException.class, () -> document.createSection(title));
    }

    @Test
    public void addContentAlreadySection() throws DocumentException {
        Section section = document.createSection(new Paragraph(new Chunk()));
        assertEquals(1, document.getSections().size());
        assertEquals(section, document.getSections().get(0));
        assertThrows(DocumentException.class, () -> document.add(new Chunk()));
    }

    @Test
    public void getPageSize() {
        assertEquals(PageSize.LETTER, document.getPageSize());
    }

    @Test
    public void getMargin() {
        Margin expected = new Margin(10, 10, 10, 10);
        assertEquals(expected, document.getMargin());
    }

    @Test
    public void setHeader() {
        Header header = Mockito.mock(Header.class);
        document.setHeader(header);
        assertSame(header, document.getHeader());
    }

    @Test
    public void setFooter() {
        Footer footer = Mockito.mock(Footer.class);
        document.setFooter(footer);
        assertSame(footer, document.getFooter());
    }
}